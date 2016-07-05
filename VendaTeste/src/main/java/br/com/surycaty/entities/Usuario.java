package br.com.surycaty.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Usuario {

	@Id
	@GeneratedValue
	@Column(name="id_usuario")
	private Integer idUsuario;
	
	@Column(nullable=false, length=16, unique=true)
	private String login;
	
	@Column(nullable=false, length=50)
	private String senha;
	
	private String nome;
	
	@Column(nullable=false, length=1)
	private String tipoUsuario;
	
	@Column(name="data_cadastro", nullable=false)
	private Date dataCadastro;
	
	public Usuario(){	}
	
	public Usuario(String login, String senha, String nome, String tipoUsuario, Date dataCadastro) {
		this.login = login;
		this.senha = senha;
		this.nome = nome;
		this.tipoUsuario = tipoUsuario;
		this.dataCadastro = dataCadastro;
	}



	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

}
