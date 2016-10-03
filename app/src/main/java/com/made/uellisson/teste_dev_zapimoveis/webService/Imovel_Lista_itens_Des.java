package com.made.uellisson.teste_dev_zapimoveis.webService;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.made.uellisson.teste_dev_zapimoveis.Dominio;
import com.made.uellisson.teste_dev_zapimoveis.modelo.Imoveis_Lista_itens;
import com.made.uellisson.teste_dev_zapimoveis.modelo.Imovel_item;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Uellisson on 02/10/2016.
 *
 * Classe usada para fazer a deserializacao dos dados da classe Imovel_Lista_itens.
 * Para isso ela obtem os dados do servidor e faz seu processamento para serem
 * exibidas posteriormente.
 */

public class Imovel_Lista_itens_Des implements JsonDeserializer {

    /**
     * Metodo que faz a desserializa o objeto
     * @param json
     * @param typeOfT
     * @param context
     * @return
     * @throws JsonParseException
     */
    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {


        JsonArray array_imoveis = json.getAsJsonObject().get("Imoveis").getAsJsonArray();

        Imovel_item imovel;
        Imoveis_Lista_itens lista_itens = new Imoveis_Lista_itens();
        ArrayList<Imovel_item> lista_imoveis = new  ArrayList<Imovel_item>();
        Dominio ct = new Dominio();

        int CodImovel;
        String PrecoVenda;
        String SubtipoImovel;
        if (json.getAsJsonObject() != null){

            for (int i = 0; i<array_imoveis.size(); i++){
                CodImovel = array_imoveis.get(i).getAsJsonObject().get("CodImovel").getAsJsonPrimitive().getAsInt();

                //no preco de venda chamamos o metodo formata_preco do controle para o deixar pronto pra exibicao
                PrecoVenda = ct.formata_preco(array_imoveis.get(i).getAsJsonObject().get("PrecoVenda").getAsJsonPrimitive().getAsInt());
                SubtipoImovel = array_imoveis.get(i).getAsJsonObject().get("SubtipoImovel").toString().replace("\"", "");

                imovel = new Imovel_item(CodImovel, PrecoVenda, SubtipoImovel);

                lista_imoveis.add(imovel);
            }

            lista_itens.setItens(lista_imoveis);

            Log.d("Erro: ", "objeto não nulo: "+lista_imoveis.get(2).getCodImovel());
        }
        else {
            Log.d("Erro: ", "objeto null");
        }

        return lista_itens;
    }
}
