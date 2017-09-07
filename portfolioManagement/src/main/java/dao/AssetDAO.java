package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;
import idao.AssetIDAO;
import model.Asset;
import model.AssetHistorique;

public class AssetDAO  implements AssetIDAO {

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
	public void findAllAssetTopaze(AssetHistoriqueDAO assetHistoriqueDAO){

	String sql = "select NBINS,COINS,COCCY,ISIN,NAINS,TICKER,TYINS,TYCLA,COGEO from tw_instrument";

	Connection conn = null;

	try {
		conn = dataSourceTopaze.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		Asset asset = null;
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			asset = new Asset(
				rs.getInt("NBINS"),
				rs.getString("COINS"),
				rs.getString("ISIN"),
				rs.getString("TICKER"),
				rs.getString("COCCY"),
				rs.getString("TYINS"),
				rs.getString("NAINS"),
				rs.getString("TYCLA"),
				rs.getString("COGEO")
			);
			
		insertWiseamAsset(asset);
		
		AssetHistorique assetHistorique= assetHistoriqueDAO.findByAssetHistoriqueId(asset);
		
		if (assetHistorique.getDateArchivage()==null) {
			assetHistoriqueDAO.findPriceDateAssetTopaze(asset);
			
		}else {
			assetHistoriqueDAO.findPriceDateAssetTopaze(asset, assetHistorique);
		}
		
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
	public void insertWiseamAsset(Asset asset) {
		
		Asset assetTemp = findPriceDateAssetTopaze(asset);
		
		if (assetTemp != null) {
			asset.setDernierPrix(assetTemp.getDernierPrix());
			asset.setDateMAJ(assetTemp.getDateMAJ());
		}
		hibernateWiseam.saveOrUpdate(asset);
		
	}

	
	@Transactional(value="txManagerWiseam",readOnly = false)
	public Asset findPriceDateAssetTopaze(Asset asset){

	String sql = "select X.DAPRI,X.CLOSE from tw_price X where X.NBINS = ? AND X.DAPRI = (select MAX(Y.dapri) from tw_price Y where X.nbins =Y.nbins )";
	Connection conn = null;

	try {
		conn = dataSourceTopaze.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, asset.getIdAsset());
		Asset assetTemp = null;
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			assetTemp = new Asset(
				rs.getFloat("CLOSE"),
				rs.getDate("DAPRI")
			);
		}
		rs.close();
		ps.close();
		return assetTemp;
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
