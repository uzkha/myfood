package br.edu.imed.myfood;

/**
 * Created by uzumaki on 05/12/15.
 */
public class Auxiliar {

    static int calcularDivisao(int width){

        int divisor;

        if(width > 4000) {
            divisor = 8;
        }else if(width > 3000) {
            divisor = 6;
        }else if(width > 2000){
            divisor = 4;
        }else if(width > 1000){
            divisor = 2;
        }else{
            divisor = 1;
        }

        return divisor;

    }
}
