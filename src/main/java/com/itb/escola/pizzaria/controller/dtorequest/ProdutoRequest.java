package com.itb.escola.pizzaria.controller.dtorequest;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.itb.escola.pizzaria.util.BigDecimalDeserializer;


import java.math.BigDecimal;

public class ProdutoRequest {
    private String nome;
    private String descricao;
    private String tipo;
    @JsonDeserialize(using = BigDecimalDeserializer.class)
    private BigDecimal precoVenda;
    @JsonDeserialize(using = BigDecimalDeserializer.class)
    private BigDecimal precoCompra = BigDecimal.ZERO;
    private int quantidadeEstoque = 0;
    private boolean codStatus = true;
    private Long categoriaId;

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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(BigDecimal precoVenda) {
        this.precoVenda = precoVenda;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public BigDecimal getPrecoCompra() {
        return precoCompra;
    }

    public void setPrecoCompra(BigDecimal precoCompra) {
        this.precoCompra = precoCompra;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public boolean isCodStatus() {
        return codStatus;
    }

    public void setCodStatus(boolean codStatus) {
        this.codStatus = codStatus;
    }
}
