package idao;

import dao.AssetHistoriqueDAO;
import model.Asset;
import model.AssetHistorique;

public interface AssetHistoriqueIDAO {

	public AssetHistorique findByAssetHistoriqueId(Asset asset);
	
	public void findPriceDateAssetTopaze(Asset asset);
	
	public void findPriceDateAssetTopaze(Asset asset,AssetHistorique assetHistorique);
	
	public float findPerfAssetTopaze(AssetHistorique assetHistorique);
	
	public void insertWiseamAssetHist(AssetHistorique assetHistorique);
	
}
