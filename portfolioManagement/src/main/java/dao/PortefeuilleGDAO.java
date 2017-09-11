package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;
import idao.PortefeuilleGIDAO;
import model.AllocationWecoHistorique;
import model.Asset;
import model.Assureur;
import model.ClientFinal;
import model.PortefeuilleG;
import model.PortefeuilleHistorique;

public class PortefeuilleGDAO implements PortefeuilleGIDAO {

	private DataSource dataSourceTopaze;
	private DataSource dataSourceWiseam;
	
	private HibernateTemplate hibernateWiseam;
	private HibernateTemplate hibernateTopaze;
	
	public DataSource getDataSourceTopaze() {
		return dataSourceTopaze;
	}

	public void setDataSourceTopaze(DataSource dataSourceTopaze) {
		this.dataSourceTopaze = dataSourceTopaze;
	}

	public DataSource getDataSourceWiseam() {
		return dataSourceWiseam;
	}

	public void setDataSourceWiseam(DataSource dataSourceWiseam) {
		this.dataSourceWiseam = dataSourceWiseam;
	}
	
	public HibernateTemplate getHibernateWiseam() {
		return hibernateWiseam;
	}

	public void setHibernateWiseam(HibernateTemplate hibernateWiseam) {
		this.hibernateWiseam = hibernateWiseam;
	}

	public HibernateTemplate getHibernateTopaze() {
		return hibernateTopaze;
	}

	public void setHibernateTopaze(HibernateTemplate hibernateTopaze) {
		this.hibernateTopaze = hibernateTopaze;
	}

	@Transactional(value="txManagerWiseam",readOnly = false)
	public void insertWiseamPtf(PortefeuilleG portefeuilleG) {
		
		System.out.println(portefeuilleG.toString());
		
		hibernateWiseam.merge(portefeuilleG);
	}
	
	@Transactional(value="txManagerWiseam",readOnly = false)
	public void findAllPtfTopaze(PortefeuilleHistoriqueDAO portefeuilleHistoriqueDAO,ClientFinalDAO clientFinalDAO ,AllocationWecoHistoriqueDAO allocationWecoHistoriqueDAO,AssureurDAO assureurDAO){

		String sql = "select nbins,amnav,if(dasta = '0000-00-00', null, dasta) as dasta,if(danav = '0000-00-00', null, danav) as danav,codom,coccy,tyctr,tyent,naent,coent,nbben,isin from  tw_portfolio where nbins=46";

		Connection conn = null;

		try {
			conn = dataSourceTopaze.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			PortefeuilleG portefeuilleG = null;
			String nomClient;
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				portefeuilleG = new PortefeuilleG(
						rs.getInt("NBINS"),
						rs.getString("COENT"),
						rs.getString("COCCY"),
						rs.getFloat("AMNAV"),
						rs.getDate("DANAV"),
						rs.getString("TYENT"),
						rs.getString("TYCTR"),
						rs.getString("CODOM"),
						rs.getDate("DASTA"),
						rs.getString("ISIN"),
						rs.getInt("NBBEN")
						);
				
				if (portefeuilleG.getIdAR()!=0) {
					portefeuilleG.setArb(true);
				}else {
					portefeuilleG.setArb(false);
				}

				portefeuilleG.setVl(findVlPtfTopaze(portefeuilleG));
				
				if (portefeuilleG.getTypePTF().equals("Client")) {
					
					nomClient= rs.getString("NAENT");
					
					ClientFinal clientFinal = new ClientFinal(nomClient);
					
					clientFinalDAO.insertWiseamClient(clientFinal);
					
					portefeuilleG.setClientFinal(clientFinal);
				}
				
					ClientFinal clientFinal = hibernateWiseam.get(ClientFinal.class, 1);
					
					Assureur assureur = hibernateWiseam.get(Assureur.class, 1);
					
					AllocationWecoHistorique allocationWecoHistorique = hibernateWiseam.get(AllocationWecoHistorique.class, 1);
					
					portefeuilleG.setClientFinal(clientFinal);
					portefeuilleG.setAssureur(assureur);
					portefeuilleG.setAllWecoHist(allocationWecoHistorique);
					
					insertWiseamPtf(portefeuilleG);
				
					PortefeuilleHistorique portefeuilleHistorique= portefeuilleHistoriqueDAO.findByPortefeuilleHistoriqueId(portefeuilleG);
					
					if (portefeuilleHistorique.getDateArchivage()==null) {
						portefeuilleHistoriqueDAO.findVlDatePtfTopaze(portefeuilleG);
						
					}else {
						portefeuilleHistoriqueDAO.findVlDatePtfTopaze(portefeuilleG, portefeuilleHistorique);
					}
				
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
				conn.close();
				} catch (SQLException e) {}
			}
		}
	}
	
	@Transactional(value="txManagerWiseam",readOnly = false)
	public float findVlPtfTopaze(PortefeuilleG portefeuilleG){

	String sql = "select x.close from tw_price x where x.nbins = ? and x.dapri = (select max(y.dapri) from tw_price y where x.nbins =y.nbins )";
	
	Connection conn = null;
	
	try {
		conn = dataSourceTopaze.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, portefeuilleG.getIdPortefG());
		float vl= 0;
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			vl = rs.getFloat("CLOSE");
			}
		
		rs.close();
		ps.close();
		return vl;
	} catch (SQLException e) {
		throw new RuntimeException(e);
	} finally {
		if (conn != null) {
			try {
			conn.close();
			} catch (SQLException e) {}
		}
	}
}
}
