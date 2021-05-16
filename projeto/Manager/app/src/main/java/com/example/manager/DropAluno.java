package com.example.manager;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
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

public class DropAluno extends Fragment {
    private EditText nomeAluno, emailAluno, telefoneAluno;
    private Spinner seletorAlunoDel;
    private Button deletar, cancelar;
    private Database database;

    @Override
    public void onResume() {
        super.onResume();
        ArrayAdapter alunos_adapter = new ArrayAdapter(DropAluno.super.getContext(), android.R.layout.simple_spinner_item, database.listaAlunos());
        alunos_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        seletorAlunoDel.setAdapter(alunos_adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_drop_aluno, container, false);
        database = new Database(container.getContext());
        nomeAluno = v.findViewById(R.id.editTextNomeDelAluno);
        emailAluno = v.findViewById(R.id.editTextEmailDelAluno);
        telefoneAluno = v.findViewById(R.id.editTextCelularDelAluno);
        cancelar = v.findViewById(R.id.btnCanDelAluno);
        deletar = v.findViewById(R.id.btnDelAluno);
        seletorAlunoDel = v.findViewById(R.id.spinnerSeletorAlunoDel);

        preenche_seletor();

        seletorAlunoDel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0){
                    String aluno_cpf = seletorAlunoDel.getSelectedItem().toString();
                    nomeAluno.setText(database.getAlunoNome(aluno_cpf));
                    emailAluno.setText(database.getAlunoEmail(aluno_cpf));
                    telefoneAluno.setText(database.getAlunoTelefone(aluno_cpf));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DropAluno.super.getContext(),"Ação cancelada!", Toast.LENGTH_LONG).show();
                limpa_dados();
            }
        });

        deletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seletorAlunoDel.getSelectedItemId() != 0) {
                    int id_deletar = database.getAlunoId(seletorAlunoDel.getSelectedItem().toString());
                    database.deletarAluno(id_deletar);
                    limpa_dados();

                    preenche_seletor();
                    Toast.makeText(DropAluno.super.getContext(), "Aluno deletado com sucesso!", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(DropAluno.super.getContext(), "Não pode deletar esta opção!", Toast.LENGTH_LONG).show();
                }
            }
        });
        return v;
    }

    public void limpa_dados(){
        nomeAluno.setText("");
        emailAluno.setText("");
        telefoneAluno.setText("");
        seletorAlunoDel.setSelection(0);
    }

    public void preenche_seletor(){
        ArrayAdapter alunos_adapter = new ArrayAdapter(DropAluno.super.getContext(), android.R.layout.simple_spinner_item, database.listaAlunos());
        alunos_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        seletorAlunoDel.setAdapter(alunos_adapter);
    }
}