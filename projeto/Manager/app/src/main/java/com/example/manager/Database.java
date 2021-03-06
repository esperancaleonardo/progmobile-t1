package com.example.manager;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.database.sqlite.SQLiteAbortException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.Scanner;

public class Database extends SQLiteOpenHelper {

    private static final int DB_VERSION=1;
    private static final String DB_NAME="DB_CURSOS_ONLINE.db";
    private final Context context;
    private SQLiteDatabase db;

    public Database(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE TB_CURSO(" +
                "   curso_id                INTEGER     PRIMARY KEY AUTOINCREMENT," +
                "   curso_nome              TEXT        NOT NULL," +
                "   curso_carga_horaria     INTEGER     NOT NULL);");

        db.execSQL("CREATE TABLE TB_ALUNO(" +
                "    aluno_id                 INTEGER   PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "    aluno_nome               TEXT      NOT NULL," +
                "    aluno_email              TEXT      NOT NULL," +
                "    aluno_cpf                TEXT      NOT NULL," +
                "    aluno_telefone           TEXT      NOT NULL," +
                "    aluno_curso_id           INTEGER   REFERENCES TB_CURSO(curso_id) ON DELETE RESTRICT ON UPDATE CASCADE)");

        this.insereMockCursos(db);
        this.insereMockAlunos(db);
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //insere um mock de cursos contido em um csv dentro da pasta raw
    public void insereMockCursos(SQLiteDatabase db){
        Scanner scanner = new Scanner(this.context.getResources().openRawResource(R.raw.mock_data));
        db.beginTransaction();
        String line;
        while(scanner.hasNextLine() && ((line = scanner.nextLine()) != null)) {
            String[] values = line.split(",");
            if(values.length != 2){
                continue;
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put("curso_nome", values[0]);
            contentValues.put("curso_carga_horaria", values[1]);
            db.insert("TB_CURSO", null, contentValues);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    //insere um mock de alunos contido em um csv dentro da pasta raw
    public void insereMockAlunos(SQLiteDatabase db){
        Scanner scanner = new Scanner(this.context.getResources().openRawResource(R.raw.mock_data_alunos));
        db.beginTransaction();
        String line;
        while(scanner.hasNextLine() && ((line = scanner.nextLine()) != null)) {
            String[] values = line.split(",");
            if(values.length != 5){
                continue;
            }

            ContentValues contentValues = new ContentValues();
            contentValues.put("aluno_nome", values[0]);
            contentValues.put("aluno_email", values[1]);
            contentValues.put("aluno_cpf", values[2]);
            contentValues.put("aluno_telefone", values[3]);
            contentValues.put("aluno_curso_id", values[4]);
            db.insert("TB_ALUNO", null, contentValues);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }


    //busca todos os cadastros de alunos associados a cursos atrav??s do JOIN entre
    //as tabelas TB_CURSO e TB_ALUNO, pelo relacionamento criado pela chave
    //estrangeira. tbm cria uma lista de strings a ser utilizada em um listview
    public ArrayList<String> listaCadastros(){
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT aluno_nome, curso_nome, curso_carga_horaria FROM TB_ALUNO A INNER JOIN TB_CURSO C ON A.aluno_curso_id = C.curso_id", null);
        ArrayList<String> listaRegistros = new ArrayList<String>();
        while (cursor.moveToNext()){
            String aluno = cursor.getString(0);
            String curso = cursor.getString(1);
            String carga = cursor.getString(2);
            int carga_int = Integer.parseInt(carga);
            Resources res = context.getResources();
            listaRegistros.add(aluno + " cadastrou-se no curso: " + curso + " com carga hor??ria de "+ res.getQuantityString(R.plurals.plural_horas, carga_int, carga_int));
        }
        return listaRegistros;
    }

    //insere um registro na tabela curso
    public void insereCurso(Curso curso){
        db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("curso_nome", curso.getNome());
        valores.put("curso_carga_horaria", curso.getCarga_horaria());
        db.insert("TB_CURSO", null, valores);
    }

    //atualiza um registro na tabela curso
    public void atualizaCurso(int id, Curso curso){
        db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("curso_nome", curso.getNome());
        valores.put("curso_carga_horaria", curso.getCarga_horaria());
        String[] args = {String.valueOf(id)};
        db.update("TB_CURSO", valores, "curso_id=?", args);
    }

    //deleta um registro na tabela curso, quando n??o h?? alunos associados ao curso
    //a exce????o ?? capturada e arremessada para o m??todo que chama deletarCurso()
    //obedece a CONSTRAINT ON DELETE definida na cria????o do banoo.
    public void deletarCurso(int id){
        db = this.getWritableDatabase();
        String[] args = {String.valueOf(id)};
        try{
            db.delete("TB_CURSO", "curso_id=?", args);
        }
        catch (android.database.sqlite.SQLiteConstraintException e){
            throw new android.database.sqlite.SQLiteConstraintException();
        }
    }

    //deleta um registro na tabela aluno
    public void deletarAluno(int id){
        db = this.getWritableDatabase();
        String[] args = {String.valueOf(id)};
        db.delete("TB_ALUNO", "aluno_id=?", args);
    }

    //insere um registro na tabela aluno caso n??o exista um aluno ja cadastrado com o cpf
    public void insereAluno(Aluno aluno){
        db = this.getWritableDatabase();

        if(this.getAlunoNome(aluno.getCpf()) == null){
            ContentValues valores = new ContentValues();
            valores.put("aluno_nome", aluno.getNome());
            valores.put("aluno_email", aluno.getEmail());
            valores.put("aluno_cpf", aluno.getCpf());
            valores.put("aluno_telefone", aluno.getTelefone());
            valores.put("aluno_curso_id", aluno.getCurso());
            db.insert("TB_ALUNO", null, valores);
        }
        else{
            throw new SQLiteAbortException();
        }
    }

    //atualiza um registro na tabela aluno
    public void atualizaAluno(int id, Aluno aluno){
        db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("aluno_nome", aluno.getNome());
        valores.put("aluno_email", aluno.getEmail());
        valores.put("aluno_cpf", aluno.getCpf());
        valores.put("aluno_telefone", aluno.getTelefone());
        valores.put("aluno_curso_id", aluno.getCurso());
        String[] args = {String.valueOf(id)};
        db.update("TB_ALUNO", valores, "aluno_id=?", args);
    }

    //busca todos os nomes dos alunos cadastrados, para preencher os spinners de sele????o de aluno
    public ArrayList<String> listaAlunos(){
        String[] colunas = {"aluno_cpf"};
        Cursor cursor = getReadableDatabase().query("TB_ALUNO", colunas,null, null, null,null, null, null);

        ArrayList<String> listaAlunos = new ArrayList<String>();
        String c = "---------------------- Selecione um CPF ----------------------";
        listaAlunos.add(c);
        while (cursor.moveToNext()){
            c = cursor.getString(cursor.getColumnIndex("aluno_cpf"));
            listaAlunos.add(c);
        }
        return listaAlunos;
    }

    //busca todos os nomes dos cursos cadastrados, para preencher os spinners de sele????o de curso
    public ArrayList<String> listaCursos(){
        String[] colunas = {"curso_nome"};
        Cursor cursor = getReadableDatabase().query("TB_CURSO", colunas,null, null, null,null, null, null);

        ArrayList<String> listaCursos = new ArrayList<String>();
        String c = "---------------------- Selecione um curso ----------------------";
        listaCursos.add(c);
        while (cursor.moveToNext()){
            c = cursor.getString(cursor.getColumnIndex("curso_nome"));
            listaCursos.add(c);
        }
        return listaCursos;
    }

    // buscam nome, telefone e email dos alunos atrav??s do cpf
    public String getAlunoNome(String aluno_cpf){
        Cursor cursor = getReadableDatabase().rawQuery("SELECT count(*) FROM TB_ALUNO", null);
        cursor.moveToFirst();
        int registros = cursor.getInt(0);

        if(registros > 0){
            try{
                Cursor cursor2 = getReadableDatabase().rawQuery("SELECT aluno_nome FROM TB_ALUNO where aluno_cpf = '"+aluno_cpf+"'", null);
                cursor2.moveToFirst();
                String nome = cursor2.getString(0);
                return nome;
            }
            catch (CursorIndexOutOfBoundsException e){
                return null;
            }
        }
        return null;
    }
    public String getAlunoEmail(String aluno_cpf){
        Cursor cursor = getReadableDatabase().rawQuery("SELECT aluno_email FROM TB_ALUNO where aluno_cpf = '"+aluno_cpf+"'", null);
        cursor.moveToFirst();
        String nome = cursor.getString(cursor.getColumnIndex("aluno_email"));
        return nome;
    }
    public String getAlunoTelefone(String aluno_cpf){
        Cursor cursor = getReadableDatabase().rawQuery("SELECT aluno_telefone FROM TB_ALUNO where aluno_cpf = '"+aluno_cpf+"'", null);
        cursor.moveToFirst();
        String nome = cursor.getString(cursor.getColumnIndex("aluno_telefone"));
        return nome;
    }

    //busca o id horaria de um aluno atrav??s do seu cpf
    public int getAlunoId(String aluno_cpf){
        Cursor cursor = getReadableDatabase().rawQuery("SELECT aluno_id FROM TB_ALUNO where aluno_cpf = '"+aluno_cpf+"'", null);
        cursor.moveToFirst();
        int id_aluno = cursor.getInt(cursor.getColumnIndex("aluno_id"));
        return id_aluno;
    }

    //busca o id de um curso atrav??s do seu nome
    public int getCursoId(String curso_nome){
        Cursor cursor = getReadableDatabase().rawQuery("SELECT curso_id FROM TB_CURSO where curso_nome = '"+curso_nome+"'", null);
        cursor.moveToFirst();
        int id_curso = cursor.getInt(cursor.getColumnIndex("curso_id"));
        return id_curso;
    }

    //busca a carga horaria de um curso atrav??s do seu nome
    public int getCursoCh(String curso_nome){
        Cursor cursor = getReadableDatabase().rawQuery("SELECT curso_carga_horaria FROM TB_CURSO where curso_nome = '"+curso_nome+"'", null);
        cursor.moveToFirst();
        int ch_curso = cursor.getInt(cursor.getColumnIndex("curso_carga_horaria"));
        return ch_curso;
    }

    //retorna a quantidade de alunos que escolheram cada um dos cursos
    public ArrayList<String> alunosPorCurso(){
        Cursor cursor = getReadableDatabase().rawQuery("Select COUNT(aluno_id) qdt, curso_nome FROM TB_ALUNO A JOIN TB_CURSO C ON C.curso_id = A.aluno_curso_id GROUP BY curso_nome", null);
        cursor.moveToFirst();
        ArrayList<String> qtd_aluno_curso = new ArrayList<String>();

        if(cursor.getCount() > 0) {
            int qtde;
            String curso_nome;
            do {
                qtde = cursor.getInt(0);
                curso_nome = cursor.getString(1);
                Resources res = context.getResources();
                qtd_aluno_curso.add(res.getQuantityString(R.plurals.plural_alunos_curso, qtde, qtde) + " " + curso_nome + ".");
            } while (cursor.moveToNext());
        }
        return qtd_aluno_curso;
    }

}