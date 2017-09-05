package model;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class ClientFinal implements Serializable{

	private int idClient;
	private String nom;
	private String prenom;
	private String coordonnees;
	private String mail;
	private String type;
	private Date dateDebutContrat;
	
	private Set<PortefeuilleG> set_ptfs;
	private Set<CGP> set_CGPs;
	
	public ClientFinal(String nom, String prenom, String coordonnees, String mail, String type, Date dateDebutContrat) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.coordonnees = coordonnees;
		this.mail = mail;
		this.type = type;
		this.dateDebutContrat = dateDebutContrat;
	}

	public ClientFinal(int idClient, String nom, String prenom, String coordonnees, String mail, String type,
			Date dateDebutContrat) {
		super();
		this.idClient = idClient;
		this.nom = nom;
		this.prenom = prenom;
		this.coordonnees = coordonnees;
		this.mail = mail;
		this.type = type;
		this.dateDebutContrat = dateDebutContrat;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getIdClient() {
		return idClient;
	}
	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}
	
	@Column
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	@Column
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	@Column
	public String getCoordonnees() {
		return coordonnees;
	}
	public void setCoordonnees(String coordonnees) {
		this.coordonnees = coordonnees;
	}
	
	@Column
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	@Column
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Column
	public Date getDateDebutContrat() {
		return dateDebutContrat;
	}
	public void setDateDebutContrat(Date dateDebutContrat) {
		this.dateDebutContrat = dateDebutContrat;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "clientFinal")
	public Set<PortefeuilleG> getSet_ptfs() {
		return set_ptfs;
	}

	public void setSet_ptfs(Set<PortefeuilleG> set_ptfs) {
		this.set_ptfs = set_ptfs;
	}
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "set_clientF")
	public Set<CGP> getSet_CGPs() {
		return set_CGPs;
	}

	public void setSet_CGPs(Set<CGP> set_CGPs) {
		this.set_CGPs = set_CGPs;
	}
	
}
