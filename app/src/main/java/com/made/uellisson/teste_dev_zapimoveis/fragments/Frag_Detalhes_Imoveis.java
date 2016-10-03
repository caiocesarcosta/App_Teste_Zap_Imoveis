package com.made.uellisson.teste_dev_zapimoveis.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.made.uellisson.teste_dev_zapimoveis.Activity_Principal;
import com.made.uellisson.teste_dev_zapimoveis.R;
import com.made.uellisson.teste_dev_zapimoveis.modelo.Imovel;
import com.made.uellisson.teste_dev_zapimoveis.webService.Imovel_Des;
import com.made.uellisson.teste_dev_zapimoveis.webService.Imovel_Service;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 *
 * Fragment usada para exibir os detalhes de um imovel, a partir do layout frag_detalhes_imoveis.
 */
public class Frag_Detalhes_Imoveis extends Fragment implements AdapterView.OnClickListener{


    ImageView iv_foto;
    TextView tv_preco_venda;
    TextView tv_descricao;
    TextView tv_anunciante;
    TextView tv_cod_anunciante;
    TextView tv_data_atualizacao;
    TextView tv_cod_imovel;
    TextView tv_subtipo_imovel;
    TextView tv_endereco_completo;

    ImageButton ib_prev;
    ImageButton ib_next;

    int numero_foto;

    ArrayList<Bitmap> lista_fotos;

    FloatingActionButton float_contato;

    Activity_Principal activity_principal;
    int codigo_imovel;

    private ProgressDialog buscando;


    /**
     * Metodo que carrega o layout e inicializa seus compoentes.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_detalhes_imoveis, container, false);

        iv_foto = (ImageView) view.findViewById(R.id.iv_foto);
        tv_preco_venda = (TextView) view.findViewById(R.id.tv_preco_venda);
        tv_descricao = (TextView) view.findViewById(R.id.tv_descricao);
        tv_anunciante = (TextView) view.findViewById(R.id.tv_anunciante);
        tv_cod_anunciante = (TextView) view.findViewById(R.id.tv_cod_anunciante);
        tv_data_atualizacao = (TextView) view.findViewById(R.id.tv_data_atualizacao);
        tv_cod_imovel = (TextView) view.findViewById(R.id.tv_cod_imovel);
        tv_subtipo_imovel = (TextView) view.findViewById(R.id.tv_subtipo_Imovel);
        tv_endereco_completo = (TextView) view.findViewById(R.id.tv_end_completo);

        ib_prev = (ImageButton) view.findViewById(R.id.ib_prev);
        ib_next = (ImageButton) view.findViewById(R.id.ib_next);
        ib_prev.setOnClickListener(this);
        ib_next.setOnClickListener(this);

        lista_fotos = new ArrayList<>();
        numero_foto = 0;

        //inicializa a Activity_Principal
        activity_principal = (Activity_Principal) getActivity();

        //muda titulo da barra superior
        activity_principal.setTitle("Detalhes do Imovel");


        //deixao o botao flutuante visivel
        activity_principal.getFab().setVisibility(View.VISIBLE);

        //inicializa o botao flutuante e deixa ele clicavel
        float_contato = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        float_contato.setOnClickListener(this);

        //usamos para pegar o codigo do imovel, que o cliente vai comunicar o interesse
        codigo_imovel= activity_principal.getCodigo_imovel_global();

        //setamos o numero da fragment, para controlarmos o botao voltar
        activity_principal.setNumero_fragment(1);

        busca_dados_imovel();


        return view;
    }


    /**
     * Metodo que gerencia a busca dos dados do imovel no servidor, usando metodos das classes do pacote webservice
     */
    public void busca_dados_imovel (){

        buscando = ProgressDialog.show(activity_principal, "Buscando Dados do Imóvel...", "Por Favor Aguarde um Momento...");

        //usamos para pegar o codigo do imovel, que o cliente vai comunicar o interesse
        activity_principal = (Activity_Principal) getActivity();
        codigo_imovel= activity_principal.getCodigo_imovel_global();


        String url_base = "http://demo4573903.mockable.io/";

        Gson gson = new GsonBuilder().registerTypeAdapter(Imovel.class, new Imovel_Des()).create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url_base)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Imovel_Service imovel_service = retrofit.create(Imovel_Service.class);

        final Call<Imovel> imovel = imovel_service.getImoveis(codigo_imovel);


        imovel.enqueue(new Callback<Imovel>() {

            /**
             * Metodo chamado se ocorrer a conexao com o servidor
             * @param call
             * @param response
             */
            @Override
            public void onResponse(Call<Imovel> call, Response<Imovel> response) {
                if (response.isSuccessful()) {

                    tv_preco_venda.setText(response.body().getPrecoVenda());

                    Bitmap bitmap;
                    for (int i = 0; i<response.body().getUrlImagem().size(); i++){
                        bitmap = response.body().getUrlImagem().get(i);
                        lista_fotos.add(bitmap);

                    }

                    //carrega primeira foto
                    iv_foto.setImageBitmap(lista_fotos.get(numero_foto));

                    //FFF
                    if(response.body().getAreaTotal()!=response.body().getAreaUtil() && response.body().getSuites() != 0 && response.body().getVagas()!=0){

                        tv_descricao.setText("Oferta "+response.body().getSubTipoOferta()+": "+response.body().getTipoImovel()+
                                " com "+response.body().getAreaTotal()+"m² de área total e "+response.body().getAreaUtil()+"m² de área útil, " +
                                "com "+response.body().getDormitorios()+" dormitório(s), " + response.body().getSuites()+
                                " suítes e "+response.body().getVagas()+" vagas pra garagem.");
//
                    }

                    else {
                        //FFV
                        if(response.body().getAreaTotal()!=response.body().getAreaUtil() && response.body().getSuites() != 0 && response.body().getVagas()==0){
                            tv_descricao.setText("Oferta "+response.body().getSubTipoOferta()+": "+response.body().getTipoImovel()+
                                    " com "+response.body().getAreaTotal()+"m² de área total e "+response.body().getAreaUtil()+"m² de área útil, " +
                                    "com "+response.body().getDormitorios()+" dormitório(s), " + response.body().getSuites()+
                                    " suítes.");
                        }
                        else {
                            //FVF
                            if (response.body().getAreaTotal() != response.body().getAreaUtil() && response.body().getSuites() == 0 && response.body().getVagas() != 0) {
                                tv_descricao.setText("Oferta " + response.body().getSubTipoOferta() +": " + response.body().getTipoImovel() +
                                        " com " + response.body().getAreaTotal() + "m² de área total e " + response.body().getAreaUtil() + "m² de área útil, " +
                                        "com " + response.body().getDormitorios() + " dormitório(s) e " + response.body().getVagas() + " vaga(s) pra garagem.");
                            } else {
                                //FVV
                                if (response.body().getAreaTotal() != response.body().getAreaUtil() && response.body().getSuites() == 0 && response.body().getVagas() == 0) {
                                    tv_descricao.setText("Oferta " + response.body().getSubTipoOferta() + ": " + response.body().getTipoImovel() +
                                            " com " + response.body().getAreaTotal() + "m² de área total e " + response.body().getAreaUtil() + "m² de área útil, " +
                                            "com " + response.body().getDormitorios() + " dormitório(s).");
                                } else {
                                    //VFF
                                    if (response.body().getAreaTotal() == response.body().getAreaUtil() && response.body().getSuites() != 0 && response.body().getVagas() != 0) {
                                        tv_descricao.setText("Oferta " + response.body().getSubTipoOferta() + ": " + response.body().getTipoImovel() +
                                                " de " + response.body().getAreaTotal() + "m², " +
                                                "com " + response.body().getDormitorios() + " dormitório(s), " + response.body().getSuites() +
                                                " suítes e " + response.body().getVagas() + " vagas pra garagem.");
                                    } else {
                                        //VFV
                                        if (response.body().getAreaTotal() == response.body().getAreaUtil() && response.body().getSuites() != 0 && response.body().getVagas() == 0) {
                                            tv_descricao.setText("Oferta " + response.body().getSubTipoOferta() + ": " + response.body().getTipoImovel() +
                                                    " de " + response.body().getAreaTotal() + "m²," +
                                                    " com " + response.body().getDormitorios() + " dormitório(s), " +
                                                    +response.body().getSuites() + " suítes.");
                                        } else {
                                            //VVF
                                            if (response.body().getAreaTotal() == response.body().getAreaUtil() && response.body().getSuites() == 0 && response.body().getVagas() != 0) {
                                                tv_descricao.setText("Oferta " + response.body().getSubTipoOferta() + ": " + response.body().getTipoImovel() +
                                                        " de " + response.body().getAreaTotal() + "m²," +
                                                        " com " + response.body().getDormitorios() + " dormitório(s), " +
                                                        " e " + response.body().getVagas() + " vagas pra garagem.");
                                            }
                                            //VVV
                                            else {
                                                tv_descricao.setText("Oferta " + response.body().getSubTipoOferta() + ": " + response.body().getTipoImovel() +
                                                        " de " + response.body().getAreaTotal() + "m²," +
                                                        " com " + response.body().getDormitorios() + " dormitório(s).");
                                            }
                                        }

                                    }
                                }
                            }
                        }
                    }//fim do FFF


                tv_anunciante.setText(response.body().getCliente().getNomeFantasia());
                tv_cod_anunciante.setText(String.valueOf(response.body().getCliente().getCodCliente()));
                tv_data_atualizacao.setText(response.body().getDataAtualizacao());
                tv_cod_imovel.setText(String.valueOf(response.body().getCodImovel()));
                tv_subtipo_imovel.setText(response.body().getSubtipoImovel());


                if(response.body().getEndereco().getComplemento().equalsIgnoreCase("")){
                    tv_endereco_completo.setText(response.body().getEndereco().getLogradouro()+", Nº "
                            + response.body().getEndereco().getNumero()+", "
                            + response.body().getEndereco().getBairro()+"("+ response.body().getEndereco().getZona()+") "
                            + response.body().getEndereco().getCidade()+" - "+ response.body().getEndereco().getEstado()+
                            ". CEP: "+ response.body().getEndereco().getCep()+".");
                }

                else {
                    tv_endereco_completo.setText(response.body().getEndereco().getLogradouro()+", Nº "
                            + response.body().getEndereco().getNumero()+ " - "+response.body().getEndereco().getComplemento()+", "
                            + response.body().getEndereco().getBairro()+"("+ response.body().getEndereco().getZona()+"), "
                            + response.body().getEndereco().getCidade()+" - "+ response.body().getEndereco().getEstado()+
                            ". CEP: "+ response.body().getEndereco().getCep()+".");
                }

                //finaliza a barra de progresso
                buscando.dismiss();

            }//fim do if isSuccessful


                //chamado se nao retornar o resultado esperado
                else {
                    Toast.makeText(activity_principal, "Erro: no momento os detalhes deste imóvel não estão disponíveis!", Toast.LENGTH_LONG).show();

                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frame_act, new Frag_Lista_Imoveis()).commit();

                    //Toast.makeText(fragment.getActivity(), "Falha ao buscar imoveis!" + response.errorBody(), Toast.LENGTH_LONG).show();
                    Log.d("Erro: ", "OnResponse "+response.errorBody().toString());

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
            public void onFailure(Call<Imovel> call, Throwable t) {
                Toast.makeText(activity_principal, "Erro: Problema ao estabelecer conexão com o servidor!", Toast.LENGTH_LONG).show();

                //finaliza a barra de progresso
                buscando.dismiss();
                //Log.d("Erro: imovel não disponivel", t.getMessage().toString());
            }

        });


    }


    /**
     * Metodo que gerencia o click nos botoes float, prev e next
     *
     * @param view
     */
    @Override
    public void onClick(View view) {

        if(view==float_contato){
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_act, new Fragment_Contato()).commit();
        }
        else {
            //gerencia o botao que mostra as fotos anteriores
            if (view==ib_prev){

                if (numero_foto>0){
                    numero_foto=numero_foto-1;
                    iv_foto.setImageBitmap(lista_fotos.get(numero_foto));
                    ib_next.setEnabled(true);
                }
                else{
                    ib_prev.setEnabled(false);
                }

            }
            else{
                //gerencia o botao que mostra as fotos seguintes
                if (view==ib_next){
                    if (numero_foto<lista_fotos.size()-1){
                        numero_foto=numero_foto+1;
                        iv_foto.setImageBitmap(lista_fotos.get(numero_foto));
                        ib_prev.setEnabled(true);
                    }
                    else{
                        ib_next.setEnabled(false);
                    }

                }
            }
        }

    }

}

