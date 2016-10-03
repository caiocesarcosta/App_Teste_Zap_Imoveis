package com.made.uellisson.teste_dev_zapimoveis.webService;

import com.made.uellisson.teste_dev_zapimoveis.modelo.Contato;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Uellisson on 30/09/2016.
 *
 * Interface que especifica como funcionara o metodo postar_contato,
 * que envia um contato para o servidor.
 */
public interface Contato_Service {

    @POST("imoveis/contato")
    Call<Contato> postar_contato(@Body Contato contato);

}
