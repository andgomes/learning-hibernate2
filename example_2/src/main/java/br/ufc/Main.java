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

	private static SessionFactory sessionFactory;

	public static void main(String[] args) {
	
		System.out.println("Hello Hibernate!");
		
		try {
			
			sessionFactory = getSessionFactory();
			
			Session session = sessionFactory.openSession();
			
			Transaction tx = session.beginTransaction();
			
			Message message = new Message("Hello World");
			
			session.save(message);
			
			tx.commit();
			
			tx = session.beginTransaction();
		
			message = (Message) session.load(Message.class, 1L);
			
			message.setText("Greetings Earthling");
			
			Message nextMessage = new Message("Take me to your leader");
			
			message.setNextMessage(nextMessage);
			
			tx.commit();
			
			tx = session.beginTransaction();
			
			List messages =
				session.find("from Message as m order by m.text asc");
			
			System.out.println(messages.size() + " message(s) found:");
			
			for (Iterator iter = messages.iterator(); iter.hasNext();) {
			
				Message loadedMessage = (Message) iter.next();
				System.out.println(loadedMessage.getText());
			
			}
			
			tx.commit();
			
			session.close();
		
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	
	}
	
	private static SessionFactory getSessionFactory()
			throws HibernateException {
	
		Configuration cfg = new Configuration();
		
		return cfg.configure().buildSessionFactory();
	
	}

}
