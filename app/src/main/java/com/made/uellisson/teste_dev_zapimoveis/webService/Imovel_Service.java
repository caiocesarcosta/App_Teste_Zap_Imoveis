package com.made.uellisson.teste_dev_zapimoveis.webService;

import com.made.uellisson.teste_dev_zapimoveis.modelo.Imovel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Uellisson on 03/10/2016.
 *
 * Interface que especifica como funcionara o metodo getImoveis,
 * que envia um contato para o servidor.
 */

public interface Imovel_Service {
    @GET("imoveis/{id}")
    Call<Imovel> getImoveis(@Path("id") int id);

}
