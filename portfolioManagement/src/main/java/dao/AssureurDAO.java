package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import idao.AssureurIDAO;
import model.Asset;
import model.Assureur;

public class AssureurDAO implements AssureurIDAO {

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
	public void insert(Assureur assureur) {
		hibernateWiseam.saveOrUpdate(assureur);
		
	}
	
	public void insertAssureur(Assureur assureur){

		String sql = "INSERT INTO ASSUREUR " +
				"(NOMASSUREUR, COORDONNEES) VALUES (?, ?)";
		Connection conn = null;

		try {
			conn = dataSourceWiseam.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, assureur.getNomAssureur());
			ps.setString(2, assureur.getCoordonnees());
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

	public Assureur findByAssureurId(int custId){

		String sql = "SELECT * FROM ASSUREUR WHERE IDASSUREUR = ?";

		Connection conn = null;

		try {
			conn = dataSourceTopaze.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, custId);
			Assureur assureur = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				assureur = new Assureur(
					rs.getString("NAENT"),
					rs.getString("TYCTR")
				);
			}
			rs.close();
			ps.close();
			return assureur;
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
	
	public Assureur findByAssureurTopaze(int custId){ //46

		String sql = "SELECT NAENT,TYCTR FROM tw_portfolio WHERE NBINS = ?";

		Connection conn = null;

		try {
			conn = dataSourceTopaze.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, custId);
			Assureur assureur = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				assureur = new Assureur(
					rs.getString("NAENT"),
					rs.getString("TYCTR")
				);
			}
			rs.close();
			ps.close();
			return assureur;
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
