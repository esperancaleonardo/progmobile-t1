package com.example.manager;

import android.database.sqlite.SQLiteAbortException;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;

public class CadAluno extends Fragment {

    private EditText aluno_nome, aluno_email, aluno_cpf, aluno_telefone;
    private Database database;
    private Spinner spinner_seletor_curso;
    private Button cancelar, cadastrar;

    @Override
    public void onResume() {
        super.onResume();
        ArrayList<String> cursos_lista = database.listaCursos();
        ArrayAdapter<CharSequence> cursos_adapter = new ArrayAdapter(super.getContext(), android.R.layout.simple_spinner_item, cursos_lista);
        cursos_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_seletor_curso.setAdapter(cursos_adapter);
        Log.e("CADALUNO", "SPINNER ATUALIZADO");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cad_aluno, container, false);
        database = new Database(container.getContext());
        aluno_nome = v.findViewById(R.id.editTextNomeAluno);
        aluno_email = v.findViewById(R.id.editTextEmailAluno);
        aluno_cpf = v.findViewById(R.id.editTextCpfAluno);
        aluno_telefone = v.findViewById(R.id.editTextTelefoneAluno);
        spinner_seletor_curso = v.findViewById(R.id.spinnerSeletorCurso);
        cadastrar = v.findViewById(R.id.btnCadAluno);
        cancelar = v.findViewById(R.id.btnCanCadAluno);

        ArrayList<String> cursos_lista = database.listaCursos();
        ArrayAdapter<CharSequence> cursos_adapter = new ArrayAdapter(container.getContext(), android.R.layout.simple_spinner_item, cursos_lista);
        cursos_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_seletor_curso.setAdapter(cursos_adapter);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = aluno_nome.getText().toString();
                String email = aluno_email.getText().toString();
                String cpf = aluno_cpf.getText().toString();
                String telefone = aluno_telefone.getText().toString();

                if ((!nome.equals("")) && (!email.equals("")) && (!cpf.equals("")) && (!telefone.equals("")) && (spinner_seletor_curso.getSelectedItemId() != 0)){  //
                    try{
                        int id_curso = database.getCursoId(spinner_seletor_curso.getSelectedItem().toString());
                        Aluno aluno = new Aluno(nome, email, cpf, telefone, id_curso);
                        Toast.makeText(CadAluno.super.getContext(),"Aluno cadastrado com sucesso!", Toast.LENGTH_LONG).show();
                        aluno_nome.setText("");
                        aluno_email.setText("");
                        aluno_cpf.setText("");
                        aluno_telefone.setText("");
                        spinner_seletor_curso.setSelection(0);
                        database.insereAluno(aluno);
                    }catch (SQLiteAbortException e){
                        Toast.makeText(CadAluno.super.getContext(),"Já existe um usuário com este CPF", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(CadAluno.super.getContext(),"Há campos em branco!", Toast.LENGTH_LONG).show();
                }
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aluno_nome.setText("");
                aluno_email.setText("");
                aluno_cpf.setText("");
                aluno_telefone.setText("");
                spinner_seletor_curso.setSelection(0);
                Toast.makeText(CadAluno.super.getContext(),"Ação cancelada!", Toast.LENGTH_LONG).show();
            }
        });
        return v;
    }
}