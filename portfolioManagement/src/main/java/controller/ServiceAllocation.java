package controller;

import dao.AllocationDAO;
import dao.AllocationHistoriqueDAO;

public class ServiceAllocation {

	private AllocationDAO allocationDAO;
	private AllocationHistoriqueDAO allocationHistoriqueDAO;
	
	public ServiceAllocation(AllocationDAO allocationDAO, AllocationHistoriqueDAO allocationHistoriqueDAO) {

		this.allocationDAO = allocationDAO;
		this.allocationHistoriqueDAO = allocationHistoriqueDAO;
	}
	
	// Daily Integration
	public void findAllAllocationTopaze() {
		
		allocationDAO.findAllAllocationTopaze();
	    //allocationDAO.findAllAllocationTopaze(allocationHistoriqueDAO);
	}
}
