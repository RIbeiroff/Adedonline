/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by jadso on 12/02/2018.
 */

public class Sala implements Serializable {
    public String nome;
    public int totalRodadas;
    public ArrayList<String> categorias;
    public ArrayList<Socket> participantes;

    public Sala(){
        this.participantes = new ArrayList();
        this.categorias = new ArrayList();
    }

    public Sala(String nome, ArrayList<Socket> participantes){
        this.participantes = participantes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Socket> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(ArrayList<Socket> participantes) {
        this.participantes = participantes;
    }

    public void addParticipante(Socket participante){
        this.participantes.add(participante);
    }

    public void removeParticipante(int indice){
        this.participantes.remove(indice);
    }

    public void removeParticipante(Socket participante){
        this.participantes.remove(participante);
    }

    public void addCategoria(String categoria){
        this.categorias.add(categoria);
    }

    public void removeCategoria(int indice){
        this.categorias.remove(indice);
    }

    public void removeCategoria(String categoria){
        this.categorias.remove(categoria);
    }

    public String exibirSala(){
        String exibicao = "";
        exibicao += "Nome da sala: " + this.nome + "\n";

        if (this.categorias.size() > 0){
            exibicao += "Categorias: \n";
            for (String categoria : this.categorias){
                exibicao += categoria + "\n";
            }
        }

        if (this.participantes.size() > 0){
            exibicao += "Participantes: \n";
            for (Socket participante : this.participantes){
                exibicao += "End ip: " + participante.getInetAddress() + " Porta: " + participante.getPort() + "\n";
            }
        }

        exibicao += "Total de rodadas: " + this.totalRodadas;
        return exibicao;
    }
}
