import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Game extends Application
{
    // Create buttons for playing the game and viewing the tutorial.
    private Button btTutorial = new Button("Tutorial");
    private Button btPlay = new Button("Play");

    public static final String MEDIA_URL = 
        "https://www.wikihow.com/video/6/60/Play+Solitaire+Step+0+Version+3.360p.mp4";

    @Override // Override the start method in the Application class.
    public void start(Stage primaryStage) 
    {  
        GridPane gridPane = new GridPane();
        
        gridPane.setHgap(5);
        gridPane.setVgap(30);

        // Create text for title and credits.
        Text title = new Text("JAVATAIRE");
        title.setFont(Font.font("Times New Roman", FontWeight.BOLD, 
        FontPosture.REGULAR, 40));
        gridPane.add(title, 0, 0);
        GridPane.setHalignment(title, HPos.CENTER);

        Text nameTag = new Text("A Game by Jacob Chung");  
        nameTag.setFont(Font.font("Times New Roman", FontWeight.BOLD, 
        FontPosture.REGULAR, 24));
        gridPane.add(nameTag, 0, 1);
        GridPane.setHalignment(nameTag, HPos.CENTER);
        
        // Add buttons.
        gridPane.add(btTutorial, 0, 4);
        gridPane.add(btPlay, 0, 3);
        GridPane.setHalignment(btPlay, HPos.CENTER);
        GridPane.setHalignment(btTutorial, HPos.CENTER);

        // Set properties for UI.
        gridPane.setAlignment(Pos.TOP_CENTER);

        // Load title image.
        Image titleImage = new Image("https://upload.wikimedia.org/wikipedia/commons/thumb/d/d3/Playing_card_diamond_A.svg/1638px-Playing_card_diamond_A.svg.png");
        ImageView imageView = new ImageView(titleImage);   
        imageView.setFitWidth(200);
        imageView.setFitHeight(300);
        imageView.setImage(titleImage);

        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(imageView); 
        hBox.setAlignment(Pos.CENTER);
        gridPane.add(hBox,0 , 2);

        // Create a title scene and place it in the stage.
        Scene scene = new Scene(gridPane, 800, 600);
        primaryStage.setTitle("Javataire"); // Set title.
        primaryStage.setScene(scene); // Place the scene in the stage.
        primaryStage.show(); // Display the stage.

        btTutorial.setOnAction(e -> displayHelp());
        btPlay.setOnAction(e -> playGame(primaryStage));
    }

    /**
     * Handles event where "Help" button is clicked.
     */
    public void displayHelp()
    {                        
        Stage helpStage = new Stage();

        Media media = new Media(MEDIA_URL);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

        Button playButton = new Button(">");
        playButton.setOnAction(e -> {
        if (playButton.getText().equals(">")) {
            mediaPlayer.play();
            playButton.setText("||");
        } else {
            mediaPlayer.pause();
            playButton.setText(">");
        }
        });

        Button rewindButton = new Button("<<");
        rewindButton.setOnAction(e -> mediaPlayer.seek(Duration.ZERO));
        
        Slider slVolume = new Slider();
        slVolume.setPrefWidth(150);
        slVolume.setMaxWidth(Region.USE_PREF_SIZE);
        slVolume.setMinWidth(30);
        slVolume.setValue(50);
        mediaPlayer.volumeProperty().bind(
        slVolume.valueProperty().divide(100));

        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(playButton, rewindButton,
        new Label("Volume"), slVolume);

        BorderPane pane = new BorderPane();
        pane.setCenter(mediaView);
        pane.setBottom(hBox);

        // Create a scene and place it in the stage
        Scene scene = new Scene(pane, 700, 600);
        helpStage.setTitle("Tutorial"); // Set the stage title
        helpStage.setScene(scene); // Place the scene in the stage
        helpStage.show(); // Display the stage    
    }

    /**
     * Handles event for launching game when "Play" button is pressed.
     */
    public void playGame(Stage primaryStage)
    {
        Stage playStage = new Stage();

        // Create a scene and place it in the stage
        // Scene scene = new Scene(pane, 800, 600);
        playStage.setTitle("Current Session"); // Set the stage title
        // primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.hide();
        playStage.show(); // Display the stage 
    }
    public static void main(String[] args)
    {
        launch(args);
    }
}
