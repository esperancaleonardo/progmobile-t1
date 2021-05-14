package com.example.manager;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;

public class EdtAluno extends Fragment {

    private EditText nomeAluno, emailAluno, telefoneAluno;
    private Spinner  seletorCurso, seletorAlunoEdt;
    private Button editar, cancelar;
    private Database database;
    int click = 0;

    @Override
    public void onResume() {
        super.onResume();
        ArrayList<String> alunos_lista = database.listaAlunos();
        ArrayAdapter alunos_adapter = new ArrayAdapter(EdtAluno.super.getContext(), android.R.layout.simple_spinner_item, alunos_lista);
        alunos_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        seletorAlunoEdt.setAdapter(alunos_adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edt_aluno, container, false);

        database = new Database(container.getContext());

        nomeAluno = v.findViewById(R.id.editTextNomeEdtAluno);
        emailAluno = v.findViewById(R.id.editTextEmailEdtAluno);
        telefoneAluno = v.findViewById(R.id.editTextTelefoneEdtAluno);
        seletorAlunoEdt  = v.findViewById(R.id.spinnerSeletorAlunoEdit);
        seletorCurso = v.findViewById(R.id.spinnerSeletorEdtCursoALuno);
        editar = v.findViewById(R.id.btnEditAluno);
        cancelar = v.findViewById(R.id.btnCanEditAluno);
        seletorCurso.setEnabled(false);

        //carrega os dados dos cursos do banco e preenche o spinner
        ArrayList<String> alunos_lista = database.listaAlunos();
        ArrayAdapter alunos_adapter = new ArrayAdapter(EdtAluno.super.getContext(), android.R.layout.simple_spinner_item, alunos_lista);
        alunos_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        seletorAlunoEdt.setAdapter(alunos_adapter);

        seletorAlunoEdt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0){ //quando o spinner for diferente de "Selecione um CPF"
                    //busca os dados no banco e permite a correção/alteração dos mesmos
                    String aluno_cpf =  seletorAlunoEdt.getSelectedItem().toString();
                    nomeAluno.setText(database.getAlunoNome(aluno_cpf));
                    emailAluno.setText(database.getAlunoEmail(aluno_cpf));
                    telefoneAluno.setText(database.getAlunoTelefone(aluno_cpf));
                    ArrayList<String> cursos_lista = database.listaCursos();
                    ArrayAdapter cursos_adapter = new ArrayAdapter(EdtAluno.super.getContext(), android.R.layout.simple_spinner_item, cursos_lista);
                    cursos_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    seletorCurso.setAdapter(cursos_adapter);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //limpa os campos e exibe toast informativo
                Toast.makeText(EdtAluno.super.getContext(),"Ação cancelada!", Toast.LENGTH_LONG).show();
                seletorAlunoEdt.setSelection(0);
                nomeAluno.setText("");
                emailAluno.setText("");
                telefoneAluno.setText("");
            }
        });

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//verifica os campos preeenchidos e insere no banco
                if(click == 0){
                    click = 1;

                    seletorCurso.setEnabled(true);
                    nomeAluno.setEnabled(true);
                    nomeAluno.setFocusable(true);
                    nomeAluno.setFocusableInTouchMode(true);
                    emailAluno.setEnabled(true);
                    emailAluno.setFocusable(true);
                    emailAluno.setFocusableInTouchMode(true);
                    telefoneAluno.setEnabled(true);
                    telefoneAluno.setFocusable(true);
                    telefoneAluno.setFocusableInTouchMode(true);
                    editar.setText("SALVAR");

                }
                else{
                    click=0;

                    String nome = nomeAluno.getText().toString();
                    String email = emailAluno.getText().toString();
                    String telefone = telefoneAluno.getText().toString();

                    //verifica se existem campos vazios antes de inserir e exibe informação em toast
                    if((!nome.equals("")) && (!email.equals("")) && (!telefone.equals("")) && (seletorCurso.getSelectedItemId()!= 0)){
                        int id_alteracao = database.getAlunoId(seletorAlunoEdt.getSelectedItem().toString());
                        int id_curso = database.getCursoId(seletorCurso.getSelectedItem().toString());
                        Aluno aluno = new Aluno(nome, email, seletorAlunoEdt.getSelectedItem().toString(), telefone, id_curso);

                        database.atualizaAluno(id_alteracao, aluno);
                        seletorAlunoEdt.setSelection(0);
                        nomeAluno.setText("");
                        emailAluno.setText("");
                        telefoneAluno.setText("");
                        seletorCurso.setAdapter(null);
                        Toast.makeText(EdtAluno.super.getContext(),"Aluno alterado com sucesso!", Toast.LENGTH_LONG).show();

                    }
                    else{
                        Toast.makeText(EdtAluno.super.getContext(),"Há campos em branco!", Toast.LENGTH_LONG).show();
                    }

                    seletorCurso.setAdapter(null);
                    seletorCurso.setEnabled(false);
                    nomeAluno.setEnabled(false);
                    nomeAluno.setFocusable(false);
                    nomeAluno.setFocusableInTouchMode(false);
                    emailAluno.setEnabled(false);
                    emailAluno.setFocusable(false);
                    emailAluno.setFocusableInTouchMode(false);
                    telefoneAluno.setEnabled(false);
                    telefoneAluno.setFocusable(false);
                    telefoneAluno.setFocusableInTouchMode(false);
                    editar.setText("EDITAR");
                }
            }
        });

        return v;
    }
}