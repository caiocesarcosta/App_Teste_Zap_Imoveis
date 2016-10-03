package com.made.uellisson.teste_dev_zapimoveis.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.made.uellisson.teste_dev_zapimoveis.Activity_Principal;
import com.made.uellisson.teste_dev_zapimoveis.R;

/**
 *
 * Classe responsavel por gerenciar o Fragmento de que mostra informações sobre o aplicativo,
 * a partir do layout frag_sobre.
 *
 */
public class Frag_sobre extends Fragment {

    Activity_Principal activity_principal;

    /**
     * Metodo que carrega o layout e inicializa seus compoentes.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_sobre, container, false);

        //inicializa a Activity_Principal
        activity_principal = (Activity_Principal) getActivity();

        //muda titulo da barra superior
        activity_principal.setTitle("Sobre o Teste Zap");

        //setamos o numero da fragment, para controlarmos o botao voltar
        activity_principal.setNumero_fragment(1);

        //deixao o botao flutuante invisivel
        activity_principal.getFab().setVisibility(View.INVISIBLE);

        return view;
    }

}
