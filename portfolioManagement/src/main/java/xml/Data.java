package xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = {"type", "legend", "listSet", "listRow", "disclaimer", "listText"})
public class Data {

	private String type;
	private String legend;
	private List<Set> listSet = new ArrayList<Set>();
	private List<Row> listRow = new ArrayList<Row>();
	private String disclaimer;
	private List<String> listText = new ArrayList<String>();
	
	public Data() {
		super();
	}

	public Data(String type) {
		super();
		this.type = type;
	}

	public Data(String type, String legend) {
		super();
		this.type = type;
		this.legend = legend;
	}
	
	public String getType() {
		return type;
	}
	@XmlAttribute
	public void setType(String type) {
		this.type = type;
	}
	
	public String getLegend() {
		return legend;
	}
	@XmlElement
	public void setLegend(String legend) {
		this.legend = legend;
	}
	
	public List<Set> getListSet() {
		return listSet;
	}
	@XmlElement(name="set")
	public void setListSet(List<Set> listSet) {
		this.listSet = listSet;
	}
	
	public List<Row> getListRow() {
		return listRow;
	}
	@XmlElement(name="row")
	public void setListRow(List<Row> listRow) {
		this.listRow = listRow;
	}

	public String getDisclaimer() {
		return disclaimer;
	}
	@XmlElement
	public void setDisclaimer(String disclaimer) {
		this.disclaimer = disclaimer;
	}

	public List<String> getListText() {
		return listText;
	}
	@XmlElement(name="text")
	public void setListText(List<String> listText) {
		this.listText = listText;
	}
	
	
}
