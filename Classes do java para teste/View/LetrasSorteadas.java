/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author jadso
 */
public class LetrasSorteadas {
    
    static ArrayList<Integer> numerosSorteados = new ArrayList(); 	
    static int contador = 1;

    public static char ArrayTopic () {
          
        if (numerosSorteados.size() < 26){
            Random generator = new Random();
            int numeroSorteado =  generator.nextInt(25);
            char[] alfabeto = new char[] {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};

            while (true){
                if (!numerosSorteados.contains(numeroSorteado)){
                    numerosSorteados.add(numeroSorteado);
                    return alfabeto[numeroSorteado];
                } else 
                    numeroSorteado++;
                
                if (numeroSorteado == 26)
                    numeroSorteado = 0;
            }
        }
        return '-';
    }
        
    public static void main(String[] args){
 //       char[] alfabeto = new char[] {'a','b','c','d'};
//    abcdefghijklmnpqrstuvwxyz

/*
    while (numerosSorteados.size() < 26)    
           System.out.println(ArrayTopic() + " ");
    
    }
   */
    }
}
