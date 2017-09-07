package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;
import idao.ClientFinalIDAO;
import model.ClientFinal;

public class ClientFinalDAO implements  ClientFinalIDAO {

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
	public void insert(ClientFinal clientFinal) {
	
		hibernateWiseam.saveOrUpdate(clientFinal);
		
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
