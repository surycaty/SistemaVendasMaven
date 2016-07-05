package br.com.surycaty.beans;

import javax.faces.bean.ManagedBean;

import br.com.surycaty.entities.ProdutoVenda;

@ManagedBean
public class ProdutoVendaBean {

	private ProdutoVenda produtoVenda;

	public ProdutoVenda getProdutoVenda() {
		return produtoVenda;
	}

	public void setProdutoVenda(ProdutoVenda produtoVenda) {
		this.produtoVenda = produtoVenda;
	}
	
	public void salvar(){
		
	}
}
