package javataire;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
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
        if (playButton.getText().equals(">")) 
        {
            mediaPlayer.play();
            playButton.setText("||");
        } else 
        {
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
        Stage playStage = new Stage(); // Stage used for initializing game.
        Button btStart = new Button("Start");
        GridPane gamePane = new GridPane();

        gamePane.setHgap(5);
        gamePane.setVgap(30);

        // Create text for intro frame.
        Text title = new Text("Click the button to create a deck.");
        title.setFont(Font.font("Times New Roman", FontWeight.BOLD, 
        FontPosture.REGULAR, 20));
        gamePane.add(title, 0, 0);
        GridPane.setHalignment(title, HPos.CENTER);

        gamePane.add(btStart, 0, 1);
        GridPane.setHalignment(btStart, HPos.CENTER);

        // Set properties for UI.
        gamePane.setAlignment(Pos.CENTER);

        Scene introScene = new Scene(gamePane, 800, 600);
        playStage.setTitle("New Game"); // Set the stage title
        playStage.setScene(introScene); // Place the scene in the stage
        primaryStage.hide();
        playStage.show(); // Display the stage 

        ///////////////////////////////////////////////////////////////////////

        // Create separate stage and scene for ingame sessions.
        Stage sessionStage = new Stage(); // Used for in-progress sessions.
        GridPane sessionPane = new GridPane();
        ToolBar toolbar = new ToolBar();

        sessionPane.setHgap(5);
        sessionPane.setVgap(30);

        // Create buttons for user control and add them to a toolbar.
        Button btMenu = new Button();
        Button btMove = new Button();
        btMenu.setText("Menu");
        btMove.setText("Move");
        toolbar.getItems().add(btMenu);
        toolbar.getItems().add(new Separator());
        toolbar.getItems().add(btMove);
        toolbar.getItems().add(new Separator());
        
        GridPane.setHalignment(btMenu, HPos.RIGHT);
        VBox vBox = new VBox(toolbar);
        
        sessionStage.setTitle("Current Session");
        Scene sessionScene = new Scene(vBox, 1000, 600);
        sessionStage.setScene(sessionScene);

        // Once the start button is clicked, a new game is loaded.
        btStart.setOnAction(e -> 
            {
                playStage.hide();
                sessionStage.show();
                Deck deck = new Deck();
                deck.initialize();
                deck.shuffle();
                drawCards(playStage, deck);
            });

        btMenu.setOnAction(e -> showMenu(sessionStage, primaryStage));
    }

    /**
     * Handles bringing up the menu during a session.
     */
    public void showMenu(Stage currentStage, Stage primaryStage)
    {
        Stage menuStage = new Stage();
        Button btTutorial = new Button("Tutorial");
        Button btRestart = new Button("Restart");
        Button btQuit = new Button("Quit");
        GridPane menuPane = new GridPane();

        menuPane.setHgap(5);
        menuPane.setVgap(30);

        menuPane.add(btTutorial, 0, 1);
        GridPane.setHalignment(btTutorial, HPos.CENTER);
        menuPane.add(btRestart, 0, 2);
        GridPane.setHalignment(btRestart, HPos.CENTER);
        menuPane.add(btQuit, 0, 3);
        GridPane.setHalignment(btQuit, HPos.CENTER);

        menuPane.setAlignment(Pos.TOP_CENTER);

        menuStage.setTitle("Menu");
        Scene menuScene = new Scene(menuPane, 300, 250);
        menuStage.setScene(menuScene);
        menuStage.show();

        // Handle events for each button.
        btTutorial.setOnAction(e -> displayHelp());
        btRestart.setOnAction(e -> 
        {
            currentStage.hide();
            menuStage.hide();
            playGame(primaryStage);
        });
        btQuit.setOnAction(e -> 
        {
            currentStage.hide();
            menuStage.hide();
            start(primaryStage);
        });
    }
    
    /*
     * TO DO: check if putting menu and other stuff from playStage works better in here.
     */
    public void drawCards(Stage playStage, Deck deck)
    {
        GridPane cardPane = new GridPane();
        Scene scene = playStage.getScene();

        HBox cardBox = new HBox();
    } 

    public static void main(String[] args)
    {
        launch(args);
    }
}
