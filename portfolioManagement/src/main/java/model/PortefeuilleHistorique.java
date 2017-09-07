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
public class PortefeuilleHistorique implements Serializable{

	private float vl;
	private float perf;
	private Date dateArchivage;
	
	private PortefeuilleG portef;


	public PortefeuilleHistorique(Date dateArchivage, PortefeuilleG portef) {
		super();
		this.dateArchivage = dateArchivage;
		this.portef = portef;
	}

	public PortefeuilleHistorique(float vl, Date dateArchivage, PortefeuilleG portef) {
		super();
		this.vl = vl;
		this.dateArchivage = dateArchivage;
		this.portef = portef;
	}

	@Column
	public float getVl() {
		return vl;
	}
	public void setVl(float vl) {
		this.vl = vl;
	}
	
	@Column
	public float getPerf() {
		return perf;
	}

	public void setPerf(float perf) {
		this.perf = perf;
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
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPortefG", nullable = false)
	public PortefeuilleG getPortef() {
		return portef;
	}
	public void setPortef(PortefeuilleG portef) {
		this.portef = portef;
	}
}
