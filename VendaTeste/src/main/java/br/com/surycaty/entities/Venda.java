package br.com.surycaty.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.surycaty.utils.Format;

@Entity
@Table(name="venda")
public class Venda {
	
	@Id
	@GeneratedValue
	@Column(name="id_venda")
	private Integer idVenda;
	
	private int formaPagamento;
	private float valor;
	
	/*@OneToMany
	@JoinTable(name="produto_venda",  
			joinColumns={@JoinColumn(name="id_venda")},  
	    	inverseJoinColumns={@JoinColumn(name="id_produto_venda")})
	private List<ProdutoVenda> produtosVenda;*/
	
	public Integer getIdVenda() {
		return idVenda;
	}
	
	public void setIdVenda(Integer idVenda) {
		this.idVenda = idVenda;
	}
	
	public int getFormaPagamento() {
		return formaPagamento;
	}
	
	public void setFormaPagamento(int formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	
	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = Format.moeda(valor);
	}
	
	/*public List<ProdutoVenda> getProdutosVenda() {
		return produtosVenda;
	}

	public void setProdutosVenda(List<ProdutoVenda> produtos) {
		this.produtosVenda = produtos;
	}*/

}
