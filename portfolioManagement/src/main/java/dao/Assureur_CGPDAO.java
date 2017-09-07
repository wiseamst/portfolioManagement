package dao;

import org.springframework.orm.hibernate5.HibernateTemplate;

public class Assureur_CGPDAO {

	private HibernateTemplate hibernateWiseam;
	private HibernateTemplate hibernateTopaze;
	
	public HibernateTemplate getHibernateWiseam() {
		return hibernateWiseam;
	}
	public void setHibernateWiseam(HibernateTemplate hibernateWiseam) {
		this.hibernateWiseam = hibernateWiseam;
	}
	public HibernateTemplate getHibernateTopaze() {
		return hibernateTopaze;
	}
	public void setHibernateTopaze(HibernateTemplate hibernateTopaze) {
		this.hibernateTopaze = hibernateTopaze;
	}
	
	
}
