package idao;

import model.PortefeuilleG;
import model.PortefeuilleHistorique;

public interface PortefeuilleHistoriqueIDAO {

	public PortefeuilleHistorique findByPortefeuilleHistoriqueId(PortefeuilleG portefeuilleG);
	
	public void findVlDatePtfTopaze(PortefeuilleG portefeuilleG);
	
	public void findVlDatePtfTopaze(PortefeuilleG portefeuilleG,PortefeuilleHistorique portefeuilleHistorique);
	
	public float findPerfPtfTopaze(PortefeuilleHistorique portefeuilleHistorique);
	
	public void insertWiseamPtfHist(PortefeuilleHistorique portefeuilleHistorique);
}
