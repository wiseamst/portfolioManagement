package mainApp;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
import xml.RunXml;

public class RunXmlForExtranet {

	public static void main(String[] args) throws ParseException, JAXBException {

		
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
		
		RunXml runXML = new RunXml(portefeuilleGDAO,portefeuilleHistoriqueDAO,allocationDAO,allocationWecoHistoriqueDAO,assetDAO,assetHistoriqueDAO); 
		
		runXML.runXmlAllPtfs();

		
	}

}
