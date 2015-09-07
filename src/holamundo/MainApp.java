package holamundo;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

	public static final String FEDERATION_NAME = "HolaMundo";
	public static final String FDD = "HolaMundoObjectModel-ieee-1516e.xml";
	
	private Stage primaryStage;
    private BorderPane rootLayout;

    public MainApp() {
	}
    
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Logger");
		initRootLayout();
		showLoggerOverview();
	}
	
	private void showLoggerOverview() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/LoggerOverview.fxml"));
			AnchorPane loggerOverview;
			loggerOverview = (AnchorPane) loader.load();
			rootLayout.setCenter(loggerOverview);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
	private void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
