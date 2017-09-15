package xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = {"title", "listData"})
public class Capsule {

	private int id;
	private String type;
	private String title;
	private List<Data> listData = new ArrayList<Data>();
	
	public Capsule() {
		super();
	}

	public Capsule(int id, String type, String title) {
		super();
		this.id = id;
		this.type = type;
		this.title = title;
	}

	public Capsule(int id, String type) {
		super();
		this.id = id;
		this.type = type;
	}

	@XmlAttribute
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@XmlAttribute
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@XmlElement
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@XmlElement(name="data")
	public List<Data> getListData() {
		return listData;
	}
	public void setListData(List<Data> listData) {
		this.listData = listData;
	}
	
	
	
}
