package com.example.jadso.adedonline.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.jadso.adedonline.R;

import java.util.ArrayList;

/**
 * Created by jadso on 07/03/2018.
 */

public class RespostaAdapter extends ArrayAdapter<Resposta> {

    private final Context context;
    private final ArrayList<Resposta> elementos;

    public RespostaAdapter(Context context, ArrayList<Resposta> elementos) {
        super(context, R.layout.activity_adapter_correcao_respostas, elementos);
        this.context = context;
        this.elementos = elementos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.activity_adapter_correcao_respostas, parent, false);

        TextView txtCategoria = (TextView) rowView.findViewById(R.id.txtCategoria);
        TextView txtResposta = (TextView) rowView.findViewById(R.id.txtResposta);

        txtCategoria.setText(elementos.get(position).categoria);
        txtResposta.setText(elementos.get(position).resposta);
        return rowView;
    }
}
