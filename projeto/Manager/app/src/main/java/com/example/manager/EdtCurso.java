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

public class EdtCurso extends Fragment {

    EditText nomeCurso, cargaCurso;
    Spinner seletorCurso;
    Button editar, cancelar;
    Database database;
    int click = 0;

    @Override
    public void onResume() {
        super.onResume();
        ArrayList<String> cursos_lista = database.listaCursos();
        ArrayAdapter cursos_adapter = new ArrayAdapter(EdtCurso.super.getContext(), android.R.layout.simple_spinner_item, cursos_lista);
        cursos_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        seletorCurso.setAdapter(cursos_adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edt_curso, container, false);

        database = new Database(container.getContext());

        nomeCurso = v.findViewById(R.id.editTextNomeEditCurso);
        cargaCurso = v.findViewById(R.id.editTextCargaEditCurso);
        cancelar = v.findViewById(R.id.btnCanEditCurso);
        editar = v.findViewById(R.id.btnEditCurso);
        seletorCurso = v.findViewById(R.id.spinnerSeletorCursoEdit);

        //carrega os dados dos cursos presentes em banco para o spinner de selecao de curso
        ArrayList<String> cursos_lista = database.listaCursos();
        ArrayAdapter cursos_adapter = new ArrayAdapter(EdtCurso.super.getContext(), android.R.layout.simple_spinner_item, cursos_lista);
        cursos_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        seletorCurso.setAdapter(cursos_adapter);

        seletorCurso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0){//quando o spinner for diferente de "Selecione um curso"
                    //busca os dados no banco e permite a correção/alteração dos mesmos
                    nomeCurso.setText(cursos_lista.get(position));
                    int carga = database.getCursoCh((String) cursos_lista.get(position));
                    cargaCurso.setText(Integer.toString(carga));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//limpa os campos e exibe toast informativo
                Toast.makeText(EdtCurso.super.getContext(),"Ação cancelada!", Toast.LENGTH_LONG).show();
                seletorCurso.setSelection(0);
                nomeCurso.setText("");
                cargaCurso.setText("");
                nomeCurso.setEnabled(false);
                nomeCurso.setFocusable(false);
                nomeCurso.setFocusableInTouchMode(false);
                cargaCurso.setEnabled(false);
                cargaCurso.setFocusable(false);
                cargaCurso.setFocusableInTouchMode(false);
            }
        });

        editar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(click == 0){
                    click = 1;

                    nomeCurso.setEnabled(true);
                    nomeCurso.setFocusable(true);
                    nomeCurso.setFocusableInTouchMode(true);
                    cargaCurso.setEnabled(true);
                    cargaCurso.setFocusable(true);
                    cargaCurso.setFocusableInTouchMode(true);
                    editar.setText("SALVAR");
                }
                else{
                    click = 0;

                    //verifica os campos preeenchidos e insere no banco
                    //verifica se existem campos vazios antes de inserir e exibe informação em toast
                    if ((!nomeCurso.getText().toString().equals("")) && (!cargaCurso.getText().toString().equals(""))
                            && (seletorCurso.getSelectedItemId() != 0)) {
                        int id_alterado = database.getCursoId(seletorCurso.getSelectedItem().toString());
                        Curso curso = new Curso(nomeCurso.getText().toString(), Integer.valueOf(cargaCurso.getText().toString()));

                        database.atualizaCurso(id_alterado, curso);
                        seletorCurso.setSelection(0);
                        nomeCurso.setText("");
                        cargaCurso.setText("");

                        ArrayList<String> cursos_lista = database.listaCursos();
                        ArrayAdapter cursos_adapter = new ArrayAdapter(container.getContext(), android.R.layout.simple_spinner_item, cursos_lista);
                        cursos_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        seletorCurso.setAdapter(cursos_adapter);

                        Toast.makeText(EdtCurso.super.getContext(), "Curso alterado com sucesso!", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(EdtCurso.super.getContext(),"Há campos em branco!", Toast.LENGTH_LONG).show();
                    }

                    nomeCurso.setEnabled(false);
                    nomeCurso.setFocusable(false);
                    nomeCurso.setFocusableInTouchMode(false);
                    cargaCurso.setEnabled(false);
                    cargaCurso.setFocusable(false);
                    cargaCurso.setFocusableInTouchMode(false);
                    editar.setText("EDITAR");

                }
            }
        });
        return v;
    }
}