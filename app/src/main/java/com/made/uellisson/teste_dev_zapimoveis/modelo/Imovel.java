package com.made.uellisson.teste_dev_zapimoveis.modelo;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Uellisson on 03/10/2016.
 *
 *  Classe modelo que especifica como e criado um objeto Imovel
 */

public class Imovel implements Serializable {
    private static final long serialVersionUID = 1L;

    private int codImovel;
    private String tipoImovel;
    private Endereco endereco;
    private String precoVenda;
    private int dormitorios;
    private int suites;
    private int vagas;
    private int areaUtil;
    private int areaTotal;
    private String dataAtualizacao;
    private Cliente cliente;
    private ArrayList<Bitmap> urlImagem;
    private String subTipoOferta;
    private String subtipoImovel;

    public Imovel() {
    }

    public int getCodImovel() {
        return codImovel;
    }

    public void setCodImovel(int codImovel) {
        this.codImovel = codImovel;
    }

    public String getTipoImovel() {
        return tipoImovel;
    }

    public void setTipoImovel(String tipoImovel) {
        this.tipoImovel = tipoImovel;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(String precoVenda) {
        this.precoVenda = precoVenda;
    }

    public int getDormitorios() {
        return dormitorios;
    }

    public void setDormitorios(int dormitorios) {
        this.dormitorios = dormitorios;
    }

    public int getSuites() {
        return suites;
    }

    public void setSuites(int suites) {
        this.suites = suites;
    }

    public int getVagas() {
        return vagas;
    }

    public void setVagas(int vagas) {
        this.vagas = vagas;
    }

    public int getAreaUtil() {
        return areaUtil;
    }

    public void setAreaUtil(int areaUtil) {
        this.areaUtil = areaUtil;
    }

    public int getAreaTotal() {
        return areaTotal;
    }

    public void setAreaTotal(int areaTotal) {
        this.areaTotal = areaTotal;
    }

    public String getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(String dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ArrayList<Bitmap> getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(ArrayList<Bitmap> urlImagem) {
        this.urlImagem = urlImagem;
    }

    public String getSubTipoOferta() {
        return subTipoOferta;
    }

    public void setSubTipoOferta(String subTipoOferta) {
        this.subTipoOferta = subTipoOferta;
    }

    public String getSubtipoImovel() {
        return subtipoImovel;
    }

    public void setSubtipoImovel(String subtipoImovel) {
        this.subtipoImovel = subtipoImovel;
    }
}
