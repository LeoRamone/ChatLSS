package com.leonardo_soares_soares.batepapolss.model;
/**
 * Created by Leonardo Soares on 17/07/18.
 * leonardo_soares_santos@outlook.com
 */
public class Contato {

    private String identificadorUsuario,nome,emal;

    public Contato( ) {

    }

    public String getIdentificadorUsuario() {
        return identificadorUsuario;
    }

    public void setIdentificadorUsuario(String identificadorUsuario) {
        this.identificadorUsuario = identificadorUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmal() {
        return emal;
    }

    public void setEmal(String emal) {
        this.emal = emal;
    }
}
