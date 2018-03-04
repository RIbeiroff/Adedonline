package com.example.jadso.adedonline.Controller.Cliente;

import android.provider.ContactsContract;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by jadso on 04/03/2018.
 */

public class ThreadEscutaStop implements Runnable {

    Socket conexao;

    public ThreadEscutaStop(Socket conexao){
        this.conexao = conexao;
    }

    @Override
    public void run(){
        try {
            BufferedReader entradaDeDados = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            String stop = entradaDeDados.readLine().toString(); //Recebo o stop

            //Se o conteúdo da mensagem é stop, então eu devo retornar uma confirmação
            //Se for confirmastop, eu não preciso retornar nada
            if (stop.compareTo("stop") == 0){
                DataOutputStream saidaDeDados = new DataOutputStream(conexao.getOutputStream());
                saidaDeDados.writeBytes("confirmadostop" + '\n');
                saidaDeDados.flush();
                System.out.println("Não fui o primeiro a disparar o stop");
            } else if (stop.compareTo("confirmadostop") == 0){
                System.out.println("Fui o primeiro a disparar o stop");
            }
        } catch (IOException ex) {
            Logger.getLogger(ThreadEscutaStop.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}