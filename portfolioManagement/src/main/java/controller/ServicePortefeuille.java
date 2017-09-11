package controller;

import dao.AllocationWecoHistoriqueDAO;
import dao.AssureurDAO;
import dao.ClientFinalDAO;
import dao.PortefeuilleGDAO;
import dao.PortefeuilleHistoriqueDAO;
import model.AllocationWecoHistorique;
import model.Assureur;
import model.ClientFinal;

public class ServicePortefeuille {
 
     private PortefeuilleGDAO portefeuilleGDAO;
     
     private PortefeuilleHistoriqueDAO portefeuilleHistoriqueDAO;
     
	 private ClientFinalDAO clientFinalDAO;

	 private AllocationWecoHistoriqueDAO allocationWecoHistoriqueDAO;
     
     private AssureurDAO assureurDAO;
     
	 
	public ServicePortefeuille(PortefeuilleGDAO portefeuilleGDAO, PortefeuilleHistoriqueDAO portefeuilleHistoriqueDAO,
			ClientFinalDAO clientFinalDAO, AllocationWecoHistoriqueDAO allocationWecoHistoriqueDAO,
			AssureurDAO assureurDAO) {
		super();
		this.portefeuilleGDAO = portefeuilleGDAO;
		this.portefeuilleHistoriqueDAO = portefeuilleHistoriqueDAO;
		this.clientFinalDAO = clientFinalDAO;
		this.allocationWecoHistoriqueDAO = allocationWecoHistoriqueDAO;
		this.assureurDAO = assureurDAO;
	}


	public void initAssociatedTables() {

		clientFinalDAO.checkFictif();
		
		assureurDAO.checkFictif();
		
		allocationWecoHistoriqueDAO.checkFictif();
	
	}

	public void findAllPtfTopaze () {
		
		portefeuilleGDAO.findAllPtfTopaze(portefeuilleHistoriqueDAO,clientFinalDAO , allocationWecoHistoriqueDAO, assureurDAO);

	}
	
}
