package model;

import java.io.Serializable;
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
public class Assureur implements Serializable {

	private int idAssureur;
	private String nomAssureur;
	private String coordonnees;
	
	private Set<CGP> set_CGPs;
	private Set<PortefeuilleG> set_ptfs;
	
	
	public Assureur(String nomAssureur, String coordonnees) {
		super();
		this.nomAssureur = nomAssureur;
		this.coordonnees = coordonnees;
	}

	public Assureur(int idAssureur, String nomAssureur, String coordonnees) {
		super();
		this.idAssureur = idAssureur;
		this.nomAssureur = nomAssureur;
		this.coordonnees = coordonnees;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getIdAssureur() {
		return idAssureur;
	}
	public void setIdAssureur(int idAssureur) {
		this.idAssureur = idAssureur;
	}
	
	@Column
	public String getNomAssureur() {
		return nomAssureur;
	}
	public void setNomAssureur(String nomAssureur) {
		this.nomAssureur = nomAssureur;
	}
	
	@Column
	public String getCoordonnees() {
		return coordonnees;
	}
	public void setCoordonnees(String coordonnees) {
		this.coordonnees = coordonnees;
	}
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "set_assureurs")
	public Set<CGP> getSet_CGPs() {
		return set_CGPs;
	}

	public void setSet_CGPs(Set<CGP> set_CGPs) {
		this.set_CGPs = set_CGPs;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "assureur")
	public Set<PortefeuilleG> getSet_ptfs() {
		return set_ptfs;
	}

	public void setSet_ptfs(Set<PortefeuilleG> set_ptfs) {
		this.set_ptfs = set_ptfs;
	}

	

}
