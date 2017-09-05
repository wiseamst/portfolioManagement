package model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

@Entity
public class AllocationHistorique implements Serializable{
	
	private float qty;
	private float poids;
	private float prixAllocation;
	private Date dateArchivageAllocation;
	
	private Allocation allocation;
	
	public AllocationHistorique(float qty, float poids, float prixAllocation, Date dateArchivageAllocation) {
		super();
		this.qty = qty;
		this.poids = poids;
		this.prixAllocation = prixAllocation;
		this.dateArchivageAllocation = dateArchivageAllocation;
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
	
	@Id
	@Column
	public java.sql.Date getDateArchivageAllocation() {
		return dateArchivageAllocation;
	}
	public void setDateArchivageAllocation(Date dateArchivageAllocation) {
		this.dateArchivageAllocation = dateArchivageAllocation;
	}
	
    @Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = "idAsset"),
		@JoinColumn(name = "idPortefG") })
	public Allocation getAllocation() {
		return allocation;
	}

	public void setAllocation(Allocation allocation) {
		this.allocation = allocation;
	}
}
