package model;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Allocation implements Serializable{

	private Asset asset;
	private PortefeuilleG portef;
	
	private float qty;
	private float poids;
	private float prixAllocation;
	private Date dateAllocation;

	public Allocation(float qty, float poids, float prixAllocation) {
		super();
		this.qty = qty;
		this.poids = poids;
		this.prixAllocation = prixAllocation;
	}

	@Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idAsset")
	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPortefG")
	public PortefeuilleG getPortef() {
		return portef;
	}

	public void setPortef(PortefeuilleG portef) {
		this.portef = portef;
	}

	@Column
	public float getQty() {
		return qty;
	}
	public void setQty(float qty) {
		this.qty = qty;
	}
	@Column
	public float getPoids() {
		return poids;
	}
	public void setPoids(float poids) {
		this.poids = poids;
	}
	@Column
	public float getPrixAllocation() {
		return prixAllocation;
	}
	public void setPrixAllocation(float prixAllocation) {
		this.prixAllocation = prixAllocation;
	}
	@Column
	public Date getDateAllocation() {
		return dateAllocation;
	}
	public void setDateAllocation(Date dateAllocation) {
		this.dateAllocation = dateAllocation;
	}
    
}
