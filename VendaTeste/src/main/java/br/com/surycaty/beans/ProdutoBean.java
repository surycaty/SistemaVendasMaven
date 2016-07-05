package br.com.surycaty.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.surycaty.entities.Produto;
import br.com.surycaty.utils.HibernateUtil;

@ManagedBean
@ViewScoped
public class ProdutoBean {

	private Session session = HibernateUtil.getSessionFactory().openSession();
	private Transaction transaction = null;

	private Produto produto = new Produto();

	private List<Produto> produtos = new ArrayList<>();

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public void limparDados() {
		this.produto = new Produto();
	}

	public void atualizar(Produto produto) {
		this.produto = produto;
	}

	public void salvar() {

		session = HibernateUtil.getSessionFactory().openSession();
		transaction = null;
		String textoMensagem = null;
		FacesMessage msg = null;

		try {
			transaction = session.beginTransaction();

			if (produto.getIdProduto() != null && produto.getIdProduto() > 0) {
				session.update(produto);
				textoMensagem = "Produto Atualizado!";
			} else {
				produto.setDataCadastro(new Date());
				session.save(produto);
				textoMensagem = "Produto Cadastrado!";
			}

			transaction.commit();

			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, textoMensagem, null);

			produto = new Produto();
		} catch (HibernateException HiEx) {
			transaction.rollback();
			msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro ao Salvar Produto!", null);
		} finally {
			session.close();

			if (FacesContext.getCurrentInstance() != null) {
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<Produto> listarTodos() {

		session = HibernateUtil.getSessionFactory().openSession();

		produtos = null;

		try {

			produtos = session.createQuery("FROM Produto").list();

			return produtos;

		} catch (HibernateException e) {
			return new ArrayList<>();
		} finally {
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Produto> listarPorNome(String nomeLike) {

		session = HibernateUtil.getSessionFactory().openSession();

		produtos = null;

		try {
			Query query = session.createQuery("FROM Produto p where p.nome LIKE :nome ");
			produtos = query.setParameter("nome", "%" + nomeLike + "%").list();

			if (produtos.size() == 0) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro Produto",
						"Produto não encontrado!");

				if (FacesContext.getCurrentInstance() != null)
					FacesContext.getCurrentInstance().addMessage("erroProduto", msg);
			}

			return produtos;

		} catch (HibernateException e) {

			return new ArrayList<Produto>();
		} finally {
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	public Produto isProduto(Produto produto) {

		session = HibernateUtil.getSessionFactory().openSession();

		Produto retorno = null;
		try {

			Query query = session.createQuery("FROM Produto p where p.idProduto = :idProduto ");
			List<Produto> lista = query.setParameter("idProduto", produto.getIdProduto()).list();

			if (lista.size() > 0)
				retorno = lista.get(0);

			if (retorno == null) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Produto não encontrado!", null);

				if (FacesContext.getCurrentInstance() != null)
					FacesContext.getCurrentInstance().addMessage("erroProduto", msg);
			}

			return retorno;

		} catch (HibernateException e) {

			return null;
		} finally {
			session.close();
		}
	}
}
