package com.example.manager;

import android.database.sqlite.SQLiteAbortException;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
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
        ArrayAdapter<CharSequence> cursos_adapter = new ArrayAdapter(super.getContext(),
                android.R.layout.simple_spinner_item, database.listaCursos());
        cursos_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_seletor_curso.setAdapter(cursos_adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cad_aluno, container, false);
        database = new Database(container.getContext());
        aluno_nome = v.findViewById(R.id.editTextNomeAluno);
        aluno_email = v.findViewById(R.id.editTextEmailAluno);
        aluno_cpf = v.findViewById(R.id.editTextCpfAluno);
        aluno_telefone = v.findViewById(R.id.editTextTelefoneAluno);
        spinner_seletor_curso = v.findViewById(R.id.spinnerSeletorCurso);
        cadastrar = v.findViewById(R.id.btnCadAluno);
        cancelar = v.findViewById(R.id.btnCanCadAluno);

        ArrayAdapter<CharSequence> cursos_adapter = new ArrayAdapter(container.getContext(),
                android.R.layout.simple_spinner_item, database.listaCursos());
        cursos_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_seletor_curso.setAdapter(cursos_adapter);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = aluno_nome.getText().toString();
                String email = aluno_email.getText().toString();
                String cpf = aluno_cpf.getText().toString();
                String telefone = aluno_telefone.getText().toString();

                if ((!nome.equals("")) && (!email.equals("")) && (!cpf.equals(""))
                        && (!telefone.equals(""))
                        && (spinner_seletor_curso.getSelectedItemId() != 0)){
                    try{
                        int id_curso = database.getCursoId(spinner_seletor_curso.getSelectedItem().toString());
                        Aluno aluno = new Aluno(nome, email, cpf, telefone, id_curso);
                        database.insereAluno(aluno);
                        Toast.makeText(CadAluno.super.getContext(),"Aluno cadastrado com sucesso!", Toast.LENGTH_LONG).show();
                        limpa_campos();
                    }catch (SQLiteAbortException e){
                        Toast.makeText(CadAluno.super.getContext(),"J?? existe um aluno com este CPF", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(CadAluno.super.getContext(),"H?? campos em branco!", Toast.LENGTH_LONG).show();
                }
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpa_campos();
                Toast.makeText(CadAluno.super.getContext(),"A????o cancelada!", Toast.LENGTH_LONG).show();
            }
        });
        return v;
    }

    private void limpa_campos(){
        aluno_nome.setText("");
        aluno_email.setText("");
        aluno_cpf.setText("");
        aluno_telefone.setText("");
        spinner_seletor_curso.setSelection(0);
    }
}