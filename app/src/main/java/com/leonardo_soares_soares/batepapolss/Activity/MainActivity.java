package com.leonardo_soares_soares.batepapolss.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.leonardo_soares_soares.batepapolss.R;
import com.leonardo_soares_soares.batepapolss.adapter.TabAdapter;
import com.leonardo_soares_soares.batepapolss.config.ConfiguracaoFireBase;
import com.leonardo_soares_soares.batepapolss.helper.Base64Custom;
import com.leonardo_soares_soares.batepapolss.helper.Preferencias;
import com.leonardo_soares_soares.batepapolss.helper.SlidingTabLayout;
import com.leonardo_soares_soares.batepapolss.model.Contato;
import com.leonardo_soares_soares.batepapolss.model.Usuario;

/**
 * Created by Leonardo Soares on 17/07/18.
 * leonardo_soares_santos@outlook.com
 */
public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FirebaseAuth usuarioAutentificacao;
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private String identificadorContato;
    private DatabaseReference firebase;
    // private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("BatePapoLSS");
        setSupportActionBar(toolbar);
        usuarioAutentificacao = ConfiguracaoFireBase.getFireBaseAutenticacao();
        // databaseReference.child("pontos").setValue(290);

        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.stl_tabs);
        viewPager = (ViewPager) findViewById(R.id.vp_pagina);


        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.colorslind));

        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);

        slidingTabLayout.setViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_sair:
                deslogarUsuario();
                return true;
            case R.id.item_configuracoes:
                return true;
            case R.id.item_adicionar:
                abrirCadastroContato();
                return true;
            default:
                ;
                return super.onOptionsItemSelected(item);

        }


    }

    private void abrirCadastroContato() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

        alertDialog.setTitle("Novo contado");
        alertDialog.setMessage("E-mail do usuario");
        alertDialog.setCancelable(false);
        final EditText editText = new EditText(MainActivity.this);
        alertDialog.setView(editText);

        alertDialog.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String emailContato = editText.getText().toString();

                if (emailContato.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Preencha o email", Toast.LENGTH_LONG).show();

                } else {

                    identificadorContato = Base64Custom.codificarBase64(emailContato);


                    firebase = ConfiguracaoFireBase.getFireBase();
                    firebase = firebase.child("usuarios").child(identificadorContato);

                    firebase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if (dataSnapshot.getValue() != null) {

                               Usuario usarioContatoRecebe= dataSnapshot.getValue(Usuario.class);

                                Preferencias preferencias = new Preferencias(MainActivity.this);
                                String identificadorUsuarioLogado = preferencias.getIdentificador();

                                firebase = ConfiguracaoFireBase.getFireBase();
                                firebase = firebase.child("contatos").child(identificadorUsuarioLogado).child(identificadorContato);

                                Contato contato = new Contato();
                                contato.setIdentificadorUsuario(identificadorContato);
                                contato.setEmal(usarioContatoRecebe.getEmail());
                                contato.setNome(usarioContatoRecebe.getNome());
                                firebase.setValue(contato);

                            } else {
                                Toast.makeText(MainActivity.this, "Usuario sem cadastro no aplicativo", Toast.LENGTH_LONG).show();


                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }

            }
        });
        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialog.create();
        alertDialog.show();
    }

    public void deslogarUsuario() {
        usuarioAutentificacao.signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();


    }

    @Override
    public void onBackPressed() {
        vibrar();
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("     Você esta saindo");
        //define a mensagem
        builder.setMessage("  Tem certeza que quer fazer isso?");
        //define um botão como positivo
        //builder.setIcon(R.drawable.botservice);
        //define um botão como negativo.
        builder.setNegativeButton("SIM", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                finish();
            }
        });
        builder.setPositiveButton("NÃO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });
        //cria o AlertDialog
        AlertDialog alerta = builder.create();
        //Exibe
        alerta.show();
    }

    public void vibrar() {
        Vibrator rr = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long mili = 100;
        rr.vibrate(mili);


    }
}
