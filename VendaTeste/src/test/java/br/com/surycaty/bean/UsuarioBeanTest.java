package br.com.surycaty.bean;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import br.com.surycaty.beans.UsuarioBean;
import br.com.surycaty.entities.Usuario;

public class UsuarioBeanTest {

	public static UsuarioBean usuarioBean = new UsuarioBean();
	
	@BeforeClass
	public static void setup(){
		
		Usuario u1 = new Usuario("admin", "aeiou","Admin", "A", new Date());
		
		usuarioBean.setUsuario(u1);
		usuarioBean.salvar();
		
		Usuario u2 = new Usuario("usuario", "123456","Usuário", "V", new Date());
				
		usuarioBean.setUsuario(u2);
		usuarioBean.salvar();
		
	}
	
	@Test
	public void salvarUsuarioTest() {
		Usuario u = usuarioBean.listarPorNome("Usu").get(0);
		assertEquals("usuario", u.getLogin());
		assertEquals("V", u.getTipoUsuario());
	}
	
	@Test
	public void alterarUsuarioTest(){
		Usuario u = usuarioBean.listarPorNome("Usu").get(0);
		Usuario u1 = usuarioBean.listarPorNome("Usu").get(0);
		
		u1.setNome("User");
		u1.setSenha("a1e2i3o4u5");
		
		usuarioBean.atualizar(u1);
		usuarioBean.salvar();
		
		Usuario uAlterado = usuarioBean.listarPorNome("Use").get(0);
		
		assertEquals("usuario", u.getLogin());
		assertEquals("V", u.getTipoUsuario());
		assertNotEquals(u.getNome(), uAlterado.getNome());
		assertNotEquals(u.getSenha(), uAlterado.getSenha());
	}

	
	public void excluirUsuarioTest(){
		
		for (Usuario usuario : usuarioBean.listarTodos()) {
			usuarioBean.excluir(usuario);
		}
	}
	
}
