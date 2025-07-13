package br.ufscar.dc.dsw.game_testing_api.dto;

import java.util.List;

public class EstrategiaRequestDTO {
    
    private String nome;
    private String descricao;
    private String dicas;
    private List<String> imagens;

    // Construtores
    public EstrategiaRequestDTO() {}

    public EstrategiaRequestDTO(String nome, String descricao, String dicas, List<String> imagens) {
        this.nome = nome;
        this.descricao = descricao;
        this.dicas = dicas;
        this.imagens = imagens;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDicas() {
        return dicas;
    }

    public void setDicas(String dicas) {
        this.dicas = dicas;
    }

    public List<String> getImagens() {
        return imagens;
    }

    public void setImagens(List<String> imagens) {
        this.imagens = imagens;
    }
}
