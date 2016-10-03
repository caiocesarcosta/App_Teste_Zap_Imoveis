package com.made.uellisson.teste_dev_zapimoveis.webService;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.made.uellisson.teste_dev_zapimoveis.Dominio;
import com.made.uellisson.teste_dev_zapimoveis.modelo.Cliente;
import com.made.uellisson.teste_dev_zapimoveis.modelo.Endereco;
import com.made.uellisson.teste_dev_zapimoveis.modelo.Imovel;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Uellisson on 02/10/2016.
 *
 * Classe usada para fazer a deserializacao dos dados da classe Imovel.
 * Para isso ela obtem os dados do servidor e faz seu processamento para serem
 * exibidas posteriormente.
 */
public class Imovel_Des implements JsonDeserializer {


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

        JsonObject object_imovel = json.getAsJsonObject().getAsJsonObject("Imovel");
        JsonObject object_end = json.getAsJsonObject().getAsJsonObject("Imovel").get("Endereco").getAsJsonObject();
        JsonObject object_cliente = json.getAsJsonObject().getAsJsonObject("Imovel").get("Cliente").getAsJsonObject();
        JsonArray object_Fotos = json.getAsJsonObject().getAsJsonObject("Imovel").get("Fotos").getAsJsonArray();


        //Obtencao da data do Json
        String data_json = object_imovel.get("DataAtualizacao").toString();
        String data_Long = data_json.replace("/", "").replace("Date", "").replace("(","").replace(")","").replace("\"","");

        //Formatacao da data
        Date date = new Date(Long.parseLong(data_Long));
        //data com dia, mes, ano, hora, minuto e segundo-> "dd:MM:yyyy - hh:mm:kk"
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String data_formatada = dateFormat.format(date);


        Imovel imovel = new Imovel();
        Endereco endereco = new Endereco();
        Cliente cliente = new Cliente();
        Dominio ct = new Dominio();


        if (json.getAsJsonObject() != null){

            imovel.setCodImovel(object_imovel.get("CodImovel").getAsJsonPrimitive().getAsInt());
            imovel.setTipoImovel(object_imovel.get("TipoImovel").toString().replace("\"", ""));

            //Obtensao do endereco
            endereco.setLogradouro(object_end.get("Logradouro").toString().replace("\"", ""));
            endereco.setNumero(object_end.get("Numero").toString().replace("\"", ""));
            endereco.setComplemento(object_end.get("Complemento").toString().replace("\"", ""));
            endereco.setCep(object_end.get("CEP").toString().replace("\"", ""));
            endereco.setBairro(object_end.get("Bairro").toString().replace("\"", ""));
            endereco.setCidade(object_end.get("Cidade").toString().replace("\"", ""));
            endereco.setEstado(object_end.get("Estado").toString().replace("\"", ""));
            endereco.setZona(object_end.get("Zona").toString().replace("\"", ""));
            //fazemos o set no endereco do imovel
            imovel.setEndereco(endereco);


            String preco_formatado = ct.formata_preco(object_imovel.get("PrecoVenda").getAsJsonPrimitive().getAsInt());
            imovel.setPrecoVenda(preco_formatado);


            imovel.setDormitorios(object_imovel.get("Dormitorios").getAsJsonPrimitive().getAsInt());
            imovel.setSuites(object_imovel.get("Suites").getAsJsonPrimitive().getAsInt());
            imovel.setVagas(object_imovel.get("Vagas").getAsJsonPrimitive().getAsInt());
            imovel.setAreaUtil(object_imovel.get("AreaUtil").getAsJsonPrimitive().getAsInt());
            imovel.setAreaTotal(object_imovel.get("AreaTotal").getAsJsonPrimitive().getAsInt());
            imovel.setDataAtualizacao(data_formatada);

            cliente.setCodCliente(object_cliente.get("CodCliente").getAsJsonPrimitive().getAsInt());
            cliente.setNomeFantasia(object_cliente.get("NomeFantasia").toString().replace("\"", ""));
            imovel.setCliente(cliente);


            String url_foto;
            Bitmap bitmap;
            ArrayList<Bitmap> lista_fotos = new ArrayList<>();
            for (int i = 0; i<object_Fotos.size(); i++){
                url_foto = object_Fotos.get(i).toString().replace("\"", "");
                bitmap = baixarImagem(url_foto);
                lista_fotos.add(bitmap);

            }

            imovel.setUrlImagem(lista_fotos);

            imovel.setSubTipoOferta(object_imovel.get("SubTipoOferta").toString().replace("\"", ""));
            imovel.setSubtipoImovel(object_imovel.get("SubtipoImovel").toString().replace("\"", ""));


            // Log.d("Erro: ", "objeto não nulo: "+imoveis.getUrlImagem());
        }
        else {
            Log.d("Erro: ", "objeto null");
        }

        return imovel;
    }


    /**
     * Faz o dawnload da imagem aparir da sua url e retorna uma imagem Bitmap
     * @param url
     * @return Bitmap
     */
    private Bitmap baixarImagem(String url) {
        try{
            URL endereco;
            InputStream inputStream;
            Bitmap imagem;
            endereco = new URL(url);
            inputStream = endereco.openStream();
            imagem = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            return imagem;
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
