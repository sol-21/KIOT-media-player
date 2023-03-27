
package mediaplayer;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import static javafx.scene.media.MediaPlayer.Status.PLAYING;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import javax.swing.JOptionPane;


public class FXMLDocumentController implements Initializable {
    public String filePath;
    public MediaPlayer mediaPlayer;
    @FXML
    private MediaView mediaView;
    @FXML
    private Slider slider;
    @FXML
    private Slider mediaSlider;
    
    private List<String> extensions;
    @FXML
    private void handleButtonAction(ActionEvent event){
        extensions = Arrays.asList("*.mp3","*.mp4","*.3gp","*.mkv","*.MP3","*.MP4","*.MKV","*.3GP","*.flv","*.wmv");
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("select file",extensions);
        
        try{
            fileChooser.getExtensionFilters().add(filter);
            fileChooser.setTitle("Select File to Open");
            File file = fileChooser.showOpenDialog(null);
            filePath = file.toURI().toString();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        if(filePath!= null){
            Media media = new Media(filePath);
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
                DoubleProperty width = mediaView.fitWidthProperty();
                DoubleProperty height = mediaView.fitHeightProperty();
                
                width.bind(Bindings.selectDouble(mediaView.sceneProperty(),"width"));
                height.bind(Bindings.selectDouble(mediaView.sceneProperty(),"height"));
              
                
                slider.setValue(mediaPlayer.getVolume()*100);
                
                
                
                slider.valueProperty().addListener(new InvalidationListener(){
                    @Override
                    public void invalidated(Observable obeservable){
                        mediaPlayer.setVolume(slider.getValue()/100);
                    } 
                });
                
                
                
                
                mediaPlayer.setOnReady(new Runnable(){
                @Override
                public void run() {
                    Duration totalTime = media.getDuration();
                    mediaSlider.setMin(0.0);
                    mediaSlider.setValue(0.0);
                    mediaSlider.setMax(totalTime.toSeconds());
                    
                    mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>(){
                    @Override
                    public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                        mediaSlider.setValue(newValue.toSeconds());
                    }
                    
                  });
                    
                 mediaSlider.setOnMousePressed(new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent event){
                        mediaPlayer.seek(Duration.seconds(mediaSlider.getValue()));
                    }
                });
                    
                    mediaSlider.setOnMouseDragged(new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent event){
                        mediaPlayer.seek(Duration.seconds(mediaSlider.getValue()));
                    }
                });     
                   
              }
            });
                
               
                
                
                
            mediaPlayer.play();
        }
        
    }
    
    @FXML
    private void pauseVideo(ActionEvent event){
        try{
            if(mediaPlayer.getStatus() == PLAYING){
                mediaPlayer.pause();
            }else{ 
                mediaPlayer.play();
              
            }
        }catch(Exception e){
            
            JOptionPane.showMessageDialog(null,"Open up a File First");
        }
        
    }
    
    @FXML
    private void stopVideo(ActionEvent event){
        try{
        mediaPlayer.stop();
        }catch(NullPointerException e ){
           
            JOptionPane.showMessageDialog(null,"Open up a File First");
        }
    }
    
    @FXML
    private void fastVideo(ActionEvent event){
        try{
            mediaPlayer.setRate(mediaPlayer.getRate()+0.12);
        }catch(NullPointerException e){
            JOptionPane.showMessageDialog(null,"Open up a File First");
        }   
    }
    
    @FXML
    private void slowVideo(ActionEvent event){
        try{
            mediaPlayer.setRate(mediaPlayer.getRate()-0.12);
        }catch(NullPointerException e){
            JOptionPane.showMessageDialog(null,"Open up a File First");
        }
        
    }
    
    @FXML
    private void closeVideo(ActionEvent event){
        System.exit(0);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    
    
}
