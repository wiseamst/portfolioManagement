package xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import dao.AllocationDAO;
import dao.AllocationWecoHistoriqueDAO;
import dao.AssetDAO;
import dao.AssetHistoriqueDAO;
import dao.PortefeuilleGDAO;
import dao.PortefeuilleHistoriqueDAO;
import model.Allocation;
import model.AllocationWecoHistorique;
import model.Asset;
import model.PortefeuilleG;
import series.Fonctions;
import series.Performance;

public class RunXml {

	private List<AllocationWecoHistorique> wecos = new ArrayList<AllocationWecoHistorique>();
	private List<Asset> assets = new ArrayList<Asset>();
	
	private HashMap<Integer,List<Performance>> perfs = new HashMap<Integer,List<Performance>>();
	private HashMap<Integer,List<Performance>> vlAssetHists = new HashMap<Integer,List<Performance>>();
	private HashMap<Integer,List<Performance>> perfAssetHists = new HashMap<Integer,List<Performance>>();
	
	private HashMap<Integer,List<Performance>> vlRebased = new HashMap<Integer,List<Performance>>();
	private HashMap<Integer,List<Performance>> perfRebased = new HashMap<Integer,List<Performance>>();

	private HashMap<Integer,List<Performance>> draw = new HashMap<Integer,List<Performance>>();
	
	private HashMap<Integer,List<Performance>> volat_2015 = new HashMap<Integer,List<Performance>>();
	private HashMap<Integer,List<Performance>> volat_2016 = new HashMap<Integer,List<Performance>>();
	private HashMap<Integer,List<Performance>> volat_2017 = new HashMap<Integer,List<Performance>>();

	private HashMap<Integer,Float> ptfReturn_creation = new HashMap<Integer,Float>();
	private HashMap<Integer,Float> ptfReturn_1mois = new HashMap<Integer,Float>();
	private HashMap<Integer,Float> ptfReturn_3mois = new HashMap<Integer,Float>();
	private HashMap<Integer,Float> ptfReturn_6mois = new HashMap<Integer,Float>();
	private HashMap<Integer,Float> ptfReturn_12mois = new HashMap<Integer,Float>();
	private HashMap<Integer,Float> ptfReturn_2015 = new HashMap<Integer,Float>();
	private HashMap<Integer,Float> ptfReturn_2016 = new HashMap<Integer,Float>();
	private HashMap<Integer,Float> ptfReturn_2017 = new HashMap<Integer,Float>();
	private HashMap<Integer,Float> ptfVolat_creation = new HashMap<Integer,Float>();
	private HashMap<Integer,Float> ptfVolat_2015 = new HashMap<Integer,Float>();
	private HashMap<Integer,Float> ptfVolat_2016 = new HashMap<Integer,Float>();
	private HashMap<Integer,Float> ptfVolat_2017 = new HashMap<Integer,Float>();
	
	private HashMap<Integer,Float> assetReturn_1mois = new HashMap<Integer,Float>();
	private HashMap<Integer,Float> assetVolat_1mois = new HashMap<Integer,Float>();
 	
	private List<PortefeuilleG>  ptfs = new ArrayList<PortefeuilleG>();
    
	private Fonctions fonction;
    
	public RunXml(PortefeuilleGDAO portefeuilleGDAO, PortefeuilleHistoriqueDAO portefeuilleHistoriqueDAO,AllocationDAO allocationDAO,AllocationWecoHistoriqueDAO allocationWecoHistoriqueDAO,AssetDAO assetDAO,AssetHistoriqueDAO assetHistoriqueDAO) throws ParseException {
		
		fonction= new Fonctions(portefeuilleGDAO,portefeuilleHistoriqueDAO,allocationDAO,allocationWecoHistoriqueDAO,assetDAO,assetHistoriqueDAO);
		
		ptfs =portefeuilleGDAO.findWiseamPtf();
	    
		perfs= fonction.findallPtfWithPtfHistAscVL();
		
		wecos = fonction.findallWecos();
				
		vlRebased= fonction.calc_all_rebasedVL();
		perfRebased= fonction.calc_all_rebasedPERF();
		
		draw = fonction.calc_drawdown();
		
		ptfReturn_creation = fonction.calc_return_creation();
		
		ptfReturn_1mois = fonction.calc_return_mois(1,20);
		ptfReturn_3mois = fonction.calc_return_mois(3,20);
		ptfReturn_6mois = fonction.calc_return_mois(6,20);
		ptfReturn_12mois = fonction.calc_return_mois(12,20);
		
		ptfReturn_2015 = fonction.calc_return_annee(2015);
		ptfReturn_2016 = fonction.calc_return_annee(2016);
		ptfReturn_2017 = fonction.calc_return_annee(2017);
		
		ptfVolat_creation= fonction.calc_volatilite();
		
		volat_2015= fonction.calc_annee(2015);
		volat_2016= fonction.calc_annee(2016);
		volat_2017= fonction.calc_annee(2017);
		
		ptfVolat_2015 = fonction.calc_volatilite_by_list(volat_2015);
		ptfVolat_2016 = fonction.calc_volatilite_by_list(volat_2016);
		ptfVolat_2017 = fonction.calc_volatilite_by_list(volat_2017);
		
	}
	
	public void runXmlAllPtfs() throws JAXBException {
	 	
		for (PortefeuilleG ptfTemp: ptfs) {
			
			assets = fonction.findallAssetByPTF(ptfTemp);
			
			vlAssetHists=fonction.findAssetVL(assets);
			
			assetReturn_1mois = fonction.calc_return_mois_asset(assets, 1, 20);
			assetVolat_1mois =fonction.calc_volatilite_by_list(fonction.calc_mois_asset(assets, 1, 20));

			run(ptfTemp);
		}
	}
	
	public void run(PortefeuilleG ptf) throws JAXBException {
		
		 try {
	      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		  Xml xml = new Xml();
		  
		  xml.setTitle(ptf.getNom()); // Ptf name
		  xml.setDate(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
		  xml.setFreq("Nothing"); // May be to read from excel
		  xml.setRef(0);  // May be to read from excel
		  xml.setIdreporting(0);  // May be to read from excel
		  xml.setType("Nothing");  // May be to read from excel
		  xml.setCat("Nothing");  // May be to read from excel

		  List<Integer> listId = new ArrayList<Integer>();
		  listId.add(ptf.getClientFinal().getIdClient());
		  
		  xml.setClient(new Client(listId));
		  
		  Overview overview = new Overview();
		  overview.setVl(ptf.getVl());
		  overview.set_1year("Nothing");
		  
		  xml.setOverview(overview);
		  
		  /////////////////////////////////////////////////////////////////////
		  Capsule capsule_1 = new Capsule(1,"chart_line");

		  List<String> listLegend_1 = new ArrayList<String>();
		  listLegend_1.add(ptf.getNom());
		  
		  List<Set> listSet_1 = new ArrayList<Set>();
		  if (vlRebased.containsKey(ptf.getIdPortefG())){
			  for (Performance perf: vlRebased.get(ptf.getIdPortefG())) {
				  listSet_1.add(new Set(perf.getDate(),String.valueOf(perf.getValeur())));
			  }
		  }
		  Data data_1 = new Data("chart"); 
		  data_1.setLegend(listLegend_1);
		  data_1.setListSet(listSet_1); 
		  
		  List<Data> listData_1 = new ArrayList<Data>();
		  listData_1.add(data_1);
		  
		  capsule_1.setListData(listData_1);
		  
		  /////////////////////////////////////////////////////////////////////
		  Capsule capsule_2 = new Capsule(2,"chart_line_drawdown");
		  
		  List<String> listLegend_2 = new ArrayList<String>();
		  listLegend_2.add(ptf.getNom());
		  
		  List<Set> listSet_2 = new ArrayList<Set>();
		  
		  if (draw.containsKey(ptf.getIdPortefG())){
			  for (Performance perf: draw.get(ptf.getIdPortefG())) {
				  listSet_2.add(new Set(perf.getDate(),String.valueOf(perf.getValeur())));
			  }
		  }
		  
		  Data data_2 = new Data("chart"); 
		  data_2.setLegend(listLegend_2);
		  data_2.setListSet(listSet_2);
		  
		  List<Data> listData_2 = new ArrayList<Data>();
		  listData_2.add(data_2);
		  
		  capsule_2.setListData(listData_2);
		  
		  /////////////////////////////////////////////////////////////////////
		  Capsule capsule_3 = new Capsule(3,"graph_line");
		  
		  List<String> listLegend_3_a = new ArrayList<String>();
		  listLegend_3_a.add(ptf.getNom());

		  List<Set> listSet_3_a = new ArrayList<Set>();
		  if (perfRebased.containsKey(ptf.getIdPortefG())){
			  for (Performance perf: perfRebased.get(ptf.getIdPortefG())) {
				  listSet_3_a.add(new Set(perf.getDate(),String.valueOf(perf.getValeur())));
			  }
		  }
		  
		  Data data_3_a = new Data("chart"); 
		  data_3_a.setLegend(listLegend_3_a);
		  data_3_a.setListSet(listSet_3_a); 

		  List<String> listLegend_3_b = new ArrayList<String>();
		  listLegend_3_b.add("Benchmark");
		  
		  List<Set> listSet_3_b = new ArrayList<Set>();
		  if (perfRebased.containsKey(ptf.getIdAR())){
			  for (Performance perf: perfRebased.get(ptf.getIdAR())) {
				  listSet_3_b.add(new Set(perf.getDate(),String.valueOf(perf.getValeur())));
			  }
		  }
		  
		  Data data_3_b = new Data("chart");
		  data_3_b.setLegend(listLegend_3_b);
		  data_3_b.setListSet(listSet_3_b); 
		  
		  List<Data> listData_3 = new ArrayList<Data>();
		  listData_3.add(data_3_a);
		  listData_3.add(data_3_b);
		  
		  capsule_3.setListData(listData_3);
		  
		  /////////////////////////////////////////////////////////////////////
		  Capsule capsule_4 = new Capsule(4,"graph_line");
		  
		  List<String> listLegend_4 = new ArrayList<String>();
		  listLegend_4.add(ptf.getNom());
		  
		  List<Set> listSet_4 = new ArrayList<Set>();
		  if (perfs.containsKey(ptf.getIdPortefG())){
			  for (Performance perf: perfs.get(ptf.getIdPortefG())) {
				  listSet_4.add(new Set(perf.getDate(),String.valueOf(perf.getValeur())));
			  }
		  }
		  
		  /////////////////////////////////////////////////////////////////////
		  Capsule capsule_5 = new Capsule(5,"column");
		  
		  List<Cell> listCell_5_b = new ArrayList<Cell>();
		  List<Cell> listCell_5_c = new ArrayList<Cell>();
		  List<Cell> listCell_5_d = new ArrayList<Cell>();
		  List<Cell> listCell_5_e = new ArrayList<Cell>();
		  List<Cell> listCell_5_f = new ArrayList<Cell>();
		  List<Cell> listCell_5_g = new ArrayList<Cell>();
		  List<Cell> listCell_5_h = new ArrayList<Cell>();
		  List<Cell> listCell_5_i = new ArrayList<Cell>();
	  	  List<Cell> listCell_5_j = new ArrayList<Cell>();
	  	  List<Cell> listCell_5_k = new ArrayList<Cell>();
	  	  List<Cell> listCell_5_l = new ArrayList<Cell>();
	  	  
		  for (AllocationWecoHistorique weco: wecos) {

			  if(weco.getPtfG().getIdPortefG()==ptf.getIdPortefG() && weco.getFacteursRisque().equals("Monétaire")) {
				  listCell_5_b.add(new Cell("Monétaire"));
				  listCell_5_b.add(new Cell(Float.toString(weco.getPourcentagePTF())));
				  listCell_5_b.add(new Cell(Float.toString(weco.getPourcentageBench())));
				  listCell_5_b.add(new Cell(Float.toString(weco.getDiff())));
			  } 
		  
			  if(weco.getPtfG().getIdPortefG()==ptf.getIdPortefG() && weco.getFacteursRisque().equals("Obligations d'Etat (High Grade)")) {
				  listCell_5_c.add(new Cell("Obligations d'Etat (High Grade)"));
				  listCell_5_c.add(new Cell(Float.toString(weco.getPourcentagePTF())));
				  listCell_5_c.add(new Cell(Float.toString(weco.getPourcentageBench())));
				  listCell_5_c.add(new Cell(Float.toString(weco.getDiff())));
			  }  
		  
			  if(weco.getPtfG().getIdPortefG()==ptf.getIdPortefG() && weco.getFacteursRisque().equals("Obligations émergentes")) {
				  listCell_5_d.add(new Cell("Obligations émergentes"));
				  listCell_5_d.add(new Cell(Float.toString(weco.getPourcentagePTF())));
				  listCell_5_d.add(new Cell(Float.toString(weco.getPourcentageBench())));
				  listCell_5_d.add(new Cell(Float.toString(weco.getDiff())));
			  }  
		  
			  if(weco.getPtfG().getIdPortefG()==ptf.getIdPortefG() && weco.getFacteursRisque().equals("Actions")) {
				  listCell_5_e.add(new Cell("Actions"));
				  listCell_5_e.add(new Cell(Float.toString(weco.getPourcentagePTF())));
				  listCell_5_e.add(new Cell(Float.toString(weco.getPourcentageBench())));
				  listCell_5_e.add(new Cell(Float.toString(weco.getDiff())));
			  }  

			  if(weco.getPtfG().getIdPortefG()==ptf.getIdPortefG() && weco.getFacteursRisque().equals("Actions américaines")) {
				  listCell_5_f.add(new Cell("Actions américaines"));
				  listCell_5_f.add(new Cell(Float.toString(weco.getPourcentagePTF())));
				  listCell_5_f.add(new Cell(Float.toString(weco.getPourcentageBench())));
				  listCell_5_f.add(new Cell(Float.toString(weco.getDiff())));
			  }  
		  
			  if(weco.getPtfG().getIdPortefG()==ptf.getIdPortefG() && weco.getFacteursRisque().equals("Actions européennes")) {
				  listCell_5_g.add(new Cell("Actions européennes"));
				  listCell_5_g.add(new Cell(Float.toString(weco.getPourcentagePTF())));
				  listCell_5_g.add(new Cell(Float.toString(weco.getPourcentageBench())));
				  listCell_5_g.add(new Cell(Float.toString(weco.getDiff())));
			  }
		  
			  if(weco.getPtfG().getIdPortefG()==ptf.getIdPortefG() && weco.getFacteursRisque().equals("Actions émergentes")) {
				  listCell_5_h.add(new Cell("Actions émergentes"));
				  listCell_5_h.add(new Cell(Float.toString(weco.getPourcentagePTF())));
				  listCell_5_h.add(new Cell(Float.toString(weco.getPourcentageBench())));
				  listCell_5_h.add(new Cell(Float.toString(weco.getDiff())));
			  }  

			  if(weco.getPtfG().getIdPortefG()==ptf.getIdPortefG() && weco.getFacteursRisque().equals("USD/EUR")) {
				  listCell_5_i.add(new Cell("USD/EUR"));
				  listCell_5_i.add(new Cell(Float.toString(weco.getPourcentagePTF())));
				  listCell_5_i.add(new Cell(Float.toString(weco.getPourcentageBench())));
				  listCell_5_i.add(new Cell(Float.toString(weco.getDiff())));
			  }  

			  if(weco.getPtfG().getIdPortefG()==ptf.getIdPortefG() && weco.getFacteursRisque().equals("Small Cap vs Large Cap")) {
				  listCell_5_j.add(new Cell("Small Cap vs Large Cap"));
				  listCell_5_j.add(new Cell(Float.toString(weco.getPourcentagePTF())));
				  listCell_5_j.add(new Cell(Float.toString(weco.getPourcentageBench())));
				  listCell_5_j.add(new Cell(Float.toString(weco.getDiff())));
			  }  
		  

			  if(weco.getPtfG().getIdPortefG()==ptf.getIdPortefG() && weco.getFacteursRisque().equals("Croissance vs Value")) {
				  listCell_5_k.add(new Cell("Croissance vs Value"));
				  listCell_5_k.add(new Cell(Float.toString(weco.getPourcentagePTF())));
				  listCell_5_k.add(new Cell(Float.toString(weco.getPourcentageBench())));
				  listCell_5_k.add(new Cell(Float.toString(weco.getDiff())));
			  }  
		  
			  if(weco.getPtfG().getIdPortefG()==ptf.getIdPortefG() && weco.getFacteursRisque().equals("Volatilité")) {
				  listCell_5_l.add(new Cell("Volatilité"));
				  listCell_5_l.add(new Cell(Float.toString(weco.getPourcentagePTF())));
				  listCell_5_l.add(new Cell(Float.toString(weco.getPourcentageBench())));
				  listCell_5_l.add(new Cell(Float.toString(weco.getDiff())));
			
			  }
		  }
		  
		  /////////////////////////////////////////////////////////////////////
		  Capsule capsule_6 = new Capsule(6,"chart_clustered_stacked_bar");
		  
		  List<Set> listSet_6 = new ArrayList<Set>();
		  listSet_6.add(new Set("01/01","0,10,0"));
		  listSet_6.add(new Set("01/02","0,20,0"));
		  listSet_6.add(new Set("01/03","20,10,20"));
		  listSet_6.add(new Set("01/04","10,10,10"));
		  listSet_6.add(new Set("01/05","50,10,40"));
		  
		  /////////////////////////////////////////////////////////////////////
		  Capsule capsule_7 = new Capsule(7,"column");
		  
		  /////////////////////////////////////////////////////////////////////
		  Capsule capsule_8 = new Capsule(8,"column");

		  List<Cell> listCell_8_1b = new ArrayList<Cell>();
		  listCell_8_1b.add(new Cell("1 mois"));
		  if (ptfReturn_1mois.containsKey(ptf.getIdPortefG())){
			  listCell_8_1b.add(new Cell(ptfReturn_1mois.get(ptf.getIdPortefG()).toString()));}
		  if (ptfReturn_1mois.containsKey(ptf.getIdAR())){
			  listCell_8_1b.add(new Cell(ptfReturn_1mois.get(ptf.getIdAR()).toString()));}
		  
		  List<Cell> listCell_8_1c = new ArrayList<Cell>();
		  listCell_8_1c.add(new Cell("3 mois"));
		  if (ptfReturn_3mois.containsKey(ptf.getIdPortefG())){
			  listCell_8_1c.add(new Cell(ptfReturn_3mois.get(ptf.getIdPortefG()).toString()));}
		  if (ptfReturn_3mois.containsKey(ptf.getIdAR())){
			  listCell_8_1c.add(new Cell(ptfReturn_3mois.get(ptf.getIdAR()).toString()));}
		  
		  List<Cell> listCell_8_1d = new ArrayList<Cell>();
		  listCell_8_1d.add(new Cell("6 mois"));
		  if (ptfReturn_6mois.containsKey(ptf.getIdPortefG())){
			  listCell_8_1d.add(new Cell(ptfReturn_6mois.get(ptf.getIdPortefG()).toString()));}
		  if (ptfReturn_6mois.containsKey(ptf.getIdAR())){
			  listCell_8_1d.add(new Cell(ptfReturn_6mois.get(ptf.getIdAR()).toString()));}
		  
		  List<Cell> listCell_8_1e = new ArrayList<Cell>();
		  listCell_8_1e.add(new Cell("1 an"));
		  if (ptfReturn_12mois.containsKey(ptf.getIdPortefG())){
			  listCell_8_1e.add(new Cell(ptfReturn_12mois.get(ptf.getIdPortefG()).toString()));}
		  if (ptfReturn_12mois.containsKey(ptf.getIdAR())){
			  listCell_8_1e.add(new Cell(ptfReturn_12mois.get(ptf.getIdAR()).toString()));}
		  
		  //////////

		  
		  List<Cell> listCell_8_2b = new ArrayList<Cell>();
		  listCell_8_2b.add(new Cell("2017"));
		  if (ptfReturn_2017.containsKey(ptf.getIdPortefG())){
			  listCell_8_2b.add(new Cell(ptfReturn_2017.get(ptf.getIdPortefG()).toString()));}
		  if (ptfReturn_2017.containsKey(ptf.getIdAR())){
			  listCell_8_2b.add(new Cell(ptfReturn_2017.get(ptf.getIdAR()).toString()));}
		  
		  List<Cell> listCell_8_2c = new ArrayList<Cell>();
		  listCell_8_2c.add(new Cell("2016"));
		  if (ptfReturn_2016.containsKey(ptf.getIdPortefG())){
			  listCell_8_2c.add(new Cell(ptfReturn_2016.get(ptf.getIdPortefG()).toString()));}
		  if (ptfReturn_2016.containsKey(ptf.getIdAR())){
			  listCell_8_2c.add(new Cell(ptfReturn_2016.get(ptf.getIdAR()).toString()));}
		  
		  List<Cell> listCell_8_2d = new ArrayList<Cell>();
		  listCell_8_2d.add(new Cell("2015"));
		  if (ptfReturn_2015.containsKey(ptf.getIdPortefG())){
			  listCell_8_2d.add(new Cell(ptfReturn_2015.get(ptf.getIdPortefG()).toString()));}
		  if (ptfReturn_2015.containsKey(ptf.getIdAR())){
			  listCell_8_2d.add(new Cell(ptfReturn_2015.get(ptf.getIdAR()).toString()));}
		  
		  List<Cell> listCell_8_2e = new ArrayList<Cell>();
		  listCell_8_2e.add(new Cell("Depuis la création"));
		  if (ptfReturn_creation.containsKey(ptf.getIdPortefG())){
			  listCell_8_2e.add(new Cell(ptfReturn_creation.get(ptf.getIdPortefG()).toString()));}
		  if (ptfReturn_creation.containsKey(ptf.getIdAR())){
			  listCell_8_2e.add(new Cell(ptfReturn_creation.get(ptf.getIdAR()).toString()));}
		  
		  //////////
		  
		  List<Cell> listCell_8_3b = new ArrayList<Cell>();
		  listCell_8_3b.add(new Cell("2017"));
		  if (ptfVolat_2017.containsKey(ptf.getIdPortefG())){
			  listCell_8_3b.add(new Cell(ptfVolat_2017.get(ptf.getIdPortefG()).toString()));}
		  if (ptfVolat_2017.containsKey(ptf.getIdAR())){
			  listCell_8_3b.add(new Cell(ptfVolat_2017.get(ptf.getIdAR()).toString()));}
		  
		  List<Cell> listCell_8_3c = new ArrayList<Cell>();
		  listCell_8_3c.add(new Cell("2016"));
		  if (ptfVolat_2016.containsKey(ptf.getIdPortefG())){
			  listCell_8_3c.add(new Cell(ptfVolat_2016.get(ptf.getIdPortefG()).toString()));}
		  if (ptfVolat_2016.containsKey(ptf.getIdAR())){
			  listCell_8_3c.add(new Cell(ptfVolat_2016.get(ptf.getIdAR()).toString()));}
		  
		  List<Cell> listCell_8_3d = new ArrayList<Cell>();
		  listCell_8_3d.add(new Cell("2015"));
		  if (ptfVolat_2015.containsKey(ptf.getIdPortefG())){
			  listCell_8_3d.add(new Cell(ptfVolat_2015.get(ptf.getIdPortefG()).toString()));}
		  if (ptfVolat_2015.containsKey(ptf.getIdAR())){
			  listCell_8_3d.add(new Cell(ptfVolat_2015.get(ptf.getIdAR()).toString()));}
		  
		  List<Cell> listCell_8_3e = new ArrayList<Cell>();
		  listCell_8_3e.add(new Cell("Depuis la création"));
		  if (ptfVolat_creation.containsKey(ptf.getIdPortefG())){
			  listCell_8_3e.add(new Cell(ptfVolat_creation.get(ptf.getIdPortefG()).toString()));}
		  if (ptfVolat_creation.containsKey(ptf.getIdAR())){
			  listCell_8_3e.add(new Cell(ptfVolat_creation.get(ptf.getIdAR()).toString()));}
		  
		  /////////////////////////////////////////////////////////////////////
		  Capsule capsule_9 = new Capsule(9,"column");
		  
		  /////////////////////////////////////////////////////////////////////
		  Capsule capsule_10 = new Capsule(10,"risk");
		  
		  /////////////////////////////////////////////////////////////////////
		  Capsule capsule_11 = new Capsule(11,"vl_encours");
		  
		  List<String> listText_11 = new ArrayList<String>();
		  
		  if (ptf.getTypePTF().equals("Fonds")) {
			  listText_11.add("VL:"+ptf.getVl());
			  listText_11.add("au");
			  listText_11.add("Encours:"+ptf.getAmnav());
		  }
		  
		  if (ptf.getTypePTF().equals("Client")) {
			  listText_11.add("Encours:"+ptf.getAmnav());
		  }

		  Data data_11 = new Data("text");
		  data_11.setListText(listText_11);
		  
		  List<Data> listData_11 = new ArrayList<Data>();	
		  listData_11.add(data_11);
		  capsule_11.setListData(listData_11);
		  
		  /////////////////////////////////////////////////////////////////////
		  Capsule capsule_12 = new Capsule(12,"column");
		  
		  /////////////////////////////////////////////////////////////////////
		  Capsule capsule_13 = new Capsule(13,"list");
		  
		  List<Cell> listCell_13_a = new ArrayList<Cell>();
		  listCell_13_a.add(new Cell("Zone géographique"));
		  listCell_13_a.add(new Cell("Classe"));
		  listCell_13_a.add(new Cell("Isin"));
		  listCell_13_a.add(new Cell("Ligne par ligne"));
		  listCell_13_a.add(new Cell("Poids dans le portefeuille"));
		  listCell_13_a.add(new Cell("Volatilité"));
		  listCell_13_a.add(new Cell("Contribution à la performance"));
		  listCell_13_a.add(new Cell("Contribution à la volatilité"));
		  
		  List<Rows> listRow_13 = new ArrayList<Rows>();
		  listRow_13.add(new Rows(listCell_13_a));

		  for (Asset asset:assets) {
			  
			  List<Cell> listCell_13_b = new ArrayList<Cell>();
			  
			  float weight = fonction.findWeight(asset.getIdAsset(),ptf.getIdPortefG());
			  
			  listCell_13_b.add(new Cell(asset.getZone()));
			  listCell_13_b.add(new Cell(asset.getClassType()));
			  listCell_13_b.add(new Cell(asset.getIsin()));
			  listCell_13_b.add(new Cell(asset.getNom()));
			  listCell_13_b.add(new Cell(String.valueOf(weight)));
			  listCell_13_b.add(new Cell(String.valueOf(assetReturn_1mois.get(asset.getIdAsset()))));
			  listCell_13_b.add(new Cell(String.valueOf(assetVolat_1mois.get(asset.getIdAsset()))));
			  listCell_13_b.add(new Cell("0"));
			  listCell_13_b.add(new Cell("0"));
			  listRow_13.add(new Rows(listCell_13_b));
			  
		  }
		  
		  /////////////////////////////////////////////////////////////////////
		  Capsule capsule_14 = new Capsule(14,"position");
		  
		  /////////////////////////////////////////////////////////////////////
		  Capsule capsule_15 = new Capsule(15,"list");
		  
		  List<Cell> listCell_15 = new ArrayList<Cell>();
		  listCell_15.add(new Cell("1"));
		  listCell_15.add(new Cell("2"));
		  
		  
		  List<Rows> listRow_15 = new ArrayList<Rows>();
		  listRow_15.add(new Rows(listCell_15));
		  
		  /////////////////////////////////////////////////////////////////////
		  Capsule capsule_16 = new Capsule(16,"list");
		  
		  /////////////////////////////////////////////////////////////////////
		  Capsule capsule_17 = new Capsule(17,"chart_pie");
		  
		  /////////////////////////////////////////////////////////////////////
		  Capsule capsule_18 = new Capsule(18,"chart_bar_contribution");
		  
		  /////////////////////////////////////////////////////////////////////
		  
		  List<Capsule> listCapsule = new ArrayList<Capsule>();
		  
		  String FILE_NAME = "C:\\Users\\stagiaire4\\Desktop\\PARAM_REPORTING_EXTRANET.xlsx";
		  
		  FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
			
		  Workbook workbook = new XSSFWorkbook(excelFile);
	           
		  Sheet datatypeSheet = workbook.getSheetAt(0);
        
          for (int j=1; j< datatypeSheet.getLastRowNum(); j++) {
          	
              Row row = datatypeSheet.getRow(j);

              if (row.getCell(0).getCellTypeEnum() == CellType.NUMERIC && (int) row.getCell(0).getNumericCellValue()==ptf.getIdPortefG()){

            	  switch((int) row.getCell(3).getNumericCellValue()) 
            	  {
            	  case 1: 
        	  		  	  capsule_1.setWeight((int) row.getCell(4).getNumericCellValue());
            		  	  capsule_1.setTitle(row.getCell(5).getStringCellValue());
            			  listCapsule.add(capsule_1);
            		  break;
            	  case 2: 
    	  		  	  	  capsule_2.setWeight((int) row.getCell(4).getNumericCellValue());
    	  		  	  	  capsule_2.setTitle(row.getCell(5).getStringCellValue());
            			  listCapsule.add(capsule_2);
            		  break;
            	  case 3:
        	  		      capsule_3.setWeight((int) row.getCell(4).getNumericCellValue());
	        		  	  capsule_3.setTitle(row.getCell(5).getStringCellValue());
            			  listCapsule.add(capsule_3);
            		  break;
            	  case 4:  		  
        	  		      capsule_4.setWeight((int) row.getCell(4).getNumericCellValue());
	        		  	  capsule_4.setTitle(row.getCell(5).getStringCellValue());
            		  	  for (int k = 6; k < row.getLastCellNum(); k=k+3) {
		                		if (row.getCell(k).getCellTypeEnum() == CellType.STRING) {
			                    	
			                    	System.out.println("input :"+k+" "+row.getCell(k).getStringCellValue() );
			                    	listSet_4.add(new Set(row.getCell(k).getStringCellValue(),row.getCell(k+1).getStringCellValue(),row.getCell(k+2).getStringCellValue()));
			                    }
            		  	  }
            		  	  
	            		  Data data_4 = new Data("chart"); 
	            		  data_4.setLegend(listLegend_4);
	            		  data_4.setListSet(listSet_4); 
	            		  
	            		  List<Data> listData_4 = new ArrayList<Data>();	
	            		  listData_4.add(data_4);
	            		  capsule_4.setListData(listData_4);
	            		  
            			  listCapsule.add(capsule_4);
            		  break;
            	  case 5:
        	  		      capsule_5.setWeight((int) row.getCell(4).getNumericCellValue());
            		      capsule_5.setTitle(row.getCell(5).getStringCellValue());
        				  List<Cell> listCell_5_a = new ArrayList<Cell>();
            		  	  
        				  //nombre bien preci
        				  for (int k = 6; k <10; k++) {
        					  if (row.getCell(k).getCellTypeEnum() == CellType.STRING) {
            				  listCell_5_a.add(new Cell(row.getCell(k).getStringCellValue()));
        					  }
            		  	  }
            		  	  
            			  List<Rows> listRow_5 = new ArrayList<Rows>();
            			  listRow_5.add(new Rows(listCell_5_a));
            			  listRow_5.add(new Rows(listCell_5_b));
            			  listRow_5.add(new Rows(listCell_5_c));
            			  listRow_5.add(new Rows(listCell_5_d));
            			  listRow_5.add(new Rows(listCell_5_e));
            			  listRow_5.add(new Rows(listCell_5_f));
            			  listRow_5.add(new Rows(listCell_5_g));
            			  listRow_5.add(new Rows(listCell_5_h));
            			  listRow_5.add(new Rows(listCell_5_i));
            			  listRow_5.add(new Rows(listCell_5_j));
            			  listRow_5.add(new Rows(listCell_5_k));
            			  listRow_5.add(new Rows(listCell_5_l));
            			  
            			  // colonne bien precise
            			  Data data_5_a = new Data("table");
            			  data_5_a.setListRow(listRow_5);
            			  data_5_a.setDisclaimer(row.getCell(10).getStringCellValue());

            			  // for to nn
            			  List<String> listText_5 = new ArrayList<String>();
            			  for (int k = 11; k <row.getLastCellNum(); k++) {
        					  if (row.getCell(k).getCellTypeEnum() == CellType.STRING) {
        						  listText_5.add(row.getCell(k).getStringCellValue());
            					  }
            			  }
            			  
            			  Data data_5_b = new Data("text");
            			  data_5_b.setListText(listText_5);
            			  
            			  List<Data> listData_5 = new ArrayList<Data>();	
            			  listData_5.add(data_5_a);
            			  listData_5.add(data_5_b);
            			  
            			  capsule_5.setListData(listData_5);
            			  
            			  listCapsule.add(capsule_5);
            		  break;
            	  case 6:
        	  		      capsule_6.setWeight((int) row.getCell(4).getNumericCellValue());
	        		  	  capsule_6.setTitle(row.getCell(5).getStringCellValue());
            		  	  
            			  List<String> listText_6 = new ArrayList<String>();
            			  listText_6.add(row.getCell(6).getStringCellValue());
            			  
            			  Data data_6_a = new Data("text");
            			  data_6_a.setListText(listText_6);
            			  		  
            			  List<String> listLegend_6 = new ArrayList<String>();
            			  listLegend_6.add(row.getCell(7).getStringCellValue());
            			  listLegend_6.add(row.getCell(8).getStringCellValue());
            			  listLegend_6.add(row.getCell(9).getStringCellValue());
            			  
            			  Data data_6_b = new Data("chart");
            			  data_6_b.setLegend(listLegend_6);
            			  data_6_b.setListSet(listSet_6);
            			  

            			  List<Data> listData_6 = new ArrayList<Data>();	
            			  listData_6.add(data_6_a);
            			  listData_6.add(data_6_b);
            			  
            			  capsule_6.setListData(listData_6);
            			  
            			  listCapsule.add(capsule_6);
            		  break;
            	  case 7:
        	  		      capsule_7.setWeight((int) row.getCell(4).getNumericCellValue());
	        		  	  capsule_7.setTitle(row.getCell(5).getStringCellValue());
            		  	  
            			  List<String> listText_7 = new ArrayList<String>();
            			  listText_7.add(row.getCell(6).getStringCellValue());

            			  Data data_7 = new Data("text");
            			  data_7.setListText(listText_7);
            			  
            			  List<Data> listData_7 = new ArrayList<Data>();	
            			  listData_7.add(data_7);
            			  capsule_7.setListData(listData_7);
            			  
            			  listCapsule.add(capsule_7);
            		  break;
            	  case 8:
        	  		      capsule_8.setWeight((int) row.getCell(4).getNumericCellValue());
	        		  	  capsule_8.setTitle(row.getCell(5).getStringCellValue());
            		  	  
            			  List<Cell> listCell_8_1a = new ArrayList<Cell>();
            			  listCell_8_1a.add(new Cell(row.getCell(6).getStringCellValue()));
            			  listCell_8_1a.add(new Cell(row.getCell(7).getStringCellValue()));
            			  listCell_8_1a.add(new Cell(row.getCell(8).getStringCellValue()));
            			  
            			  List<Rows> listRow_8_a = new ArrayList<Rows>();
            			  listRow_8_a.add(new Rows(listCell_8_1a));
            			  listRow_8_a.add(new Rows(listCell_8_1b));
            			  listRow_8_a.add(new Rows(listCell_8_1c));
            			  listRow_8_a.add(new Rows(listCell_8_1d));
            			  listRow_8_a.add(new Rows(listCell_8_1e));
            			  
            			  List<Cell> listCell_8_2a = new ArrayList<Cell>();
            			  listCell_8_2a.add(new Cell(row.getCell(9).getStringCellValue()));
            			  listCell_8_2a.add(new Cell(row.getCell(10).getStringCellValue()));
            			  listCell_8_2a.add(new Cell(row.getCell(11).getStringCellValue()));
            			  
            			  List<Rows> listRow_8_b = new ArrayList<Rows>();
            			  listRow_8_b.add(new Rows(listCell_8_2a));
            			  listRow_8_b.add(new Rows(listCell_8_2b));
            			  listRow_8_b.add(new Rows(listCell_8_2c));
            			  listRow_8_b.add(new Rows(listCell_8_2d));
            			  listRow_8_b.add(new Rows(listCell_8_2e));
            			  
            			  List<Cell> listCell_8_3a = new ArrayList<Cell>();
            			  listCell_8_3a.add(new Cell(row.getCell(12).getStringCellValue()));
            			  listCell_8_3a.add(new Cell(row.getCell(13).getStringCellValue()));
            			  listCell_8_3a.add(new Cell(row.getCell(14).getStringCellValue()));
            			  
            			  List<Rows> listRow_8_c = new ArrayList<Rows>();
            			  listRow_8_c.add(new Rows(listCell_8_3a));
            			  listRow_8_c.add(new Rows(listCell_8_3b));
            			  listRow_8_c.add(new Rows(listCell_8_3c));
            			  listRow_8_c.add(new Rows(listCell_8_3d));
            			  listRow_8_c.add(new Rows(listCell_8_3e));
            			  
            			  Data data_8_a = new Data("table");
            			  data_8_a.setListRow(listRow_8_a);
            			  
            			  Data data_8_b = new Data("table");
            			  data_8_b.setListRow(listRow_8_b);
            			  
            			  Data data_8_c = new Data("table");
            			  data_8_c.setListRow(listRow_8_c);
            			  
            			  List<Data> listData_8 = new ArrayList<Data>();	
            			  
            			  listData_8.add(data_8_a);
            			  listData_8.add(data_8_b);
            			  listData_8.add(data_8_c);
            			  
            			  capsule_8.setListData(listData_8);
            			  
            			  listCapsule.add(capsule_8);
            		  break;
            	  case 9:
        	  		      capsule_9.setWeight((int) row.getCell(4).getNumericCellValue());
	        		  	  capsule_9.setTitle(row.getCell(5).getStringCellValue());
            		  	  
            			  List<String> listText_9_a = new ArrayList<String>();
            			  listText_9_a.add(row.getCell(6).getStringCellValue());

            			  List<String> listText_9_b = new ArrayList<String>();
            			  listText_9_b.add(row.getCell(7).getStringCellValue()); 
            			 
            			  Data data_9_a = new Data("text");
            			  data_9_a.setListText(listText_9_a);
            			  
            			  Data data_9_b = new Data("text");
            			  data_9_b.setListText(listText_9_b);
            			  
            			  List<Data> listData_9 = new ArrayList<Data>();	
            			  listData_9.add(data_9_a);
            			  listData_9.add(data_9_b);
            			  
            			  capsule_9.setListData(listData_9);
            			  
            			  listCapsule.add(capsule_9);
            		  break;
            	  case 10:
        	  		      capsule_10.setWeight((int) row.getCell(4).getNumericCellValue());
	        		  	  capsule_10.setTitle(row.getCell(5).getStringCellValue());
            		  	  
            			  List<Set> listSet_10 = new ArrayList<Set>();
            			  listSet_10.add(new Set(row.getCell(6).getStringCellValue()));
            			  
            			  Data data_10 = new Data("risk");
            			  data_10.setListSet(listSet_10);
            			  
            			  List<Data> listData_10 = new ArrayList<Data>();	
            			  listData_10.add(data_10);
            			  
            			  capsule_10.setListData(listData_10);
            			  
            			  listCapsule.add(capsule_10);
            		  break;
            	  case 11:
        	  		      capsule_11.setWeight((int) row.getCell(4).getNumericCellValue());
	        		  	  capsule_11.setTitle(row.getCell(5).getStringCellValue());

            			  listCapsule.add(capsule_11);
            		  break;
            	  case 12:
        	  		      capsule_12.setWeight((int) row.getCell(4).getNumericCellValue());
	        		  	  capsule_12.setTitle(row.getCell(5).getStringCellValue());
            		  	  
            			  List<String> listText_12 = new ArrayList<String>();
            			  listText_12.add(row.getCell(6).getStringCellValue());
            			  
            			  Data data_12 = new Data("text");
            			  data_12.setListText(listText_12);
            			  
            			  List<Data> listData_12 = new ArrayList<Data>();	
            			  listData_12.add(data_12);
            			  capsule_12.setListData(listData_12);
            			  
            			  listCapsule.add(capsule_12);
            		  break;
            	  case 13:
        	  		      capsule_13.setWeight((int) row.getCell(4).getNumericCellValue());
	        		  	  capsule_13.setTitle(row.getCell(5).getStringCellValue());
            		  	  
            			  List<String> listText_13 = new ArrayList<String>();
            			  listText_13.add(row.getCell(6).getStringCellValue());
            			  
            			  Data data_13_a = new Data("text");
            			  data_13_a.setListText(listText_13);
            			  
            			  Data data_13_b = new Data("table");
            			  data_13_b.setListRow(listRow_13);

            			  List<Data> listData_13 = new ArrayList<Data>();	
            			  listData_13.add(data_13_a);
            			  listData_13.add(data_13_b);
            			  
            			  capsule_13.setListData(listData_13);
            			  
            			  listCapsule.add(capsule_13);
            		  break;
            	  case 14:
        	  		      capsule_14.setWeight((int) row.getCell(4).getNumericCellValue());
	        		  	  capsule_14.setTitle(row.getCell(5).getStringCellValue());
            		  	  
            			  List<Cell> listCell_14_a = new ArrayList<Cell>();
            			  listCell_14_a.add(new Cell(row.getCell(6).getStringCellValue()));
            			  listCell_14_a.add(new Cell(row.getCell(7).getStringCellValue()));
            			  listCell_14_a.add(new Cell(row.getCell(8).getStringCellValue()));
            			  
            			  List<Rows> listRow_14 = new ArrayList<Rows>();
            			  listRow_14.add(new Rows("bold",listCell_14_a));

            			  float sommePrix=0;
            			  float sommePoids=0;
            			  
            			  for (Asset asset:assets) {
            				  
            				  Allocation allocation = fonction.findAlloc(asset.getIdAsset(),ptf.getIdPortefG());
            				  
            				  sommePrix= sommePrix+allocation.getPrixAllocation();
            				  sommePoids = sommePoids+allocation.getPoids();
            				  
            				  List<Cell> listCell_14_b = new ArrayList<Cell>();
            				  listCell_14_b.add(new Cell(asset.getNom()));
            				  listCell_14_b.add(new Cell(String.valueOf(allocation.getPrixAllocation())));
            				  listCell_14_b.add(new Cell(String.valueOf(allocation.getPoids())));
            				  listRow_14.add(new Rows(listCell_14_b));
            			  }
            			  
            			  List<Cell> listCell_14_c = new ArrayList<Cell>();
            			  listCell_14_c.add(new Cell("bold",row.getCell(9).getStringCellValue()));
            			  listCell_14_c.add(new Cell(String.valueOf(sommePrix)));
            			  listCell_14_c.add(new Cell(String.valueOf(sommePoids)));
            			  
            			  listRow_14.add(new Rows("bold",listCell_14_c));
            			  
            			  Data data_14_a = new Data("table");
            			  data_14_a.setListRow(listRow_14);

            			  List<String> listText_14 = new ArrayList<String>();
            			  listText_14.add(row.getCell(10).getStringCellValue());
            			  
            			  Data data_14_b = new Data("text");
            			  data_14_b.setListText(listText_14);
            			  
            			  List<Data> listData_14 = new ArrayList<Data>();	
            			  listData_14.add(data_14_a);
            			  listData_14.add(data_14_b);
            			  
            			  capsule_14.setListData(listData_14);
            			  
            			  listCapsule.add(capsule_14);
            		  break;
            	  case 15:
        	  		      capsule_15.setWeight((int) row.getCell(4).getNumericCellValue());
	        		  	  capsule_15.setTitle(row.getCell(5).getStringCellValue());
            		  	  
            			  List<String> listText_15 = new ArrayList<String>();
            			  listText_15.add(row.getCell(6).getStringCellValue());
            			  
            			  Data data_15_a = new Data("text");
            			  data_15_a.setListText(listText_15);
            			  
            			  Data data_15_b = new Data("table");
            			  data_15_b.setListRow(listRow_15);

            			  List<Data> listData_15 = new ArrayList<Data>();	
            			  listData_15.add(data_15_a);
            			  listData_15.add(data_15_b);
            			  
            			  capsule_15.setListData(listData_15);
            			  
            			  listCapsule.add(capsule_15);
            		  break;
            	  case 16:
        	  		      capsule_16.setWeight((int) row.getCell(4).getNumericCellValue());
	        		  	  capsule_16.setTitle(row.getCell(5).getStringCellValue());
            		  	  for (int k = 6; k < row.getLastCellNum(); k++) {
            		  	  }
            		  	  
            			  List<String> listText_16 = new ArrayList<String>();
            			  listText_16.add(row.getCell(6).getStringCellValue());
            			  
            			  Data data_16 = new Data("text","italic");
            			  data_16.setListText(listText_16);
            			  
            			  List<Data> listData_16 = new ArrayList<Data>();	
            			  listData_16.add(data_16);
            			  
            			  capsule_16.setListData(listData_16);
            			  
            			  listCapsule.add(capsule_16);
            		  break;
            	  case 17:
        	  		      capsule_17.setWeight((int) row.getCell(4).getNumericCellValue());
	        		  	  capsule_17.setTitle(row.getCell(5).getStringCellValue());
            		  	  for (int k = 6; k < row.getLastCellNum(); k++) {
            		  	  }
            		  	  
            			  List<Set> listSet_17 = new ArrayList<Set>();
            			  listSet_17.add(new Set(row.getCell(6).getStringCellValue(),"20"));
            			  listSet_17.add(new Set(row.getCell(7).getStringCellValue(),"9,5"));
            			  listSet_17.add(new Set(row.getCell(8).getStringCellValue(),"10,5"));
            			  listSet_17.add(new Set(row.getCell(9).getStringCellValue(),"60"));
            			  
            			  Data data_17 = new Data("chart");
            			  data_17.setListSet(listSet_17);
            			  
            			  List<Data> listData_17 = new ArrayList<Data>();	
            			  listData_17.add(data_17);
            			  
            			  capsule_17.setListData(listData_17);
            			  
            			  listCapsule.add(capsule_17);
            		  break;
            	  case 18:
        	  		      capsule_18.setWeight((int) row.getCell(4).getNumericCellValue());
	        		  	  capsule_18.setTitle(row.getCell(5).getStringCellValue());
            		  	  for (int k = 6; k < row.getLastCellNum(); k++) {
            		  	  }
            		  	  
            			  List<Set> listSet_18_a = new ArrayList<Set>();
            			  listSet_18_a.add(new Set(row.getCell(6).getStringCellValue(),"0,5"));
            			  listSet_18_a.add(new Set(row.getCell(7).getStringCellValue(),"1"));
            			  listSet_18_a.add(new Set(row.getCell(8).getStringCellValue(),"-0,2"));
            			  listSet_18_a.add(new Set(row.getCell(9).getStringCellValue(),"2"));
            			  
            			  List<Set> listSet_18_b = new ArrayList<Set>();
            			  listSet_18_b.add(new Set(row.getCell(10).getStringCellValue(),"3"));
            			  listSet_18_b.add(new Set(row.getCell(11).getStringCellValue(),"1"));
            			  listSet_18_b.add(new Set(row.getCell(12).getStringCellValue(),"-2"));
            			  listSet_18_b.add(new Set(row.getCell(13).getStringCellValue(),"0,5"));
            			  
            			  Data data_18_a = new Data("chart");
            			  data_18_a.setListSet(listSet_18_a);
            			  
            			  Data data_18_b = new Data("chart");
            			  data_18_b.setListSet(listSet_18_b);
            			  
            			  List<Data> listData_18 = new ArrayList<Data>();	
            			  listData_18.add(data_18_a);
            			  listData_18.add(data_18_b);
            			  
            			  capsule_18.setListData(listData_18);
            			  
            			  listCapsule.add(capsule_18);  
            	  }

              }
          }
		  	xml.setListCapsule(listCapsule);

			File file = new File("C:\\Users\\stagiaire4\\Desktop\\wiseam\\fileForExtranet"+ptf.getIdPortefG()+".xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Xml.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(xml, file);
			jaxbMarshaller.marshal(xml, System.out);
		    }  catch (FileNotFoundException e) {
		        System.out.println("Fichier introuvable");
		   }  catch (IOException e) {
		        e.printStackTrace();
		   }

	}	
}

