package xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Row {

	private List<String> cell = new ArrayList<String>();

	public Row() {
		super();
	}

	public Row(List<String> cell) {
		super();
		this.cell = cell;
	}

	public List<String> getCell() {
		return cell;
	}

	@XmlElement
	public void setCell(List<String> cell) {
		this.cell = cell;
	}


	
}
