package model;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ColumnDefault;

//Mapping avec la table AllocationHistorique

@Entity
public class AllocationHistorique implements Serializable{
	
	private float qty;
	private float poids;
	private float prixAllocation;
	private Date dateArchivageAllocation;
	
	private Allocation allocation;

	public AllocationHistorique(Date dateArchivageAllocation, Allocation allocation) {
		super();
		this.qty = allocation.getQty();
		this.poids = allocation.getPoids();
		this.prixAllocation = allocation.getPrixAllocation();
		this.dateArchivageAllocation = dateArchivageAllocation;
		this.allocation = allocation;
	}

	public AllocationHistorique() {
		super();
	}

	@Column
	@ColumnDefault("'0.0'")
	public float getQty() {
		return qty;
	}
	public void setQty(float qty) {
		this.qty = qty;
	}
	
	@Column
	@ColumnDefault("'0.0'")
	public float getPoids() {
		return poids;
	}
	public void setPoids(float poids) {
		this.poids = poids;
	}
	
	@Column
	@ColumnDefault("'0.0'")
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
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumns({
		@JoinColumn(name = "idAsset"),
		@JoinColumn(name = "idPortefG") })
	public Allocation getAllocation() {
		return allocation;
	}

	public void setAllocation(Allocation allocation) {
		this.allocation = allocation;
	}

	@Override
	public String toString() {
		return "AllocationHistorique [qty=" + qty + ", poids=" + poids + ", prixAllocation=" + prixAllocation
				+ ", dateArchivageAllocation=" + dateArchivageAllocation + ",asset=" + allocation.getAsset().getIdAsset() + ", portef=" + allocation.getPortef().getIdPortefG() + "]";
	}

}
