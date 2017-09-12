package idao;

import dao.AssetHistoriqueDAO;
import model.Asset;

public interface AssetIDAO {

	
	public void findAllAssetTopaze(AssetHistoriqueDAO assetHistoriqueDAO);
	
	public void insertWiseamAsset(Asset asset) ;
	
	public Asset findPriceDateAssetTopaze(Asset asset);
}
