package com.made.uellisson.teste_dev_zapimoveis;

/**
 * Created by Uellisson on 02/10/2016.
 *
 * Classe que cria alguns metodos secundarios, para serem chamados em outras classes,
 * diminuindo o complexidades das classes e facilitando o reuso de metodos.
 *
 */

public class Dominio {

    /*
    Metodo que formata um inteiro em um preco, de 3450000 para R$ 345.000,00
     */

    /**
     * Metodo que formata um numero int, em uma string preco.
     *
     * Exdemplo: recebe 3450000 e retorna R$ 345.000,00
     *
     * @param preco_int
     * @return
     */
    public String formata_preco(int preco_int){
        String preco_venda = String.valueOf(preco_int);
        String preco_formatado = "";

        int i = preco_venda.length();
        while (i>0){

            if (i-3 >= 3){
                preco_formatado = preco_formatado+"."+preco_venda.substring(i-3,i);
                //Log.d("Valor de i-3>3", String.valueOf(i));
            }
            else{
                if (i-3 > 0){
                    preco_formatado = "."+preco_venda.substring(i-3,i)+preco_formatado;
                    //Log.d("Valor de i-3>0", String.valueOf(i));
                }
                else{
                    if (i-3 >= 0){
                        preco_formatado = preco_venda.substring(i-3,i)+preco_formatado;
                        //Log.d("Valor de i-3=0", String.valueOf(i));
                    }
                    else{
                        if (i-2 >= 0){
                            preco_formatado = preco_venda.substring(i-2,i)+preco_formatado;
                            //Log.d("Valor de i-2", String.valueOf(i));
                        }
                        else{
                            if (i-1 >= 0){
                                preco_formatado = preco_venda.substring(i-1,i)+preco_formatado;
                                //Log.d("Valor de i-1", String.valueOf(i));
                            }
                        }
                    }
                }

            }
            i=i-3;
        }

        return "R$ "+preco_formatado+",00";
    }
}
