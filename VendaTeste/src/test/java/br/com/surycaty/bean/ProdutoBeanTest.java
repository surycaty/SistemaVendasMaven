package br.com.surycaty.bean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import br.com.surycaty.beans.ProdutoBean;
import br.com.surycaty.entities.Produto;

public class ProdutoBeanTest {
	
	private static ProdutoBean produtoBean = new ProdutoBean();
	
	@BeforeClass
	public static void setup(){
		 
		Produto p1 = new Produto("Produto Novo 01", "L", new Date(), 123.35f);
		produtoBean.setProduto(p1);		
		produtoBean.salvar();
		
		Produto p2 = new Produto("Produto Novo 02", "Kg", new Date(), 97.86f);
		produtoBean.setProduto(p2);		
		produtoBean.salvar();
		
		Produto p3 = new Produto("Produto Novo 03", "Un", new Date(), 321.77f);
		produtoBean.setProduto(p3);		
		produtoBean.salvar();

	}

	@Test
	public void salvarProdutoTest() {
		Produto p = produtoBean.listarPorNome("o 02").get(0);
		
		assertEquals("Produto Novo 02", p.getNome());
		assertEquals("Kg", p.getUnidade());
	}
	
	@Test
	public void atualizarProdutoTest() {
		
		Produto ProdutoAnterior = produtoBean.listarPorNome("Novo 01").get(0);
		Produto produtoAlterado = produtoBean.listarPorNome("Novo 01").get(0);
		
		produtoAlterado.setUnidade("litros");
		
		produtoBean.setProduto(produtoAlterado);
		
		produtoBean.salvar();
		
		assertEquals(ProdutoAnterior.getNome(), produtoAlterado.getNome());
		assertNotEquals(ProdutoAnterior.getUnidade(), produtoAlterado.getUnidade());
		
	}
	
	@Test
	public void listarTodosProdutoTest(){
		int count = produtoBean.listarTodos().size();
		assertNotEquals(0, count);
		assertEquals(3, count);
	}
	
	//@Test
	public void excluirProdutoTest(){
		 
		for (Produto p : produtoBean.listarTodos()) {
			produtoBean.setProduto(p);
		}
		
		assertEquals(0, produtoBean.listarTodos().size());
	}

}
