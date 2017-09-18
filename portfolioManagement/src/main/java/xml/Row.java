package xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Row {

	private String style;
	private List<Cell>  listCell = new ArrayList<Cell>();
	
	public Row() {
		super();
	}

	public Row(String style) {
		super();
		this.style = style;
	}

	public Row(String style, List<Cell> listCell) {
		super();
		this.style = style;
		this.listCell = listCell;
	}

	public Row(List<Cell> listCell) {
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
