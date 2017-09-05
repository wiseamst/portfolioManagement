package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import idao.AssetIDAO;
import model.Asset;

public class AssetDAO  implements AssetIDAO {

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


	public void insert(Asset asset) {
		// TODO Auto-generated method stub
		
	}

	public void insertAsset(Asset asset){

		String sql = "INSERT INTO ASSET " +
				"(ISIN, TICKERBBG, DEVISE, TYPE, NOM, DERNIERPRIX, DATEMAJ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		Connection conn = null;

		try {
			conn = dataSourceWiseam.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, asset.getIsin());
			ps.setInt(2, asset.getTickerBBG());
			ps.setString(3, asset.getDevise());
			ps.setString(4, asset.getType());
			ps.setString(5, asset.getNom());
			ps.setFloat(6, asset.getDernierPrix());
			ps.setDate(7, asset.getDateMAJ());
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
	
	public Asset findByAssetId(int assetId){

		String sql = "SELECT * FROM ASSET WHERE IDASSET = ?";

		Connection conn = null;

		try {
			conn = dataSourceTopaze.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, assetId);
			Asset asset = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				asset = new Asset(
					rs.getInt("ISIN"),
					rs.getInt("TICKERBBG"),
					rs.getString("DEVISE"),
					rs.getString("TYPE"),
					rs.getString("NOM"),
					rs.getFloat("DERNIERPRIX"),
					rs.getDate("DATEMAJ")
				);
			}
			rs.close();
			ps.close();
			return asset;
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
