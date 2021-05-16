package com.example.manager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class CtxListagemQtdeFragment extends Fragment {

    private ListView alunosPorCurso;
    private Database database;

    @Override
    public void onResume() {
        super.onResume();
        ArrayList<String> lista = new ArrayList<String>(database.alunosPorCurso());
        if(lista.size() > 0){
            ListagemAdapter adapter = new ListagemAdapter(super.getContext(), android.R.layout.simple_list_item_1, lista);
            alunosPorCurso.setAdapter(adapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ctx_listagem, container, false);
        database = new Database(container.getContext());
        alunosPorCurso = v.findViewById(R.id.listViewCadastros);
        ArrayList<String> lista = new ArrayList<String>(database.alunosPorCurso());

        //atualiza o list view com os registros da lista
        if(lista.size() > 0){
            ListagemAdapter adapter = new ListagemAdapter(super.getContext(), android.R.layout.simple_list_item_1, lista);
            alunosPorCurso.setAdapter(adapter);
        }
        return v;
    }
}