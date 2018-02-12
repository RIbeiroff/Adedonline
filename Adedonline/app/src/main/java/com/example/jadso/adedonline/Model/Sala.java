/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.jadso.adedonline.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Sala implements Serializable {
    public String nome;
    public int totalRodadas;
    public ArrayList<Categoria> categorias;
    public ArrayList<Participante> participantes;
        
    public Sala(){
        this.participantes = new ArrayList();
        this.categorias = new ArrayList();
    }
    
    public Sala(String nome, ArrayList<Participante> participantes){
        this.participantes = participantes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Participante> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(ArrayList<Participante> participantes) {
        this.participantes = participantes;
    }
    
    public void addParticipante(Participante participante){
        this.participantes.add(participante);
    }
    
    public void removeParticipante(int indice){
        this.participantes.remove(indice);
    }    
    
    public void removeParticipante(Participante participante){
        this.participantes.remove(participante);
    }
    
    public void addCategoria(Categoria categoria){
        this.categorias.add(categoria);
    }
    
    public void removeCategoria(int indice){
        this.categorias.remove(indice);
    }

    public void removeCategoria(Categoria categoria){
        this.categorias.remove(categoria);
    }
    
    public String exibirSala(){
        String exibicao = "";
        exibicao += "Nome da sala: " + this.nome + "\n";

        if (this.categorias.size() > 0){
            exibicao += "Categorias: \n";
            for (Categoria categoria : this.categorias){
                exibicao += categoria.getCategoria() + "\n";
            }
        }

        if (this.participantes.size() > 0){
            exibicao += "Participantes: \n";
            for (Participante participante : this.participantes){
                exibicao += "End ip: " + participante.EnderecoIp + " Porta: " + participante.porta + "\n";
            }
        }

        exibicao += "Total de rodadas: " + this.totalRodadas;
        return exibicao;
    }    
}