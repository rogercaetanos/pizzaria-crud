package com.itb.escola.pizzaria.services;

import com.itb.escola.pizzaria.exceptions.BadRequest;
import com.itb.escola.pizzaria.exceptions.NotFound;
import com.itb.escola.pizzaria.model.Categoria;
import com.itb.escola.pizzaria.model.Produto;
import com.itb.escola.pizzaria.repository.CategoriaRepository;
import com.itb.escola.pizzaria.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {
    private final CategoriaRepository categoriaRepository;
    private final ProdutoRepository produtoRepository;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository, ProdutoRepository produtoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.produtoRepository = produtoRepository;
    }

    @Override
    @Transactional
    public Categoria save(Categoria categoria) {
        categoria.setCodStatus(true);
        if(!categoria.validarCategoria()) {
            throw new BadRequest(categoria.getMensagemErro());
        }
        return categoriaRepository.save(categoria);
    }

    @Override
    public Categoria findById(Long id) {
        try {
            Categoria categoria = categoriaRepository.findById(id).get();
            return categoria;
        }catch (Exception e){
            throw new NotFound("Categoria não encontrada com o id " + id);

        }
    }

    @Override
    public Categoria findByIdActive(Long id) {
            Categoria categoria = categoriaRepository.findByIdActive(id);
            if(categoria == null) {
                throw new NotFound("Categoria não encontrada com o id " + id);
            }
            return categoria;
    }

    @Override
    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    @Override
    public void addProdutoToCategoria(Long categoriaId, Long  produtoId) {
     List<Produto> produtos = new ArrayList<>();
     Categoria categoria = categoriaRepository.findById(categoriaId).get();
     Produto produto = produtoRepository.findById(produtoId).get();
     produto.setCategoria(categoria);
     produtos.add(produto);
     categoria.setProdutos(produtos);
     produtoRepository.save(produto);

    }

    @Override
    public Categoria update(Categoria categoria, Long id) {
        if (!categoria.validarCategoria()) {
            throw new BadRequest(categoria.getMensagemErro());
        }
        if (!categoriaRepository.existsById(id)) {
            throw new NotFound("Categoria não encontrada com o id " + id);
        }
        Categoria categoriaDb = categoriaRepository.findById(id).get();
        categoriaDb.setNome(categoria.getNome());
        categoriaDb.setDescricao(categoria.getDescricao());
        categoriaDb.setCodStatus(categoria.isCodStatus());
        return categoriaRepository.save(categoriaDb);

    }

    @Override
    @Transactional
    public boolean delete(Long id) {

        if(categoriaRepository.existsById(id)){
            List<Produto> produtos =produtoRepository.findAll();
            for (int i = 0; i < produtos.size() ; i++) {
                if(produtos.get(i).getCategoria() == categoriaRepository.findById(id).get()) {
                    throw new BadRequest("Não é possível a exclusão da categoria, pois existem produto(s) categorizado(s)");
                }
            }
            categoriaRepository.deleteById(id);
            return true;
        }else {
            throw new NotFound("Categoria não encontrada com o id " + id);
        }
    }

    @Override
    @Transactional
    public Categoria deleteLogic(Long id) {
        if(categoriaRepository.existsById(id)){
            List<Produto> produtos =produtoRepository.findAll();
            for (int i = 0; i < produtos.size() ; i++) {
                if(produtos.get(i).getCategoria() == categoriaRepository.findById(id).get() && produtos.get(i).isCodStatus() == true) {
                    throw new BadRequest("Não é possível a exclusão da categoria, pois existem produto(s) categorizado(s)");
                }
            }
            Categoria categoria = categoriaRepository.findById(id).get();
            categoria.setCodStatus(false);
            return categoria;
        }else {
            throw new NotFound("Categoria não encontrada com o id " + id);
        }
    }

    @Override
    public List<Categoria> findByAllActive() {
        return categoriaRepository.findByAllActive();
    }

}
