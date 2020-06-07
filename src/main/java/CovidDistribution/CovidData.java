package CovidDistribution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CovidData extends ExtractInfo {//implements java.io.Serializable {

	private Date date;
	private String dateString;
	private int cases;
	private int deaths;
	private String country;
	private String geoId; //This is an id it wont be used.
	private String countryterritoryCode; 
	private int populationData;
	private String continent;

	/**
	 * 	CovidData is a constructor which get the data that were extracted from the excel file and parse them to class.
	 *	
	 * 	<p>These columns are going to be ignored arr.get(2), arr.get(1), arr.get(3) [day, month, year] 
	 * 	because the of the dateRap which already describes these three columns.</p>
	 *
	 * 	@param arr (ArrayList<Object>) it contains objects that needed to be casted for insertion. 
	 *	@author Alexandros Kintis
	 */ 
	public CovidData(Date date, int cases, int deaths, String country, String geoId, String countryterritoryCode, int population, String continent) {		
		
		this.date = date;
		this.cases = cases;
		this.deaths = deaths;
		this.country = country;
		this.geoId = geoId;
		this.countryterritoryCode = countryterritoryCode;
		this.populationData = population;
		this.continent = continent;

		this.dateString = new SimpleDateFormat("dd/MM/yyyy").format(date);
	}

	public CovidData(Date date, int cases, int deaths, String country, String countryterritoryCode, int population)		{
		this.date = date;
		this.cases = cases;
		this.deaths = deaths;
		this.country = country;
		this.countryterritoryCode = countryterritoryCode;
		this.populationData = population;

		this.dateString = new SimpleDateFormat("dd/MM/yyyy").format(date);
	}

	/* Getters ------------------------------------------------------ */
	public static ArrayList<CovidData> getArrayList() {
		return covidArr;
	}
	
	public Date getDate() { 			
		return date;
	}

	public String getDateString() {
		return dateString;
	}

	public int getCases() {
		return cases;
	}
	
	public int getDeaths() {
		return deaths;
	}

	public String getCountry() {
		return country;
	}
	
	public String getCountryterritoryCode() {
		return countryterritoryCode;
	}

	public String getGeoId() {
		return geoId;
	}

	public int getPopulation() {
		return populationData;
	}

	public String getContinent() {
		return continent;
	}

	

	/* -------------------------------------------------------------- */	
	
	/* Print all attributes together for testing perposes */	
	public void printDistribution() {
		System.out.format("Date: %s | Cases: %d | Deaths: %d | Country: %s | GeoId: %s | CountryTerrCode: %s | Population: %d | Continent: %s \n",
				new SimpleDateFormat("MM/dd/yyyy").format(this.date),
				this.cases,
				this.deaths,
				this.country, 
				this.geoId,
				this.countryterritoryCode,
				this.populationData,
				this.continent				
				);
	}
}
