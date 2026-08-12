package com.itb.escola.pizzaria.controller;


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

import java.net.URI;


//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<DataObject> findCategoriaById(@PathVariable(value = "id") String id) {
        try {
            return ResponseEntity.ok().body(new DataObject(200,categoriaService.findById(Long.parseLong(id))));
        } catch (NumberFormatException ex) {
            throw new BadRequest("'" + id + "' não é um número inteiro válido. Por favor, forneça um valor inteiro, como 5.");
        }
    }

    @GetMapping()
    @Transactional
    public ResponseEntity<DataArray> findAllCategorias() throws ClassNotFoundException {

        return ResponseEntity.ok().body(new DataArray(200, categoriaService.findAll()));
    }

    @PostMapping()
    @Transactional
    public ResponseEntity<DataObject> salvarCategoria(@RequestBody Categoria categoria) {
        categoria.setCodStatus(true);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/categoria").toUriString());
        return ResponseEntity.created(uri).body(new DataObject(201,categoriaService.save(categoria)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataObject> updateCategoria(@RequestBody Categoria categoria, @PathVariable(value = "id") String id) {
        try {
            return ResponseEntity.ok().body(new DataObject(200,categoriaService.update(categoria, Long.parseLong(id))));
        } catch (NumberFormatException ex) {
            throw new BadRequest("'" + id + "' não é um número inteiro válido. Por favor, forneça um valor inteiro, como 10.");
        }
    }

    @PutMapping("/delete-logic/{id}")
    @Transactional
    public ResponseEntity<DataObject> deleteLogicCategoria(@PathVariable(value = "id") String id) {
        try {
            return ResponseEntity.ok().body(new DataObject(200,categoriaService.deleteLogic(Long.parseLong(id))));
        } catch (NumberFormatException ex) {
            throw new BadRequest("'" + id + "' não é um número inteiro válido. Por favor, forneça um valor inteiro, como 42.");
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Object> deleteCategoria(@PathVariable(value = "id") String id) {
        try {
            if (categoriaService.delete(Long.parseLong(id))) {
                return ResponseEntity.ok().body("Categoria com o id " + id + " excluída com sucesso");
            }
        } catch (NumberFormatException ex) {
            throw new BadRequest("'" + id + "' não é um número inteiro válido. Por favor, forneça um valor inteiro, como 5.");
        }
        return ResponseEntity.ok().body("Não foi possível a exclusão da categoria com o id " + id);
    }

}
