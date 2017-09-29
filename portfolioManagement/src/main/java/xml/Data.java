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
	private String style;
	private List<String> legend;
	private List<Set> listSet = new ArrayList<Set>();
	private List<Rows> listRow = new ArrayList<Rows>();
	private String disclaimer;
	private List<String> listText = new ArrayList<String>();
	
	public Data() {
		super();
	}

	public Data(String type) {
		super();
		this.type = type;
	}
	
	public Data(String type, String style) {
		super();
		this.type = type;
		this.style = style;
	}

	public String getType() {
		return type;
	}
	@XmlAttribute
	public void setType(String type) {
		this.type = type;
	}
	
	public List<String> getLegend() {
		return legend;
	}
	@XmlElement(name="legend")
	public void setLegend(List<String> legend) {
		this.legend = legend;
	}

	public List<Set> getListSet() {
		return listSet;
	}
	@XmlElement(name="set")
	public void setListSet(List<Set> listSet) {
		this.listSet = listSet;
	}
	
	public List<Rows> getListRow() {
		return listRow;
	}
	@XmlElement(name="row")
	public void setListRow(List<Rows> listRow) {
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

	public String getStyle() {
		return style;
	}
	@XmlAttribute
	public void setStyle(String style) {
		this.style = style;
	}
	
	
}
