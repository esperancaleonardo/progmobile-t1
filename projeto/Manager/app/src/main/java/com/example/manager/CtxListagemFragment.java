package com.example.manager;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import java.util.ArrayList;

public class CtxListagemFragment extends Fragment {
    private ListView listaCadastros;
    private Database database;

    @Override
    public void onResume() {
        super.onResume();
        ArrayList<String> lista = new ArrayList<String>(database.listaCadastros());

        if(lista.size() > 0){
            //atualiza o list view com os registros da lista
            ListagemAdapter adapter = new ListagemAdapter(super.getContext(), android.R.layout.simple_list_item_1, lista);
            listaCadastros.setAdapter(adapter);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ctx_listagem, container, false);
        database = new Database(container.getContext());
        listaCadastros = v.findViewById(R.id.listViewCadastros);

        ArrayList<String> lista = new ArrayList<String>(database.listaCadastros());

        if(lista.size() > 0){
            //atualiza o list view com os registros da lista
            ListagemAdapter adapter = new ListagemAdapter(super.getContext(), android.R.layout.simple_list_item_1, lista);
            listaCadastros.setAdapter(adapter);
        }
        return v;
    }
}