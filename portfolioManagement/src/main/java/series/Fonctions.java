package series;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import dao.AllocationDAO;
import dao.AllocationWecoHistoriqueDAO;
import dao.AssetDAO;
import dao.AssetHistoriqueDAO;
import dao.PortefeuilleGDAO;
import dao.PortefeuilleHistoriqueDAO;
import model.Allocation;
import model.AllocationWecoHistorique;
import model.Asset;
import model.AssetHistorique;
import model.PortefeuilleG;
import model.PortefeuilleHistorique;

public class Fonctions {

    private PortefeuilleGDAO portefeuilleGDAO;
    private PortefeuilleHistoriqueDAO portefeuilleHistoriqueDAO; 
	
    private AllocationDAO allocationDAO;
	
    private AllocationWecoHistoriqueDAO allocationWecoHistoriqueDAO;
	
    private AssetDAO assetDAO;
    private AssetHistoriqueDAO assetHistoriqueDAO;

	private HashMap<Integer,List<Performance>> perfs = new HashMap<Integer,List<Performance>>();
	private HashMap<Integer,List<Performance>> assetHists = new HashMap<Integer,List<Performance>>();
	private HashMap<Integer,List<Performance>> perfsRebased = new HashMap<Integer,List<Performance>>();
	private HashMap<Integer,List<Performance>> perfsReturn = new HashMap<Integer,List<Performance>>();
	private HashMap<Integer,List<Performance>> perfsDrawdown = new HashMap<Integer,List<Performance>>();
	private HashMap<Integer,List<Performance>> perfsAnnee = new HashMap<Integer,List<Performance>>();
	private HashMap<Integer,List<Performance>> perfsMois = new HashMap<Integer,List<Performance>>();
	private HashMap<Integer,Float> ptfReturn = new HashMap<Integer,Float>();
	private HashMap<Integer,Float> ptfVolat = new HashMap<Integer,Float>();
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	public Fonctions(PortefeuilleGDAO portefeuilleGDAO, PortefeuilleHistoriqueDAO portefeuilleHistoriqueDAO,AllocationDAO allocationDAO,AllocationWecoHistoriqueDAO allocationWecoHistoriqueDAO,AssetDAO assetDAO,AssetHistoriqueDAO assetHistoriqueDAO) {
		super();
		this.portefeuilleGDAO = portefeuilleGDAO;
		this.portefeuilleHistoriqueDAO = portefeuilleHistoriqueDAO;
		this.allocationDAO = allocationDAO;
		this.allocationWecoHistoriqueDAO = allocationWecoHistoriqueDAO;
		this.assetDAO = assetDAO;
		this.assetHistoriqueDAO = assetHistoriqueDAO;
	}

	// liste ptf du plus recent au plus ancien (VL)
	public HashMap<Integer,List<Performance>> findallPtfWithPtfHistVL() {
		
		perfs = new HashMap<Integer,List<Performance>>();
		
		List<PortefeuilleG> ptfs =portefeuilleGDAO.findWiseamPtf();
		
		if (ptfs.size()>0) {
			for (PortefeuilleG ptfTemp: ptfs) {
				List<Performance> listPerf= new ArrayList<Performance>();
				List<PortefeuilleHistorique> PtfHists = portefeuilleHistoriqueDAO.findWiseamPtfHistById(ptfTemp.getIdPortefG());
				if (PtfHists.size()>0) {
					for (PortefeuilleHistorique PtfHistTemp: PtfHists) {
						listPerf.add(new Performance(sdf.format(PtfHistTemp.getDateArchivage()),PtfHistTemp.getVl()));
					}
				perfs.put(ptfTemp.getIdPortefG(), listPerf)	;
				
				}
			}
		}
		
		return perfs;
	}	
	
	// liste ptf du plus ancien au plus recent (VL)
	public HashMap<Integer,List<Performance>> findallPtfWithPtfHistAscVL() {
		
		perfs = new HashMap<Integer,List<Performance>>();

		List<PortefeuilleG> ptfs =portefeuilleGDAO.findWiseamPtf();
		
		if (ptfs.size()>0) {
			for (PortefeuilleG ptfTemp: ptfs) {
				List<Performance> listPerf= new ArrayList<Performance>();
				List<PortefeuilleHistorique> PtfHists = portefeuilleHistoriqueDAO.findWiseamPtfHistByIdAsc(ptfTemp.getIdPortefG());
				if (PtfHists.size()>0) {
					for (PortefeuilleHistorique PtfHistTemp: PtfHists) {
						listPerf.add(new Performance(sdf.format(PtfHistTemp.getDateArchivage()),PtfHistTemp.getVl()));
					}
				perfs.put(ptfTemp.getIdPortefG(), listPerf)	;
				}
			}
		}
		
		return perfs;
	}
	
	// liste ptf du plus ancien au plus recent (PERF)
	public HashMap<Integer,List<Performance>> findallPtfWithPtfHistPERF() {
		
		perfs = new HashMap<Integer,List<Performance>>();

		List<PortefeuilleG> ptfs =portefeuilleGDAO.findWiseamPtf();
		
		if (ptfs.size()>0) {
			for (PortefeuilleG ptfTemp: ptfs) {
				List<Performance> listPerf= new ArrayList<Performance>();
				List<PortefeuilleHistorique> PtfHists = portefeuilleHistoriqueDAO.findWiseamPtfHistByIdAsc(ptfTemp.getIdPortefG());
				if (PtfHists.size()>0) {
					for (PortefeuilleHistorique PtfHistTemp: PtfHists) {
						listPerf.add(new Performance(sdf.format(PtfHistTemp.getDateArchivage()),PtfHistTemp.getPerf()));
					}
				perfs.put(ptfTemp.getIdPortefG(), listPerf)	;
				}
			}
		}
		
		return perfs;
	}
	
	// liste ptf du plus ancien au plus recent (PERF)
	public List<AllocationWecoHistorique> findallWecos() {
		
		return allocationWecoHistoriqueDAO.findAllWecos();
	}
	
	public HashMap<Integer,List<Performance>> findAssetVL(List<Asset> assets) {
		
		assetHists = new HashMap<Integer,List<Performance>>();
		
		List<Performance> listPerf= new ArrayList<Performance>();
		
		for (Asset asset: assets) {
			List<AssetHistorique> listAssetHist = assetHistoriqueDAO.findWiseamAssetHist(asset);
		
			for (AssetHistorique assetHist: listAssetHist) {
				listPerf.add(new Performance(sdf.format(assetHist.getDateArchivage()),assetHist.getDernierPrix()));
			}
			
			assetHists.put(asset.getIdAsset(), listPerf);
		
		}

		return assetHists;
	}

	public HashMap<Integer,List<Performance>> findAssetPERF(List<Asset> assets) {
		
		assetHists = new HashMap<Integer,List<Performance>>();
		
		List<Performance> listPerf= new ArrayList<Performance>();
		
		for (Asset asset: assets) {
			List<AssetHistorique> listAssetHist = assetHistoriqueDAO.findWiseamAssetHist(asset);

			for (AssetHistorique assetHist: listAssetHist) {
				listPerf.add(new Performance(sdf.format(assetHist.getDateArchivage()),assetHist.getPerf()));
			}

			assetHists.put(asset.getIdAsset(), listPerf);
		}

		return assetHists;
	}
	
	public List<Asset> findallAssetByPTF(PortefeuilleG portefeuilleG) {

		return assetDAO.findAssetByPtfAlloc(portefeuilleG);
		
	}
	
	public float findWeight(int idAsset,int idPortefG) {
		
		List<Allocation> allocs = allocationDAO.findWiseamAllocation(idAsset, idPortefG);
		
		if (allocs.size()==1) {
			return allocs.get(0).getPoids();
		}else {
			return 0;
		}
	}
	
	public Allocation findAlloc(int idAsset,int idPortefG) {
		
		List<Allocation> allocs = allocationDAO.findWiseamAllocation(idAsset, idPortefG);
		
		if (allocs.size()==1) {
			return allocs.get(0);
		}else {
			return null;
		}
	}
	
	//passer la liste de perf du ancien au plus recent
	public HashMap<Integer,List<Performance>> calc_all_rebasedVL(){
		
		perfs = findallPtfWithPtfHistAscVL();
		
		perfsRebased = new HashMap<Integer,List<Performance>>();
		
		 for (Entry<Integer,List<Performance>> entry : perfs.entrySet()) {
			 	
			 List<Performance> listPerf =entry.getValue();
			 	
			 List<Performance> listPerfRebased= new ArrayList<Performance>(); 
			 
			 listPerfRebased.add(new Performance(listPerf.get(0).getDate(),100));
			 
			 for (int i=1;i<listPerf.size();i++) {
				 listPerfRebased.add(new Performance(
						 listPerf.get(i).getDate(),
						 listPerfRebased.get(i-1).getValeur()*(listPerf.get(i).getValeur()/listPerf.get(i-1).getValeur())));
				 }
			 
			 perfsRebased.put(entry.getKey(), listPerfRebased);
		}
		 
			return perfsRebased;
	}
	
	public HashMap<Integer,List<Performance>> calc_all_rebasedPERF(){
		
		perfs = findallPtfWithPtfHistPERF();
		
		perfsRebased = new HashMap<Integer,List<Performance>>();
		
		 for (Entry<Integer,List<Performance>> entry : perfs.entrySet()) {
			 	
			 List<Performance> listPerf =entry.getValue();
			 	
			 List<Performance> listPerfRebased= new ArrayList<Performance>(); 
			 
			 listPerfRebased.add(new Performance(listPerf.get(0).getDate(),100));
			 
			 for (int i=1;i<listPerf.size();i++) {
				 listPerfRebased.add(new Performance(
						 listPerf.get(i).getDate(),
						 listPerfRebased.get(i-1).getValeur()*listPerf.get(i).getValeur()));
				 }
			 
			 perfsRebased.put(entry.getKey(), listPerfRebased);
		}
		 
			return perfsRebased;
	}
	
	//passer la liste de perf du ancien au plus recent selon date
	//format date dd/MM/yyyy
	public HashMap<Integer,List<Performance>> calc_all_rebasedVL(String date) throws ParseException{

			perfs = findallPtfWithPtfHistAscVL();
			
			perfsRebased = new HashMap<Integer,List<Performance>>();
			
		 	for (Entry<Integer,List<Performance>> entry : perfs.entrySet()) {
			 		
		 			List<Performance> listPerf = new ArrayList<Performance>();

			 		for (Performance perf : entry.getValue()) {
			 			
				 			java.sql.Date date_1 = new java.sql.Date(sdf.parse(perf.getDate()).getTime()); 
				 			java.sql.Date date_2 = new java.sql.Date(sdf.parse(date).getTime()); 
				 			if (date_1.compareTo(date_2)>=0) {
				 				listPerf.add(perf);
				 			}
			 		}

					 List<Performance> listPerfRebased= new ArrayList<Performance>(); 
					 listPerfRebased.add(new Performance(listPerf.get(0).getDate(),100));
					 
					 for (int i=1;i<listPerf.size();i++) {
						 listPerfRebased.add(new Performance(
								 listPerf.get(i).getDate(),
								 listPerfRebased.get(i-1).getValeur()*(listPerf.get(i).getValeur()/listPerf.get(i-1).getValeur())));
						 }
			 
					 perfsRebased.put(entry.getKey(), listPerfRebased);
			 }
			return perfsRebased;
	}
	
	//passer la liste de perf du recent au plus ancien ( list ptf desc )
	public HashMap<Integer,List<Performance>> calc_return(){
		
		perfs = findallPtfWithPtfHistVL();
		
		perfsReturn = new HashMap<Integer,List<Performance>>();
		
		for (Entry<Integer,List<Performance>> entry : perfs.entrySet()) {
			List<Performance> listPerf = entry.getValue();
			
			List<Performance> listPerfReturn= new ArrayList<Performance>(); 
			
			for (int i=0;i<listPerf.size()-1;i++) {
				float last=listPerf.get(i).getValeur();
		 		float previous=listPerf.get(i+1).getValeur();
		 		float res= (last/previous)-1;
		 		
		 		listPerfReturn.add(new Performance(listPerf.get(i).getDate(),res));
			}
			perfsReturn.put(entry.getKey(), listPerfReturn);
		}
		return perfsReturn;
	}
	
	//passer la liste de perf du recent au plus ancien ( list ptf desc )
	public HashMap<Integer,List<Performance>> calc_return_by_list(HashMap<Integer,List<Performance>> perfs){
		
		perfsReturn = new HashMap<Integer,List<Performance>>();
		
		for (Entry<Integer,List<Performance>> entry : perfs.entrySet()) {
			List<Performance> listPerf = entry.getValue();
			
			List<Performance> listPerfReturn= new ArrayList<Performance>(); 
			
			for (int i=0;i<listPerf.size()-1;i++) {
				float last=listPerf.get(i).getValeur();
		 		float previous=listPerf.get(i+1).getValeur();
		 		float res= (last/previous)-1;
		 		
		 		listPerfReturn.add(new Performance(listPerf.get(i).getDate(),res));
			}
			perfsReturn.put(entry.getKey(), listPerfReturn);
		}
		return perfsReturn;
	}
	
	//passer la liste de perf du recent au plus ancien
	public HashMap<Integer,Float> calc_return_creation(){
		
		perfs = findallPtfWithPtfHistVL();
		
		ptfReturn = new HashMap<Integer,Float>();
		
		for (Entry<Integer,List<Performance>> entry : perfs.entrySet()) {
			
			List<Performance> listPerf = entry.getValue();
			
			float last=listPerf.get(0).getValeur();
	 		float previous=listPerf.get(listPerf.size()-1).getValeur();
	 		float res= (last/previous)-1;
	 		
	 		ptfReturn.put(entry.getKey(), res);
		}
		return ptfReturn;
	}
	
	//passer la liste de perf du recent au plus ancien
	public HashMap<Integer,Float> calc_return_annee(int annee){
		
		perfs = findallPtfWithPtfHistVL();
		
		ptfReturn = new HashMap<Integer,Float>();
		
    	float last;
    	float previous;
    	float res;
    	
    	for (Entry<Integer,List<Performance>> entry : perfs.entrySet()) {
    		
	 		int i=0;
			int j=0;
			
	 		while(i<entry.getValue().size()&&annee!=Integer.parseInt(entry.getValue().get(i).getDate().substring(entry.getValue().get(i).getDate().length()-4,entry.getValue().get(i).getDate().length()))) {
	 			i=i+1;
	 		}
	 		
	 		if (i==entry.getValue().size()) {
	 			last=0;
	 		}else {
	 			last=entry.getValue().get(i).getValeur();
	 			j=i+1;
	 		}
	 		int previousAnnee= annee-1;
	 		
	 		while(j<entry.getValue().size()&&previousAnnee!=Integer.parseInt(entry.getValue().get(j).getDate().substring(entry.getValue().get(j).getDate().length()-4,entry.getValue().get(j).getDate().length()))) {
	 			j=j+1;
	 		}
	 		
	 		if (j==entry.getValue().size()) {
	 			previous=0;
	 		}else {
	 			previous=entry.getValue().get(j).getValeur();
	 		}
	 		
	 		if (last==0 ||previous==0) {
	 			res=0;
	 		}else {
	 			res= (last/previous)-1;
	 		}
	 		ptfReturn.put(entry.getKey(), res);
		}
		return ptfReturn;
	}
	
	//passer la liste de perf du recent au plus ancien
	public HashMap<Integer,Float> calc_return_mois(int mois,int nbjrs) {
		
		perfs = findallPtfWithPtfHistVL();
		
		ptfReturn = new HashMap<Integer,Float>();
		
		float res;
		
		for (Entry<Integer,List<Performance>> entry : perfs.entrySet()) {
 			
			List<Performance> listPerf = new ArrayList<Performance>();
			
 			if(mois==1) {
 				if (entry.getValue().size()>=nbjrs+1) {
			 		for (int i=0;i<nbjrs+1;i++) {
			 			listPerf.add(entry.getValue().get(i)); 					
			 		}
 				}
		 		
			}else if(mois==3){

 				if (entry.getValue().size()>=(nbjrs*3)+1) {
			 		for (int i=0;i<(nbjrs*3)+1;i++) {
			 			listPerf.add(entry.getValue().get(i)); 	
			 		}
 				}
			}else if(mois==6){
				if (entry.getValue().size()>=(nbjrs*6)+1) {
			 		for (int i=0;i<(nbjrs*6)+1;i++) {
			 			listPerf.add(entry.getValue().get(i)); 	
			 		}
				}
			}else if(mois==12){
				if (entry.getValue().size()>=(nbjrs*12)+1) {
			 		for (int i=0;i<(nbjrs*12)+1;i++) {
			 			listPerf.add(entry.getValue().get(i)); 	
			 		}
				}
			}
 			if(listPerf.size()>0) {
				float last=listPerf.get(0).getValeur();
		 		float previous=listPerf.get(listPerf.size()-1).getValeur();
		 		res= (last/previous)-1;
 			}else {
 				res=0;
 			}
 			ptfReturn.put(entry.getKey(), res);
		}
		return ptfReturn;
	}
	
	public HashMap<Integer,Float> calc_return_mois_asset(List<Asset> assets,int mois,int nbjrs) {
		
		assetHists = findAssetVL(assets);
		
		ptfReturn = new HashMap<Integer,Float>();
		
		float res;
		
		for (Entry<Integer,List<Performance>> entry : assetHists.entrySet()) {
 			
			List<Performance> listPerf = new ArrayList<Performance>();
			
 			if(mois==1) {
 				if (entry.getValue().size()>=nbjrs+1) {
			 		for (int i=0;i<nbjrs+1;i++) {
			 			listPerf.add(entry.getValue().get(i)); 					
			 		}
 				}
		 		
			}else if(mois==3){

 				if (entry.getValue().size()>=(nbjrs*3)+1) {
			 		for (int i=0;i<(nbjrs*3)+1;i++) {
			 			listPerf.add(entry.getValue().get(i)); 	
			 		}
 				}
			}else if(mois==6){
				if (entry.getValue().size()>=(nbjrs*6)+1) {
			 		for (int i=0;i<(nbjrs*6)+1;i++) {
			 			listPerf.add(entry.getValue().get(i)); 	
			 		}
				}
			}else if(mois==12){
				if (entry.getValue().size()>=(nbjrs*12)+1) {
			 		for (int i=0;i<(nbjrs*12)+1;i++) {
			 			listPerf.add(entry.getValue().get(i)); 	
			 		}
				}
			}
 			if(listPerf.size()>0) {
				float last=listPerf.get(0).getValeur();
		 		float previous=listPerf.get(listPerf.size()-1).getValeur();
		 		res= (last/previous)-1;
 			}else {
 				res=0;
 			}
 			ptfReturn.put(entry.getKey(), res);
		}
		return ptfReturn;
	}
	
	//Passer la liste de perf du recent au plus ancien
	public HashMap<Integer,List<Performance>> calc_drawdown() {
		
		perfs = findallPtfWithPtfHistVL();
		
		perfsDrawdown = new HashMap<Integer,List<Performance>>();
		
		for (Entry<Integer,List<Performance>> entry : perfs.entrySet()) {
			
			 List<Performance> listPerf = entry.getValue();
			
			 List<Performance> listPerfDrawdown= new ArrayList<Performance>(); 
			 
			for (int i=0; i<listPerf.size()-1;i++) {
				
				float valeur = listPerf.get(i).getValeur();
				
				for (int j=i+1;j<listPerf.size();j++) {
					if (listPerf.get(j).getValeur()> valeur) {
						valeur = listPerf.get(j).getValeur();}
				}
				
				float res = (listPerf.get(i).getValeur()/valeur)-1;
				
				if (res >=0) {
					res =0;}

				listPerfDrawdown.add(new Performance(listPerf.get(i).getDate(),res));
			}
				
				listPerfDrawdown.add(new Performance(listPerf.get(listPerf.size()-1).getDate(),0));
				
				perfsDrawdown.put(entry.getKey(), listPerfDrawdown);
		}
		
		return perfsDrawdown;
	}
	
	//passer la liste de perf du recent au plus ancien
	public HashMap<Integer,List<Performance>> calc_drawdown_by_list(HashMap<Integer,List<Performance>> perfs) {
		
		perfsDrawdown = new HashMap<Integer,List<Performance>>();
		
		for (Entry<Integer,List<Performance>> entry : perfs.entrySet()) {
			
			 List<Performance> listPerf = entry.getValue();
			
			 List<Performance> listPerfDrawdown= new ArrayList<Performance>(); 
			 
			for (int i=0; i<listPerf.size()-1;i++) {
				
				float valeur = listPerf.get(i).getValeur();
				
				for (int j=i+1;j<listPerf.size();j++) {
					if (listPerf.get(j).getValeur()> valeur) {
						valeur = listPerf.get(j).getValeur();}
				}
				
				float res = (listPerf.get(i).getValeur()/valeur)-1;
				
				if (res >=0) {
					res =0;}

				listPerfDrawdown.add(new Performance(listPerf.get(i).getDate(),res));
			}
				
				listPerfDrawdown.add(new Performance(listPerf.get(listPerf.size()-1).getDate(),0));
				
				perfsDrawdown.put(entry.getKey(), listPerfDrawdown);
		}
		
		return perfsDrawdown;
	}
	
	//passer la liste de perf du recent au plus ancien
	//par mois
	public HashMap<Integer,List<Performance>> calc_mois(int mois,int nbjrs) {
		
		perfs = findallPtfWithPtfHistVL();
		
		perfsMois = new HashMap<Integer,List<Performance>>();	
		
		for (Entry<Integer,List<Performance>> entry : perfs.entrySet()) {
 			
			List<Performance> listPerf = new ArrayList<Performance>();
			
 			if(mois==1) {
 				if (entry.getValue().size()>=nbjrs+1) {
			 		for (int i=0;i<nbjrs+1;i++) {
			 			listPerf.add(entry.getValue().get(i)); 					
			 		}
 				}
		 		
			}else if(mois==3){

 				if (entry.getValue().size()>=(nbjrs*3)+1) {
			 		for (int i=0;i<(nbjrs*3)+1;i++) {
			 			listPerf.add(entry.getValue().get(i)); 	
			 		}
 				}
			}else if(mois==6){
				if (entry.getValue().size()>=(nbjrs*6)+1) {
			 		for (int i=0;i<(nbjrs*6)+1;i++) {
			 			listPerf.add(entry.getValue().get(i)); 	
			 		}
				}
			}else if(mois==12){
				if (entry.getValue().size()>=(nbjrs*12)+1) {
			 		for (int i=0;i<(nbjrs*12)+1;i++) {
			 			listPerf.add(entry.getValue().get(i)); 	
			 		}
				}
			}
 			perfsMois.put(entry.getKey(), listPerf);
 			
		}
		return perfsMois;
	}
	
	//par mois
	public HashMap<Integer,List<Performance>> calc_mois_asset(List<Asset> assets,int mois,int nbjrs) {
		
		assetHists = findAssetVL(assets);
		
		perfsMois = new HashMap<Integer,List<Performance>>();	
		
		for (Entry<Integer,List<Performance>> entry : assetHists.entrySet()) {
 			
			List<Performance> listPerf = new ArrayList<Performance>();
			
 			if(mois==1) {
 				if (entry.getValue().size()>=nbjrs+1) {
			 		for (int i=0;i<nbjrs+1;i++) {
			 			listPerf.add(entry.getValue().get(i)); 					
			 		}
 				}
		 		
			}else if(mois==3){

 				if (entry.getValue().size()>=(nbjrs*3)+1) {
			 		for (int i=0;i<(nbjrs*3)+1;i++) {
			 			listPerf.add(entry.getValue().get(i)); 	
			 		}
 				}
			}else if(mois==6){
				if (entry.getValue().size()>=(nbjrs*6)+1) {
			 		for (int i=0;i<(nbjrs*6)+1;i++) {
			 			listPerf.add(entry.getValue().get(i)); 	
			 		}
				}
			}else if(mois==12){
				if (entry.getValue().size()>=(nbjrs*12)+1) {
			 		for (int i=0;i<(nbjrs*12)+1;i++) {
			 			listPerf.add(entry.getValue().get(i)); 	
			 		}
				}
			}
 			perfsMois.put(entry.getKey(), listPerf);
 			
		}
		return perfsMois;
	}
	
	//passer la liste de perf du recent au plus ancien
	//par annee
	public HashMap<Integer,List<Performance>> calc_annee(int annee) {
		
		perfs = findallPtfWithPtfHistVL();
		
		perfsAnnee = new HashMap<Integer,List<Performance>>();	
		

    	for (Entry<Integer,List<Performance>> entry : perfs.entrySet()) {
    		
			List<Performance> listPerf = new ArrayList<Performance>();
			
	 		int a=0;
			int b=0;
			int c=0;
			int d=0;
			
	 		while(a<entry.getValue().size()&&annee!=Integer.parseInt(entry.getValue().get(a).getDate().substring(entry.getValue().get(a).getDate().length()-4,entry.getValue().get(a).getDate().length()))) {
	 			a=a+1;
	 		}

		 		b=a;

	 		while(b+1<entry.getValue().size()&&annee==Integer.parseInt(entry.getValue().get(b+1).getDate().substring(entry.getValue().get(b+1).getDate().length()-4,entry.getValue().get(b+1).getDate().length()))) {
	 			b=b+1;
	 		}
	 		
	 		int previousAnnee= annee-1;
	 		
	 			c=b+1;
	 		
			while(c<entry.getValue().size()&&previousAnnee!=Integer.parseInt(entry.getValue().get(c).getDate().substring(entry.getValue().get(c).getDate().length()-4,entry.getValue().get(c).getDate().length()))) {
				c=c+1;
			}
			
		 		d=c;
		 		
			while(d+1<entry.getValue().size()&&previousAnnee==Integer.parseInt(entry.getValue().get(d+1).getDate().substring(entry.getValue().get(d+1).getDate().length()-4,entry.getValue().get(d+1).getDate().length()))) {
				d=d+1;
			}

	 		if (b!=entry.getValue().size() && d!=entry.getValue().size()) {
		 		for (int i=b;i<d+1;i++) {
		 			listPerf.add(entry.getValue().get(i));
		 		}
	 		}
	 		

	 		perfsAnnee.put(entry.getKey(), listPerf);
    	}
 			
		return perfsAnnee;
	}
	
	//passer la liste de perf du recent au plus ancien
	public HashMap<Integer,Float> calc_volatilite() {
		
		perfs = findallPtfWithPtfHistVL();
	
		ptfVolat = new HashMap<Integer,Float>();
	
		perfsReturn = calc_return_by_list(perfs);
	
		for (Entry<Integer,List<Performance>> entry : perfsReturn.entrySet()) {
			
			List<Performance> listPerf = entry.getValue();
			
			float moyenne= calc_moyenne (listPerf);
			
			float somme = 0 ; 
			for (Performance perf: listPerf) {
				somme = (float) (somme + Math.pow(perf.getValeur()-moyenne,2));
			}
			
			float res =  (float) Math.sqrt(somme*252);
			
			ptfVolat.put(entry.getKey(), res);
			
		}
		return ptfVolat;
	}

	//passer la liste de perf du recent au plus ancien ( list ptf desc )
	public HashMap<Integer,Float> calc_volatilite_by_list(HashMap<Integer,List<Performance>> perfs) {
	
		ptfVolat = new HashMap<Integer,Float>();
	
		perfsReturn = calc_return_by_list(perfs);
	
		for (Entry<Integer,List<Performance>> entry : perfsReturn.entrySet()) {
			
			List<Performance> listPerf = entry.getValue();
			
			float moyenne= calc_moyenne (listPerf);
			
			float somme = 0 ; 
			for (Performance perf: listPerf) {
				somme = (float) (somme + Math.pow(perf.getValeur()-moyenne,2));
			}
			
			float res =  (float) Math.sqrt(somme*252);
			
			ptfVolat.put(entry.getKey(), res);
			
		}
		return ptfVolat;
	}
	
	//calcul de la moyenne
	public Float calc_moyenne(List<Performance> listPerf) {

		float somme = 0;
		
		for (Performance perf: listPerf) {
			somme = somme + perf.getValeur();
		}
		
		return	 somme / listPerf.size();
		
	}
	
}
