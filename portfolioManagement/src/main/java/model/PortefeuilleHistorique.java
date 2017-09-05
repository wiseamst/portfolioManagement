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
public class PortefeuilleHistorique implements Serializable{

	private int vl;
	private int perf;
	private Date dateArchivage;
	
	private Portefeuille portef;

	public PortefeuilleHistorique(int vl, int perf, Date dateArchivage) {
		super();
		this.vl = vl;
		this.perf = perf;
		this.dateArchivage = dateArchivage;
	}
	
	@Column
	public int getVl() {
		return vl;
	}
	public void setVl(int vl) {
		this.vl = vl;
	}
	
	@Column
	public int getPerf() {
		return perf;
	}
	public void setPerf(int perf) {
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
	public Portefeuille getPortef() {
		return portef;
	}
	public void setPortef(Portefeuille portef) {
		this.portef = portef;
	}
}
