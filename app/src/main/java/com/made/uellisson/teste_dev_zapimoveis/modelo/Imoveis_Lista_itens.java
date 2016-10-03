package com.made.uellisson.teste_dev_zapimoveis.modelo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Uellisson on 02/10/2016.
 *
 * Classe modelo que especifica como e criado um objeto Imoveis_Lista_itens
 */
public class Imoveis_Lista_itens implements Serializable {
    private static final long serialVersionUID = 1L;

    private ArrayList<Imovel_item> itens;

    public Imoveis_Lista_itens() {
    }

    public Imoveis_Lista_itens(ArrayList<Imovel_item> itens) {
        this.itens = itens;
    }

    public ArrayList<Imovel_item> getItens() {
        return itens;
    }

    public void setItens(ArrayList<Imovel_item> itens) {
        this.itens = itens;
    }
}
