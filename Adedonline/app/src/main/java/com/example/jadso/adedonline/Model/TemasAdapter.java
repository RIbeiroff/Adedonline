package com.example.jadso.adedonline.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jadso.adedonline.R;

import java.util.ArrayList;

/**
 * Created by jadso on 04/03/2018.
 */

public class TemasAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final ArrayList<String> temas;

    public TemasAdapter(Context context, ArrayList<String> temas) {
        super(context, R.layout.activity_responder_adapter, temas);
        this.context = context;
        this.temas = temas;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.activity_responder_adapter, parent, false);

        TextView txtNome = (TextView) rowView.findViewById(R.id.txtNome);
        EditText edtResposta = (EditText) rowView.findViewById(R.id.edtResposta);

        txtNome.setText(temas.get(position));

        return rowView;
    }
}
