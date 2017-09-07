package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;
import idao.CGPIDAO;
import model.CGP;

public class CGPDAO implements CGPIDAO {

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
	public void insertWiseamCGP(CGP cgp) {
		
		hibernateWiseam.saveOrUpdate(cgp);
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
