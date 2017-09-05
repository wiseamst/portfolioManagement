package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import idao.ClientFinalIDAO;
import model.ClientFinal;

public class ClientFinalDAO implements  ClientFinalIDAO {

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

	public void insert(ClientFinal clientFinal) {
		// TODO Auto-generated method stub
		
	}

	public void insertClientFinal(ClientFinal clientFinal){

		String sql = "INSERT INTO CLIENTFINAL " +
				"(NOM, PRENOM, COORDONNEES, MAIL, TYPE, DATEDEBUTCONTRAT)) VALUES (?, ?, ?, ?, ?, ?, ?)";
		Connection conn = null;

		try {
			conn = dataSourceWiseam.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, clientFinal.getNom());
			ps.setString(2, clientFinal.getPrenom());
			ps.setString(3, clientFinal.getCoordonnees());
			ps.setString(4, clientFinal.getMail());
			ps.setString(5, clientFinal.getType());
			ps.setDate(6, clientFinal.getDateDebutContrat());
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

	public ClientFinal findByClientFinalId(int clientId){

		String sql = "SELECT * FROM CLIENTFINAL WHERE IDCLIENT = ?";

		Connection conn = null;

		try {
			conn = dataSourceTopaze.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, clientId);
			ClientFinal clientFinal = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				clientFinal = new ClientFinal(
					rs.getString("NOM"),
					rs.getString("PRENOM"),
					rs.getString("COORDONNEES"),
					rs.getString("MAIL"),
					rs.getString("TYPE"),
					rs.getDate("DATEDEBUTCONTRAT")
				);
			}
			rs.close();
			ps.close();
			return clientFinal;
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
