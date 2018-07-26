package com.leonardo_soares_soares.batepapolss.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.leonardo_soares_soares.batepapolss.R;
import com.leonardo_soares_soares.batepapolss.model.Contato;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Leonardo Soares on 17/07/18.
 * leonardo_soares_santos@outlook.com
 */
public class ContatoAdapter extends ArrayAdapter<Contato>{

private ArrayList<Contato>contatos;
private Context context;

    public ContatoAdapter(@NonNull Context c, @NonNull ArrayList<Contato> objects) {
        super(c, 0, objects);
        this.contatos= objects;
        this.context=c;
    }



    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
View view = null;

if (contatos != null){


    LayoutInflater layoutInflater =(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

    view = layoutInflater.inflate(R.layout.lista_contato,parent,false);

  TextView nomeContato = (TextView) view.findViewById(R.id.tv_nome);
  TextView emailContato = (TextView) view.findViewById(R.id.tv_email);
  Contato contato = contatos.get(position);
  nomeContato.setText(contato.getNome());
    emailContato.setText(contato.getEmal());
}


return view;
    }
}
