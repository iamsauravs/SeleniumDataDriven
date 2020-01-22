package com.automation.core.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;



public class ExcelSheetDriver {	
	public  String filelocation;
	public  FileInputStream ipstr = null;
	public  FileOutputStream opstr =null;
	private HSSFWorkbook wb = null;
	private HSSFSheet ws = null;	
	
	public ExcelSheetDriver(String filelocation, String wsName) {		
		this.filelocation=filelocation;
		try {
			ipstr = new FileInputStream(filelocation);
			wb = new HSSFWorkbook(ipstr);
			int sheetIndex=wb.getSheetIndex(wsName);
			ws = wb.getSheetAt(sheetIndex);
			ipstr.close();
		} catch (Exception e) {			
			e.printStackTrace();
		} 
		
	}
	
	//To retrieve No Of Rows from .xls file's sheets.
	public int retrieveNoOfRows(){		
		int rowCount=ws.getLastRowNum()+1;		
		return rowCount;		
	}
	
	//To retrieve No Of Columns from .xls file's sheets.
	public int retrieveNoOfCols(){
		int colCount=ws.getRow(0).getLastCellNum();			
		return colCount;
	}
	
	//To retrieve Cell Data with input of colName and rowName
	public String retrieveCellData(String colName, String rowName){
		
		int rowNum = retrieveNoOfRows();
		int colNum = retrieveNoOfCols();
		int colNumber=-1;
		int rowNumber=-1;			
		
		HSSFRow Suiterow = ws.getRow(0);				
		
		for(int i=0; i<colNum; i++){
			if(Suiterow.getCell(i).getStringCellValue().equals(colName.trim())){
				colNumber=i;					
			}					
		}
		
		if(colNumber==-1){
			return "";				
		}
		
		
		for(int j=0; j<rowNum; j++){
			HSSFRow Suitecol = ws.getRow(j);				
			if(Suitecol.getCell(0).getStringCellValue().equals(rowName.trim())){
				rowNumber=j;	
			}					
		}
		
		if(rowNumber==-1){
			return "";				
		}
		
		HSSFRow row = ws.getRow(rowNumber);
		HSSFCell cell = row.getCell(colNumber);
		if(cell==null){
			return "";
		}
		String value = cellToString(cell);
		return value;			
				
	}
	
	//To retrieve Particular Column Data
	public String[] retrieveColData(String colName){
		
		int rowNum = retrieveNoOfRows();
		int colNum = retrieveNoOfCols();
		int colNumber=-1;
				
		
		HSSFRow Suiterow = ws.getRow(0);				
		String data[] = new String[rowNum-1];
		for(int i=0; i<colNum; i++){
			if(Suiterow.getCell(i).getStringCellValue().equals(colName.trim())){
				colNumber=i;					
			}					
		}
		
		if(colNumber==-1){
			return null;				
		}
		
		for(int j=0; j<rowNum-1; j++){
			HSSFRow Row = ws.getRow(j+1);
			if(Row==null){
				data[j] = "";
			}
			else{
				HSSFCell cell = Row.getCell(colNumber);
				if(cell==null){
					data[j] = "";
				}
				else{
					String value = cellToString(cell);
					data[j] = value;	
				}	
			}
		}
		
		return data;			
	}
	
	//To retrieve test data from test case data sheets. noOfColumnstToIgnore Denotes Number of Columns from Last which should be skipped.
	public Object[][] retrieveTestData(int noOfColumnstToIgnore){
		
		int rowNum = retrieveNoOfRows();
		int colNum = retrieveNoOfCols();

		Object data[][] = new Object[rowNum-1][colNum-noOfColumnstToIgnore];

		for (int i=0; i<rowNum-1; i++){
			HSSFRow row = ws.getRow(i+1);
			for(int j=0; j< colNum-noOfColumnstToIgnore; j++){					
				if(row==null){
					data[i][j] = "";
				}
				else{
					HSSFCell cell = row.getCell(j);	
			
					if(cell==null){
						data[i][j] = "";							
					}
					else{
						cell.setCellType(Cell.CELL_TYPE_STRING);
						String value = cellToString(cell);
						data[i][j] = value;						
					}
				}
			}				
		}			
		return data;
	
	}		
	
	
	public static String cellToString(HSSFCell cell){
		int type;
		Object result;
		type = cell.getCellType();			
		switch (type){
			case 0 :
				result = cell.getNumericCellValue();
				break;
				
			case 1 : 
				result = cell.getStringCellValue();
				break;
				
			default :
				throw new RuntimeException("Unsupportd cell.");			
		}
		return result.toString();
	}
	
	//To write result In Particular Cell of the Sheet
	public boolean writeResult(String wsName, String colName, int rowNumber, String Result){
		try{
			int sheetIndex=wb.getSheetIndex(wsName);
			if(sheetIndex==-1)
				return false;			
			int colNum = retrieveNoOfCols();
			int colNumber=-1;
					
			
			HSSFRow Suiterow = ws.getRow(0);			
			for(int i=0; i<colNum; i++){				
				if(Suiterow.getCell(i).getStringCellValue().equals(colName.trim())){
					colNumber=i;					
				}					
			}
			
			if(colNumber==-1){
				return false;				
			}
			
			HSSFRow Row = ws.getRow(rowNumber);
			HSSFCell cell = Row.getCell(colNumber);
			if (cell == null)
		        cell = Row.createCell(colNumber);			
			
			cell.setCellValue(Result);
			
			opstr = new FileOutputStream(filelocation);
			wb.write(opstr);
			opstr.close();
			
			
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

}