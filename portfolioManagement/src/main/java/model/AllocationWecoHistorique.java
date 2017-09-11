package model;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class AllocationWecoHistorique implements Serializable {

	private int idAllocWecoHist;
	private float facteursRisque;
	private float pourcentagePTF;
	private float pourcentageBench;
	private String commentaireWeco;
	private Date dateWeco;
	
	private PortefeuilleG ptfG;
	
	public AllocationWecoHistorique(float facteursRisque, float pourcentagePTF, float pourcentageBench,
			String commentaireWeco, Date dateWeco) {
		super();
		this.facteursRisque = facteursRisque;
		this.pourcentagePTF = pourcentagePTF;
		this.pourcentageBench = pourcentageBench;
		this.commentaireWeco = commentaireWeco;
		this.dateWeco = dateWeco;
	}

	public AllocationWecoHistorique(String commentaireWeco) {
		super();
		this.commentaireWeco = commentaireWeco;
	}

	public AllocationWecoHistorique() {
		super();
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
	public float getFacteursRisque() {
		return facteursRisque;
	}
	public void setFacteursRisque(float facteursRisque) {
		this.facteursRisque = facteursRisque;
	}
	
	@Column
	public float getPourcentagePTF() {
		return pourcentagePTF;
	}
	public void setPourcentagePTF(float pourcentagePTF) {
		this.pourcentagePTF = pourcentagePTF;
	}
	
	@Column
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
	
	@OneToOne(mappedBy="allWecoHist")
	public PortefeuilleG getPtfG() {
		return ptfG;
	}
	public void setPtfG(PortefeuilleG ptfG) {
		this.ptfG = ptfG;
	}

	@Override
	public String toString() {
		return "AllocationWecoHistorique [idAllocWecoHist=" + idAllocWecoHist + ", facteursRisque=" + facteursRisque
				+ ", pourcentagePTF=" + pourcentagePTF + ", pourcentageBench=" + pourcentageBench + ", commentaireWeco="
				+ commentaireWeco + ", dateWeco=" + dateWeco + "]";
	}
	
}
