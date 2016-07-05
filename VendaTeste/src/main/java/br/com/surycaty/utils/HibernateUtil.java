package br.com.surycaty.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	
	private static final SessionFactory sessionFactory = buildSessionFactory();
	//private static final ServiceRegistry serviceRegistry;
	
	
	public static SessionFactory buildSessionFactory(){
		
		try {
			Configuration cfg = new Configuration();
			cfg.configure("hibernate.cfg.xml");
			
			return cfg.buildSessionFactory();
			
		} catch (Throwable e) {
			System.out.println("DEU PAU NO HIBERNATE: " + e.getMessage());
			
			throw new ExceptionInInitializerError();
		}
	}
	
	/*static{
		Configuration configuration = new Configuration().configure();
		
		serviceRegistry =  new ServiceRegistryBuilder().applySettings(
					configuration.getProperties()).buildServiceRegistry();
		
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	}*/
	
	public static SessionFactory getSessionFactory(){
		return sessionFactory;
	}

}
