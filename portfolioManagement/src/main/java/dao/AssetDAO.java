package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;
import idao.AssetIDAO;
import model.AllocationWecoHistorique;
import model.Asset;
import model.AssetHistorique;
import model.PortefeuilleG;

public class AssetDAO  implements AssetIDAO {

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
	public void findAllAssetTopaze(AssetHistoriqueDAO assetHistoriqueDAO){

	String sql = "select nbins,coins,coccy,isin,nains,ticker,tyins,tycla,cogeo from tw_instrument";

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
		
		System.out.println(asset.toString());
		
		hibernateWiseam.saveOrUpdate(asset);
		
	}

	@Transactional(value="txManagerWiseam",readOnly = false)
	public Asset findPriceDateAssetTopaze(Asset asset){

	String sql = "select x.dapri,x.close from tw_price x where x.nbins = ? and x.dapri = (select max(y.dapri) from tw_price y where x.nbins =y.nbins )";
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

	@Transactional(value="txManagerWiseam",readOnly = false)
	public List<Asset> findAssetByPtfAlloc(PortefeuilleG portefeuilleG){

	String sql = "select idasset,zone,classType,isin,nom from asset where idasset in (select idasset from allocation where idportefg=?)";
	Connection conn = null;

	try {
		conn = dataSourceWiseam.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, portefeuilleG.getIdPortefG());
		Asset asset= null;
		List<Asset> assets = new ArrayList<Asset>();
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			asset = new Asset(
				rs.getInt("IDASSET"),
				rs.getString("ISIN"),
				rs.getString("NOM"),
				rs.getString("CLASSTYPE"),
				rs.getString("ZONE")
			);
			
			assets.add(asset);
		}
		rs.close();
		ps.close();
		return assets;
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
