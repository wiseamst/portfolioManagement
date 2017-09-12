package dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import model.AllocationWecoHistorique;
import model.PortefeuilleG;

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
	public void findAllWeco() throws ParseException{
		List<PortefeuilleG> ptfs =findWiseamPtf();
		if (ptfs.size()>0) {
			for (PortefeuilleG ptf: ptfs) {
				readFromWeco(ptf);
			}
		}
	}

	@Transactional(value="txManagerWiseam",readOnly = false)
	public boolean readFromWeco(PortefeuilleG portefeuilleG) throws ParseException {
		
		// file name will change depending on ptf and client
		String FILE_NAME = "C:\\Users\\stagiaire4\\Desktop\\WECO_V1 2017 10 31.xls";
		
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
               		
				List<AllocationWecoHistorique> wecos =  findWiseamWecoByPtf(portefeuilleG);

		        FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
		        
		        Workbook workbook = new HSSFWorkbook(excelFile);
		           
		        Sheet datatypeSheet = workbook.getSheetAt(0);

	            for (int j=6; j< 10 + 1; j++) {
	            	
	            	AllocationWecoHistorique allocationWecoHistorique = new AllocationWecoHistorique();
	            	String allocWecoTemp="";
	            	
	                Row row = datatypeSheet.getRow(j);
	                
	                for (int k = 2; k < 5; k++)

	                    if (row.getCell(k).getCellTypeEnum() == CellType.STRING) {
	                    	allocWecoTemp=allocWecoTemp+row.getCell(k).getStringCellValue() + "&";
	                    
	                    } else if (row.getCell(k).getCellTypeEnum() == CellType.NUMERIC) {
	                    	allocWecoTemp=allocWecoTemp+row.getCell(k).getNumericCellValue() + "&";
	                    
	                    }
		                            
	                if (wecos.size()==5) {
	            			allocationWecoHistorique.setIdAllocWecoHist(wecos.get(j-6).getIdAllocWecoHist());

		            }
	                
	                String[] output2 = allocWecoTemp.split("\\&");
                 
                 	allocationWecoHistorique.setDateWeco(date_d);
                 	
                 	allocationWecoHistorique.setFacteursRisque(output2[0]);
                 	
                 	allocationWecoHistorique.setPourcentagePTF(Float.valueOf(output2[1]));
                 	
                 	allocationWecoHistorique.setPourcentageBench(Float.valueOf(output2[2]));
                 	
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
	public List<PortefeuilleG> findWiseamPtf() {
		
		return(List<PortefeuilleG>) hibernateWiseam.find("select r from PortefeuilleG r");

	}
	
	@Transactional(value="txManagerWiseam",readOnly = false)
	public List<AllocationWecoHistorique> findWiseamWecoByPtf(PortefeuilleG portefeuilleG) {
		
		Object[] params = new Object[]{portefeuilleG.getIdPortefG()};
		return(List<AllocationWecoHistorique>) hibernateWiseam.find("select r from AllocationWecoHistorique r where r.ptfG.idPortefG=?",params);

	}
	
}
