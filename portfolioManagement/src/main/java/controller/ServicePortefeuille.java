package controller;

import dao.ClientFinalDAO;
import dao.PortefeuilleGDAO;
import dao.PortefeuilleHistoriqueDAO;

public class ServicePortefeuille {
 
     private PortefeuilleGDAO portefeuilleGDAO;
     
     private PortefeuilleHistoriqueDAO portefeuilleHistoriqueDAO;
     
	 private ClientFinalDAO clientFinalDAO;

	public ServicePortefeuille(PortefeuilleGDAO portefeuilleGDAO, PortefeuilleHistoriqueDAO portefeuilleHistoriqueDAO,
			ClientFinalDAO clientFinalDAO) {
		super();
		this.portefeuilleGDAO = portefeuilleGDAO;
		this.portefeuilleHistoriqueDAO = portefeuilleHistoriqueDAO;
		this.clientFinalDAO = clientFinalDAO;
	}
	 
	public void findAllPtfTopaze () {
		
		portefeuilleGDAO.findAllPtfTopaze(portefeuilleHistoriqueDAO,clientFinalDAO);

	}
	
}
