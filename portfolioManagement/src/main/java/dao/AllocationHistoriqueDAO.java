package dao;

import javax.sql.DataSource;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;
import idao.AllocationHistoriqueIDAO;
import model.AllocationHistorique;

public class AllocationHistoriqueDAO implements  AllocationHistoriqueIDAO {

	private DataSource dataSourceTopaze; // to read from topaze
	private DataSource dataSourceWiseam; // to read from wiseam
	
	private HibernateTemplate hibernateWiseam; // to read from wiseam using hibernate template
	private HibernateTemplate hibernateTopaze; // to read from topaze using hibernate template
	
	public DataSource getDataSourceTopaze() {
		return dataSourceTopaze;
	}
	public void setDataSourceTopaze(DataSource dataSourceTopaze) {
		this.dataSourceTopaze = dataSourceTopaze;
	}

	public DataSource getDataSourceWiseam() {
		return dataSourceWiseam;
	}
	public void setDataSourceWiseam(DataSource dataSourceWiseam) {
		this.dataSourceWiseam = dataSourceWiseam;
	}

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

	@Transactional(value="txManagerWiseam",readOnly = false)
	public void insertWiseamAllocationHistorique(AllocationHistorique allocationHistorique) {
		
		System.out.println(allocationHistorique.toString());
		
		hibernateWiseam.saveOrUpdate(allocationHistorique);
		
	}
	
}
