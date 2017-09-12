package model;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

//Mapping avec la table PortefeuilleG

@Entity
public class PortefeuilleG implements Serializable  {

	private int idPortefG;
	private String nom;
	private String numContrat;
	private String devise;
	private float amnav;
	private float vl;
	private Date danav;
	private String typePTF;
	private String typeCTR;
	private String codom;
	private String valorisateur;
	private float montantInitial;
	private String bic;
	private String iban;
	private Date dasta;
	private String dureeRecommandee;
	private String affectationResultat;
	private String centralOrdre;
	private String reglementLivraison;
	private float fraisEntree;
	private float fraisSortie;
	private float FDGF;
	private float FDGV;
	private String commentaireGestion;
	private String niveauRisque;
	private String isin;
	
	private int idAR;
	private boolean arb;
	
	private ClientFinal clientFinal;
	private Assureur assureur;
	private Set<Allocation> setAlloc;
	
	public PortefeuilleG(int idPortefG, String nom, String devise, float amnav, Date danav, String typePTF,
			String typeCTR, String codom, Date dasta, String isin, int idAR) {
		super();
		this.idPortefG = idPortefG;
		this.nom = nom;
		this.devise = devise;
		this.amnav = amnav;
		this.danav = danav;
		this.typePTF = typePTF;
		this.typeCTR = typeCTR;
		this.codom = codom;
		this.dasta = dasta;
		this.isin = isin;
		this.idAR = idAR;
	}
	
	
	public PortefeuilleG(int idPortefG, String nom, String numContrat, String devise, float amnav, Date danav,
			String typePTF, String typeCTR, String codom, Date dasta, String isin, int idAR) {
		super();
		this.idPortefG = idPortefG;
		this.nom = nom;
		this.numContrat = numContrat;
		this.devise = devise;
		this.amnav = amnav;
		this.danav = danav;
		this.typePTF = typePTF;
		this.typeCTR = typeCTR;
		this.codom = codom;
		this.dasta = dasta;
		this.isin = isin;
		this.idAR = idAR;
	}

	public PortefeuilleG() {
		super();
	}

	@Id
	public int getIdPortefG() {
		return idPortefG;
	}
	public void setIdPortefG(int idPortefG) {
		this.idPortefG = idPortefG;
	}
	
	@Column
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	@Column
	public String getNumContrat() {
		return numContrat;
	}
	public void setNumContrat(String numContrat) {
		this.numContrat = numContrat;
	}
	
	@Column
	public String getDevise() {
		return devise;
	}
	public void setDevise(String devise) {
		this.devise = devise;
	}
	
	@Column
	public float getVl() {
		return vl;
	}
	public void setVl(float vl) {
		this.vl = vl;
	}
	
	@Column
	public float getAmnav() {
		return amnav;
	}
	public void setAmnav(float amnav) {
		this.amnav = amnav;
	}
	
	@Column
	public Date getDanav() {
		return danav;
	}
	public void setDanav(Date danav) {
		this.danav = danav;
	}
	
	@Column
	public String getTypePTF() {
		return typePTF;
	}
	public void setTypePTF(String typePTF) {
		this.typePTF = typePTF;
	}
	
	@Column
	public String getTypeCTR() {
		return typeCTR;
	}
	public void setTypeCTR(String typeCTR) {
		this.typeCTR = typeCTR;
	}
	
	@Column
	public String getCodom() {
		return codom;
	}
	public void setCodom(String codom) {
		this.codom = codom;
	}
	
	@Column
	public Date getDasta() {
		return dasta;
	}
	public void setDasta(Date dasta) {
		this.dasta = dasta;
	}
	@Column
	public String getValorisateur() {
		return valorisateur;
	}
	public void setValorisateur(String valorisateur) {
		this.valorisateur = valorisateur;
	}
	
	@Column
	public float getMontantInitial() {
		return montantInitial;
	}
	public void setMontantInitial(float montantInitial) {
		this.montantInitial = montantInitial;
	}
	
	@Column
	public String getBic() {
		return bic;
	}
	public void setBic(String bic) {
		this.bic = bic;
	}
	
	@Column
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	
	@Column
	public String getDureeRecommandee() {
		return dureeRecommandee;
	}
	public void setDureeRecommandee(String dureeRecommandee) {
		this.dureeRecommandee = dureeRecommandee;
	}
	
	@Column
	public String getAffectationResultat() {
		return affectationResultat;
	}
	public void setAffectationResultat(String affectationResultat) {
		this.affectationResultat = affectationResultat;
	}
	
	@Column
	public String getCentralOrdre() {
		return centralOrdre;
	}
	public void setCentralOrdre(String centralOrdre) {
		this.centralOrdre = centralOrdre;
	}
	
	@Column
	public String getReglementLivraison() {
		return reglementLivraison;
	}
	public void setReglementLivraison(String reglementLivraison) {
		this.reglementLivraison = reglementLivraison;
	}
	
	@Column
	public float getFraisEntree() {
		return fraisEntree;
	}
	public void setFraisEntree(float fraisEntree) {
		this.fraisEntree = fraisEntree;
	}
	
	@Column
	public float getFraisSortie() {
		return fraisSortie;
	}
	public void setFraisSortie(float fraisSortie) {
		this.fraisSortie = fraisSortie;
	}
	
	@Column
	public float getFDGF() {
		return FDGF;
	}
	public void setFDGF(float fDGF) {
		FDGF = fDGF;
	}
	
	@Column
	public float getFDGV() {
		return FDGV;
	}
	public void setFDGV(float fDGV) {
		FDGV = fDGV;
	}
	
	@Column
	public String getCommentaireGestion() {
		return commentaireGestion;
	}
	public void setCommentaireGestion(String commentaireGestion) {
		this.commentaireGestion = commentaireGestion;
	}
	
	@Column
	public String getNiveauRisque() {
		return niveauRisque;
	}
	public void setNiveauRisque(String niveauRisque) {
		this.niveauRisque = niveauRisque;
	}
	
	@Column
	public String getIsin() {
		return isin;
	}
	public void setIsin(String isin) {
		this.isin = isin;
	}
	
	@Column
	public boolean isArb() {
		return arb;
	}
	public void setArb(boolean arb) {
		this.arb = arb;
	}
	
	@Column
	public int getIdAR() {
		return idAR;
	}

	public void setIdAR(int idAR) {
		this.idAR = idAR;
	}
	
	@ManyToOne
	@JoinColumn(name = "idClient")
	public ClientFinal getClientFinal() {
		return clientFinal;
	}
	public void setClientFinal(ClientFinal clientFinal) {
		this.clientFinal = clientFinal;
	}
	
	@ManyToOne
	@JoinColumn(name = "idAssureur")
	public Assureur getAssureur() {
		return assureur;
	}
	public void setAssureur(Assureur assureur) {
		this.assureur = assureur;
	}
	

	@OneToMany(mappedBy = "portef")
	public Set<Allocation> getSetAlloc() {
		return setAlloc;
	}

	public void setSetAlloc(Set<Allocation> setAlloc) {
		this.setAlloc = setAlloc;
	}


	@Override
	public String toString() {
		return "PortefeuilleG [idPortefG=" + idPortefG + ", nom=" + nom + ", numContrat=" + numContrat + ", devise="
				+ devise + ", amnav=" + amnav + ", vl=" + vl + ", danav=" + danav + ", typePTF=" + typePTF
				+ ", typeCTR=" + typeCTR + ", codom=" + codom + ", valorisateur=" + valorisateur + ", montantInitial="
				+ montantInitial + ", bic=" + bic + ", iban=" + iban + ", dasta=" + dasta + ", dureeRecommandee="
				+ dureeRecommandee + ", affectationResultat=" + affectationResultat + ", centralOrdre=" + centralOrdre
				+ ", reglementLivraison=" + reglementLivraison + ", fraisEntree=" + fraisEntree + ", fraisSortie="
				+ fraisSortie + ", FDGF=" + FDGF + ", FDGV=" + FDGV + ", commentaireGestion=" + commentaireGestion
				+ ", niveauRisque=" + niveauRisque + ", isin=" + isin + ", idAR=" + idAR + ", arb=" + arb
				+ ", clientFinal=" + clientFinal.getIdClient() + ", assureur=" + assureur.getIdAssureur() + "]";
	}

}
