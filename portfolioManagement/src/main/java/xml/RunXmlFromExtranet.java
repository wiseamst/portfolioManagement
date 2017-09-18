package xml;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class RunXmlFromExtranet {

	public static void main(String[] args) throws ParseException {

		
		  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		  XmlForExtranet xml = new XmlForExtranet();
		  
		  xml.setTitle("Test reporting");
		  xml.setDate(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
		  xml.setFreq("Mensuel");
		  xml.setRef(1);
		  xml.setIdreporting(1);
		  xml.setType("C3");
		  xml.setCat("AXA");

		  List<Integer> listId = new ArrayList<Integer>();
		  listId.add(1);
		  listId.add(2);
		  
		  xml.setClient(new Client(listId));
		  
		  Overview overview = new Overview();
		  overview.setVl(1);
		  overview.set_1year("50.0%");
		  overview.setNav(2);
		  overview.setYtd(3);
		  overview.set_2year("50.0%");
		  
		  xml.setOverview(overview);
		  
		  /////////////////////////////////////////////////////////////////////
		  Capsule capsule_1 = new Capsule(1,"chart_line","Grapique 1 ligne");
		  
		  List<String> listLegend_1 = new ArrayList<String>();
		  listLegend_1.add("Légende");
		  
		  List<Set> listSet_1 = new ArrayList<Set>();
		  listSet_1.add(new Set("01/01","10"));
		  listSet_1.add(new Set("01/02","20"));
		  listSet_1.add(new Set("01/03","-20"));
		  listSet_1.add(new Set("01/04","70"));
		  listSet_1.add(new Set("01/05","50"));
		  

		  Data data_1 = new Data("chart"); 
		  data_1.setLegend(listLegend_1);
		  data_1.setListSet(listSet_1); 
		  
		  List<Data> listData_1 = new ArrayList<Data>();
		  listData_1.add(data_1);
		  
		  capsule_1.setListData(listData_1);
		  
		  /////////////////////////////////////////////////////////////////////
		  Capsule capsule_2 = new Capsule(2,"chart_line_drawdown","Graphique drawdown");
		 
		  List<String> listLegend_2 = new ArrayList<String>();
		  listLegend_2.add("Légende");
		  
		  List<Set> listSet_2 = new ArrayList<Set>();
		  listSet_2.add(new Set("01/01","3"));
		  listSet_2.add(new Set("01/02","-2"));
		  listSet_2.add(new Set("01/03","-5"));
		  listSet_2.add(new Set("01/04","7"));
		  listSet_2.add(new Set("01/05","0"));
		  
		  Data data_2 = new Data("chart"); 
		  data_2.setLegend(listLegend_2);
		  data_2.setListSet(listSet_2);
		  
		  List<Data> listData_2 = new ArrayList<Data>();
		  listData_2.add(data_2);
		  
		  capsule_2.setListData(listData_2);
		  
		  /////////////////////////////////////////////////////////////////////
		  Capsule capsule_3 = new Capsule(3,"graph_line","Graphique 2 lignes");

		  List<String> listLegend_3_a = new ArrayList<String>();
		  listLegend_3_a.add("Légende");

		  List<Set> listSet_3_a = new ArrayList<Set>();
		  listSet_3_a.add(new Set("01/01","10"));
		  listSet_3_a.add(new Set("01/02","20"));
		  listSet_3_a.add(new Set("01/03","-20"));
		  listSet_3_a.add(new Set("01/04","70"));
		  listSet_3_a.add(new Set("01/05","50"));
		  
		  Data data_3_a = new Data("chart"); 
		  data_3_a.setLegend(listLegend_3_a);
		  data_3_a.setListSet(listSet_3_a); 

		  List<String> listLegend_3_b = new ArrayList<String>();
		  listLegend_3_b.add("Légende");
		  
		  List<Set> listSet_3_b = new ArrayList<Set>();
		  listSet_3_b.add(new Set("01/01","20"));
		  listSet_3_b.add(new Set("01/02","50"));
		  listSet_3_b.add(new Set("01/03","30"));
		  listSet_3_b.add(new Set("01/04","-30"));
		  listSet_3_b.add(new Set("01/05","60"));
		  
		  Data data_3_b = new Data("chart");
		  data_3_b.setLegend(listLegend_3_b);
		  data_3_b.setListSet(listSet_3_b); 
		  
		  List<Data> listData_3 = new ArrayList<Data>();
		  listData_3.add(data_3_a);
		  listData_3.add(data_3_b);
		  
		  capsule_3.setListData(listData_3);
		  
		  /////////////////////////////////////////////////////////////////////
		  Capsule capsule_4 = new Capsule(4,"graph_line","Graphique 1 ligne avec commentaire");
		  
		  List<String> listLegend_4 = new ArrayList<String>();
		  listLegend_4.add("Légende");
		  
		  List<Set> listSet_4 = new ArrayList<Set>();
		  listSet_4.add(new Set("01/01","10","Commentraire for 01/01"));
		  listSet_4.add(new Set("01/02","20","Commentraire for 01/02"));
		  listSet_4.add(new Set("01/03","-20"));
		  listSet_4.add(new Set("01/04","70"));
		  listSet_4.add(new Set("01/05","50"));
		  
		  Data data_4 = new Data("chart"); 
		  data_4.setLegend(listLegend_4);
		  data_4.setListSet(listSet_4); 
		  
		  List<Data> listData_4 = new ArrayList<Data>();	
		  listData_4.add(data_4);
		  capsule_4.setListData(listData_4);
		  
		  /////////////////////////////////////////////////////////////////////
		  Capsule capsule_5 = new Capsule(5,"column","Tableau avec col droite");

		  List<Cell> listCell_5_a = new ArrayList<Cell>();
		  listCell_5_a.add(new Cell("1"));
		  listCell_5_a.add(new Cell("2"));
		  
		  List<Cell> listCell_5_b = new ArrayList<Cell>();
		  listCell_5_b.add(new Cell("3"));
		  listCell_5_b.add(new Cell("4"));
		  
		  List<Cell> listCell_5_c = new ArrayList<Cell>();
		  listCell_5_c.add(new Cell("5"));
		  listCell_5_c.add(new Cell("6"));
		  
		  List<Row> listRow_5 = new ArrayList<Row>();
		  listRow_5.add(new Row(listCell_5_a));
		  listRow_5.add(new Row(listCell_5_b));
		  listRow_5.add(new Row(listCell_5_c));
		  
		  Data data_5_a = new Data("table");
		  data_5_a.setListRow(listRow_5);
		  data_5_a.setDisclaimer("Disclaimer du tableau");

		  List<String> listText_5 = new ArrayList<String>();
		  listText_5.add("BLA BLA BLA ");
		  listText_5.add("BOO BOO BOO"); 
		  
		  Data data_5_b = new Data("text");
		  data_5_b.setListText(listText_5);
		  
		  List<Data> listData_5 = new ArrayList<Data>();	
		  listData_5.add(data_5_a);
		  listData_5.add(data_5_b);
		  
		  capsule_5.setListData(listData_5);
		  
		  /////////////////////////////////////////////////////////////////////
		  Capsule capsule_6 = new Capsule(6,"chart_clustered_stacked_bar","Tableau baton cummulé");
		  
		  List<String> listText_6 = new ArrayList<String>();
		  listText_6.add("BLA BLA BLA ");

		  Data data_6_a = new Data("text");
		  data_6_a.setListText(listText_6);
		  
		  List<String> listLegend_6 = new ArrayList<String>();
		  listLegend_6.add("Légende 1");
		  listLegend_6.add("Légende 2");
		  listLegend_6.add("Légende 3");
		  
		  List<Set> listSet_6 = new ArrayList<Set>();
		  listSet_6.add(new Set("01/01","0,10,0"));
		  listSet_6.add(new Set("01/02","0,20,0"));
		  listSet_6.add(new Set("01/03","20,10,20"));
		  listSet_6.add(new Set("01/04","10,10,10"));
		  listSet_6.add(new Set("01/05","50,10,40"));
		  
		  Data data_6_b = new Data("chart");
		  data_6_b.setLegend(listLegend_6);
		  data_6_b.setListSet(listSet_6);
		  
		  List<Data> listData_6 = new ArrayList<Data>();	
		  listData_6.add(data_6_a);
		  listData_6.add(data_6_b);
		  
		  capsule_6.setListData(listData_6);
		  
		  /////////////////////////////////////////////////////////////////////
		  Capsule capsule_7 = new Capsule(7,"column","Block commentaire");
		  
		  List<String> listText_7 = new ArrayList<String>();
		  listText_7.add("BLA BLA BLA ");

		  Data data_7 = new Data("text");
		  data_7.setListText(listText_7);
		  
		  List<Data> listData_7 = new ArrayList<Data>();	
		  listData_7.add(data_7);
		  capsule_7.setListData(listData_7);
		  
		  /////////////////////////////////////////////////////////////////////
		  Capsule capsule_8 = new Capsule(8,"column","3 tableaux");
		  
		  List<Cell> listCell_8_1a = new ArrayList<Cell>();
		  listCell_8_1a.add(new Cell("1"));
		  listCell_8_1a.add(new Cell("2"));
		  
		  List<Cell> listCell_8_1b = new ArrayList<Cell>();
		  listCell_8_1b.add(new Cell("3"));
		  listCell_8_1b.add(new Cell("4"));
		  
		  List<Cell> listCell_8_1c = new ArrayList<Cell>();
		  listCell_8_1c.add(new Cell("5"));
		  listCell_8_1c.add(new Cell("6"));
		  
		  List<Row> listRow_8_a = new ArrayList<Row>();
		  listRow_8_a.add(new Row(listCell_8_1a));
		  listRow_8_a.add(new Row(listCell_8_1b));
		  listRow_8_a.add(new Row(listCell_8_1c));
		  //////////
		  List<Cell> listCell_8_2a = new ArrayList<Cell>();
		  listCell_8_2a.add(new Cell("10"));
		  listCell_8_2a.add(new Cell("20"));
		  
		  List<Cell> listCell_8_2b = new ArrayList<Cell>();
		  listCell_8_2b.add(new Cell("30"));
		  listCell_8_2b.add(new Cell("40"));
		  
		  List<Cell> listCell_8_2c = new ArrayList<Cell>();
		  listCell_8_2c.add(new Cell("50"));
		  listCell_8_2c.add(new Cell("60"));
		  
		  List<Row> listRow_8_b = new ArrayList<Row>();
		  listRow_8_b.add(new Row(listCell_8_2a));
		  listRow_8_b.add(new Row(listCell_8_2b));
		  listRow_8_b.add(new Row(listCell_8_2c));
		  //////////
		  List<Cell> listCell_8_3a = new ArrayList<Cell>();
		  listCell_8_3a.add(new Cell("7"));
		  listCell_8_3a.add(new Cell("8"));
		  
		  List<Cell> listCell_8_3b = new ArrayList<Cell>();
		  listCell_8_3b.add(new Cell("9"));
		  listCell_8_3b.add(new Cell("10"));
		  
		  List<Cell> listCell_8_3c = new ArrayList<Cell>();
		  listCell_8_3c.add(new Cell("11"));
		  listCell_8_3c.add(new Cell("12"));
		  
		  List<Row> listRow_8_c = new ArrayList<Row>();
		  listRow_8_c.add(new Row(listCell_8_3a));
		  listRow_8_c.add(new Row(listCell_8_3b));
		  listRow_8_c.add(new Row(listCell_8_3c));
		  //////////
		  
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
		  
		  /////////////////////////////////////////////////////////////////////
		  Capsule capsule_9 = new Capsule(9,"column","Block informations (2 colonnes)");
		  
		  List<String> listText_9_a = new ArrayList<String>();
		  listText_9_a.add("BLA BLA BLA ");

		  List<String> listText_9_b = new ArrayList<String>();
		  listText_9_b.add("BOO BOO BOO"); 
		 
		  Data data_9_a = new Data("text");
		  data_9_a.setListText(listText_9_a);
		  
		  Data data_9_b = new Data("text");
		  data_9_b.setListText(listText_9_b);
		  
		  List<Data> listData_9 = new ArrayList<Data>();	
		  listData_9.add(data_9_a);
		  listData_9.add(data_9_b);
		  
		  capsule_9.setListData(listData_9);
		  
		  /////////////////////////////////////////////////////////////////////
		  Capsule capsule_10 = new Capsule(10,"risk","Echelle de risque (norme DICI)");

		  List<Set> listSet_10 = new ArrayList<Set>();
		  listSet_10.add(new Set("4"));
		  
		  Data data_10 = new Data("risk");
		  data_10.setListSet(listSet_10);
		  
		  List<Data> listData_10 = new ArrayList<Data>();	
		  listData_10.add(data_10);
		  
		  capsule_10.setListData(listData_10);
		  
		  /////////////////////////////////////////////////////////////////////
		  Capsule capsule_11 = new Capsule(11,"vl_encours");
		  
		  List<String> listText_11 = new ArrayList<String>();
		  listText_11.add("BLA BLA BLA ");
		  listText_11.add("BOO BOO BOO"); 
		  listText_11.add("BAM BAM BAM ");
		  
		  Data data_11 = new Data("text");
		  data_11.setListText(listText_11);
		  
		  List<Data> listData_11 = new ArrayList<Data>();	
		  listData_11.add(data_11);
		  capsule_11.setListData(listData_11);
		  
		  /////////////////////////////////////////////////////////////////////
		  Capsule capsule_12 = new Capsule(12,"column");
		  
		  List<String> listText_12 = new ArrayList<String>();
		  listText_12.add("BLA BLA BLA ");
		  
		  Data data_12 = new Data("text");
		  data_12.setListText(listText_12);
		  
		  List<Data> listData_12 = new ArrayList<Data>();	
		  listData_12.add(data_12);
		  capsule_12.setListData(listData_12);
		  
		  /////////////////////////////////////////////////////////////////////
		  Capsule capsule_13 = new Capsule(13,"list","Sélection des sous-jacents");
		  
		  List<String> listText_13 = new ArrayList<String>();
		  listText_13.add("BLA BLA BLA");
		  
		  List<Cell> listCell_13_a = new ArrayList<Cell>();
		  listCell_13_a.add(new Cell("1"));
		  listCell_13_a.add(new Cell("2"));
		  
		  List<Cell> listCell_13_b = new ArrayList<Cell>();
		  listCell_13_b.add(new Cell("3"));
		  listCell_13_b.add(new Cell("4"));
		  
		  List<Cell> listCell_13_c = new ArrayList<Cell>();
		  listCell_13_c.add(new Cell("5"));
		  listCell_13_c.add(new Cell("6"));
		  
		  List<Row> listRow_13 = new ArrayList<Row>();
		  listRow_13.add(new Row(listCell_13_a));
		  listRow_13.add(new Row(listCell_13_b));
		  listRow_13.add(new Row(listCell_13_c));
		  
		  Data data_13_a = new Data("text");
		  data_13_a.setListText(listText_13);
		  
		  Data data_13_b = new Data("table");
		  data_13_b.setListRow(listRow_13);

		  List<Data> listData_13 = new ArrayList<Data>();	
		  listData_13.add(data_13_a);
		  listData_13.add(data_13_b);
		  
		  capsule_13.setListData(listData_13);
		  
		  /////////////////////////////////////////////////////////////////////
		  Capsule capsule_14 = new Capsule(14,"position");
		  
		  List<Cell> listCell_14_a = new ArrayList<Cell>();
		  listCell_14_a.add(new Cell("1"));
		  listCell_14_a.add(new Cell("2"));
		  
		  List<Cell> listCell_14_b = new ArrayList<Cell>();
		  listCell_14_b.add(new Cell("3"));
		  listCell_14_b.add(new Cell("4"));
		  
		  List<Cell> listCell_14_c = new ArrayList<Cell>();
		  listCell_14_c.add(new Cell("bold","5"));
		  listCell_14_c.add(new Cell("6"));
		  
		  List<Row> listRow_14 = new ArrayList<Row>();
		  listRow_14.add(new Row("bold",listCell_14_a));
		  listRow_14.add(new Row(listCell_14_b));
		  listRow_14.add(new Row("bold",listCell_14_c));
		  
		  Data data_14_a = new Data("table");
		  data_14_a.setListRow(listRow_14);

		  List<String> listText_14 = new ArrayList<String>();
		  listText_14.add("BLA BLA BLA ");
		  
		  Data data_14_b = new Data("text");
		  data_14_b.setListText(listText_14);
		  
		  List<Data> listData_14 = new ArrayList<Data>();	
		  listData_14.add(data_14_a);
		  listData_14.add(data_14_b);
		  
		  capsule_14.setListData(listData_14);
		  
		  /////////////////////////////////////////////////////////////////////
		  Capsule capsule_15 = new Capsule(15,"list","Frais de commission");
		  
		  List<String> listText_15 = new ArrayList<String>();
		  listText_15.add("BLA BLA BLA ");
		  
		  List<Cell> listCell_15 = new ArrayList<Cell>();
		  listCell_15.add(new Cell("1"));
		  listCell_15.add(new Cell("2"));
		  
		  
		  List<Row> listRow_15 = new ArrayList<Row>();
		  listRow_15.add(new Row(listCell_15));
		  
		  Data data_15_a = new Data("text");
		  data_15_a.setListText(listText_15);
		  
		  Data data_15_b = new Data("table");
		  data_15_b.setListRow(listRow_15);

		  List<Data> listData_15 = new ArrayList<Data>();	
		  listData_15.add(data_15_a);
		  listData_15.add(data_15_b);
		  
		  capsule_15.setListData(listData_15);
		  
		  /////////////////////////////////////////////////////////////////////
		  Capsule capsule_16 = new Capsule(16,"list");
		  
		  List<String> listText_16 = new ArrayList<String>();
		  listText_16.add("BLA BLA BLA ");
		  
		  Data data_16 = new Data("text","italic");
		  data_16.setListText(listText_16);
		  
		  List<Data> listData_16 = new ArrayList<Data>();	
		  listData_16.add(data_16);
		  
		  capsule_16.setListData(listData_16);
		  
		  /////////////////////////////////////////////////////////////////////
		  Capsule capsule_17 = new Capsule(17,"chart_pie","Pie chart");
		 
		  
		  List<Set> listSet_17 = new ArrayList<Set>();
		  listSet_17.add(new Set("Légende 1","20"));
		  listSet_17.add(new Set("Légende 2","9,5"));
		  listSet_17.add(new Set("Légende 3","10,5"));
		  listSet_17.add(new Set("Légende 4","60"));
		  
		  Data data_17 = new Data("chart");
		  data_17.setListSet(listSet_17);
		  
		  List<Data> listData_17 = new ArrayList<Data>();	
		  listData_17.add(data_17);
		  
		  capsule_17.setListData(listData_17);
		  /////////////////////////////////////////////////////////////////////
		  Capsule capsule_18 = new Capsule(18,"chart_bar_contribution","Graphique bar contribution");
		  
		  List<Set> listSet_18_a = new ArrayList<Set>();
		  listSet_18_a.add(new Set("Légende 1","0,5"));
		  listSet_18_a.add(new Set("Légende 2","1"));
		  listSet_18_a.add(new Set("Légende 3","-0,2"));
		  listSet_18_a.add(new Set("Légende 4","2"));
		  
		  List<Set> listSet_18_b = new ArrayList<Set>();
		  listSet_18_b.add(new Set("Légende 1","3"));
		  listSet_18_b.add(new Set("Légende 2","1"));
		  listSet_18_b.add(new Set("Légende 3","-2"));
		  listSet_18_b.add(new Set("Légende 4","0,5"));
		  
		  Data data_18_a = new Data("chart");
		  data_18_a.setListSet(listSet_18_a);
		  
		  Data data_18_b = new Data("chart");
		  data_18_b.setListSet(listSet_18_b);
		  
		  List<Data> listData_18 = new ArrayList<Data>();	
		  listData_18.add(data_18_a);
		  listData_18.add(data_18_b);
		  
		  capsule_18.setListData(listData_18);
		  
		  /////////////////////////////////////////////////////////////////////
		  List<Capsule> listCapsule = new ArrayList<Capsule>();
		  listCapsule.add(capsule_1);
		  listCapsule.add(capsule_2);
		  listCapsule.add(capsule_3);
		  listCapsule.add(capsule_4);
		  listCapsule.add(capsule_5);
		  listCapsule.add(capsule_6);
		  listCapsule.add(capsule_7);
		  listCapsule.add(capsule_8);
		  listCapsule.add(capsule_9);
		  listCapsule.add(capsule_10);
		  listCapsule.add(capsule_11);
		  listCapsule.add(capsule_12);
		  listCapsule.add(capsule_13);
		  listCapsule.add(capsule_14);
		  listCapsule.add(capsule_15);
		  listCapsule.add(capsule_16);
		  listCapsule.add(capsule_17);
		  listCapsule.add(capsule_18);
		  
		  xml.setListCapsule(listCapsule);
		  
		  try {

			File file = new File("C:\\Users\\stagiaire4\\Desktop\\fileForExtranet.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(XmlForExtranet.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(xml, file);
			jaxbMarshaller.marshal(xml, System.out);

		      } catch (JAXBException e) {
			e.printStackTrace();
		      }

		}
}
