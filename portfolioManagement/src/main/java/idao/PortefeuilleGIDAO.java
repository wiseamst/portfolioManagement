package idao;

import java.text.ParseException;
import java.util.List;

import org.springframework.dao.DataAccessException;

import dao.AssureurDAO;
import dao.ClientFinalDAO;
import dao.PortefeuilleHistoriqueDAO;
import model.ClientFinal;
import model.PortefeuilleG;

public interface PortefeuilleGIDAO {

	public void insertWiseamPtf(PortefeuilleG portefeuilleG);
	
	public void findAllPtfTopaze(PortefeuilleHistoriqueDAO portefeuilleHistoriqueDAO,ClientFinalDAO clientFinalDAO,AssureurDAO assureurDAO) throws DataAccessException, ParseException;
	
	public float findVlPtfTopaze(PortefeuilleG portefeuilleG);
	
	public List<ClientFinal> findWiseamClientByName(String nomClient);
}
