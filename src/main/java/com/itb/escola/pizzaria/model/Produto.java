package com.itb.escola.pizzaria.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.itb.escola.pizzaria.util.BigDecimalDeserializer;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "produtos")
public class Produto {

    // Encapsulamento : proteger meus atributos do acesso desordenado, pode estar ligado
    //                 às questões de validação

    // Para obter o encapsulamento temos que seguir alguns passos:

    // 1º Passo : Trabalhar os modificadores de acesso :
    // Temos três:
    // public    ->  Acesso livre para todas as classes
    // private   -> Acesso permitido apenas dentro da classe
    // protected -> Acesso permitido apenas para as classes filhas (Herança)

    // Enfim, deixar os atributos private ou protected

    // 2º Passo: Criar métodos de acesso aos atributos, uma prática muito utilizada
    //           é criar os métodos SETTER´S E GETTER´S
    //   SET -> atribuir a informação
    //   GET -> recuperar a informação

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String nome;
    @Column(nullable = true, length = 45)
    private String tipo;
    @Column(nullable = true, length = 250)
    private String descricao;
    @Column(nullable = true, columnDefinition = "DECIMAL(5,2)")
    @JsonDeserialize(using = BigDecimalDeserializer.class)
    private BigDecimal precoCompra = BigDecimal.ZERO;
    @Column(nullable = true, columnDefinition = "DECIMAL(5,2)")
    @JsonDeserialize(using = BigDecimalDeserializer.class)
    private BigDecimal precoVenda = BigDecimal.ZERO;;
    @Column(nullable = true)
    private int quantidadeEstoque = 0;
    private boolean codStatus = true;

    public Produto() {

    }

    public Produto(Long id, String nome,String tipo, BigDecimal precoVenda, int quantidadeEstoque) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.precoVenda = precoVenda;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public Produto(Long id,String nome,String tipo, BigDecimal precoVenda, int quantidadeEstoque, Categoria categoria) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.precoVenda = precoVenda;
        this.quantidadeEstoque = quantidadeEstoque;
        this.categoria = categoria;
    }



    // @ManyToOne :  Muitos para UM
    @ManyToOne (cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id", referencedColumnName = "id", nullable = true)
    private Categoria categoria;


    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ItemPedido> itemPedidos = new ArrayList<ItemPedido>();

    // Atributos de apoio
    @Transient
    @JsonIgnore
    private String mensagemErro = "";
    @Transient
    @JsonIgnore
    private boolean isValid = true;

    // void: "mudo", ou seja, o método não tem retorno

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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPrecoCompra() {
        return precoCompra;
    }

    public void setPrecoCompra(BigDecimal precoCompra) {
        this.precoCompra = precoCompra;
    }

    public BigDecimal getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(BigDecimal precoVenda) {
        this.precoVenda = precoVenda;
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<ItemPedido> getItemPedidos() {
        return itemPedidos;
    }

    public void setItemPedidos(List<ItemPedido> itemPedidos) {
        this.itemPedidos = itemPedidos;
    }

    public String getMensagemErro() {
        return mensagemErro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return Objects.equals(id, produto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean validarProduto() {

        if(nome == null || nome.isEmpty()){
            mensagemErro += "O nome do produto é obrigatório:";
            isValid = false;
        }
        if(tipo == null || tipo.isEmpty()){
            mensagemErro += "O tipo do produto é obrigatório:";
            isValid = false;
        }
        if(descricao == null || descricao.isEmpty()){
            mensagemErro += "A descrição do produto é obrigatório:";
            isValid = false;
        }
        if (precoVenda.compareTo(BigDecimal.ZERO) < 0) {
            mensagemErro += "O preço de venda do produto deve ser maior que zero:";
            isValid = false;
        }

        if (precoCompra.compareTo(BigDecimal.ZERO) < 0) {
            mensagemErro += "O preço de compra do produto deve ser maior que zero:";
            isValid = false;
        }

        return isValid;
    }


}
