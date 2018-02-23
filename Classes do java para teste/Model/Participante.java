/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.net.InetAddress;

/**
 *
 * @author jadso
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
