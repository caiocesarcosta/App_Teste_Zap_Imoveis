package com.made.uellisson.teste_dev_zapimoveis;

import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.made.uellisson.teste_dev_zapimoveis.fragments.Frag_Detalhes_Imoveis;
import com.made.uellisson.teste_dev_zapimoveis.fragments.Frag_Lista_Imoveis;
import com.made.uellisson.teste_dev_zapimoveis.fragments.Frag_sobre;

/**
 * Classe principal, que gerencia a Activity_Principal e todas as
 * Fragments da aplicacao.
 */
public class Activity_Principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    //gerencia a visibilidade do botão flutuante da mensagem
    FloatingActionButton fab;
    public FloatingActionButton getFab() {
        return fab;
    }

    public void setFab(FloatingActionButton fab) {
        this.fab = fab;
    }

    //variavel que permite acessar o codigo do imovel em qualquer fragment
    private int codigo_imovel_global;

    //variavel que permite saber que fragment esta aparecendo, para usar no onBackPressed
    private int numero_fragment;

    public int getCodigo_imovel_global() {
        return codigo_imovel_global;
    }

    public void setCodigo_imovel_global(int codigo_imovel_global) {
        this.codigo_imovel_global = codigo_imovel_global;
    }

    //metodos acessores e modificadores da variaveis codigo_imovel_global e numero_fragment
    public void setClient(GoogleApiClient client) {
        this.client = client;
    }

    public int getNumero_fragment() {
        return numero_fragment;
    }

    public void setNumero_fragment(int numero_fragment) {
        this.numero_fragment = numero_fragment;
    }


    /**
     * Metodo Oncrete, responsavel por carregar o layout activity_principal e
     * inicializar todos os componentes da classe.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Carrega a Lista de imoveis logo na primeira tela
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_act, new Frag_Lista_Imoveis()).commit();

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * Controla os eventos do botao voltar, atraves da variavel numero_fragment, que
     * e setada em cada fragment.
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            //caso esteja na lista, a aplicacao sera finalizada
            if (numero_fragment==0){
                super.onBackPressed();

            }
            else {
                //caso esteja na fragment detalhe volta pra lista
                if (numero_fragment==1){
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frame_act, new Frag_Lista_Imoveis()).commit();
                    numero_fragment = numero_fragment-1;
                }
                //caso esteja na fragment contato
                else{
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frame_act, new Frag_Detalhes_Imoveis()).commit();
                    numero_fragment = numero_fragment-1;

                }
            }

        }

    }

    /**
     * Gerencia o menu de itens, que fica na gaveta lateral
     * @param item
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fm = getFragmentManager();

        if (id == R.id.nav_lista_imoveis) {
            fm.beginTransaction().replace(R.id.frame_act, new Frag_Lista_Imoveis()).commit();
        }
        else if (id == R.id.nav_sobre_nos) {
            fm.beginTransaction().replace(R.id.frame_act, new Frag_sobre()).commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Activity_Principal Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }


}
