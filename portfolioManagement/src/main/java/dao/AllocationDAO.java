package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;
import idao.AllocationIDAO;
import model.Allocation;
import model.Asset;
import model.PortefeuilleG;

public class AllocationDAO implements AllocationIDAO{

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
	public Allocation findAllAllocationTopaze(){

		String sql = "select nbins,nbpos,qtpos,vapos,wgpos from tw_position";

		Connection conn = null;

		try {
			conn = dataSourceTopaze.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			Allocation allocation = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				allocation = new Allocation(
					rs.getFloat("QTPOS"),
					rs.getFloat("WGPOS"),
					rs.getFloat("VAPOS")
				);	
			
			PortefeuilleG portefeuilleTemp = hibernateWiseam.get(PortefeuilleG.class,rs.getInt("NBINS"));
			Asset assetTemp = hibernateWiseam.get(Asset.class, rs.getInt("NBPOS"));

			if (assetTemp!=null && portefeuilleTemp!=null) {
				
				allocation.setAsset(assetTemp);
				allocation.setPortef(portefeuilleTemp);
				
				insertWiseamAllocation(allocation);
			}

			}
			
			rs.close();
			ps.close();
			
			return allocation;
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

	@Transactional(value="txManagerWiseam",readOnly = false)
	public void insertWiseamAllocation (Allocation allocation) {
		
		System.out.println(allocation.toString());
		
		hibernateWiseam.saveOrUpdate(allocation);

	}
}
