package br.com.surycaty.utils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.surycaty.entities.Produto;

@FacesConverter(value="ConversorProduto")
public class Conversor implements Converter {

	@Override
	public Object getAsObject(FacesContext contexto, UIComponent component, String value) {
		Integer i = 0;
		Produto p = new Produto();
		
		try{
			i = Integer.valueOf(value);
			
			if (i > 0) {
				
	            p.setIdProduto(i);
	            
	            return p;
	        }
			
		}catch(Exception nEx)
		{
			p = new Produto();
		}
		
        return p;
	}

	@Override
	public String getAsString(FacesContext contexto, UIComponent component, Object obj) {
		
		if(obj != null && !"".equals(obj)){
			try{
				return String.valueOf(((Produto)obj).getIdProduto());
			}
			catch(ConverterException cex){
				return null;
			}
		}
		
		return null;
	}

	
}
