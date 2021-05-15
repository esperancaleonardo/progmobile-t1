package com.example.manager;

import android.annotation.SuppressLint;
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
    private EditText nomeCurso, cargaCurso;
    private Spinner seletorCurso;
    private Button editar, cancelar;
    private Database database;
    private int click = 0;

    @Override
    public void onResume() {
        super.onResume();
        ArrayAdapter cursos_adapter = new ArrayAdapter(EdtCurso.super.getContext(), android.R.layout.simple_spinner_item, database.listaCursos());
        cursos_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        seletorCurso.setAdapter(cursos_adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edt_curso, container, false);
        database = new Database(container.getContext());
        nomeCurso = v.findViewById(R.id.editTextNomeEditCurso);
        cargaCurso = v.findViewById(R.id.editTextCargaEditCurso);
        cancelar = v.findViewById(R.id.btnCanEditCurso);
        editar = v.findViewById(R.id.btnEditCurso);
        seletorCurso = v.findViewById(R.id.spinnerSeletorCursoEdit);

        //carrega os dados dos cursos presentes em banco para o spinner de selecao de curso
        ArrayAdapter cursos_adapter = new ArrayAdapter(EdtCurso.super.getContext(), android.R.layout.simple_spinner_item, database.listaCursos());
        cursos_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        seletorCurso.setAdapter(cursos_adapter);

        seletorCurso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0){//quando o spinner for diferente de "Selecione um curso"
                    //busca os dados no banco e permite a correção/alteração dos mesmos
                    ArrayList<String> cursos_lista = database.listaCursos();
                    int carga = database.getCursoCh((String) cursos_lista.get(position));
                    nomeCurso.setText(cursos_lista.get(position));
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
                click = 0;
                limpa_campos(false, "", "EDITAR");
            }
        });

        editar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(click == 0){
                    click = 1;
                    seletorCurso.setEnabled(false);
                    limpa_campos(true, "dummy", "SALVAR");
                }
                else{
                    click = 0;
                    //verifica os campos preeenchidos e insere no banco
                    //verifica se existem campos vazios antes de inserir e exibe informação em toast
                    if ((!nomeCurso.getText().toString().equals("")) && (!cargaCurso.getText().toString().equals(""))) {
                        int id_alterado = database.getCursoId(seletorCurso.getSelectedItem().toString());
                        Curso curso = new Curso(nomeCurso.getText().toString(), Integer.valueOf(cargaCurso.getText().toString()));
                        database.atualizaCurso(id_alterado, curso);
                        ArrayAdapter cursos_adapter = new ArrayAdapter(container.getContext(), android.R.layout.simple_spinner_item, database.listaCursos());
                        cursos_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        seletorCurso.setAdapter(cursos_adapter);
                        seletorCurso.setSelection(0);
                        seletorCurso.setEnabled(true);
                        Toast.makeText(EdtCurso.super.getContext(), "Curso alterado com sucesso!", Toast.LENGTH_LONG).show();
                        limpa_campos(false, "", "EDITAR");
                    }
                    else{
                        Toast.makeText(EdtCurso.super.getContext(),"Há campos em branco!", Toast.LENGTH_LONG).show();
                        click=1;
                    }
                }
            }
        });
        return v;
    }

    private void limpa_campos(Boolean flagHabilita, String text, String btnText){
        if(text.equals("")){
            nomeCurso.setText(text);
            cargaCurso.setText(text);
        }
        nomeCurso.setEnabled(flagHabilita);
        nomeCurso.setFocusable(flagHabilita);
        nomeCurso.setFocusableInTouchMode(flagHabilita);
        cargaCurso.setEnabled(flagHabilita);
        cargaCurso.setFocusable(flagHabilita);
        cargaCurso.setFocusableInTouchMode(flagHabilita);
        editar.setText(btnText);
    }
}