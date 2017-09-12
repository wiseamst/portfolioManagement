package model;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

//Mapping avec la table CGP

@Entity
public class CGP implements Serializable {

	private int idCGP;
	private String nomSociete;
	private String nomCGP;
	private String coordonnees;
	
	private Set<Assureur> set_assureurs;
	private Set<ClientFinal> set_clientF;
	
	
	public CGP(String nomSociete, String nomCGP, String coordonnees) {
		super();
		this.nomSociete = nomSociete;
		this.nomCGP = nomCGP;
		this.coordonnees = coordonnees;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getIdCGP() {
		return idCGP;
	}
	public void setIdCGP(int idCGP) {
		this.idCGP = idCGP;
	}
	
	@Column
	public String getNomSociete() {
		return nomSociete;
	}
	public void setNomSociete(String nomSociete) {
		this.nomSociete = nomSociete;
	}
	
	@Column
	public String getNomCGP() {
		return nomCGP;
	}
	public void setNomCGP(String nomCGP) {
		this.nomCGP = nomCGP;
	}
	
	@Column
	public String getCoordonnees() {
		return coordonnees;
	}
	public void setCoordonnees(String coordonnees) {
		this.coordonnees = coordonnees;
	}
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="Assureur_CGP", 
	 joinColumns=@JoinColumn(name="idCGP"),
	 inverseJoinColumns=@JoinColumn(name="idAssureur"))
	public Set<Assureur> getSet_assureurs() {
		return set_assureurs;
	}

	public void setSet_assureurs(Set<Assureur> set_assureurs) {
		this.set_assureurs = set_assureurs;
	}
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="ClientFinal_CGP", 
	 joinColumns=@JoinColumn(name="idCGP"),
	 inverseJoinColumns=@JoinColumn(name="idClient"))
	public Set<ClientFinal> getSet_clientF() {
		return set_clientF;
	}

	public void setSet_clientF(Set<ClientFinal> set_clientF) {
		this.set_clientF = set_clientF;
	}

	@Override
	public String toString() {
		return "CGP [idCGP=" + idCGP + ", nomSociete=" + nomSociete + ", nomCGP=" + nomCGP + ", coordonnees="
				+ coordonnees + "]";
	}

}
