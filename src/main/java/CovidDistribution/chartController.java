package CovidDistribution;

import java.util.ArrayList;
import java.net.URL; 
import java.util.ResourceBundle; 
import java.util.Date;
import java.util.ListIterator;
/* Javafx */
import javafx.scene.chart.LineChart;
import javafx.fxml.Initializable;
import javafx.fxml.FXML; 
import javafx.scene.chart.XYChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.CategoryAxis;

public class chartController extends tableViewController implements Initializable {
	
	@FXML
	private LineChart<String, Integer> lineChart;

	@FXML
	private CategoryAxis xAxis;

	@FXML
	private NumberAxis yAxis;

	//ArrayList of Countries	
	ArrayList<CovidData> countries;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		countries = getCountrysData();
		lineChart.setTitle(countries.get(0).getCountry().replace('_', ' '));
		lineChart.setCreateSymbols(false); 		

		xAxis.setLabel("Date");
		yAxis.setLabel("Cases");

		XYChart.Series casesSeries = new XYChart.Series();
		casesSeries.setName("Cases");
		XYChart.Series deathsSeries = new XYChart.Series();
		deathsSeries.setName("Deaths");
		
		for (ListIterator<CovidData> iterator = countries.listIterator(countries.size()); iterator.hasPrevious();) {

			final CovidData country = iterator.previous();

			/*
			 * To populate our from the first case and after
			 */
			if(country.getCases()>0) {
				casesSeries.getData().add(new XYChart.Data(country.getDateString(), country.getCases()));
				deathsSeries.getData().add(new XYChart.Data(country.getDateString(), country.getDeaths()));
			}

		}

		lineChart.getData().addAll(casesSeries,deathsSeries);
	}

}
