package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import idao.AllocationHistoriqueIDAO;
import model.AllocationHistorique;

public class AllocationHistoriqueDAO implements  AllocationHistoriqueIDAO {

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

	public void insert(AllocationHistorique allocationHistorique) {
		// TODO Auto-generated method stub
		
	}

	public void insertAllocationHistorique(AllocationHistorique allocationHistorique){

		String sql = "INSERT INTO ALLOCATIONHISTORIQUE " +
				"(QTY,POIDS,PRIXALLOCATION,DATEARCHIVAGEALLOCATION) VALUES (?, ?, ?, ?, ?)";
		Connection conn = null;

		try {
			conn = dataSourceWiseam.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setFloat(1, allocationHistorique.getQty());
			ps.setFloat(2, allocationHistorique.getPoids());
			ps.setFloat(3, allocationHistorique.getPrixAllocation());
			ps.setDate(4, allocationHistorique.getDateArchivageAllocation());
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
	
	public AllocationHistorique findByAllocationHistoriqueId(int allocationHistoriqueId){

		String sql = "SELECT * FROM ALLOCATIONHISTORIQUE WHERE IDALLOCHIST = ?";

		Connection conn = null;

		try {
			conn = dataSourceTopaze.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, allocationHistoriqueId);
			AllocationHistorique allocationHistorique = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				allocationHistorique = new AllocationHistorique(
					rs.getFloat("QTY"),
					rs.getFloat("POIDS"),
					rs.getFloat("PRIXALLOCATION"),
					rs.getDate("DATEARCHIVAGEALLOCATION")
				);
			}
			rs.close();
			ps.close();
			return allocationHistorique;
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
