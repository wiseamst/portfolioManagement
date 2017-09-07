package dao;

import javax.sql.DataSource;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;
import idao.AllocationWecoHistoriqueIDAO;
import model.AllocationWecoHistorique;

public class AllocationWecoHistoriqueDAO implements  AllocationWecoHistoriqueIDAO {

	private DataSource dataSourceTopaze;
	private DataSource dataSourceWiseam;
	
	private HibernateTemplate hibernateWiseam;
	private HibernateTemplate hibernateTopaze;
	
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
	public void insertWiseamWeco(AllocationWecoHistorique allocationWecoHistorique) {
		
		hibernateWiseam.saveOrUpdate(allocationWecoHistorique);
	}
	
}
