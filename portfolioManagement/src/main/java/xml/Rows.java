package xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Rows {

	private String style;
	private List<Cell>  listCell = new ArrayList<Cell>();
	
	public Rows() {
		super();
	}

	public Rows(String style) {
		super();
		this.style = style;
	}

	public Rows(String style, List<Cell> listCell) {
		super();
		this.style = style;
		this.listCell = listCell;
	}

	public Rows(List<Cell> listCell) {
		super();
		this.listCell = listCell;
	}

	public String getStyle() {
		return style;
	}
	@XmlAttribute
	public void setStyle(String style) {
		this.style = style;
	}

	public List<Cell> getListCell() {
		return listCell;
	}
	
	@XmlElement(name="cell")
	public void setListCell(List<Cell> listCell) {
		this.listCell = listCell;
	}


	
}
