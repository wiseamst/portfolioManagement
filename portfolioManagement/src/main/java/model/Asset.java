package model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Asset implements Serializable {

	private int idAsset;
	private int isin;
	private int tickerBBG;
	private String devise;
	private String type;
	private String nom;
	private float dernierPrix;
	private Date dateMAJ;
	private String classType;
	private String  zone;
	private Set<Allocation> setAlloc;
	
	public Asset(int isin, int tickerBBG, String devise, String type, String nom, float dernierPrix, Date dateMAJ) {
		super();
		this.isin = isin;
		this.tickerBBG = tickerBBG;
		this.devise = devise;
		this.type = type;
		this.nom = nom;
		this.dernierPrix = dernierPrix;
		this.dateMAJ = dateMAJ;
	}
	
	
	public Asset(int idAsset, int isin, int tickerBBG, String devise, String type, String nom, float dernierPrix,
			Date dateMAJ) {
		super();
		this.idAsset = idAsset;
		this.isin = isin;
		this.tickerBBG = tickerBBG;
		this.devise = devise;
		this.type = type;
		this.nom = nom;
		this.dernierPrix = dernierPrix;
		this.dateMAJ = dateMAJ;
	}


	@Id
	public int getIdAsset() {
		return idAsset;
	}
	public void setIdAsset(int idAsset) {
		this.idAsset = idAsset;
	}
	
	@Column
	public int getIsin() {
		return isin;
	}
	public void setIsin(int isin) {
		this.isin = isin;
	}
	
	@Column
	public int getTickerBBG() {
		return tickerBBG;
	}
	public void setTickerBBG(int tickerBBG) {
		this.tickerBBG = tickerBBG;
	}
	
	@Column
	public String getDevise() {
		return devise;
	}
	public void setDevise(String devise) {
		this.devise = devise;
	}
	
	@Column
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Column
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	@Column
	public float getDernierPrix() {
		return dernierPrix;
	}
	public void setDernierPrix(float dernierPrix) {
		this.dernierPrix = dernierPrix;
	}
	
	@Column
	public Date getDateMAJ() {
		return dateMAJ;
	}
	public void setDateMAJ(Date dateMAJ) {
		this.dateMAJ = dateMAJ;
	}

	@Column
	public String getClassType() {
		return classType;
	}
	public void setClassType(String classType) {
		this.classType = classType;
	}

	@Column
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}


	@OneToMany(mappedBy = "asset")
	public Set<Allocation> getSetAlloc() {
		return setAlloc;
	}

	public void setSetAlloc(Set<Allocation> setAlloc) {
		this.setAlloc = setAlloc;
	}
	
}
