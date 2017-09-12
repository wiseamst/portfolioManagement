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
	public void checkFictif() {
		
		ClientFinal clientFinal = hibernateWiseam.get(ClientFinal.class, 1);

		if (clientFinal ==null) {
			clientFinal = new ClientFinal("Fictif");
			insertWiseamClient(clientFinal);
		}
	}
	
	@Transactional(value="txManagerWiseam",readOnly = false)
	public void insertWiseamClient(ClientFinal clientFinal) {
		
		System.out.println(clientFinal.toString());
		
		hibernateWiseam.saveOrUpdate(clientFinal);
		
	}

	@Transactional(value="txManagerWiseam",readOnly = false)
	public ClientFinal findByClientFinalId(int clientId){

		String sql = "select * from clientfinal where idclient = ?";

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
