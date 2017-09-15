package xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Overview {

	private float vl;
	private String _1year;
	private float nav;
	private float ytd;
	private String _2year;
	
	public Overview() {
		super();
	}
	public float getVl() {
		return vl;
	}
	@XmlElement
	public void setVl(float vl) {
		this.vl = vl;
	}
	
	public String get_1year() {
		return _1year;
	}
	@XmlElement(name="1year")
	public void set_1year(String _1year) {
		this._1year = _1year;
	}
	
	public float getNav() {
		return nav;
	}
	@XmlElement
	public void setNav(float nav) {
		this.nav = nav;
	}
	
	public float getYtd() {
		return ytd;
	}
	@XmlElement
	public void setYtd(float ytd) {
		this.ytd = ytd;
	}
	
	public String get_2year() {
		return _2year;
	}
	@XmlElement(name="2year")
	public void set_2year(String _2year) {
		this._2year = _2year;
	}
		
}
