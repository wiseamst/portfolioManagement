package dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.sql.DataSource;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;
import idao.AllocationWecoHistoriqueIDAO;
import model.Allocation;
import model.AllocationHistorique;
import model.AllocationWecoHistorique;
import model.PortefeuilleG;
import series.Performance;

public class AllocationWecoHistoriqueDAO implements  AllocationWecoHistoriqueIDAO {

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
	public List<AllocationWecoHistorique> findAllWecos(){

	String sql = "SELECT a.facteursRisque,a.pourcentagePTF,a.pourcentageBench,a.diff,a.commentaireWeco,a.dateweco,a.idportefg from allocationwecohistorique a where a.dateweco=(select max(b.dateweco) from allocationwecohistorique b)";

		Connection conn = null;

		try {
			conn = dataSourceWiseam.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			AllocationWecoHistorique allocationWecoHistorique= null;
			List<AllocationWecoHistorique> wecos = new ArrayList<AllocationWecoHistorique>();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				PortefeuilleG portefeuilleG = hibernateWiseam.get(PortefeuilleG.class, rs.getInt("IDPORTEFG"));
				allocationWecoHistorique = new AllocationWecoHistorique(
					rs.getString("FACTEURSRISQUE"),
					rs.getFloat("POURCENTAGEPTF"),
					rs.getFloat("POURCENTAGEBENCH"),
					rs.getFloat("DIFF"),
					rs.getString("COMMENTAIREWECO"),
					rs.getDate("DATEWECO")
				);
				allocationWecoHistorique.setPtfG(portefeuilleG);
				wecos.add(allocationWecoHistorique);
				
			}
			rs.close();
			ps.close();
			return wecos;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
				conn.close();
				} catch (SQLException e) {}
			}
		}
	}
	
	@Transactional(value="txManagerWiseam",readOnly = false)
	public void findAllWeco(PortefeuilleGDAO portefeuilleGDAO) throws ParseException{
		List<PortefeuilleG> ptfs =portefeuilleGDAO.findWiseamPtf();
		if (ptfs.size()>0) {
			for (PortefeuilleG ptf: ptfs) {
				readFromWeco(ptf);
			}
		}
	}

	@Transactional(value="txManagerWiseam",readOnly = false)
	public boolean readFromWeco(PortefeuilleG portefeuilleG) throws ParseException {
		
		// file name will change depending on ptf and client
		String FILE_NAME = "C:\\Users\\stagiaire4\\Desktop\\WECO_V1 2017 10 30.xls";
		
		 try {
			 
	            String date_file = FILE_NAME.substring(FILE_NAME.length()- 14,FILE_NAME.length() - 4).trim();

	            String[] date_file_output = date_file.split("\\ ");
	            
	            String date="";
	            
	            for (int i=0;i<date_file_output.length;i++) {
	        		
	            	if (date_file_output[i].length()==1) {
	        			date_file_output[i]=String.format("%02d", Integer.parseInt(date_file_output[i]));
	        			}
	        		
	            	date=date+date_file_output[i];

	            }
	    		
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		           
				java.sql.Date date_d = new java.sql.Date(sdf.parse(date).getTime()); 
               		
				List<AllocationWecoHistorique> wecos =  findWiseamWecoByPtf(portefeuilleG,date_d);

		        FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
		        
		        Workbook workbook = new HSSFWorkbook(excelFile);
		           
		        Sheet datatypeSheet = workbook.getSheetAt(0);

	            for (int j=6; j< 18; j++) {
	            	
	            	AllocationWecoHistorique allocationWecoHistorique = new AllocationWecoHistorique();
	            	String allocWecoTemp="";
	            	
	                Row row = datatypeSheet.getRow(j);
	                
	                for (int k = 2; k < 6; k++)

	                    if (row.getCell(k).getCellTypeEnum() == CellType.STRING) {
	                    	allocWecoTemp=allocWecoTemp+row.getCell(k).getStringCellValue() + "&";
	                    
	                    } else if (row.getCell(k).getCellTypeEnum() == CellType.NUMERIC) {
	                    	allocWecoTemp=allocWecoTemp+row.getCell(k).getNumericCellValue() + "&";
	                    
	                    }
		                            
	                if (wecos.size()==12) {
	            			allocationWecoHistorique.setIdAllocWecoHist(wecos.get(j-6).getIdAllocWecoHist());

		            }
	                
	                String[] output2 = allocWecoTemp.split("\\&");
                 
                 	allocationWecoHistorique.setDateWeco(date_d);
                 	
                 	allocationWecoHistorique.setFacteursRisque(output2[0]);
                 	
                 	allocationWecoHistorique.setPourcentagePTF(Float.valueOf(output2[1]));
                 	
                 	allocationWecoHistorique.setPourcentageBench(Float.valueOf(output2[2]));
                 	
                 	allocationWecoHistorique.setDiff(Float.valueOf(output2[3]));
                 	
                 	allocationWecoHistorique.setPtfG(portefeuilleG);       
                 	
                 	insertWiseamWeco(allocationWecoHistorique);

	            }
	            
                   System.out.println("Fin Weco");
                   
                   return true;
                   
                }  catch (FileNotFoundException e) {
                     System.out.println("Fichier introuvable");
                     return false;
                }  catch (IOException e) {
                     e.printStackTrace();
                     return false;
                }  
	}
	
	@Transactional(value="txManagerWiseam",readOnly = false)
	public void insertWiseamWeco(AllocationWecoHistorique allocationWecoHistorique) {
		
		System.out.println(allocationWecoHistorique.toString());
		
		hibernateWiseam.merge(allocationWecoHistorique);
	}
	
	@Transactional(value="txManagerWiseam",readOnly = false)
	public List<AllocationWecoHistorique> findWiseamWecoByPtf(PortefeuilleG portefeuilleG,Date dateWeco) {
		
		Object[] params = new Object[]{portefeuilleG.getIdPortefG(),dateWeco};
		return(List<AllocationWecoHistorique>) hibernateWiseam.find("select r from AllocationWecoHistorique r where r.ptfG.idPortefG=? and r.dateWeco=?",params);

	}
	
}
