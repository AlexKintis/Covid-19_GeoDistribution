package CovidDistribution;

/* Java Lang */
import java.net.URL; 
import java.io.IOException;
import java.util.ResourceBundle; 
import java.util.Date;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.time.format.DateTimeFormatter;
/* Javafx */
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.beans.property.SimpleStringProperty; 
import javafx.beans.property.SimpleIntegerProperty; 
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.control.Alert.AlertType; //
import javafx.scene.control.Alert;

/**
 * this is the Controller to handle the window fxml
 *
 */
public class tableViewController implements Initializable  {
		
	@FXML private TableView<CovidData> tableView;
    	@FXML private TableColumn<CovidData, String> date;
    	@FXML private TableColumn<CovidData, Integer> cases;
    	@FXML private TableColumn<CovidData, Integer> deaths;
    	@FXML private TableColumn<CovidData, String> country;
    	@FXML private TableColumn<CovidData, String> countryterritoryCode;
    	@FXML private TableColumn<CovidData, Integer> population;
	@FXML private javafx.scene.control.Button closeButton;	
	
	/* Selected item to parse to the chart */
	private static CovidData selectedItem = null;	
	private static HashMap<String, ArrayList<CovidData>> countryCovidMap;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		/* Table View */
		date.setCellValueFactory(new PropertyValueFactory<>("DateString"));	
		cases.setCellValueFactory(new PropertyValueFactory<>("Cases"));	
		deaths.setCellValueFactory(new PropertyValueFactory<>("Deaths"));	
		country.setCellValueFactory(new PropertyValueFactory<>("Country"));	
		countryterritoryCode.setCellValueFactory(new PropertyValueFactory<>("CountryterritoryCode"));		
		population.setCellValueFactory(new PropertyValueFactory<>("Population"));	
		
		/* Sort cases and insert data to the table */
		cases.setSortType(TableColumn.SortType.DESCENDING);
		// Set Data to the TableView
		tableView.setItems(getData());
		tableView.getSortOrder().add(cases);	
		
		/**
		 * Mouse click tableView listener
		 */
		tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> { 
			selectedItem = newSelection; 
		});	
		
	}
	
	protected ArrayList<CovidData> getCountrysData() {
		return countryCovidMap.get(selectedItem.getCountry());	
	}

	private ObservableList<CovidData> getData() {

		ObservableList<CovidData> list = FXCollections.observableArrayList();
			
		//Getting Distincive Country Names
		List<String> countries = new ArrayList<String>();
		CovidData.getArrayList().forEach( (n) -> countries.add(n.getCountry()));
		Set<String> dinstictiveCountries = new HashSet<String>(countries); 

		//Key Country value ObjCOviddata	
		countryCovidMap = new HashMap<String, ArrayList<CovidData>>();
		

		//Get the distinctive countries and iterates through them
		for(String country : dinstictiveCountries) { 

			String territoryCode = new String();
			String countryName = new String();
			int cases = 0;
			int deaths = 0;
			int population = 0;
			ArrayList<CovidData> arr = new ArrayList<CovidData>();
	
			//Iterate through the ArrayLIst<CovidData> 
			for(CovidData obj : CovidData.getArrayList()) {

				//If the item we iterate is the dinstive country	
				if(obj.getCountry().contains(country)) {

					arr.add(obj);			

					cases += obj.getCases();  	
					deaths += obj.getDeaths();	
					territoryCode = obj.getCountryterritoryCode();
					countryName = obj.getCountry();
					population = obj.getPopulation();
				}
			}
				
			countryCovidMap.put(country, arr);
			CovidData dummy = new CovidData(getOldestCaseDate(countryCovidMap.get(country)), cases, deaths, countryName, territoryCode, population);
			list.add(dummy);
		}
		
		return list;
	}

	private Date getOldestCaseDate(ArrayList<CovidData> arr) {
		
		Date oldestDate = new Date();  
		boolean flag = false;	
		int i = arr.size()-1;	

		do{
			if(arr.get(i).getCases() > 0) {
				oldestDate = arr.get(i).getDate();
				flag = true;
			}
			i--;
		} while(flag == false && i >= 0);
		
		return oldestDate; 
	}	
	
	/* Button Action Events */
	@FXML
	private void closeButtonAction(ActionEvent event)
	{
		Stage stage = (Stage) closeButton.getScene().getWindow();
    		stage.close();
	}
	
	@FXML
	private void openChartButtonAction(ActionEvent event)
	{
		if(selectedItem != null) {

			try {
				Parent root = FXMLLoader.load(App.class.getClassLoader().getResource("chartLayout.fxml"));
				Stage stage = new Stage();
				stage.setTitle(String.format("%s Pandemic Chart", selectedItem.getCountry().replace('_', ' ')));
				stage.setScene(new Scene(root, 1280, 720));

				stage.setMinHeight(700);
				stage.setMinWidth(1280);
				stage.sizeToScene();

				stage.show();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			
		} 
	}

	
} 
