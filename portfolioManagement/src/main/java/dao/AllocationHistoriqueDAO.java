package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;
import idao.AllocationHistoriqueIDAO;
import model.Allocation;
import model.AllocationHistorique;
import model.PortefeuilleG;
import model.PortefeuilleHistorique;

public class AllocationHistoriqueDAO implements  AllocationHistoriqueIDAO {

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
	public AllocationHistorique findByAllocationHistoriqueId(Allocation allocation){

	String sql = "select max(datearchivageallocation) datearchivageallocation from allocationhistorique where idportefg = ? and idasset = ?";

		Connection conn = null;

		try {
			conn = dataSourceWiseam.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, allocation.getPortef().getIdPortefG());
			ps.setInt(2, allocation.getAsset().getIdAsset());
			AllocationHistorique allocationHistorique = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				allocationHistorique = new AllocationHistorique(
					rs.getDate("DATEARCHIVAGEALLOCATION"),
					allocation
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

	@Transactional(value="txManagerWiseam",readOnly = false)
	public void findAllocHistTopaze(Allocation allocation){

	String sql = "select x.nbins,x.nbpos,x.dapos from tw_position x where x.nbins= ? and x.nbpos= ? and"
			+ " x.dapos < ?  order by x.dapos desc";

	Connection conn = null;
	
	try {
		conn = dataSourceTopaze.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, allocation.getPortef().getIdPortefG());
		ps.setInt(2, allocation.getAsset().getIdAsset());
		ps.setDate(3, allocation.getDateAllocation());
		AllocationHistorique allocationHistoriqueTemp = null;
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			allocationHistoriqueTemp = new AllocationHistorique(
					rs.getDate("DAPOS"),
					allocation
			);
			
			insertWiseamAllocationHistorique(allocationHistoriqueTemp);
		
		}
		rs.close();
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
	
	@Transactional(value="txManagerWiseam",readOnly = false)
	public void findAllocHistTopaze(Allocation allocation,AllocationHistorique allocationHistorique){

	String sql = "select x.nbins,x.nbpos,x.dapos from tw_position x where x.nbins= ? and x.nbpos= ? and"
			+ " x.dapos < ? and x.dapos > ? order by x.dapos desc";

	Connection conn = null;
	
	try {
		conn = dataSourceTopaze.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, allocation.getPortef().getIdPortefG());
		ps.setInt(2, allocation.getAsset().getIdAsset());
		ps.setDate(3, allocation.getDateAllocation());
		ps.setDate(4, allocationHistorique.getDateArchivageAllocation());
		AllocationHistorique allocationHistoriqueTemp = null;
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			allocationHistoriqueTemp = new AllocationHistorique(
					rs.getDate("DAPOS"),
					allocation
			);
			
			insertWiseamAllocationHistorique(allocationHistoriqueTemp);
		
		}
		rs.close();
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
	
	@Transactional(value="txManagerWiseam",readOnly = false)
	public void insertWiseamAllocationHistorique(AllocationHistorique allocationHistorique) {
		
		System.out.println(allocationHistorique.toString());
		
		hibernateWiseam.saveOrUpdate(allocationHistorique);
		
	}
	
}
