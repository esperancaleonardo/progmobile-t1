package com.example.manager;

import android.content.Context;
import android.widget.ArrayAdapter;
import java.util.HashMap;
import java.util.List;

class ListagemAdapter extends ArrayAdapter<String> {
    private HashMap<String, Integer> array = new HashMap<String, Integer>();

    public ListagemAdapter(Context context, int textViewResourceId, List<String> objects) {
        super(context, textViewResourceId, objects);
        for (int i = 0; i < objects.size(); ++i) {
            array.put(objects.get(i), i);
        }
    }

    @Override
    public long getItemId(int position) {
        String item = getItem(position);
        return array.get(item);
    }

    @Override
    public boolean hasStableIds(){return true;}
}