package com.itb.escola.pizzaria;

import com.itb.escola.pizzaria.services.CategoriaService;
import com.itb.escola.pizzaria.model.Categoria;
import com.itb.escola.pizzaria.model.Produto;
import com.itb.escola.pizzaria.services.ProdutoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class PizzariaApplication {

	public static void main(String[] args) {

		SpringApplication.run(PizzariaApplication.class, args);

	}
  // String nome,  double precoVenda, int quantidadeEstoque, Categoria categoria
	@Bean
	CommandLineRunner run(ProdutoService produtoService, CategoriaService categoriaService){
		return args -> {
          if(categoriaService.findAll().size() == 0) {

			  Categoria categoria = new Categoria(null,"Pizzas Salgadas","Categorização de todas as pizzas salgadas: grandes e brotinhos");
			  categoria.setCodStatus(true);
			  Categoria categoria2 = new Categoria(null,"Pizzas Doces","Categorização de todas as pizzas doces: grandes e brotinhos");
			  categoria2.setCodStatus(true);
			  categoria = categoriaService.save(categoria);
			  categoria2 = categoriaService.save(categoria2);

			  Produto produto = new Produto(null,"Muçarela","Grande", new BigDecimal(47.99), 100 );
			  produto.setCodStatus(true);
			  produto.setDescricao("A pizza é coberta com molho de tomate, queijo tipo mussarela, azeitonas pretas e orégano e massa com fermentação natural");
			  Produto produto1 = new Produto(null,"Calabresa", "Grande",new BigDecimal(39.99), 100);
			  produto1.setCodStatus(true);
			  produto1.setDescricao("Uma deliciosa combinação de Linguiça Calabresa, rodelas de cebolas frescas, azeitonas pretas, mussarela, polpa de tomate, orégano e massa especial");
			  Produto produto2 = new Produto(null,"Romeu e Julieta", "Brotinho",new BigDecimal(35.60), 50);
			  produto2.setCodStatus(true);
			  produto2.setDescricao("O sabor leve da goiabada em conjunto com a mussarela é a representação do sucesso garantido");
			  Produto produto3 = new Produto(null,"Chocolate com morangos", "Brotinho",new BigDecimal(35.60), 50);
			  produto3.setCodStatus(true);
			  produto3.setDescricao("Pizza de Chocolate com Morangos deliciosa feita com Cobertura Chocolate Meio Amargo NESTLÉ DOIS FRADES e NESTLÉ Creme de Leite");

			  produto = produtoService.save(produto);
			  produto1 = produtoService.save(produto1);
			  produto2 = produtoService.save(produto2);
			  produto3 = produtoService.save(produto3);

			  categoriaService.addProdutoToCategoria(categoria.getId(), produto.getId());
			  categoriaService.addProdutoToCategoria(categoria.getId(), produto1.getId());
			  categoriaService.addProdutoToCategoria(categoria2.getId(), produto2.getId());
			  categoriaService.addProdutoToCategoria(categoria2.getId(), produto3.getId());

			}else {
				System.out.println("Pizzas já cadastradas no banco de dados!");
			}
		};
	}

}
