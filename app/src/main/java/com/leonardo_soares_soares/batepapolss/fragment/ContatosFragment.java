package com.leonardo_soares_soares.batepapolss.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.leonardo_soares_soares.batepapolss.Activity.CadastroUsuarioActivity;
import com.leonardo_soares_soares.batepapolss.Activity.ConversaActivity;
import com.leonardo_soares_soares.batepapolss.R;
import com.leonardo_soares_soares.batepapolss.adapter.ContatoAdapter;
import com.leonardo_soares_soares.batepapolss.config.ConfiguracaoFireBase;
import com.leonardo_soares_soares.batepapolss.helper.Preferencias;
import com.leonardo_soares_soares.batepapolss.model.Contato;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContatosFragment extends Fragment {
    private ListView listView;
    private ArrayAdapter arrayAdapter;
    private ArrayList<Contato> contatos;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerContatos;

    public ContatosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        firebase.addValueEventListener(valueEventListenerContatos);
    }

    @Override
    public void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListenerContatos);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        contatos = new ArrayList<>();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contatos, container, false);


        listView = (ListView) view.findViewById(R.id.lv_contatos);
       // arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, contatos);

        arrayAdapter = new ContatoAdapter(getActivity(),contatos);
        listView.setAdapter(arrayAdapter);

        Preferencias preferencias = new Preferencias(getActivity());
        String identificadorLogado = preferencias.getIdentificador();
        firebase = ConfiguracaoFireBase.getFireBase().child("contatos").child(identificadorLogado);


        valueEventListenerContatos = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                contatos.clear();

                for (DataSnapshot dados : dataSnapshot.getChildren()) {

                    Contato contato = dados.getValue(Contato.class);
                    contatos.add(contato);
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ConversaActivity.class);

                Contato contato= contatos.get(position);
                intent.putExtra("nome",contato.getNome());
                intent.putExtra("email",contato.getEmal());

                startActivity(intent);
            }
        });
        return view;
    }

}
