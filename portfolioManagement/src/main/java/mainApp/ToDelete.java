package mainApp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ToDelete {

	public static void main(String[] args) {
		
		// file name will change depending on ptf and client
		String FILE_NAME = "C:\\Users\\stagiaire4\\Desktop\\PARAM_REPORTING_EXTRANET_v0.xlsx";
		
		 try {
			 
			 	double input=0;
			 	HashMap<Integer,String> capculesPtf = new HashMap<Integer,String>();
			 	capculesPtf.put(1, "Capcule_1");
			 	capculesPtf.put(2,  "Capcule_2");
			 	capculesPtf.put(3,  "Capcule_3");
			 	capculesPtf.put(4,  "Capcule_4");
			 	capculesPtf.put(5,  "Capcule_5");
			 	capculesPtf.put(6,  "Capcule_6");
			 	capculesPtf.put(7,  "Capcule_7");
			 	capculesPtf.put(8,  "Capcule_8");
			 	capculesPtf.put(9,  "Capcule_9");
			 	capculesPtf.put(18,  "Capcule_18");
			 	
			 	input=18.0;
		        FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
		        
		        Workbook workbook = new XSSFWorkbook(excelFile);
		           
		        Sheet datatypeSheet = workbook.getSheetAt(0);

	            for (int j=1; j< datatypeSheet.getLastRowNum(); j++) {
	            	
	                Row row = datatypeSheet.getRow(j);

	                // 46 represents the ID PTF
	                if (row.getCell(0).getCellTypeEnum() == CellType.NUMERIC && (int) row.getCell(0).getNumericCellValue()==46){
	                	if  (capculesPtf.containsKey((int) row.getCell(3).getNumericCellValue())){
	                		
	                		int id =(int) row.getCell(3).getNumericCellValue();
	                		System.out.println("capcule: "+capculesPtf.get(id));
	            	 	
	                	    int weight =(int) row.getCell(4).getNumericCellValue();
	                	    
	                	    // updater la position de la capucle
	                	    
	                	    System.out.println("weight: "+weight);
	                		
			                for (int k = 6; k < row.getLastCellNum(); k++) {
				                    
			                		if (row.getCell(k).getCellTypeEnum() == CellType.STRING) {
				                    	
				                    	System.out.println("input :"+k+" "+row.getCell(k).getStringCellValue() );
				                    
				                    } else if (row.getCell(k).getCellTypeEnum() == CellType.NUMERIC) {
				                    	System.out.println("input :"+k+" "+row.getCell(k).getNumericCellValue());
				                    
				                    } 
			                }
	                	}
	                }
	            }
	            
                   System.out.println("Fin Excel");
                   
                   
                }  catch (FileNotFoundException e) {
                     System.out.println("Fichier introuvable");
                }  catch (IOException e) {
                     e.printStackTrace();
                }

	}

}
