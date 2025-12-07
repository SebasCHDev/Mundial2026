package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/principal.fxml"));
        Scene scene = new Scene(loader.load(), 800, 600);
        stage.setTitle("Simulador Mundial 2026");
        stage.setScene(scene);
        stage.show();
    }

    // Este método ES necesario, incluso en JavaFX
    public static void main(String[] args) {
        launch(args); // ← pasa 'args', no uses launch() sin parámetros
    }
}