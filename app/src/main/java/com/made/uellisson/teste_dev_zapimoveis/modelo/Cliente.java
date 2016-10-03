package com.made.uellisson.teste_dev_zapimoveis.modelo;

/**
 * Created by Uellisson on 01/10/2016.
 *
 * Classe modelo que especifica como e criado um objeto cliente
 */
public class Cliente {

    private int codCliente;
    private String nomeFantasia;

    public Cliente() {
    }

    public int getCodCliente() {
        return codCliente;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setCodCliente(int codCliente) {
        this.codCliente = codCliente;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }
}
