package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;
import idao.AssetHistoriqueIDAO;
import model.Allocation;
import model.Asset;
import model.AssetHistorique;
import model.ClientFinal;
import model.PortefeuilleG;

public class AssetHistoriqueDAO implements AssetHistoriqueIDAO {

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
	public AssetHistorique findByAssetHistoriqueId(Asset asset){

		String sql = "select max(datearchivage) datearchivage from assethistorique where idasset = ?";

		Connection conn = null;

		try {
			conn = dataSourceWiseam.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, asset.getIdAsset());
			AssetHistorique assetHistorique = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				assetHistorique = new AssetHistorique(
					rs.getDate("DATEARCHIVAGE"),
					asset
				);
			}
			rs.close();
			ps.close();
			return assetHistorique;
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
	public void findPriceDateAssetTopaze(Asset asset){

	String sql = "select x.nbins,x.dapri,x.close from tw_price x where x.nbins=? and"
			+ " x.dapri < (select max(y.dapri) from tw_price y where x.nbins = y.nbins) order by x.dapri desc ";
// and x.dapri>'2017-07-01'
	Connection conn = null;

	try {
		conn = dataSourceTopaze.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, asset.getIdAsset());
		AssetHistorique assetHistorique = null;
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			assetHistorique = new AssetHistorique(
					rs.getFloat("CLOSE"),
					rs.getDate("DAPRI"),
					asset
				);
		
		assetHistorique.setPerf(findPerfAssetTopaze(assetHistorique));
			
		insertWiseamAssetHist(assetHistorique);
		
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
	public void findPriceDateAssetTopaze(Asset asset,AssetHistorique assetHistorique){

	String sql = "select x.nbins,x.dapri,x.close from tw_price x where x.nbins= ? and"
			+ " x.dapri < (select max(y.dapri) from tw_price y where x.nbins = y.nbins) and"
			+ " x.dapri > ? order by x.dapri desc";

	Connection conn = null;
	
	try {
		conn = dataSourceTopaze.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, asset.getIdAsset());
		ps.setDate(2, assetHistorique.getDateArchivage());
		AssetHistorique assetHistoriqueTemp = null;
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			assetHistoriqueTemp = new AssetHistorique(
				rs.getFloat("CLOSE"),
				rs.getDate("DAPRI"),
				asset
			);
		
		assetHistoriqueTemp.setPerf(findPerfAssetTopaze(assetHistoriqueTemp));
		
		insertWiseamAssetHist(assetHistoriqueTemp);
		
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
	public float findPerfAssetTopaze(AssetHistorique assetHistorique){

	String sql = "select varet from tw_return where nbins= ? and daret= ? ";

	Connection conn = null;
	
	try {
		conn = dataSourceTopaze.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, assetHistorique.getAsset().getIdAsset());
		ps.setDate(2, assetHistorique.getDateArchivage());
		float perf= 0;
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			perf = rs.getFloat("VARET");
			}
		
		rs.close();
		ps.close();
		return perf;
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
	public void insertWiseamAssetHist(AssetHistorique assetHistorique) {
		
		System.out.println(assetHistorique.toString());
		
		hibernateWiseam.saveOrUpdate(assetHistorique);
		
	}
	
	@Transactional(value="txManagerWiseam",readOnly = false)
	public List<AssetHistorique> findWiseamAssetHist(Asset asset) {
		
		Object[] params = new Object[]{asset.getIdAsset()};
		return (List<AssetHistorique>) hibernateWiseam.find("select r from AssetHistorique r where r.asset.idAsset=?  ORDER BY r.dateArchivage DESC",params);

	}
}
