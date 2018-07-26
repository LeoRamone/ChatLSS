package com.leonardo_soares_soares.batepapolss.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
/**
 * Created by Leonardo Soares on 17/07/18.
 * leonardo_soares_santos@outlook.com
 */
public final class ConfiguracaoFireBase {

    private static DatabaseReference referenciaFireBase;
    private static FirebaseAuth autenticacao;

    public static DatabaseReference getFireBase(){


        if (referenciaFireBase ==null) {
            referenciaFireBase = FirebaseDatabase.getInstance().getReference();

        }
        return referenciaFireBase;
    }
    public static  FirebaseAuth getFireBaseAutenticacao(){
if (autenticacao==null){
    autenticacao =FirebaseAuth.getInstance();
}
return autenticacao;

    }
}
