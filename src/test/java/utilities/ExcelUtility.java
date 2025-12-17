package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {
	public FileInputStream inputFile;
	public FileOutputStream outputFile;
	public XSSFWorkbook workBook;
	public XSSFSheet sheet;
	public XSSFRow row;
	public XSSFCell cell;
	public CellStyle style;
	String path;
	public ExcelUtility(String path) {
		this.path=path;
	}
	
	public int getRowCount(String xlsheet) throws IOException {
		inputFile=new FileInputStream(path);
		workBook=new XSSFWorkbook(inputFile);
		sheet=workBook.getSheet(xlsheet);
		int count=sheet.getLastRowNum();
		workBook.close();
		inputFile.close();
		return count;
	}
	public int getCellCount(String xlsheet,int rownum) throws IOException {
		inputFile=new FileInputStream(path);
		workBook=new XSSFWorkbook(inputFile);
		sheet=workBook.getSheet(xlsheet);
		row=sheet.getRow(rownum);
		int count=row.getLastCellNum();
		workBook.close();
		inputFile.close();
		return count;
	}
	public String getCellData(String xlsheet,int rownum,int cellnum) throws IOException {
		inputFile=new FileInputStream(path);
		workBook=new XSSFWorkbook(inputFile);
		sheet=workBook.getSheet(xlsheet);
		row=sheet.getRow(rownum);
		cell=row.getCell(cellnum);
		String data;
		try {
			//data=cell.toString();
			DataFormatter formater=new DataFormatter();
			data=formater.formatCellValue(cell);//return the formated value of the cell as a string 
		}catch(Exception e) {
			data="";
		}
		workBook.close();
		inputFile.close();
		return data;
	}
	public void setCellData(String xlsheet,int rownum,int cellnum,String data) throws IOException {
		File xlfile=new File(path);
		if(!xlfile.exists()) //if xlfile is not exist then create a new file
		{
			workBook=new XSSFWorkbook();
			outputFile=new FileOutputStream(path);
			workBook.write(outputFile);
		}
		inputFile=new FileInputStream(path);
		workBook=new XSSFWorkbook(inputFile);

		if(workBook.getSheetIndex(xlsheet)==-1)// if sheet is not exist then create a new sheet
		{
			workBook.createSheet(xlsheet);
		}
		sheet=workBook.getSheet(xlsheet);
		if(sheet.getRow(rownum)==null) //If row is not exist then create a new row
		{
			sheet.createRow(rownum);
		}
		row=sheet.getRow(rownum);
		
		cell=row.createCell(cellnum);
		cell.setCellValue(data);
		
		outputFile=new FileOutputStream(path);
		workBook.write(outputFile);
		workBook.close();
		inputFile.close();
		outputFile.close();
	}
	public void fillGreenColor(String xlsheet,int rownum,int cellnum) throws IOException {
		inputFile=new FileInputStream(path);
		workBook=new XSSFWorkbook(inputFile);
		sheet=workBook.getSheet(xlsheet);
		row=sheet.getRow(rownum);
		cell=row.getCell(cellnum);
		
		style=workBook.createCellStyle();
		style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cell.setCellStyle(style);
		outputFile=new FileOutputStream(path);
		workBook.write(outputFile);
		workBook.close();
		inputFile.close();
		outputFile.close();
	}
	public void fillRedColor(String xlsheet,int rownum,int cellnum) throws IOException {
		inputFile=new FileInputStream(path);
		workBook=new XSSFWorkbook(inputFile);
		sheet=workBook.getSheet(xlsheet);
		row=sheet.getRow(rownum);
		cell=row.getCell(cellnum);
		
		style=workBook.createCellStyle();
		style.setFillForegroundColor(IndexedColors.RED.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cell.setCellStyle(style);
		outputFile=new FileOutputStream(path);
		workBook.write(outputFile);
		workBook.close();			
		inputFile.close();
		outputFile.close();
	}
	
}
