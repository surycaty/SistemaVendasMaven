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

import br.com.surycaty.entities.Usuario;
import br.com.surycaty.utils.HibernateUtil;

@ManagedBean
@ViewScoped
public class UsuarioBean {
	
	private Session session = HibernateUtil.getSessionFactory().openSession();
    private Transaction transaction = null;
	
	private Usuario usuario = new Usuario();
	
	private List<Usuario> listaUsuario = null;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getListaUsuario() {
		
		if(listaUsuario == null){
			listaUsuario = listarTodos();
		}
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}
	
	public void salvar(){
		
		session = HibernateUtil.getSessionFactory().openSession();
	    transaction = null;
				
		String textoMensagem = null;
		FacesMessage msg = null;
		
		try{
			transaction = session.beginTransaction();
			
			if(usuario.getIdUsuario() != null && usuario.getIdUsuario() > 0){
				session.update(usuario);
				textoMensagem = "Usuário Atualizado!";
			}else{
				usuario.setDataCadastro(new Date());
				session.save(usuario);
				textoMensagem = "Usuário Cadastrado!";
			}
			
			transaction.commit();
			
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, textoMensagem, null);
        	        	
        	usuario = new Usuario();
			
		}catch(HibernateException hEx){
			transaction.rollback();
			
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao Salvar Usuário!", hEx.getMessage());

		}finally{
			session.close();
			
			if(FacesContext.getCurrentInstance() != null){
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		}
	}

	public void atualizar(Usuario usuario){
		this.usuario = usuario;
	}
	
	public void excluir(Usuario usuario){
		session = HibernateUtil.getSessionFactory().openSession();
        transaction = null;
        
        FacesMessage msg = null;
                
        try {
            transaction = session.beginTransaction();
            
            session.delete(usuario);
            
            transaction.commit();
            
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário '" + usuario.getNome() + "' foi excluído!", null);
        	
        } catch (HibernateException e) {
            transaction.rollback();
            
        	FacesContext.getCurrentInstance().addMessage(null, msg);
        } finally {
            session.close();
            
            if(FacesContext.getCurrentInstance() != null){
            	FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
	}
	
	@SuppressWarnings("unchecked")
	public List<Usuario> listarTodos() {
		
		session = HibernateUtil.getSessionFactory().openSession();
        transaction = null;
                
        try {
            transaction = session.beginTransaction();
            listaUsuario = session.createQuery("FROM Usuario").list();
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
        } finally {
            session.close();
        }
        return listaUsuario;
	}
	
	@SuppressWarnings("unchecked")
	public List<Usuario> listarPorNome(String nomeLike){
		session = HibernateUtil.getSessionFactory().openSession();

        listaUsuario = null;
        
        try {
            Query query = session.createQuery("FROM Usuario u where u.nome LIKE :nome "); 
            listaUsuario = query.setParameter("nome", "%" + nomeLike + "%") .list();
            
            if(listaUsuario.size() == 0){
            	FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro Usuário", "Usuário não encontrado!");
            	
            	if(FacesContext.getCurrentInstance() != null)
            		FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            
            return listaUsuario;
            
        } catch (HibernateException e) {
        	
        	return new ArrayList<Usuario>();
        } finally {
            session.close();
        }
	}
}
