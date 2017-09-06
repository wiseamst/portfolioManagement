package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.orm.hibernate5.HibernateTemplate;

import idao.AllocationIDAO;
import model.Allocation;

public class AllocationDAO implements AllocationIDAO{

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


	public void setDataSourceWiseam(DataSource dataSourceWiseam) {
		this.dataSourceWiseam = dataSourceWiseam;
	}

	public void insertAllocation(Allocation allocation){

		String sql = "INSERT INTO ALLOCATION " +
				"(IDALLOC,QTY,POIDS,PRIXALLOCATION,DATEALLOCATION) VALUES (?, ?, ?, ?, ?)";
		Connection conn = null;

		try {
			conn = dataSourceWiseam.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setFloat(1, allocation.getQty());
			ps.setFloat(2, allocation.getPoids());
			ps.setFloat(3, allocation.getPrixAllocation());
			ps.setDate(4, allocation.getDateAllocation());
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
	
	public Allocation findByAllocationId(int allocationId){

		String sql = "SELECT * FROM ALLOCATION WHERE IDALLOC = ?";

		Connection conn = null;

		try {
			conn = dataSourceTopaze.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, allocationId);
			Allocation allocation = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				allocation = new Allocation(
					rs.getFloat("QTY"),
					rs.getFloat("POIDS"),
					rs.getFloat("PRIXALLOCATION"),
					rs.getDate("DATEALLOCATION")
				);
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


	public void insert(Allocation allocation) {
		// TODO Auto-generated method stub
		
	}
}
