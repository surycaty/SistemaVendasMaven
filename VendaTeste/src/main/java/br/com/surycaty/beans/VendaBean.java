package br.com.surycaty.beans;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.surycaty.entities.Caixa;
import br.com.surycaty.entities.Produto;
import br.com.surycaty.entities.ProdutoVenda;
import br.com.surycaty.entities.Usuario;
import br.com.surycaty.entities.Venda;
import br.com.surycaty.utils.HibernateUtil;

@ManagedBean
@ViewScoped
public class VendaBean {
	
	private Venda venda = new Venda();
	
	private Caixa caixa = new Caixa();
	
	private Produto produtoAdicionado = new Produto();
	
	private List<ProdutoVenda> listaProdutoVenda = new ArrayList<ProdutoVenda>();
		
	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public Produto getProdutoAdicionado() {
		return produtoAdicionado;
	}

	public void setProdutoAdicionado(Produto produtoAdicionado) {
		this.produtoAdicionado = produtoAdicionado;
	}
	
	public List<ProdutoVenda> getListaProdutoVenda() {
		return listaProdutoVenda;
	}

	public void setListaProdutoVenda(List<ProdutoVenda> listaProdutoVenda) {
		this.listaProdutoVenda = listaProdutoVenda;
	}
	
	public void adicionaProduto(){
		
		if(produtoAdicionado.getIdProduto() == null || produtoAdicionado.getIdProduto() == 0){
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Preencher Código do Produto!", null );
        	if(FacesContext.getCurrentInstance() != null){
        		FacesContext.getCurrentInstance().addMessage(null, msg);
        	}
        	
        	produtoAdicionado = new Produto();
        	
        	return;
		}
		
		Produto recuperado = new ProdutoBean().isProduto(produtoAdicionado);
		
		if(recuperado != null){
			
			if(listaProdutoVenda != null){
				boolean existeItem = false;
				for (ProdutoVenda produtoVenda : listaProdutoVenda) {
					if(produtoVenda.getProduto().getIdProduto() == recuperado.getIdProduto()){
						produtoVenda.setQuantidade(produtoVenda.getQuantidade() + 1);
						produtoVenda.setValorPorProduto(produtoVenda.getValorPorProduto() + recuperado.getValor());
						
						existeItem = true;
					}
				}
				
				if(!existeItem){
					ProdutoVenda produtoVenda = new ProdutoVenda();
					produtoVenda.setProduto(recuperado);
					produtoVenda.setQuantidade(1);
					produtoVenda.setValorPorProduto(recuperado.getValor());
					produtoVenda.setValorUnitario(recuperado.getValor());
					
					listaProdutoVenda.add(produtoVenda);
				}
			}else{
				listaProdutoVenda = new ArrayList<>();
				ProdutoVenda produtoVenda = new ProdutoVenda();
				produtoVenda.setProduto(recuperado);
				produtoVenda.setQuantidade(1);
				produtoVenda.setValorPorProduto(recuperado.getValor());
				produtoVenda.setValorUnitario(recuperado.getValor());
				
				listaProdutoVenda.add(produtoVenda);
				
			}
		}
		
		produtoAdicionado = new Produto();
		
		float valor = 0;
		for (ProdutoVenda pv : listaProdutoVenda) {
			valor = (valor + pv.getValorPorProduto());
		}
		
		venda.setValor(valor);
	}

	public void salvar(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		FacesMessage msg = null;
		
		try{
			transaction = session.beginTransaction();
						
			session.save(venda);
			
			for (ProdutoVenda produtoVenda : listaProdutoVenda) {
				produtoVenda.setVenda(venda);
				session.save(produtoVenda);
			}
			
			//salvar Caixa
			Usuario usuario = new Usuario();
			usuario.setIdUsuario(1);
			
			caixa.setTipoTranzacao("E");
			caixa.setDescricao("Venda");
			caixa.setValor(venda.getValor());
			caixa.setUsuario(usuario);
			
			session.save(caixa);
			
			transaction.commit();
			
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Venda Finalizada Com Sucesso!", null );
        	
        	
        	venda = new Venda();
        	produtoAdicionado = new Produto();
        	listaProdutoVenda = new ArrayList<ProdutoVenda>();
        	
		}catch(HibernateException hiEx){
			transaction.rollback();
			msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro ao Efetuar a Venda!", null );
		}
		finally{
			session.close();
			
			if(FacesContext.getCurrentInstance() != null)
				FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public void salvarTeste(){
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		
		try{
			transaction = session.beginTransaction();
			
			venda.setFormaPagamento(1);
			venda.setValor(21.34f);
			
			List<ProdutoVenda> produtos = new ArrayList<ProdutoVenda>();
			
			ProdutoVenda pv1 = new ProdutoVenda();
			//pv1.setIdProdutoVenda(1);
			Produto p1 = new Produto();
			p1.setIdProduto(1);
			
			pv1.setProduto(p1);
			pv1.setQuantidade(3);
			pv1.setValorUnitario(1.2f);
			pv1.setValorPorProduto(3.6f);
			
			
			ProdutoVenda pv2 = new ProdutoVenda();
			//pv1.setIdProdutoVenda(1);
			Produto p2 = new Produto();
			p2.setIdProduto(2);
			
			pv2.setProduto(p2);
			pv2.setQuantidade(2);
			pv2.setValorUnitario(1.4f);
			pv2.setValorPorProduto(2.8f);
						
			produtos.add(pv1);
			produtos.add(pv2);
			
			//venda.setProdutosVenda(produtos);
			
			session.save(venda);
			for (ProdutoVenda produtoVenda : produtos) {
				produtoVenda.setVenda(venda);
				session.save(produtoVenda);
			}
						
			transaction.commit();
		}catch(HibernateException hiEx){
			System.out.println(hiEx.getStackTrace());
			System.out.println(hiEx.getMessage());
			transaction.rollback();
		}
		finally{
			session.close();
		}
	}

	public void popularBanco(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		
		try{
			transaction = session.beginTransaction();
			
			Produto p1 = new Produto();
			p1.setIdProduto(1);
			p1.setNome("Produto 01");
			p1.setUnidade("Kg");
			p1.setValor(12.34f);
			
			Produto p2 = new Produto();
			p2.setIdProduto(2);
			p2.setNome("Produto 02");
			p2.setUnidade("Kg");
			p2.setValor(23.45f);
			
			Produto p3 = new Produto();
			p3.setIdProduto(3);
			p3.setNome("Produto 03");
			p3.setUnidade("Kg");
			p3.setValor(34.56f);
			
			session.save(p1);
			session.save(p2);
			session.save(p3);
			
			Usuario u1 = new Usuario();
			u1.setIdUsuario(1);
			u1.setNome("Usuario Teste");
			u1.setSenha("senha");
			u1.setTipoUsuario("A");
			u1.setLogin("teste");
			u1.setDataCadastro(new Date());
			
			session.save(u1);
			
			transaction.commit();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Banco populado!", null );
        	FacesContext.getCurrentInstance().addMessage(null, msg);
        	        	
		}catch(HibernateException hiEx){
			transaction.rollback();
		}
		finally{
			session.close();
		}
	}
}
