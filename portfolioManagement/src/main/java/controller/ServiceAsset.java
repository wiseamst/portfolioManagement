package controller;

import dao.AssetDAO;
import dao.AssetHistoriqueDAO;

public class ServiceAsset {
   
	private AssetDAO assetDAO;
    private AssetHistoriqueDAO assetHistoriqueDAO;
	
    public ServiceAsset(AssetDAO assetDAO, AssetHistoriqueDAO assetHistoriqueDAO) {

		
		this.assetDAO = assetDAO;
		this.assetHistoriqueDAO = assetHistoriqueDAO;
	}
    
	public void findAllAssetTopaze () {
		
		assetDAO.findAllAssetTopaze(assetHistoriqueDAO);

	}

}
