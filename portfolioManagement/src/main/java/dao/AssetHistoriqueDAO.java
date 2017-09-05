package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import idao.AssetHistoriqueIDAO;
import model.AssetHistorique;

public class AssetHistoriqueDAO implements AssetHistoriqueIDAO {

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

	public void insert(AssetHistorique assetHistorique) {
		// TODO Auto-generated method stub
		
	}

	public void insertAssetHistorique(AssetHistorique assetHistorique){

		String sql = "INSERT INTO ASSETHISTORIQUE " +
				"(PERF, DERNIERPRIX, DATEARCHIVAGE) VALUES (?, ?, ?, ?)";
		Connection conn = null;

		try {
			conn = dataSourceWiseam.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, assetHistorique.getPerf());
			ps.setFloat(2, assetHistorique.getDernierPrix());
			ps.setDate(3, assetHistorique.getDateArchivage());
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
	
	public AssetHistorique findByAssetHistoriqueId(int assetHistoriqueId){

		String sql = "SELECT * FROM ASSETHISTORIQUE WHERE IDASSETHIST = ?";

		Connection conn = null;

		try {
			conn = dataSourceTopaze.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, assetHistoriqueId);
			AssetHistorique assetHistorique = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				assetHistorique = new AssetHistorique(
					rs.getInt("PERF"),
					rs.getFloat("DERNIERPRIX"),
					rs.getDate("DATEARCHIVAGE")
				);
			}
			rs.close();
			ps.close();
			return assetHistorique;
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
