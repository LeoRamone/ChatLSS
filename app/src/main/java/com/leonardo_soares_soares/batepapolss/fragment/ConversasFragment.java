package com.leonardo_soares_soares.batepapolss.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import com.leonardo_soares_soares.batepapolss.Activity.ConversaActivity;
import com.leonardo_soares_soares.batepapolss.Activity.MainActivity;
import com.leonardo_soares_soares.batepapolss.R;
import com.leonardo_soares_soares.batepapolss.adapter.ConversaAdapter;
import com.leonardo_soares_soares.batepapolss.config.ConfiguracaoFireBase;
import com.leonardo_soares_soares.batepapolss.helper.Base64Custom;
import com.leonardo_soares_soares.batepapolss.helper.Preferencias;
import com.leonardo_soares_soares.batepapolss.model.Conversa;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConversasFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter<Conversa> adapter;
    private ArrayList<Conversa> conversas;

    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerConversas;

    public ConversasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_conversas, container, false);

        // Monta listview e adapter
        conversas = new ArrayList<>();
        listView = (ListView) view.findViewById(R.id.lv_conversas);
        adapter = new ConversaAdapter(getActivity(), conversas );
        listView.setAdapter( adapter );

        // recuperar dados do usuário
        Preferencias preferencias = new Preferencias(getActivity());
        String idUsuarioLogado = preferencias.getIdentificador();

        // Recuperar conversas do Firebase
        firebase =ConfiguracaoFireBase.getFireBase()
                .child("conversas")
                .child( idUsuarioLogado );

        valueEventListenerConversas = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                conversas.clear();
                for ( DataSnapshot dados: dataSnapshot.getChildren() ){
                    Conversa conversa = dados.getValue( Conversa.class );
                    conversas.add(conversa);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Conversa conversa = conversas.get(i);
                Intent intent = new Intent(getActivity(),ConversaActivity.class);


                intent.putExtra("nome",conversa.getNome());

                String email = Base64Custom.decodificarBase64(conversa.getIdUsuario());

                intent.putExtra("email" ,email);

startActivity(intent);
            }
        });

        return view;

    }


    public void onBackPressed() {


        Intent intent = new Intent(getActivity(),MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        firebase.addValueEventListener(valueEventListenerConversas);
    }

    @Override
    public void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListenerConversas);
    }



}