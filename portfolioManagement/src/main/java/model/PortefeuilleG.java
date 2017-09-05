package model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class PortefeuilleG implements Serializable  {

	private int idPortefG;
	private String nom;
	private String numContrat;
	private String devise;
	private float actifNet;
	private float vl;
	private Date dateMAJ;
	private String formeJuridiquePTF;
	private String depositaire;
	private String valorisateur;
	private float montantInitial;
	private String bic;
	private String iban;
	private String catAMF;
	private Date dateCreation;
	private String classificationAMF;
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
	
	private ClientFinal clientFinal;
	private Assureur assureur;
	private AllocationWecoHistorique allWecoHist;
	private Set<Allocation> setAlloc;
	
	public PortefeuilleG(String nom, String numContrat, String devise, float actifNet, float vl, Date dateMAJ,
			String formeJuridiquePTF, String depositaire, String valorisateur, float montantInitial, String bic,
			String iban, String catAMF, Date dateCreation, String classificationAMF, String dureeRecommandee,
			String affectationResultat, String centralOrdre, String reglementLivraison, float fraisEntree,
			float fraisSortie, float fDGF, float fDGV, String commentaireGestion, String niveauRisque) {
		super();
		this.nom = nom;
		this.numContrat = numContrat;
		this.devise = devise;
		this.actifNet = actifNet;
		this.vl = vl;
		this.dateMAJ = dateMAJ;
		this.formeJuridiquePTF = formeJuridiquePTF;
		this.depositaire = depositaire;
		this.valorisateur = valorisateur;
		this.montantInitial = montantInitial;
		this.bic = bic;
		this.iban = iban;
		this.catAMF = catAMF;
		this.dateCreation = dateCreation;
		this.classificationAMF = classificationAMF;
		this.dureeRecommandee = dureeRecommandee;
		this.affectationResultat = affectationResultat;
		this.centralOrdre = centralOrdre;
		this.reglementLivraison = reglementLivraison;
		this.fraisEntree = fraisEntree;
		this.fraisSortie = fraisSortie;
		FDGF = fDGF;
		FDGV = fDGV;
		this.commentaireGestion = commentaireGestion;
		this.niveauRisque = niveauRisque;
	}

	public PortefeuilleG(int idPortefG, String nom, String numContrat, String devise, float actifNet, float vl,
			Date dateMAJ, String formeJuridiquePTF, String depositaire, String valorisateur, float montantInitial,
			String bic, String iban, String catAMF, Date dateCreation, String classificationAMF,
			String dureeRecommandee, String affectationResultat, String centralOrdre, String reglementLivraison,
			float fraisEntree, float fraisSortie, float fDGF, float fDGV, String commentaireGestion,
			String niveauRisque) {
		super();
		this.idPortefG = idPortefG;
		this.nom = nom;
		this.numContrat = numContrat;
		this.devise = devise;
		this.actifNet = actifNet;
		this.vl = vl;
		this.dateMAJ = dateMAJ;
		this.formeJuridiquePTF = formeJuridiquePTF;
		this.depositaire = depositaire;
		this.valorisateur = valorisateur;
		this.montantInitial = montantInitial;
		this.bic = bic;
		this.iban = iban;
		this.catAMF = catAMF;
		this.dateCreation = dateCreation;
		this.classificationAMF = classificationAMF;
		this.dureeRecommandee = dureeRecommandee;
		this.affectationResultat = affectationResultat;
		this.centralOrdre = centralOrdre;
		this.reglementLivraison = reglementLivraison;
		this.fraisEntree = fraisEntree;
		this.fraisSortie = fraisSortie;
		FDGF = fDGF;
		FDGV = fDGV;
		this.commentaireGestion = commentaireGestion;
		this.niveauRisque = niveauRisque;
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
	public float getActifNet() {
		return actifNet;
	}
	public void setActifNet(float actifNet) {
		this.actifNet = actifNet;
	}
	
	@Column
	public float getVl() {
		return vl;
	}
	public void setVl(float vl) {
		this.vl = vl;
	}
	
	@Column
	public Date getDateMAJ() {
		return dateMAJ;
	}
	public void setDateMAJ(Date dateMAJ) {
		this.dateMAJ = dateMAJ;
	}
	
	@Column
	public String getFormeJuridiquePTF() {
		return formeJuridiquePTF;
	}
	public void setFormeJuridiquePTF(String formeJuridiquePTF) {
		this.formeJuridiquePTF = formeJuridiquePTF;
	}
	
	@Column
	public String getDepositaire() {
		return depositaire;
	}
	public void setDepositaire(String depositaire) {
		this.depositaire = depositaire;
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
	public String getCatAMF() {
		return catAMF;
	}
	public void setCatAMF(String catAMF) {
		this.catAMF = catAMF;
	}
	
	@Column
	public Date getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}
	
	@Column
	public String getClassificationAMF() {
		return classificationAMF;
	}
	public void setClassificationAMF(String classificationAMF) {
		this.classificationAMF = classificationAMF;
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
	
	@ManyToOne
	@JoinColumn(name = "idClient", nullable = false)
	public ClientFinal getClientFinal() {
		return clientFinal;
	}
	public void setClientFinal(ClientFinal clientFinal) {
		this.clientFinal = clientFinal;
	}
	
	@ManyToOne
	@JoinColumn(name = "idAssureur", nullable = false)
	public Assureur getAssureur() {
		return assureur;
	}
	public void setAssureur(Assureur assureur) {
		this.assureur = assureur;
	}
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "idAllocWecoHist")
	public AllocationWecoHistorique getAllWecoHist() {
		return allWecoHist;
	}
	public void setAllWecoHist(AllocationWecoHistorique allWecoHist) {
		this.allWecoHist = allWecoHist;
	}

	@OneToMany(mappedBy = "portef", cascade = CascadeType.ALL, orphanRemoval = true)
	public Set<Allocation> getSetAlloc() {
		return setAlloc;
	}

	public void setSetAlloc(Set<Allocation> setAlloc) {
		this.setAlloc = setAlloc;
	}
	
}
