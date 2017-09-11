package mainApp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import controller.ServiceAllocation;
import controller.ServiceAsset;
import controller.ServicePortefeuille;
import dao.AllocationDAO;
import dao.AllocationHistoriqueDAO;
import dao.AllocationWecoHistoriqueDAO;
import dao.AssetDAO;
import dao.AssetHistoriqueDAO;
import dao.AssureurDAO;
import dao.CGPDAO;
import dao.ClientFinalDAO;
import dao.PortefeuilleGDAO;
import dao.PortefeuilleHistoriqueDAO;

public class App {

	public static void main(String[] args) {
 
		try {
			
			Connection con = DriverManager.getConnection("jdbc:mysql://ns300088.ovh.net/topazeweb","wiseam","top2017aze!");
			System.out.println("Connection up");
		
    	    ApplicationContext context = new ClassPathXmlApplicationContext("spring/Spring-Module.xml");
    	
         
            AssetDAO assetDAO = (AssetDAO) context.getBean("assetDAO");
            
            AssetHistoriqueDAO assetHistoriqueDAO = (AssetHistoriqueDAO) context.getBean("assetHistoriqueDAO");
            
            AllocationDAO allocationDAO = (AllocationDAO) context.getBean("allocationDAO");
            
            AllocationHistoriqueDAO allocationHistoriqueDAO = (AllocationHistoriqueDAO) context.getBean("allocationHistoriqueDAO");
            
            AllocationWecoHistoriqueDAO allocationWecoHistoriqueDAO = (AllocationWecoHistoriqueDAO) context.getBean("allocationWecoHistoriqueDAO");
            
            AssureurDAO assureurDAO = (AssureurDAO) context.getBean("assureurDAO");
            
            CGPDAO cGPDAO = (CGPDAO) context.getBean("cGPDAO");
           
            
            ClientFinalDAO clientFinalDAO = (ClientFinalDAO) context.getBean("clientFinalDAO");
            
            PortefeuilleGDAO portefeuilleGDAO = (PortefeuilleGDAO) context.getBean("portefeuilleGDAO");
            
            PortefeuilleHistoriqueDAO portefeuilleHistoriqueDAO = (PortefeuilleHistoriqueDAO) context.getBean("portefeuilleHistoriqueDAO");
            
            ServiceAsset serviceAsset = new ServiceAsset(assetDAO,assetHistoriqueDAO);
            
            serviceAsset.findAllAssetTopaze();
            
            ServicePortefeuille servicePortefeuille = new ServicePortefeuille(portefeuilleGDAO, portefeuilleHistoriqueDAO, clientFinalDAO , allocationWecoHistoriqueDAO, assureurDAO);
            
            servicePortefeuille.initAssociatedTables();
            
            servicePortefeuille.findAllPtfTopaze();
            
            ServiceAllocation serviceAllocation = new ServiceAllocation(allocationDAO,allocationHistoriqueDAO);

            serviceAllocation.findAllAllocationTopaze();
                
            
		} catch (SQLException e) {
			System.out.println("Connection down");
			e.printStackTrace();
		}
	
	}

}


/*
 * Configuration configuration = new Configuration();
 * configuration = configuration.configure("database/hibernate.cfg.xml");
 * SessionFactory factory = configuration.buildSessionFactory();
 * */


/*
 * AssureurDAO assureurDAO = (AssureurDAO) context.getBean("assureurDAO");
 * Assureur assureur = assureurDAO.findByAssureurTopaze(121);
 * assureurDAO.insert(new Assureur(assureur.getCoordonnees(),assureur.getNomAssureur()));
 * */
