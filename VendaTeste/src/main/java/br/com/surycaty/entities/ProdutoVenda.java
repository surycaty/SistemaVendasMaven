package br.com.surycaty.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.surycaty.utils.Format;

@Entity
@Table(name="produto_venda")
public class ProdutoVenda {
	
	@Id
	@GeneratedValue
	@Column(name = "id_produto_venda")
	private Integer idProdutoVenda;
	
	@OneToOne
	private Produto produto;
	
	@OneToOne
	/*@JoinTable(name="venda",  
	joinColumns={@JoinColumn(name="id_produto_venda")},  
	inverseJoinColumns={@JoinColumn(name="id_venda")})*/
	private Venda venda;
	
	private int quantidade;
	
	private float valorUnitario;
	
	private float valorPorProduto;

	public Integer getIdProdutoVenda() {
		return idProdutoVenda;
	}

	public void setIdProdutoVenda(Integer idProdutoVenda) {
		this.idProdutoVenda = idProdutoVenda;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public float getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(float valorUnitario) {
		this.valorUnitario = Format.moeda(valorUnitario);
	}

	public float getValorPorProduto() {
		return valorPorProduto;
	}

	public void setValorPorProduto(float valorPorProduto) {
		this.valorPorProduto = Format.moeda(valorPorProduto);
	}
	
}
