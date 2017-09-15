package xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Client {

	private List<Integer> id = new ArrayList<Integer>();

	public Client() {
		super();
	}

	public Client(List<Integer> id) {
		super();
		this.id = id;
	}

	public List<Integer> getId() {
		return id;
	}

	@XmlElement
	public void setId(List<Integer> id) {
		this.id = id;
	}
	
	
}
