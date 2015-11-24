package br.edu.imed.myfood.model;

/**
 * Created by diogo on 22/11/2015.
 */
public class Usuario {

    private Long id;

    private String nome;

    private String email;

    private String senha;

    private String senhaCfm;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSenhaCfm() {
        return senhaCfm;
    }

    public void setSenhaCfm(String senhaCfm) {
        this.senhaCfm = senhaCfm;
    }
}
