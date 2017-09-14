package idao;

import dao.AllocationHistoriqueDAO;
import model.Allocation;

public interface AllocationIDAO {
	
	public void findAllAllocationTopaze(AllocationHistoriqueDAO allocationHistoriqueDAO);
	public void insertWiseamAllocation (Allocation allocation);
	
}
