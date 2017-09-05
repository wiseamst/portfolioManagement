package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import idao.AllocationWecoHistoriqueIDAO;
import model.AllocationWecoHistorique;

public class AllocationWecoHistoriqueDAO implements  AllocationWecoHistoriqueIDAO {

	private DataSource dataSourceTopaze;
	private DataSource dataSourceWiseam;
	
	
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

	public void insert(AllocationWecoHistorique allocationWecoHistorique) {
		// TODO Auto-generated method stub
		
	}

	public void insertAssureur(AllocationWecoHistorique allocationWecoHistorique){

		String sql = "INSERT INTO ALLOCATIONWECOHISTORIQUE " +
				"(FACTEURSRISQUE, POURCENTAGEPTF, POURCENTAGEBENCH, COMMENTAIREWECO, DATEWECO) VALUES (?, ?, ?, ?, ?, ?)";
		Connection conn = null;

		try {
			conn = dataSourceWiseam.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setFloat(1, allocationWecoHistorique.getFacteursRisque());
			ps.setFloat(2, allocationWecoHistorique.getPourcentagePTF());
			ps.setFloat(3, allocationWecoHistorique.getPourcentageBench());
			ps.setString(4, allocationWecoHistorique.getCommentaireWeco());
			ps.setDate(5, allocationWecoHistorique.getDateWeco());
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

	public AllocationWecoHistorique findByAllocationWecoHistoriqueId(int allocationWecoHistoriqueId){

		String sql = "SELECT * FROM ALLOCATIONWECOHISTORIQUE WHERE IDALLOCWECOHIST = ?";

		Connection conn = null;

		try {
			conn = dataSourceTopaze.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, allocationWecoHistoriqueId);
			AllocationWecoHistorique allocationWecoHistorique = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				allocationWecoHistorique = new AllocationWecoHistorique(
					rs.getFloat("FACTEURSRISQUE"),
					rs.getFloat("POURCENTAGEPTF"),
					rs.getFloat("POURCENTAGEBENCH"),
					rs.getString("COMMENTAIREWECO"),
					rs.getDate("DATEWECO")
				);
			}
			rs.close();
			ps.close();
			return allocationWecoHistorique;
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
