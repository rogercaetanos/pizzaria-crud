package com.itb.escola.pizzaria.controller;


import com.itb.escola.pizzaria.controller.dtorequest.ProdutoRequest;
import com.itb.escola.pizzaria.controller.dtoresponse.DataArray;
import com.itb.escola.pizzaria.controller.dtoresponse.DataObject;
import com.itb.escola.pizzaria.controller.dtoresponse.ObjectDeleteResponse;
import com.itb.escola.pizzaria.exceptions.BadRequest;
import com.itb.escola.pizzaria.model.Categoria;
import com.itb.escola.pizzaria.model.Produto;
import com.itb.escola.pizzaria.services.CategoriaService;
import com.itb.escola.pizzaria.services.ProdutoService;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;


//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataObject> findProdutoById(@PathVariable(value = "id") String id) {
        try {
            Long value = Long.parseLong(id);
            return ResponseEntity.ok().body(new DataObject(200, produtoService.findById(value)));
        } catch (NumberFormatException ex) {
            // caso linha abaixo, o retorno seri um Object e não Produto
            //return ResponseEntity.badRequest().body("Erro: '" + id + "' não é um número inteiro válido. Por favor, forneça um valor inteiro, como 42.");
            throw new BadRequest("'" + id + "' não é um número inteiro válido. Por favor, forneça um valor inteiro, como 42.");
        }
    }

    @GetMapping()
    public ResponseEntity<DataArray> findAllProdutos() {

        return ResponseEntity.ok().body(new DataArray(200,produtoService.findAll()));
    }

    @PostMapping()
    public ResponseEntity<DataObject> saveProduto(@RequestBody ProdutoRequest produtoRequest) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/produto").toUriString());
        Produto produto = criarProduto(produtoRequest);
        if(produtoRequest.getCategoriaId() != null) {
            Categoria categoria = new Categoria();
            categoria.setId(produtoRequest.getCategoriaId());
            produto.setCategoria(categoria);
        }

        return ResponseEntity.created(uri).body(new DataObject(201,produtoService.save(produto)));

    }

    @PutMapping("/{id}")
    public ResponseEntity<DataObject> updateProduto(@RequestBody ProdutoRequest produtoRequest, @PathVariable(value = "id") String id) {
        try {
            Produto produto = criarProduto(produtoRequest);
            if(produtoRequest.getCategoriaId() != null) {
                Categoria categoria = new Categoria();
                categoria.setId(produtoRequest.getCategoriaId());
                produto.setCategoria(categoria);
            }
            return ResponseEntity.ok().body(new DataObject(200,produtoService.update(produto, Long.parseLong(id))));
        } catch (NumberFormatException ex) {
            throw new BadRequest("'" + id + "' não é um número inteiro válido. Por favor, forneça um valor inteiro, como 42.");
        }

    }

    @PutMapping("/delete-logic/{id}")
    @Transactional
    public ResponseEntity<ObjectDeleteResponse> deleteLogicProduto(@PathVariable(value = "id") String id) {
        try {

            return ResponseEntity.ok().body(new ObjectDeleteResponse(200,"Produto excluído com sucesso",
                                                                      produtoService.deleteLogic(Long.parseLong(id)).getId()));
        } catch (NumberFormatException ex) {
            throw new BadRequest("'" + id + "' não é um número inteiro válido. Por favor, forneça um valor inteiro, como 42.");
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<ObjectDeleteResponse> deleteProduto(@PathVariable(value = "id") String id) {
        try {
            if (produtoService.delete(Long.parseLong(id))) {
                return ResponseEntity.ok().body(new ObjectDeleteResponse(200,"Produto excluído com sucesso ",Long.parseLong(id)));
            }
        } catch (NumberFormatException ex) {
            throw new BadRequest("'" + id + "' não é um número inteiro válido. Por favor, forneça um valor inteiro, como 5.");
        }
        return ResponseEntity.ok().body(new ObjectDeleteResponse(200,"Não foi possível excluir o produto ",Long.parseLong(id)));
    }

    private Produto criarProduto (ProdutoRequest produtoRequest) {
        Produto produto = new Produto();
        produto.setNome(produtoRequest.getNome());
        produto.setDescricao(produtoRequest.getDescricao());
        produto.setTipo(produtoRequest.getTipo());
        produto.setPrecoVenda(produtoRequest.getPrecoVenda());
        produto.setPrecoCompra(produtoRequest.getPrecoCompra());
        produto.setQuantidadeEstoque(produtoRequest.getQuantidadeEstoque());
        produto.setCodStatus(produtoRequest.isCodStatus());
        return produto;
    }

}
