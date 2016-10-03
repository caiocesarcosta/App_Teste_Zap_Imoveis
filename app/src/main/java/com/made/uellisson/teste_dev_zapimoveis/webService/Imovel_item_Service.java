package com.made.uellisson.teste_dev_zapimoveis.webService;

import com.made.uellisson.teste_dev_zapimoveis.modelo.Imoveis_Lista_itens;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Uellisson on 02/10/2016.
 *
 * Interface que especifica como funcionara o metodo getLista_Imoveis,
 * que envia um contato para o servidor.
 */
public interface Imovel_item_Service {
    @GET("imoveis")
    Call<Imoveis_Lista_itens> getLista_Imoveis();
}
