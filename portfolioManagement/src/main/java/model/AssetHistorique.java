package model;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ColumnDefault;

//Mapping avec la table AssetHistorique

@Entity
public class AssetHistorique implements Serializable{
	
	private float perf;
	private float dernierPrix;
	private Date dateArchivage;
	
	private Asset asset;

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

	public AssetHistorique() {
		super();
	}

	@Column
	@ColumnDefault("'0.0'")
	public void setPerf(float perf) {
		this.perf = perf;
	}

	public void setDernierPrix(float dernierPrix) {
		this.dernierPrix = dernierPrix;
	}
	
	@Column
	@ColumnDefault("'0.0'")
	public float getDernierPrix() {
		return dernierPrix;
	}
	public float getPerf() {
		return perf;
	}
	
	@Id
	@Column
	public Date getDateArchivage() {
		return dateArchivage;
	}
	public void setDateArchivage(Date dateArchivage) {
		this.dateArchivage = dateArchivage;
	}
	
	@Id
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idAsset")
	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	@Override
	public String toString() {
		return "AssetHistorique [perf=" + perf + ", dernierPrix=" + dernierPrix + ", dateArchivage=" + dateArchivage
				+ ", asset=" + asset.getIdAsset() + "]";
	}

}
