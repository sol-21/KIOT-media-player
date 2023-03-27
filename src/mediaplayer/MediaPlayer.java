
package mediaplayer;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class MediaPlayer extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setTitle("Kiot Media Player");
       
        
        scene.setOnMouseClicked(new EventHandler <MouseEvent>(){
            @Override
            public void handle(MouseEvent clicked) {
                if(clicked.getClickCount() == 2){
                    if(!stage.isFullScreen()){
                        stage.setFullScreen(true);
                        
                        
                    }else{
                        stage.setFullScreen(false);
                    }
                    
                }
            }
            
        });
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
