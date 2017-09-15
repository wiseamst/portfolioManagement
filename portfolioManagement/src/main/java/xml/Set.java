package xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement
public class Set {

	private String label;
	private String value;
	private String commentaire;
	
	public Set() {
		super();
	}
	public Set(String label, String value) {
		super();
		this.label = label;
		this.value = value;
	}
	
	public Set(String label, String value, String commentaire) {
		super();
		this.label = label;
		this.value = value;
		this.commentaire = commentaire;
	}
	
	public String getLabel() {
		return label;
	}
	@XmlAttribute
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getValue() {
		return value;
	}
	@XmlAttribute
	public void setValue(String value) {
		this.value = value;
	}
	
	@XmlValue
	public String getCommentaire() {
		return commentaire;
	}
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}
		
	
}
