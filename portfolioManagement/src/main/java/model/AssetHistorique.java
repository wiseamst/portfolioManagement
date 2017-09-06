package model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class AssetHistorique implements Serializable{
	
	private int perf;
	private float dernierPrix;
	private Date dateArchivage;
	
	private Asset asset;
	
	
	public AssetHistorique(int perf, float dernierPrix, Date dateArchivage) {
		super();
		this.perf = perf;
		this.dernierPrix = dernierPrix;
		this.dateArchivage = dateArchivage;
	}

	public AssetHistorique(float dernierPrix, Date dateArchivage, Asset asset) {
		super();
		this.dernierPrix = dernierPrix;
		this.dateArchivage = dateArchivage;
		this.asset = asset;
	}

	public AssetHistorique(Date dateArchivage, Asset asset) {
		super();
		this.dateArchivage = dateArchivage;
		this.asset = asset;
	}

	@Column
	public int getPerf() {
		return perf;
	}
	public void setPerf(int perf) {
		this.perf = perf;
	}
	
	@Column
	public float getDernierPrix() {
		return dernierPrix;
	}
	public void setDernierPrix(float dernierPrix) {
		this.dernierPrix = dernierPrix;
	}
	
	@Id
	@Column
	public Date getDateArchivage() {
		return dateArchivage;
	}
	public void setDateArchivage(Date dateArchivage) {
		this.dateArchivage = dateArchivage;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idAsset", nullable = false)
	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}
}
