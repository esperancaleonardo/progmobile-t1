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

public class DropCurso extends Fragment {
    private EditText nomeCurso;
    private Spinner seletorCurso;
    private Button deletar, cancelar;
    private Database database;

    @Override
    public void onResume() {
        super.onResume();
        ArrayAdapter cursos_adapter = new ArrayAdapter(DropCurso.super.getContext(), android.R.layout.simple_spinner_item, database.listaCursos());
        cursos_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        seletorCurso.setAdapter(cursos_adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_drop_curso, container, false);
        database = new Database(container.getContext());
        nomeCurso = v.findViewById(R.id.editTextNomeDelCurso);
        cancelar = v.findViewById(R.id.btnCanDelCurso);
        deletar = v.findViewById(R.id.btnDelCurso);
        seletorCurso = v.findViewById(R.id.spinnerSeletorCursoDel);

        preenche_seletor(container);

        seletorCurso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0){
                    nomeCurso.setText(database.listaCursos().get(position));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DropCurso.super.getContext(),"A????o cancelada!", Toast.LENGTH_LONG).show();
                limpa_campos();
            }
        });

        deletar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(seletorCurso.getSelectedItemId() != 0) {
                    try {
                        int id_deletar = database.getCursoId(seletorCurso.getSelectedItem().toString());
                        database.deletarCurso(id_deletar);
                        limpa_campos();
                        preenche_seletor(container);
                        Toast.makeText(DropCurso.super.getContext(), "Curso alterado com sucesso!", Toast.LENGTH_LONG).show();
                    }
                    catch (android.database.sqlite.SQLiteConstraintException e){
                        Toast.makeText(DropCurso.super.getContext(), "H?? alunos cadastrados no curso!", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(DropCurso.super.getContext(), "N??o pode deletar esta op????o!", Toast.LENGTH_LONG).show();
                }
            }
        });
        return v;
    }

    public void limpa_campos(){
        seletorCurso.setSelection(0);
        nomeCurso.setText("");
    }

    public void preenche_seletor(ViewGroup container){
        ArrayAdapter cursos_adapter = new ArrayAdapter(container.getContext(), android.R.layout.simple_spinner_item, database.listaCursos());
        cursos_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        seletorCurso.setAdapter(cursos_adapter);
    }
}