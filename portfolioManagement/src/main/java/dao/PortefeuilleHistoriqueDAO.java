package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;
import idao.PortefeuilleHistoriqueIDAO;
import model.PortefeuilleG;
import model.PortefeuilleHistorique;

public class PortefeuilleHistoriqueDAO  implements PortefeuilleHistoriqueIDAO {

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
	public PortefeuilleHistorique findByPortefeuilleHistoriqueId(PortefeuilleG portefeuilleG){

		String sql = "select max(datearchivage) datearchivage from portefeuillehistorique where idportefg = ?";

		Connection conn = null;

		try {
			conn = dataSourceWiseam.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, portefeuilleG.getIdPortefG());
			PortefeuilleHistorique portefeuilleHistorique = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				portefeuilleHistorique = new PortefeuilleHistorique(
					rs.getDate("DATEARCHIVAGE"),
					portefeuilleG
				);
			}
			rs.close();
			ps.close();
			return portefeuilleHistorique;
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
	public void findVlDatePtfTopaze(PortefeuilleG portefeuilleG){

	String sql = "select x.nbins,x.dapri,x.close from tw_price x where x.nbins=? and"
			+ " x.dapri < ?  and x.dapri>='2017-08-01' order by x.dapri desc";

	Connection conn = null;

	try {
		conn = dataSourceTopaze.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, portefeuilleG.getIdPortefG());
		ps.setDate(2, portefeuilleG.getDanav());
		PortefeuilleHistorique portefeuilleHistorique = null;
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			portefeuilleHistorique = new PortefeuilleHistorique(
					rs.getFloat("CLOSE"),
					rs.getDate("DAPRI"),
					portefeuilleG
				);
		
		portefeuilleHistorique.setPerf(findPerfPtfTopaze(portefeuilleHistorique));
			
		insertWiseamPtfHist(portefeuilleHistorique);
		
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
	public void findVlDatePtfTopaze(PortefeuilleG portefeuilleG,PortefeuilleHistorique portefeuilleHistorique){

	String sql = "select x.nbins,x.dapri,x.close from tw_price x where x.nbins= ? and"
			+ " x.dapri < ? and x.dapri > ? order by x.dapri desc";

	Connection conn = null;
	
	try {
		conn = dataSourceTopaze.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, portefeuilleG.getIdPortefG());
		ps.setDate(2, portefeuilleG.getDanav());
		ps.setDate(3, portefeuilleHistorique.getDateArchivage());
		PortefeuilleHistorique portefeuilleHistoriqueTemp = null;
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			portefeuilleHistoriqueTemp = new PortefeuilleHistorique(
					rs.getFloat("CLOSE"),
					rs.getDate("DAPRI"),
					portefeuilleG
			);
		
			portefeuilleHistoriqueTemp.setPerf(findPerfPtfTopaze(portefeuilleHistoriqueTemp));
			
			insertWiseamPtfHist(portefeuilleHistoriqueTemp);
		
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
	public float findPerfPtfTopaze(PortefeuilleHistorique portefeuilleHistorique){

	String sql = "select varet from tw_return where nbins= ? and daret= ? ";

	Connection conn = null;
	
	try {
		conn = dataSourceTopaze.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, portefeuilleHistorique.getPortef().getIdPortefG());
		ps.setDate(2, portefeuilleHistorique.getDateArchivage());
		float perf= 0;
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			perf = rs.getFloat("VARET");
			}
		
		rs.close();
		ps.close();
		return perf;
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
	public void insertWiseamPtfHist(PortefeuilleHistorique portefeuilleHistorique) {
		
		System.out.println(portefeuilleHistorique.toString());
		
		hibernateWiseam.saveOrUpdate(portefeuilleHistorique);
		
	}
}
