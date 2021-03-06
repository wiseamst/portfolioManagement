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

import org.hibernate.annotations.ColumnDefault;

//Mapping avec la table AllocationWecoHistorique

@Entity
public class AllocationWecoHistorique implements Serializable {

	private int idAllocWecoHist;
	private String facteursRisque;
	private float pourcentagePTF;
	private float pourcentageBench;
	private float diff;
	private String commentaireWeco;
	private Date dateWeco;
	
	private PortefeuilleG ptfG;

	public AllocationWecoHistorique() {
		super();
	}

	public AllocationWecoHistorique(String facteursRisque, float pourcentagePTF, float pourcentageBench, float diff,
			String commentaireWeco, Date dateWeco) {
		super();
		this.facteursRisque = facteursRisque;
		this.pourcentagePTF = pourcentagePTF;
		this.pourcentageBench = pourcentageBench;
		this.diff = diff;
		this.commentaireWeco = commentaireWeco;
		this.dateWeco = dateWeco;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getIdAllocWecoHist() {
		return idAllocWecoHist;
	}
	public void setIdAllocWecoHist(int idAllocWecoHist) {
		this.idAllocWecoHist = idAllocWecoHist;
	}
	
	@Column
	public String getFacteursRisque() {
		return facteursRisque;
	}

	public void setFacteursRisque(String facteursRisque) {
		this.facteursRisque = facteursRisque;
	}
	
	@Column
	@ColumnDefault("'0.0'")
	public float getPourcentagePTF() {
		return pourcentagePTF;
	}

	public void setPourcentagePTF(float pourcentagePTF) {
		this.pourcentagePTF = pourcentagePTF;
	}
	
	@Column
	@ColumnDefault("'0.0'")
	public float getPourcentageBench() {
		return pourcentageBench;
	}
	public void setPourcentageBench(float pourcentageBench) {
		this.pourcentageBench = pourcentageBench;
	}
	
	@Column
	public String getCommentaireWeco() {
		return commentaireWeco;
	}
	public void setCommentaireWeco(String commentaireWeco) {
		this.commentaireWeco = commentaireWeco;
	}
	
	@Column
	public Date getDateWeco() {
		return dateWeco;
	}
	public void setDateWeco(Date dateWeco) {
		this.dateWeco = dateWeco;
	}
	
	@Column
	@ColumnDefault("'0.0'")
	public float getDiff() {
		return diff;
	}

	public void setDiff(float diff) {
		this.diff = diff;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idPortefG")
	public PortefeuilleG getPtfG() {
		return ptfG;
	}
	public void setPtfG(PortefeuilleG ptfG) {
		this.ptfG = ptfG;
	}

	@Override
	public String toString() {
		return "AllocationWecoHistorique [idAllocWecoHist=" + idAllocWecoHist + ", facteursRisque=" + facteursRisque
				+ ", pourcentagePTF=" + pourcentagePTF + ", pourcentageBench=" + pourcentageBench + ", diff=" + diff
				+ ", commentaireWeco=" + commentaireWeco + ", dateWeco=" + dateWeco + ", ptfG=" + ptfG.getIdPortefG() + "]";
	}

}
