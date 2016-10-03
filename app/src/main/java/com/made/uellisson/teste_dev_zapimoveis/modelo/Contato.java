package com.made.uellisson.teste_dev_zapimoveis.modelo;

import java.io.Serializable;

/**
 * Created by Uellisson on 30/09/2016.
 *
 * Classe modelo que especifica como e criado um objeto Contato
 */
public class Contato implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nome;
    private String email;
    private String telefone;
    private String msg_anuncio;

    /**
     * Contrutor da classe Contato que recebe como parametro todos os seus atributos.
     *
     * @param nome
     * @param email
     * @param telefone
     * @param msg_anuncio
     */
    public Contato(String nome, String email, String telefone, String msg_anuncio) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.msg_anuncio = msg_anuncio;
    }


    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getMsg_anuncio() {
        return msg_anuncio;
    }
}
