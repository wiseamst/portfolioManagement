package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import idao.CGPIDAO;
import model.CGP;

public class CGPDAO implements CGPIDAO {

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

	public void insert(CGP cgp) {
		// TODO Auto-generated method stub
		
	}

	public void insertCGP(CGP cgp){

		String sql = "INSERT INTO CGP " +
				"(NOMSOCIETE, NOMCGP, COORDONNEES) VALUES (?, ?, ?, ?)";
		Connection conn = null;

		try {
			conn = dataSourceWiseam.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, cgp.getNomSociete());
			ps.setString(2, cgp.getNomCGP());
			ps.setString(3, cgp.getCoordonnees());
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

	public CGP findByCGPId(int cgpId){

		String sql = "SELECT * FROM CGP WHERE IDCGP = ?";

		Connection conn = null;

		try {
			conn = dataSourceTopaze.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, cgpId);
			CGP cgp = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				cgp = new CGP(
					rs.getString("NOMSOCIETE"),
					rs.getString("NOMCGP"),
					rs.getString("COORDONNEES")
				);
			}
			rs.close();
			ps.close();
			return cgp;
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