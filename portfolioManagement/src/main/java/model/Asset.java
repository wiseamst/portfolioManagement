package model;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

//Mapping avec la table Asset

@Entity
public class Asset implements Serializable {

	private int idAsset;
	private String coins;
	private String isin;
	private String tickerBBG;
	private String devise;
	private String type;
	private String nom;
	private float dernierPrix;
	private Date dateMAJ;
	private String classType;
	private String  zone;
	private Set<Allocation> setAlloc;
	
	public Asset(int idAsset, String coins, String isin, String tickerBBG, String devise, String type, String nom,
			String classType, String zone) {
		super();
		this.idAsset = idAsset;
		this.coins = coins;
		this.isin = isin;
		this.tickerBBG = tickerBBG;
		this.devise = devise;
		this.type = type;
		this.nom = nom;
		this.classType = classType;
		this.zone = zone;
	}

	public Asset(float dernierPrix, Date dateMAJ) {
		super();
		this.dernierPrix = dernierPrix;
		this.dateMAJ = dateMAJ;
	}

	public Asset() {
		super();
	}

	@Id
	public int getIdAsset() {
		return idAsset;
	}
	public void setIdAsset(int idAsset) {
		this.idAsset = idAsset;
	}
	
	@Column	
	public String getCoins() {
		return coins;
	}

	public void setCoins(String coins) {
		this.coins = coins;
	}

	@Column
	public String getIsin() {
		return isin;
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

	@Column
	public String getTickerBBG() {
		return tickerBBG;
	}

	public void setTickerBBG(String tickerBBG) {
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

	@Override
	public String toString() {
		return "Asset [idAsset=" + idAsset + ", coins=" + coins + ", isin=" + isin + ", tickerBBG=" + tickerBBG
				+ ", devise=" + devise + ", type=" + type + ", nom=" + nom + ", dernierPrix=" + dernierPrix
				+ ", dateMAJ=" + dateMAJ + ", classType=" + classType + ", zone=" + zone + "]";
	}
	
}
