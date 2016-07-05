package br.com.surycaty.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.surycaty.utils.Format;

@Entity
@Table(name="produto")
public class Produto {
	
	@Id
	@GeneratedValue
	@Column(name="id_produto")
	private Integer idProduto;
	
	@Column(nullable=false)
	private String nome;
	
	@Column(nullable=false)
	private String unidade;
	
	@Column(name="data_cadastro", nullable=false)
	private Date dataCadastro;
		
	private float valor;
	
	public Produto(){}
	
	public Produto(String nome, String unidade, Date dataCadastro, float valor){
		this.nome = nome;
		this.unidade = unidade;
		this.dataCadastro = dataCadastro;
		this.valor = valor;
	}
	
	public Integer getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Integer idProduto) {
		this.idProduto = idProduto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = Format.moeda(valor);
	}	
}
