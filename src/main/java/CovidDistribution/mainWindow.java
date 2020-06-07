package CovidDistribution;

import javafx.application.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import java.io.IOException;

public class mainWindow extends javafx.application.Application {
	
	Stage primaryStage;

	@Override		
	public void start(Stage rootStage) throws Exception {

		//Import file from resources windowLayout.fxml
		this.primaryStage = rootStage;
			
		//Fxml | Css
		try {
			Parent root = FXMLLoader.load(App.class.getClassLoader().getResource("windowLayout.fxml"));

			Scene primaryScene = new Scene(root,800,840);

			primaryStage.setTitle("Covid 19");	
			primaryStage.setResizable(true);	
			primaryStage.setScene(primaryScene);

			primaryStage.setMinHeight(840);
			primaryStage.setMinWidth(800);

			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
	}

}

