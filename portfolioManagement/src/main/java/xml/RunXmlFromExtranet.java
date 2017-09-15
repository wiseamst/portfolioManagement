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
		  
		  List<Data> listData_1 = new ArrayList<Data>();	 
		  Data data_1 = new Data("chart","Légende"); 
		  List<Set> listSet_1 = new ArrayList<Set>();
		  
		  listSet_1.add(new Set("01/01","10"));
		  listSet_1.add(new Set("01/02","20"));
		  listSet_1.add(new Set("01/03","-20"));
		  listSet_1.add(new Set("01/04","70"));
		  listSet_1.add(new Set("01/05","50"));
		  
		  data_1.setListSet(listSet_1); 
		  listData_1.add(data_1);
		  capsule_1.setListData(listData_1);
		  
		  /////////////////////////////////////////////////////////////////////
		  Capsule capsule_2 = new Capsule(2,"chart_line_drawdown","Graphique drawdown");
		  
		  List<Data> listData_2 = new ArrayList<Data>();	
		 
		  Data data_2 = new Data("chart","Légende"); 
		  List<Set> listSet_2 = new ArrayList<Set>();
		  listSet_2.add(new Set("01/01","3"));
		  listSet_2.add(new Set("01/02","-2"));
		  listSet_2.add(new Set("01/03","-5"));
		  listSet_2.add(new Set("01/04","7"));
		  listSet_2.add(new Set("01/05","0"));
		  data_2.setListSet(listSet_2); 
		  
		  listData_2.add(data_2);
		  capsule_2.setListData(listData_2);
		  
		  /////////////////////////////////////////////////////////////////////
		  Capsule capsule_3 = new Capsule(3,"graph_line","Graphique 2 lignes");
		  
		  List<Data> listData_3 = new ArrayList<Data>();	 
		  
		  Data data_3_a = new Data("chart","Légende"); 
		  List<Set> listSet_3_a = new ArrayList<Set>();
		  listSet_3_a.add(new Set("01/01","10"));
		  listSet_3_a.add(new Set("01/02","20"));
		  listSet_3_a.add(new Set("01/03","-20"));
		  listSet_3_a.add(new Set("01/04","70"));
		  listSet_3_a.add(new Set("01/05","50"));
		  data_3_a.setListSet(listSet_3_a); 
		  
		  Data data_3_b = new Data("chart","Légende"); 
		  List<Set> listSet_3_b = new ArrayList<Set>();
		  listSet_3_b.add(new Set("01/01","20"));
		  listSet_3_b.add(new Set("01/02","50"));
		  listSet_3_b.add(new Set("01/03","30"));
		  listSet_3_b.add(new Set("01/04","-30"));
		  listSet_3_b.add(new Set("01/05","60"));
		  data_3_b.setListSet(listSet_3_b); 
		  
		  listData_3.add(data_3_a);
		  listData_3.add(data_3_b);
		  
		  capsule_3.setListData(listData_3);
		  
		  /////////////////////////////////////////////////////////////////////
		  Capsule capsule_4 = new Capsule(4,"graph_line","Graphique 1 ligne avec commentaire");
		  
		  List<Data> listData_4 = new ArrayList<Data>();	 
		  
		  Data data_4 = new Data("chart","Légende"); 
		  List<Set> listSet_4 = new ArrayList<Set>();
		  listSet_4.add(new Set("01/01","10","Commentraire for 01/01"));
		  listSet_4.add(new Set("01/02","20","Commentraire for 01/02"));
		  listSet_4.add(new Set("01/03","-20"));
		  listSet_4.add(new Set("01/04","70"));
		  listSet_4.add(new Set("01/05","50"));
		  data_4.setListSet(listSet_4); 
		  
		  listData_4.add(data_4);
		  capsule_4.setListData(listData_4);
		  
		  /////////////////////////////////////////////////////////////////////
		  Capsule capsule_5 = new Capsule(5,"column","Tableau avec col droite");
		  
		  List<Data> listData_5 = new ArrayList<Data>();	 
		  
		  Data data_5_a = new Data("table");
		  
		  List<Row> listRow_5 = new ArrayList<Row>();
		  List<String> listCell_5_a = new ArrayList<String>();
		  listCell_5_a.add("1");
		  listCell_5_a.add("2");
		  
		  List<String> listCell_5_b = new ArrayList<String>();
		  listCell_5_b.add("3");
		  listCell_5_b.add("4");
		  
		  List<String> listCell_5_c = new ArrayList<String>();
		  listCell_5_c.add("5");
		  listCell_5_c.add("6");
		  
		  listRow_5.add(new Row(listCell_5_a));
		  listRow_5.add(new Row(listCell_5_b));
		  listRow_5.add(new Row(listCell_5_c));
		  
		  data_5_a.setListRow(listRow_5);
		  
		  //
		  
		  data_5_a.setDisclaimer("Disclaimer du tableau");

		  //
		  
		  Data data_5_b = new Data("text");
		  List<String> listText_5 = new ArrayList<String>();
		  listText_5.add("BLA BLA BLA ");
		  listText_5.add("BOO BOO BOO"); 
		  data_5_b.setListText(listText_5);
		  
		  listData_5.add(data_5_a);
		  listData_5.add(data_5_b);
		  capsule_5.setListData(listData_5);
		  
		  /////////////////////////////////////////////////////////////////////
		  Capsule capsule_6 = new Capsule(6,"chart_clustered_stacked_bar","Tableau baton cummulé");
		  Capsule capsule_7 = new Capsule(7,"column","Block commentaire");
		  Capsule capsule_8 = new Capsule(8,"column","3 tableaux");
		  Capsule capsule_9 = new Capsule(9,"column","Block informations (2 colonnes)");
		  Capsule capsule_10 = new Capsule(10,"risk","Echelle de risque (norme DICI)");
		  Capsule capsule_11 = new Capsule(11,"vl_encours");
		  Capsule capsule_12 = new Capsule(12,"column");
		  Capsule capsule_13 = new Capsule(13,"list","Sélection des sous-jacents");
		  Capsule capsule_14 = new Capsule(14,"position");
		  Capsule capsule_15 = new Capsule(15,"list","Frais de commission");
		  Capsule capsule_16 = new Capsule(16,"list");
		  Capsule capsule_17 = new Capsule(17,"chart_pie","Pie chart");
		  Capsule capsule_18 = new Capsule(18,"chart_bar_contribution","Graphique bar contribution");
		  
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
