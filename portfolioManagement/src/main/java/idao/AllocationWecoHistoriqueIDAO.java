package idao;

import java.sql.Date;
import java.text.ParseException;
import java.util.List;

import dao.PortefeuilleGDAO;
import model.AllocationWecoHistorique;
import model.PortefeuilleG;

public interface AllocationWecoHistoriqueIDAO {

	
	public boolean readFromWeco(PortefeuilleG portefeuilleG) throws ParseException;
	
	public void insertWiseamWeco(AllocationWecoHistorique allocationWecoHistorique);
	
	public List<AllocationWecoHistorique> findWiseamWecoByPtf(PortefeuilleG portefeuilleG,Date dateWeco);
}
