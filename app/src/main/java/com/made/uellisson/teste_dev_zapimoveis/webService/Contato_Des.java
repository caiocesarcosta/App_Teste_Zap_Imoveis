package com.made.uellisson.teste_dev_zapimoveis.webService;

import android.util.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.made.uellisson.teste_dev_zapimoveis.modelo.Contato;

import java.lang.reflect.Type;

/**
 * Created by Uellisson on 02/10/2016.
 *
 * Classe usada para fazer a deserializacao dos dados da classe Contato.
 * Para isso ela obtem os dados do servidor e faz seu processamento para serem
 * exibidas posteriormente.
 *
 */
public class Contato_Des implements JsonDeserializer {

    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonObject object_imovel = json.getAsJsonObject().getAsJsonObject().getAsJsonArray("contato").getAsJsonObject();


        Contato contato=null;

        String nome;
        String email;
        String telefone;
        String msg_anuncio;


        if (json.getAsJsonObject() != null){

            nome = object_imovel.get("Nome").toString();
            email = object_imovel.get("Email").toString();
            telefone = object_imovel.get("Telefone").toString();
            msg_anuncio = object_imovel.get("CodImovel").toString();

            contato = new Contato(nome, email, telefone, msg_anuncio);

            Log.d("Erro: ", "objeto não nulo: "+nome);
        }
        else {
            Log.d("Erro: ", "objeto null");
        }

        return contato;
    }
}
