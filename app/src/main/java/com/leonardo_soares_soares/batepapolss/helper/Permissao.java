package com.leonardo_soares_soares.batepapolss.helper;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Leonardo Soares on 17/07/18.
 * leonardo_soares_santos@outlook.com
 */
public class Permissao {

    public static boolean validaPermissoes(int requestCode,Activity activity,String[]permissoes){

        if (Build.VERSION.SDK_INT >=23){

            List<String>listaPermissoes = new ArrayList<>();
            for (String permissao:permissoes){

              Boolean validaPermissao =  ContextCompat.checkSelfPermission(activity,permissao)== PackageManager.PERMISSION_GRANTED;

              if (!validaPermissao){listaPermissoes.add(permissao);


              }

            }
if (listaPermissoes.isEmpty())return true;

            String[]novasPermissoes = new String[listaPermissoes.size()];
            listaPermissoes.toArray(novasPermissoes);

            ActivityCompat.requestPermissions(activity,novasPermissoes,requestCode);
        }

        return true;


    }


}
