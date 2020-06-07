package CovidDistribution;

import java.io.IOException;
import javafx.application.Application;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;
import java.io.*;

/**
 * This is a simple implementation of covid 
 * pandemic tracking.
 * @author Alexandros Kintis 
 */
public class App {
	
	public static final String fileName = "COVID-19-geographic-disbtribution-worldwide.xlsx";

	public static void main(String[] args) {	
			
		System.out.println();

		ExcelFile f1 = new ExcelFile();
		if(f1.fileExists()) 
			new ExtractInfo().getExcelInfo(f1.getFile());

		ExcelFile.printFilesInterval();
					
		Application.launch(mainWindow.class, args);	
	}

}
