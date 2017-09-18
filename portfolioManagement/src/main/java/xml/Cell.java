package xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class Cell {

	private String style;
	private String valeurCell;
	
	public Cell(String style, String valeurCell) {
		super();
		this.style = style;
		this.valeurCell = valeurCell;
	}
	
	public Cell(String valeurCell) {
	super();
	this.valeurCell = valeurCell;
	}

	public String getStyle() {
		return style;
	}

	@XmlAttribute
	public void setStyle(String style) {
		this.style = style;
	}

	public String getValeurCell() {
		return valeurCell;
	}
	@XmlValue
	public void setValeurCell(String valeurCell) {
		this.valeurCell = valeurCell;
	}
	
}
