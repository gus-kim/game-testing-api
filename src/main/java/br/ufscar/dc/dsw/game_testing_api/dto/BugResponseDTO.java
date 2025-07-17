package br.ufscar.dc.dsw.game_testing_api.dto;

public class BugResponseDTO {

    private Long id;
    private String titulo;
    private String descricao;

    public BugResponseDTO() {}

    public BugResponseDTO(Long id, String titulo, String descricao) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }
}
