package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;
import idao.PortefeuilleGIDAO;
import model.Assureur;
import model.ClientFinal;
import model.PortefeuilleG;
import model.PortefeuilleHistorique;

public class PortefeuilleGDAO implements PortefeuilleGIDAO {

	private DataSource dataSourceTopaze; // to read from topaze
	private DataSource dataSourceWiseam; // to read from wiseam
	
	private HibernateTemplate hibernateWiseam; // to read from wiseam using hibernate template
	private HibernateTemplate hibernateTopaze; // to read from topaze using hibernate template
	
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
	public List<PortefeuilleG> findWiseamPtf() {
		
		return(List<PortefeuilleG>) hibernateWiseam.find("select r from PortefeuilleG r");

	}
	
	@Transactional(value="txManagerWiseam",readOnly = false)
	public void findAllPtfTopaze(PortefeuilleHistoriqueDAO portefeuilleHistoriqueDAO,ClientFinalDAO clientFinalDAO,AssureurDAO assureurDAO) throws DataAccessException, ParseException{

		String sql = "select nbins,coacc,amnav,if(dasta = '0000-00-00', null, dasta) as dasta,if(danav = '0000-00-00', null, danav) as danav,codom,coccy,tyctr,tyent,naent,coent,nbben,isin from  tw_portfolio";

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
						rs.getString("COACC"),
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
					portefeuilleG.setArb(true); // if ptf is related to benchmark set Arb to true
				}else {
					portefeuilleG.setArb(false); // if not
				}
				
				if (portefeuilleG.getTypePTF().equals("Client")) {
					
					nomClient= rs.getString("NAENT");
					
					ClientFinal clientFinal = new ClientFinal(nomClient);
					
					List<ClientFinal> clients = findWiseamClientByName(nomClient); // check client existence
					
					if (clients.size()>0) {
						clientFinal=clients.get(0); // client already exists
					}	
					
					clientFinalDAO.insertWiseamClient(clientFinal); //Create new client type if ptf is client
					
					portefeuilleG.setClientFinal(clientFinal);
				}else {
					
					ClientFinal clientFinal = hibernateWiseam.get(ClientFinal.class, 1); // if it is not a client set the idclient to 1 ( fictif)
					portefeuilleG.setClientFinal(clientFinal);
				}
				    PortefeuilleG portefeuilleGTemp = hibernateWiseam.get(PortefeuilleG.class, portefeuilleG.getIdPortefG());
				    
				    if (portefeuilleGTemp!=null && portefeuilleGTemp.getAssureur().getIdAssureur()==1 || portefeuilleGTemp==null) {
					    Assureur assureur = hibernateWiseam.get(Assureur.class, 1); // set the idassureur to 1 (fictif)
						portefeuilleG.setAssureur(assureur);
				    }
					
				    if (portefeuilleG.getTypePTF().equals("Fonds") || portefeuilleG.getTypePTF().equals("Profil de gestion")) {
				    	
				    	portefeuilleG.setVl(findVlPtfTopaze(portefeuilleG));
				    	
				    	PortefeuilleHistorique portefeuilleHistorique= portefeuilleHistoriqueDAO.findByPortefeuilleHistoriqueId(portefeuilleG);
				    	
						if (portefeuilleHistorique.getDateArchivage()==null) {
							portefeuilleHistoriqueDAO.findVlDatePtfTopaze(portefeuilleG);  // no line into hist for this ptf
							
						}else {
							portefeuilleHistoriqueDAO.findVlDatePtfTopaze(portefeuilleG, portefeuilleHistorique); // already line into hist for this ptf
						}
				    }else {
				    	portefeuilleG.setVl(0);
				    }
					
					insertWiseamPtf(portefeuilleG); // insert line by line ptf
							
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
	@Transactional(value="txManagerWiseam",readOnly = false)
	public List<ClientFinal> findWiseamClientByName(String nomClient) {
		
		Object[] params = new Object[]{nomClient};
		return(List<ClientFinal>) hibernateWiseam.find("select r from ClientFinal r where r.nom=?",params);

	}

}
