package controller;

import java.text.ParseException;
import org.springframework.dao.DataAccessException;
import dao.AssureurDAO;
import dao.ClientFinalDAO;
import dao.PortefeuilleGDAO;
import dao.PortefeuilleHistoriqueDAO;

public class ServicePortefeuille {
 
     private PortefeuilleGDAO portefeuilleGDAO;
     private PortefeuilleHistoriqueDAO portefeuilleHistoriqueDAO;  
	 private ClientFinalDAO clientFinalDAO; 
     private AssureurDAO assureurDAO;

	public ServicePortefeuille(PortefeuilleGDAO portefeuilleGDAO, PortefeuilleHistoriqueDAO portefeuilleHistoriqueDAO,
			ClientFinalDAO clientFinalDAO, AssureurDAO assureurDAO) {
		
		this.portefeuilleGDAO = portefeuilleGDAO;
		this.portefeuilleHistoriqueDAO = portefeuilleHistoriqueDAO;
		this.clientFinalDAO = clientFinalDAO;
		this.assureurDAO = assureurDAO;
	}

	public void initAssociatedTables() {

		clientFinalDAO.checkFictif();
		
		assureurDAO.checkFictif();
		
	}

	// Daily Integration
	public void findAllPtfTopaze () throws DataAccessException, ParseException {
		
		portefeuilleGDAO.findAllPtfTopaze(portefeuilleHistoriqueDAO,clientFinalDAO, assureurDAO);

	}
	
}
