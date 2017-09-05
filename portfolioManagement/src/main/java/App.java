import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import dao.AssureurDAO;
import model.Assureur;

public class App {

	public static void main(String[] args) {
 
		try {
		Connection con = DriverManager.getConnection("jdbc:mysql://ns300088.ovh.net/topazeweb","wiseam","top2017aze!");
		System.out.println("Connection établie");
		
    	ApplicationContext context =
        		new ClassPathXmlApplicationContext("spring/Spring-Module.xml");
    	

    		AssureurDAO assureurDAO = (AssureurDAO) context.getBean("assureurDAO");
            Assureur assureur = assureurDAO.findByAssureurTopaze(46);
            assureurDAO.insertAssureur(assureur);
            
            
	} catch (SQLException e) {
		System.out.println("Connection failed");
		e.printStackTrace();
	}

	}

}


/*	    Configuration configuration = new Configuration();
configuration = configuration.configure("database/hibernate.cfg.xml");
SessionFactory factory = configuration.buildSessionFactory();*/