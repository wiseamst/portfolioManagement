package idao;

import java.text.ParseException;
import java.util.List;

import model.AllocationWecoHistorique;
import model.PortefeuilleG;

public interface AllocationWecoHistoriqueIDAO {

	public void findAllWeco() throws ParseException;
	
	public boolean readFromWeco(PortefeuilleG portefeuilleG) throws ParseException;
	
	public void insertWiseamWeco(AllocationWecoHistorique allocationWecoHistorique);
	
	public List<PortefeuilleG> findWiseamPtf();
	
	public List<AllocationWecoHistorique> findWiseamWecoByPtf(PortefeuilleG portefeuilleG);
}
