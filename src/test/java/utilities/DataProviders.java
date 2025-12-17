package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {

@DataProvider(name="LoginData")
	public String[][] getData() throws IOException{
	
	String path="./testData/Opencart_LoginData.xlsx"; //taking the xl file
	ExcelUtility xlutil=new ExcelUtility(path); //create an object of xl utility
	
	int totalRows=xlutil.getRowCount("Sheet1");
	int totalCells=xlutil.getCellCount("Sheet1",1);
	String loginData[][]=new String[totalRows][totalCells];// create a 2D array which store the excel data;
	
	for (int i = 1; i <=totalRows; i++) {
		for (int j = 0; j < totalCells; j++) {
			loginData[i-1][j]=xlutil.getCellData("Sheet1", i, j);
		}
	}
	return loginData;
}
}
