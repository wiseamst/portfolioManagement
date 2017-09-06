package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

import org.springframework.orm.hibernate5.HibernateTemplate;

import idao.PortefeuilleHistoriqueIDAO;
import model.PortefeuilleHistorique;

public class PortefeuilleHistoriqueDAO  implements PortefeuilleHistoriqueIDAO {

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


	public void insert(PortefeuilleHistorique portefeuilleHistorique) {
		// TODO Auto-generated method stub
		
	}

	public void insertPortefeuilleHistorique(PortefeuilleHistorique portefeuilleHistorique){

		String sql = "INSERT INTO PORTEFEUILLEHISTORIQUE " +
				"(VL, PERF, DATEARCHIVAGE) VALUES (?, ?, ?, ?)";
		Connection conn = null;

		try {
			conn = dataSourceWiseam.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, portefeuilleHistorique.getVl());
			ps.setInt(2, portefeuilleHistorique.getPerf());
			ps.setDate(3, portefeuilleHistorique.getDateArchivage());
			ps.executeUpdate();
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
	
	public PortefeuilleHistorique findByPortefeuilleHistoriqueId(int portefeuilleHistoriqueId){

		String sql = "SELECT * FROM PORTEFEUILLEHISTORIQUE WHERE IDPORTEFHIST = ?";

		Connection conn = null;

		try {
			conn = dataSourceTopaze.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, portefeuilleHistoriqueId);
			PortefeuilleHistorique portefeuilleHistorique = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				portefeuilleHistorique = new PortefeuilleHistorique(
					rs.getInt("VL"),
					rs.getInt("PERF"),
					rs.getDate("DATEARCHIVAGE")
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
}
