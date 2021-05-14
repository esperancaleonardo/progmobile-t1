package com.example.manager;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CadCurso extends Fragment {

    private EditText curso_nome, curso_ch;
    private Database database;
    private Button cancelar, cadastrar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cad_curso, container, false);
        database = new Database(container.getContext());
        curso_nome = v.findViewById(R.id.editTextNomeCadCurso);
        curso_ch = v.findViewById(R.id.editTextChCadCurso);
        cancelar = v.findViewById(R.id.btnCanCadCurso);
        cadastrar = v.findViewById(R.id.btnCadCurso);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = curso_nome.getText().toString();
                String ch = curso_ch.getText().toString();

                if ((!nome.equals("")) && (!ch.equals(""))){
                    Curso curso = new Curso(nome, Integer.parseInt(ch));
                    Toast.makeText(CadCurso.super.getContext(),"Curso cadastrado com sucesso!", Toast.LENGTH_LONG).show();
                    curso_ch.setText("");
                    curso_nome.setText("");
                    database.insereCurso(curso);
                }
                else{
                    Toast.makeText(CadCurso.super.getContext(),"Há campos em branco!", Toast.LENGTH_LONG).show();
                }
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curso_ch.setText("");
                curso_nome.setText("");
                Toast.makeText(CadCurso.super.getContext(),"Ação cancelada!", Toast.LENGTH_LONG).show();
            }
        });
        return v;
    }
}