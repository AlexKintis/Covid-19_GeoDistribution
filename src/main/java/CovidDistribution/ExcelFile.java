package CovidDistribution;

import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.lang.Exception;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Locale;
import java.util.Map;
import java.util.List;
import java.util.Arrays;
import java.io.File;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.Channels;
import java.util.concurrent.TimeUnit;

public class ExcelFile {
	
	final private String downloadFileUrl = "https://www.ecdc.europa.eu/sites/default/files/documents/COVID-19-geographic-disbtribution-worldwide.xlsx"; 
	private static long filesHoursInterval;
	private File file; 

	public ExcelFile() {
	
		file = new File(App.fileName);	
		
		
		if(!file.exists()) getNewFile();
		
		/* At this point we just check if our local file is out of date, so we download the new one */
		Date fileDate = getFileDate();
		Date onlineFileDate = getOnlineFileDateLastModified();
		this.filesHoursInterval = TimeUnit.HOURS.convert(onlineFileDate.getTime() - fileDate.getTime() , TimeUnit.MILLISECONDS);

		System.out.format("Remote file Last-Modified Date : %s\n", onlineFileDate);
		System.out.format("Local file creation date : %s\n", fileDate);

		if(filesHoursInterval > 0) {
			getNewFile();
		}		
			
		
	}

	public static void printFilesInterval() {
		System.out.println("Time difference between local and remote file : " + filesHoursInterval + "(hours)"); 
	}

	public boolean fileExists() {
		return file.exists(); 
	}

	/* this method "unveils" the excel file to the "public" */
	public File getFile() {
		return file;	
	}

	private void getNewFile() {

		try 
		{
			System.out.println("Getting newer version of the Excel file...");
			URL website = new URL(downloadFileUrl);
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			FileOutputStream fos = new FileOutputStream(App.fileName,false);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);	

		} catch(UnknownHostException uhex) {
			System.out.println(uhex.getMessage());
		} catch(IOException ioex) {
			System.out.println(ioex.getMessage());
		}	
	}

	private Date getFileDate() {
	
		Date d = null;
		
		try {
			//file = new File(Covid_19_Gui.fileName);
			Path filePath = file.toPath();

			BasicFileAttributes attr = Files.readAttributes(filePath, BasicFileAttributes.class);
			
			d = new Date(attr.creationTime().toMillis());
			

		} catch( IOException ioex ) {
			System.out.println(ioex.getMessage());
		}

		return d;	
	}

	private Date getOnlineFileDateLastModified(){
	
		Date d = null;
		try {	
			URL urlFile = new URL(downloadFileUrl);
			URLConnection conn = urlFile.openConnection();

			Map<String, List<String>> onlineFileHeader = conn.getHeaderFields();
			
			// Get from http header the last modified as string and parse it to date format
			String dateHString = onlineFileHeader.get("Last-Modified").toString();
			dateHString = dateHString.substring(1, dateHString.length()-1);
			SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.ENGLISH);
			d = format.parse(dateHString);
			/* If we would like to see the whole header of the file we are downloading we could just uncomment the under under */
			//onlineFileHeader.entrySet().forEach( entry -> System.out.println("Key : " + entry.getKey() + " ,Value : " + entry.getValue()));
		
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return d;
	}	

}
