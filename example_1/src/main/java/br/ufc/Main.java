package br.ufc;

import java.util.List;
import java.util.Iterator;
import java.util.Properties;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.MappingException;
import net.sf.hibernate.Session;
import net.sf.hibernate.SessionFactory;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.cfg.Configuration;

public class Main {

	public static void main(String[] args) {
	
		System.out.println("Hello Hibernate!");
		
		try {
		
			Session session = getSessionFactory().openSession();
		
			Transaction tx = session.beginTransaction();
		
			Message message = new Message("Hello World");
		
			session.save(message);
		
			tx.commit();
		
			session.close();
		
			Session newSession = getSessionFactory().openSession();
		
			Transaction newTransaction = newSession.beginTransaction();
		
			List messages =
				newSession.find("from Message as m order by m.text asc");
			
			System.out.println(messages.size() + " message(s) found:");
			
			for (Iterator iter = messages.iterator(); iter.hasNext();) {
			
				Message loadedMessage = (Message) iter.next();
				System.out.println(message.getText());
			
			}
			
			newTransaction.commit();
			
			newSession.close();
		
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	
	}
	
	private static SessionFactory getSessionFactory()
			throws HibernateException {
	
		Configuration cfg = new Configuration();

		cfg.addClass(Message.class);
		
		Properties props = new Properties();
		
		props.put("hibernate.connection.driver_class",
				"org.postgresql.Driver");
		props.put("hibernate.connection.url",
				"jdbc:postgresql://localhost/enterprisedb");
		props.put("hibernate.connection.username",
				"postgres");
		props.put("hibernate.connection.password",
				"admin");
		props.put("hibernate.dialect",
				"net.sf.hibernate.dialect.PostgreSQLDialect");
				
		props.put("hibernate.c3p0.min_size", "5");
		props.put("hibernate.c3p0.max_size", "20");
		props.put("hibernate.c3p0.timeout", "300");
		props.put("hibernate.c3p0.max_statements", "50");
		props.put("hibernate.c3p0.idle_test_period", 3000);
		
		cfg.setProperties(props);
		
		return cfg.buildSessionFactory();
	
	}

}
