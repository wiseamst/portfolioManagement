package controller;

import java.text.ParseException;
import dao.AllocationWecoHistoriqueDAO;
import dao.PortefeuilleGDAO;

public class ServiceAllocationWeco {

	private AllocationWecoHistoriqueDAO allocationWecoHistoriqueDAO;

	public ServiceAllocationWeco(AllocationWecoHistoriqueDAO allocationWecoHistoriqueDAO) {
		
		this.allocationWecoHistoriqueDAO = allocationWecoHistoriqueDAO;
	}
	
	// Monthly Integration
	public void findAllWeco(PortefeuilleGDAO portefeuilleGDAO) throws ParseException {
		
		allocationWecoHistoriqueDAO.findAllWeco(portefeuilleGDAO);
	}
}
