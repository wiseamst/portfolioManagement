package xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="XML")
@XmlType(propOrder = {"title", "date","freq", "ref","idreporting", "type","cat","client","overview","listCapsule"})
public class XmlForExtranet {

	private String title;
	private String date;
	private String freq;
	private int ref;
	private int idreporting;
	private String type;
	private String cat;
	private Client client;
	private Overview overview;
	private List<Capsule> listCapsule = new ArrayList<Capsule>();
	
	public String getTitle() {
		return title;
	}
	
	@XmlElement
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDate() {
		return date;
	}
	
	@XmlElement
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getFreq() {
		return freq;
	}

	@XmlElement
	public void setFreq(String freq) {
		this.freq = freq;
	}
	
	public int getRef() {
		return ref;
	}

	@XmlElement
	public void setRef(int ref) {
		this.ref = ref;
	}
	public int getIdreporting() {
		return idreporting;
	}
	
	@XmlElement
	public void setIdreporting(int idreporting) {
		this.idreporting = idreporting;
	}
	public String getType() {
		return type;
	}
	
	@XmlElement
	public void setType(String type) {
		this.type = type;
	}
	public String getCat() {
		return cat;
	}
	
	@XmlElement
	public void setCat(String cat) {
		this.cat = cat;
	}

	public Client getClient() {
		return client;
	}

	@XmlElement(name="clients")
	public void setClient(Client client) {
		this.client = client;
	}

	public Overview getOverview() {
		return overview;
	}
	@XmlElement
	public void setOverview(Overview overview) {
		this.overview = overview;
	}

	public List<Capsule> getListCapsule() {
		return listCapsule;
	}
	
	@XmlElement(name="capsule")
	public void setListCapsule(List<Capsule> listCapsule) {
		this.listCapsule = listCapsule;
	}

	
}
