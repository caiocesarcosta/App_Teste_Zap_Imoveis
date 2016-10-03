package com.made.uellisson.teste_dev_zapimoveis.modelo;

import java.io.Serializable;

/**
 * Created by Uellisson on 02/10/2016.
 *
 *  Classe modelo que especifica como e criado um objeto Imovel_Item
 */
public class Imovel_item implements Serializable {

    private static final long serialVersionUID = 1L;

    private int codImovel;
    private String precoVenda;
    private String subtipoImovel;

    public Imovel_item(int codImovel, String precoVenda, String subtipoImovel) {
        this.codImovel = codImovel;
        this.precoVenda = precoVenda;
        this.subtipoImovel = subtipoImovel;
    }

    public int getCodImovel() {
        return codImovel;
    }

    public String getPrecoVenda() {
        return precoVenda;
    }

    public String getSubtipoImovel() {
        return subtipoImovel;
    }
}
