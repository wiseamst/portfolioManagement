package controller;

import java.text.ParseException;
import dao.AllocationWecoHistoriqueDAO;

public class ServiceAllocationWeco {

	private AllocationWecoHistoriqueDAO allocationWecoHistoriqueDAO;

	public ServiceAllocationWeco(AllocationWecoHistoriqueDAO allocationWecoHistoriqueDAO) {
		
		this.allocationWecoHistoriqueDAO = allocationWecoHistoriqueDAO;
	}
	
	// Monthly Integration
	public void findAllWeco() throws ParseException {
		
		allocationWecoHistoriqueDAO.findAllWeco();
	}
}
