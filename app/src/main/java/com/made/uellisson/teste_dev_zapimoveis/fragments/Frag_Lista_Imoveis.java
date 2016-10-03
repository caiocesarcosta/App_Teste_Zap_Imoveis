package com.made.uellisson.teste_dev_zapimoveis.fragments;

import android.app.FragmentManager;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.made.uellisson.teste_dev_zapimoveis.Activity_Principal;
import com.made.uellisson.teste_dev_zapimoveis.R;
import com.made.uellisson.teste_dev_zapimoveis.modelo.Imoveis_Lista_itens;
import com.made.uellisson.teste_dev_zapimoveis.webService.Imovel_Lista_itens_Des;
import com.made.uellisson.teste_dev_zapimoveis.webService.Imovel_item_Service;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 *
 * Classe responsavel por gerenciar o Fragmento lista de imoveis, a partir do layout frag_lista_imoveis.
 *
 */
public class Frag_Lista_Imoveis extends ListFragment implements AdapterView.OnItemClickListener{


    Activity_Principal activity_principal;
    private ProgressDialog buscando;

    ArrayList<Integer> lista_de_codigos;

    //Cada item da lista é composto pelo sub-tipo do imovel e preço.
    ArrayAdapter<String> itens_da_lista;


    /**
     * Metodo que carrega o layout e inicializa seus compoentes.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_lista_imoveis, container, false);

        activity_principal = (Activity_Principal) getActivity();
        activity_principal.setTitle("Lista de Imoveis");

        itens_da_lista = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1);
        lista_de_codigos = new ArrayList<>();


        //deixao o botao flutuante invisivel
        activity_principal.getFab().setVisibility(View.INVISIBLE);

        return view;
    }



    /**
     * Metodo chamado apos criar o fragment(onCreateView), ele sera usado para modificarmos elementos do layout
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getListView().setOnItemClickListener(this);

        busca_lista_imoveis();

    }

    /**
     * Metodo usado para gerenciarmos o click em um item da lista e pegar o codigo do imovel clicado
     * @param adapterView
     * @param view
     * @param i
     * @param l
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        //atualizo o codigo do imovel na activity principal
        activity_principal.setCodigo_imovel_global(lista_de_codigos.get(i));

        //chama a activity de detalhe, que carrega as informacoes do imovel selecionado
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_act, new Frag_Detalhes_Imoveis()).commit();
    }

    /**
     * Metodo que gerencia a busca dos dados do imovel no servidor, usando metodos das classes do pacote webservice
     */
    public void busca_lista_imoveis (){

        buscando = ProgressDialog.show(activity_principal, "Buscando Lista de Imóveis...", "Por Favor Aguarde um Momento...");


        String url_base = "http://demo4573903.mockable.io/";

        Gson gson = new GsonBuilder().registerTypeAdapter(Imoveis_Lista_itens.class, new Imovel_Lista_itens_Des()).create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url_base)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        final Imovel_item_Service imovel_item_service = retrofit.create(Imovel_item_Service.class);

        final Call<Imoveis_Lista_itens> lista_imoveis = imovel_item_service.getLista_Imoveis();


        lista_imoveis.enqueue(new Callback<Imoveis_Lista_itens>() {

            /**
             * Metodo chamado se ocorrer a conexao com o servidor
             * @param call
             * @param response
             */
            @Override
            public void onResponse(Call<Imoveis_Lista_itens> call, Response<Imoveis_Lista_itens> response) {
                //chamado se a o get retornar o resultado esperado
                if (response.isSuccessful()) {

                    String item;
                    for (int i = 0; i<response.body().getItens().size(); i++){
                        item = response.body().getItens().get(i).getSubtipoImovel()+".\n"+response.body().getItens().get(i).getPrecoVenda();
                        itens_da_lista.add(item);
                        lista_de_codigos.add(response.body().getItens().get(i).getCodImovel());
                    }

                    setListAdapter(itens_da_lista);

                    //finaliza a barra de progresso
                    buscando.dismiss();

                }
                //chamado se a o get nao retornar o resultado esperado
                else {
                    Toast.makeText(activity_principal, "Falha ao Buscar Lista de Imoveis!" + response.errorBody(), Toast.LENGTH_LONG).show();
                    //Log.d("Erro: ", "OnResponse"+response.errorBody().toString());

                    //finaliza a barra de progresso
                    buscando.dismiss();
                }
            }

            /**
             * Metodo chamado no caso de problema na conexao com o servidor
             * @param call
             * @param t
             */
            @Override
            public void onFailure(Call<Imoveis_Lista_itens> call, Throwable t) {
                Toast.makeText(getActivity(), "Erro: Problema na conexão com o Servidor!" + t.getMessage(), Toast.LENGTH_LONG).show();
                //finaliza a barra de progresso.
                buscando.dismiss();

                //Log.d("Erro: ", "OnFailure"+t.getMessage().toString());

            }

        });

    }
}
