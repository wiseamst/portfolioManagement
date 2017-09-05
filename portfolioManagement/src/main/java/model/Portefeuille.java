package model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;

@Entity
public class Portefeuille extends PortefeuilleG implements Serializable {

	private int idAR;
	
	public Portefeuille(String nom, String numContrat, String devise, float actifNet, float vl, Date dateMAJ,
			String formeJuridiquePTF, String depositaire, String valorisateur, float montantInitial, String bic,
			String iban, String catAMF, Date dateCreation, String classificationAMF, String dureeRecommandee,
			String affectationResultat, String centralOrdre, String reglementLivraison, float fraisEntree,
			float fraisSortie, float fDGF, float fDGV, String commentaireGestion, String niveauRisque, int idAR) {
		super(nom, numContrat, devise, actifNet, vl, dateMAJ, formeJuridiquePTF, depositaire, valorisateur,
				montantInitial, bic, iban, catAMF, dateCreation, classificationAMF, dureeRecommandee,
				affectationResultat, centralOrdre, reglementLivraison, fraisEntree, fraisSortie, fDGF, fDGV,
				commentaireGestion, niveauRisque);
		this.idAR = idAR;
	}

	public Portefeuille(int idPortefG, String nom, String numContrat, String devise, float actifNet, float vl,
			Date dateMAJ, String formeJuridiquePTF, String depositaire, String valorisateur, float montantInitial,
			String bic, String iban, String catAMF, Date dateCreation, String classificationAMF,
			String dureeRecommandee, String affectationResultat, String centralOrdre, String reglementLivraison,
			float fraisEntree, float fraisSortie, float fDGF, float fDGV, String commentaireGestion,
			String niveauRisque, int idAR) {
		super(idPortefG, nom, numContrat, devise, actifNet, vl, dateMAJ, formeJuridiquePTF, depositaire, valorisateur,
				montantInitial, bic, iban, catAMF, dateCreation, classificationAMF, dureeRecommandee,
				affectationResultat, centralOrdre, reglementLivraison, fraisEntree, fraisSortie, fDGF, fDGV,
				commentaireGestion, niveauRisque);
		this.idAR = idAR;
	}

	public int getIdAR() {
		return idAR;
	}

	public void setIdAR(int idAR) {
		this.idAR = idAR;
	}
	
}
