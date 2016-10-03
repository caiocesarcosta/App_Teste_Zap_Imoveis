package com.made.uellisson.teste_dev_zapimoveis.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.made.uellisson.teste_dev_zapimoveis.Activity_Principal;
import com.made.uellisson.teste_dev_zapimoveis.R;
import com.made.uellisson.teste_dev_zapimoveis.modelo.Contato;
import com.made.uellisson.teste_dev_zapimoveis.webService.Contato_Service;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 *
 * Classe responsavel por gerenciar o Fragmento contato, a partir do layout fragment_contato.
 */
public class Fragment_Contato extends Fragment implements View.OnClickListener{

    Activity_Principal activity_principal;
    int codigo_imovel;

    //Compoentes do layout fragment_contato
    EditText et_nome;
    EditText et_telefone;
    EditText et_email;
    EditText et_msg_anuncio;
    Button bt_enviar;
    Button bt_cancelar;

    private ProgressDialog enviando;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_contato, container, false);

        et_nome = (EditText) view.findViewById(R.id.et_nome);
        et_email = (EditText) view.findViewById(R.id.et_email);
        et_telefone = (EditText) view.findViewById(R.id.et_telefone);
        et_msg_anuncio = (EditText) view.findViewById(R.id.et_msg_anuncio);


        //usamos para pegar o codigo do imovel, que o cliente vai comunicar o interesse
        activity_principal = (Activity_Principal) getActivity();
        codigo_imovel= activity_principal.getCodigo_imovel_global();

        et_msg_anuncio.setText("Olá tenho interesse no imóvel anunciado com o código: "+codigo_imovel+". \nAguardo o Retorno!");

        bt_enviar = (Button) view.findViewById(R.id.bt_enviar);
        bt_cancelar = (Button) view.findViewById(R.id.bt_cancelar);

        bt_enviar.setOnClickListener(this);
        bt_cancelar.setOnClickListener(this);

        //setamos o numero da fragment, para controlarmos o botao voltar
        activity_principal.setNumero_fragment(2);

        //deixao o botao flutuante invisivel
        activity_principal.getFab().setVisibility(View.INVISIBLE);

        activity_principal.setTitle("Contato");


        return view;

    }


    @Override
    public void onClick(View view) {
        if (view==bt_enviar){

            //validando campos
            if (et_nome.getText().toString().equals("")){
                Toast.makeText(activity_principal, "Preencha Seu Nome!", Toast.LENGTH_LONG).show();
            }
            else{
                if (!et_email.getText().toString().contains("@")){
                    Toast.makeText(activity_principal, "Preencha Seu E-mail Corretamente!", Toast.LENGTH_LONG).show();
                }
                else{
                    if (et_telefone.getText().toString().equals("")){
                        Toast.makeText(activity_principal, "Preencha Seu Telefone!", Toast.LENGTH_LONG).show();
                    }

                    //Todos os campos foram preenchidos
                    else{
                        //envia a mensagem
                        post_contato();

                        //Apos enviar a mensagem, volta para tela de detalhes do imovel..
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.frame_act, new Frag_Detalhes_Imoveis()).commit();
                    }

                }
            }

        }

        else {
            if (view==bt_cancelar){
                //caso o usuario cancele, voltara para a tela anterior, com os detalhes do imovel que estava vendo.
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame_act, new Frag_Detalhes_Imoveis()).commit();
            }
        }
    }


    /**
     * Posta um contato
     */
    public void post_contato(){

        enviando = ProgressDialog.show(activity_principal, "Enviando Mensagem...", "Por Favor Aguarde um Momento...");

        String url_base = "http://demo4573903.mockable.io/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url_base)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Contato_Service ct_service = retrofit.create(Contato_Service.class);
        Contato c = new Contato("nome", "email", "telefone", "mensagem");

        Call<Contato> postagem = ct_service.postar_contato(c);

        postagem.enqueue(new Callback<Contato>() {
            @Override
            public void onResponse(Call<Contato> call, Response<Contato> response) {
                if (response.isSuccessful()){
                    Toast.makeText(activity_principal, "Contato enviado com sucesso!", Toast.LENGTH_LONG).show();
                    Log.d("Passou!!", "response isSuccessful");

                    //finaliza a barra de progresso
                    enviando.dismiss();
                }

                else{
                    Toast.makeText(activity_principal, "Falha ao Enviar Mensagem!"+response.errorBody(), Toast.LENGTH_LONG).show();
                    Log.d("Erro: ", response.errorBody().toString());

                    //finaliza a barra de progresso
                    enviando.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Contato> call, Throwable t) {
                Toast.makeText(activity_principal, "Erro: "+t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("Erro: ", "Problema na conexao "+t.getMessage());

                //finaliza a barra de progresso
                enviando.dismiss();
            }

        });

    }
}
