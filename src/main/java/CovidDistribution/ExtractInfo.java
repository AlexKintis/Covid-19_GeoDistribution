package CovidDistribution;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.poi.ss.usermodel.Cell; 
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Exctracts pandemic data 
 * @author Alexandros Kintis
 */ 
public class ExtractInfo {

	protected static ArrayList<CovidData> covidArr = new ArrayList<CovidData>();
	private ArrayList<Object> arr;

	/** 
	 * Extract data from Excel file
	 * @param file filePath of the xlsx file 
	 * @author Alexandros Kintis
	 */	
	public void getExcelInfo(File file){

		int count = 0;
		try { 

			arr = new ArrayList<Object>();

			FileInputStream fis = new FileInputStream(file); 

			XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
			
			XSSFSheet sheet = myWorkBook.getSheetAt(0); 
		
			for (int rowNum = 1; rowNum < sheet.getLastRowNum(); rowNum++) {
				
				arr.clear();

				Row r = sheet.getRow(rowNum);
				
				if (r == null) {
					System.out.println("Row : " + r.getRowNum() + " is empty!!");
					continue;
				}

				for (int cn = 0; cn < r.getLastCellNum(); cn++) {
					
					Cell cell = r.getCell(cn, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					
					if (cell.getCellType() == CellType.BLANK) {
						cell.setCellValue(0);
						fillArr(cell, cn);	
					} else {
						fillArr(cell, cn);	
					}
					
				}
				
				covidArr.add(new CovidData((Date)arr.get(0), (int)arr.get(4), (int)arr.get(5), (String)arr.get(6), (String)arr.get(7), (String)arr.get(8), (int)arr.get(9), (String)arr.get(10))); 
			}
						
		} catch(IOException ioex) {
			System.out.println(ioex.getMessage());
		}

	}

	/**
	 * Prepares data for class insertion 
	 * @param cell apache poi cell
	 * @param cn place of the column	
	 */
	private void fillArr(Cell cell, int cn) {

		switch(cn) {
			
			case 0:
				try {
					this.arr.add(new SimpleDateFormat("dd-MMM-yyyy").parse(cell.toString())); 
				} catch (ParseException pex) {
					System.out.println(pex.getMessage());
				}
				break;
			
			case 1:
				this.arr.add((int)Double.parseDouble(cell.toString()));
				break;
			case 2:
				this.arr.add((int)Double.parseDouble(cell.toString()));
				break;
			case 3:
				this.arr.add((int)Double.parseDouble(cell.toString()));
				break;
			case 4:
				this.arr.add((int)Double.parseDouble(cell.toString()));
				break;
			case 5: 
				this.arr.add((int)Double.parseDouble(cell.toString()));
				break;
			case 6:
				this.arr.add(cell.toString()); 
				break;
			case 7:
				this.arr.add(cell.toString()); 
				break;
			case 8: 
				this.arr.add(cell.toString()); 
				break;
			case 9:
				this.arr.add(cell.toString().equals(Integer.toString(0)) ? 0 : (int)Double.parseDouble(cell.toString()));
				break;
			case 10:
				this.arr.add(cell.toString()); 
				break;
		}
	}
	

}	
