package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;
import idao.AssetHistoriqueIDAO;
import model.Asset;
import model.AssetHistorique;

public class AssetHistoriqueDAO implements AssetHistoriqueIDAO {

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
	
	public AssetHistorique findByAssetHistoriqueId(Asset asset){

		String sql = "SELECT MAX(DATEARCHIVAGE) DATEARCHIVAGE FROM ASSETHISTORIQUE WHERE IDASSET = ?";

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

	String sql = "SELECT x.nbins,x.dapri,x.close FROM tw_price x WHERE x.nbins=? and"
			+ " x.dapri < (SELECT max(y.dapri) FROM tw_price y WHERE x.nbins = y.nbins) ";

	Connection conn = null;

	try {
		conn = dataSourceTopaze.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, asset.getIdAsset());
		AssetHistorique assetHistorique = null;
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			assetHistorique = new AssetHistorique(
					rs.getFloat("CLOSE"), //ID ASSET
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

	String sql = "SELECT x.nbins,x.dapri,x.close FROM tw_price x WHERE x.nbins= ? and"
			+ " x.dapri < (SELECT max(y.dapri) FROM tw_price y WHERE x.nbins = y.nbins) and"
			+ " x.dapri > ?";

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
				rs.getFloat("CLOSE"), //ID ASSET
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

	String sql = "SELECT varet FROM tw_return WHERE nbins= ? and daret= ? ";

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
		
		hibernateWiseam.saveOrUpdate(assetHistorique);
		
	}
}
