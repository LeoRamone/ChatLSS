package com.leonardo_soares_soares.batepapolss.helper;

import android.util.Base64;
/**
 * Created by Leonardo Soares on 17/07/18.
 * leonardo_soares_santos@outlook.com
 */
public class Base64Custom {

    public static String codificarBase64(String texto){

       return Base64.encodeToString(texto.getBytes(),Base64.DEFAULT).replaceAll("(\\n|\\r)","");


    }
    public static String decodificarBase64(String textoCodificado){
        return new String( Base64.decode(textoCodificado,Base64.DEFAULT));


    }
}
