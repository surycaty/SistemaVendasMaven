package br.com.surycaty.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import br.com.surycaty.utils.Format;

@Entity
public class Caixa {

	@Id
	@GeneratedValue
	@Column(name="id_caixa")
	private Integer idCaixa;
	
	@OneToOne
	private Usuario usuario;
	
	@Column(nullable=false, length=1)
	private String tipoTranzacao;
	
	private String descricao;
	
	private float valor;

	public Integer getIdCaixa() {
		return idCaixa;
	}

	public void setIdCaixa(Integer idCaixa) {
		this.idCaixa = idCaixa;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getTipoTranzacao() {
		return tipoTranzacao;
	}

	public void setTipoTranzacao(String tipoTranzacao) {
		this.tipoTranzacao = tipoTranzacao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = Format.moeda(valor);
	}
}
