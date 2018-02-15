package com.example.jadso.adedonline.Model;

import java.io.Serializable;
import java.net.InetAddress;

/**
 * Created by jadso on 12/02/2018.
 */

public class Participante implements Serializable {

    public InetAddress EnderecoIp;
    public int porta;

    public Participante(InetAddress  EnderecoIp, int porta){
        this.EnderecoIp = EnderecoIp;
        this.porta = porta;
    }

    public InetAddress getEnderecoIp() {
        return EnderecoIp;
    }

    public void setEnderecoIp(InetAddress EnderecoIp) {
        this.EnderecoIp = EnderecoIp;
    }

    public int getPorta() {
        return porta;
    }

    public void setPorta(int porta) {
        this.porta = porta;
    }
}
