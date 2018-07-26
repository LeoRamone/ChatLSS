package com.leonardo_soares_soares.batepapolss.Activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.leonardo_soares_soares.batepapolss.R;
import com.leonardo_soares_soares.batepapolss.helper.Preferencias;

import java.util.HashMap;
/**
 * Created by Leonardo Soares on 17/07/18.
 * leonardo_soares_santos@outlook.com
 */
public class ValidadorActivity extends AppCompatActivity {


    private EditText codigoValidacao;
    private Button validar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validador);


        codigoValidacao=(EditText)findViewById(R.id.edit_cod_validacao);
        validar = (Button)findViewById(R.id.bt_validar);

        SimpleMaskFormatter simpleMaskcodigoValidacao = new SimpleMaskFormatter("NNNN");
        MaskTextWatcher maskCodigoValidacao = new MaskTextWatcher(codigoValidacao,simpleMaskcodigoValidacao);


        codigoValidacao.addTextChangedListener(maskCodigoValidacao);

        validar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Preferencias preferencias = new Preferencias(ValidadorActivity.this);
              /*  HashMap<String,String>usuario = preferencias.getDadosUsuario();

                String tokenGerado = usuario.get("token");
                String tokenDigitado = codigoValidacao.getText().toString();

                if (tokenDigitado.equals(tokenGerado)){
                    Toast.makeText(ValidadorActivity.this,"Token VALIDADO",Toast.LENGTH_LONG).show();

                }else {
                    Toast.makeText(ValidadorActivity.this,"Token não VALIDADO",Toast.LENGTH_LONG).show();


                }
*/
            }
        });
    }
}
