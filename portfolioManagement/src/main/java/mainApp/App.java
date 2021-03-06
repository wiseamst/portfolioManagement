package mainApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.text.ParseException;

import javax.xml.bind.JAXBException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;

import controller.ServiceAllocation;
import controller.ServiceAllocationWeco;
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
import series.Fonctions;
import xml.RunXml;

public class App {

	public static void main(String[] args) throws DataAccessException, ParseException, JAXBException {
 
		try {
			
			//Connection avec Topaze
			Connection con = DriverManager.getConnection("jdbc:mysql://ns300088.ovh.net/topazeweb","wiseam","top2017aze!");
			
			System.out.println("Connection avec Topaze up");
		
			//Appel fichier de configuration springs
    	    ApplicationContext context = new ClassPathXmlApplicationContext("spring/Spring-Module.xml");
    	
    	    //Chargement des beans Spring
    	    //Un  bean dao pour chaque entité
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
            
            
            //Création des services
            
            ServiceAsset serviceAsset = new ServiceAsset(assetDAO,assetHistoriqueDAO);

            serviceAsset.findAllAssetTopaze();//Chargement des assets
            

            ServicePortefeuille servicePortefeuille = new ServicePortefeuille(portefeuilleGDAO, portefeuilleHistoriqueDAO, clientFinalDAO, assureurDAO);
            
            servicePortefeuille.initAssociatedTables();//chargement fictif Client + Assueur ( id=1)
            
            servicePortefeuille.findAllPtfTopaze();//Chargement des portefeuilles + portefeuilles hist
            
            ServiceAllocation serviceAllocation = new ServiceAllocation(allocationDAO,allocationHistoriqueDAO);

            serviceAllocation.findAllAllocationTopaze();//Chargement des allocs

            ServiceAllocationWeco serviceAllocationWeco = new ServiceAllocationWeco(allocationWecoHistoriqueDAO);
            
            serviceAllocationWeco.findAllWeco(portefeuilleGDAO);//Chargement des wecos
         

            
		} catch (SQLException e) {
			System.out.println("Connection down");
			e.printStackTrace();
		}
	
	}

}
