package com.itb.escola.pizzaria.services;


import com.itb.escola.pizzaria.exceptions.BadRequest;
import com.itb.escola.pizzaria.exceptions.NotFound;
import com.itb.escola.pizzaria.model.Categoria;
import com.itb.escola.pizzaria.model.Produto;
import com.itb.escola.pizzaria.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaService categoriaService;

    public ProdutoServiceImpl(ProdutoRepository produtoRepository, CategoriaService categoriaService) {
        this.produtoRepository = produtoRepository;
        this.categoriaService = categoriaService;
    }

    @Override
    public Produto findById(Long id) {

        try {
            Produto produto = produtoRepository.findById(id).get();
            return produto;
        } catch (Exception e) {
            throw new NotFound("Produto não encontrado com o id " + id);

        }
    }

    @Override
    public Produto findByIdActive(Long id) {
        Produto produto = produtoRepository.findByIdActive(id);
        if (produto == null) {
            throw new NotFound("Produto não encontrado com o id " + id);
        }
        return produto;
    }

    @Override
    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    @Override
    public List<Produto> findByAllActive() {
        return produtoRepository.findByAllActive();
    }

    @Override
    @Transactional
    public Produto save(Produto produto) {
        if (!produto.validarProduto()) {
            throw new BadRequest(produto.getMensagemErro());
        }
        // É possível salvar o produto sem categoria, pois a chave estrangeria é NULL
        if (produto.getCategoria() != null) {
            Categoria categoria = categoriaService.findById(produto.getCategoria().getId());
            if (categoria == null) {
                throw new BadRequest("Não foi encontrado a categoria com o id " + produto.getCategoria().getId());
            }
        }
        return produtoRepository.save(produto);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        if (produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
            return true;
        } else {
            throw new NotFound("Produto não encontrado com o id " + id);
        }
    }

    @Transactional
    @Override
    public Produto update(Produto produto, Long id) {
        if (!produto.validarProduto()) {
            throw new BadRequest(produto.getMensagemErro());
        }
        if (!produtoRepository.existsById(id)) {
            throw new NotFound("Produto não encontrado com o id " + id);
        }
        Produto produtoDb = produtoRepository.findById(id).get();
        produtoDb.setNome(produto.getNome());
        produtoDb.setDescricao(produto.getDescricao());
        produtoDb.setTipo(produto.getTipo());
        produtoDb.setPrecoVenda(produto.getPrecoVenda());
        produtoDb.setCodStatus(produto.isCodStatus());
      //  if (produto.getPrecoCompra() != null && produto.getPrecoCompra().compareTo(BigDecimal.ZERO) > 0) {
        produtoDb.setPrecoCompra(produto.getPrecoCompra());
      //  }
      //  if (produto.getQuantidadeEstoque() > 0) {
         produtoDb.setQuantidadeEstoque(produto.getQuantidadeEstoque());
      //  }

        if(produto.getCategoria() != null) {
            Categoria categoriaDb = categoriaService.findById(produto.getCategoria().getId());
            if (categoriaDb == null) {
                throw new NotFound("Categoria não encontrada com o id " + produto.getCategoria().getId());
            }
            produtoDb.setCategoria(categoriaDb);
        }
        return produtoRepository.save(produtoDb);
    }

    @Override
    @Transactional
    public Produto deleteLogic(Long id) {

        try {
            Produto produto = produtoRepository.findById(id).get();
            produto.setCodStatus(false);
            return produto;
        } catch (Exception e) {
            throw new NotFound("Produto não encontrado com o id " + id);

        }
    }
}
