package javataire;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
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
import javafx.scene.layout.StackPane;
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
        Stage sessionStage = new Stage(); 
        ToolBar toolbar = new ToolBar();
        VBox root = new VBox();
        root.setPadding(new Insets(5));

        // Create buttons for user control and add them to a toolbar.
        Button btMenu = new Button();
        Button btMove = new Button();
        btMenu.setText("Menu");
        btMove.setText("Move");
        toolbar.getItems().add(btMenu);
        toolbar.getItems().add(new Separator());
        toolbar.getItems().add(btMove);
        toolbar.getItems().add(new Separator());
    
        // Once the start button is clicked, a new game is loaded.
        btStart.setOnAction(e -> 
            {
                playStage.hide();
                sessionStage.show();
                Deck deck = new Deck();
                deck.initialize();
                deck.shuffle();
                sessionStage.setTitle("Current Session");

                VBox cardArea = new VBox();
                cardArea = drawCards(deck);
                root.getChildren().addAll(toolbar, cardArea);

                Scene sessionScene = new Scene(root, 935, 600);
                sessionStage.setScene(sessionScene);
            });

        btMenu.setOnAction(e -> showMenu(sessionStage, primaryStage));
        // btMove.setOnAction(e- > "");
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
     * Draws cards onto the scene.
     */
    public VBox drawCards(Deck deck)
    {
        // Set up HBoxes for each row of cards.
        HBox hBox1 = new HBox();
        HBox hBox2 = new HBox();
        HBox hBox3 = new HBox();
        HBox hBox4 = new HBox();
        HBox hBox5 = new HBox();
        HBox hBox6 = new HBox();
        HBox hBox7 = new HBox();
        HBox hBox8 = new HBox();

        // Holds each HBox for every line of cards.
        VBox vBox = new VBox();  

        // Stack
        StackPane stack = new StackPane(); 

        vBox.setSpacing(5);

        // Load stock card image and add it to the box.
        Image stockImage = new Image("/javataire/resources/card_back.png");
        ImageView stockImageView = new ImageView(stockImage);   
        ArrayList<ImageView> foundationArray = new ArrayList<ImageView>();
        stockImageView.setFitWidth(100);
        stockImageView.setFitHeight(150);
        stockImageView.setImage(stockImage);

        hBox1.setMargin(stockImageView, new Insets(0, 20, 0, 0));

        // Load the waste card image and add it to the box.
        Image wasteImage = new Image("/javataire/resources/cardoutline.png");
        ImageView wasteImageView = new ImageView(wasteImage);
        wasteImageView.setFitWidth(100);
        wasteImageView.setFitHeight(150);
        wasteImageView.setImage(wasteImage);

        hBox1.setMargin(wasteImageView, new Insets(0, 140, 0, 0));

        // Load foundation card images.
        Image foundationImage1 = new Image("/javataire/resources/cardoutline.png");
        ImageView foundationImage1View = new ImageView(foundationImage1);
        foundationImage1View.setFitWidth(100);
        foundationImage1View.setFitHeight(150);
        foundationImage1View.setImage(foundationImage1);
        foundationArray.add(foundationImage1View);

        hBox1.setMargin(foundationImage1View, new Insets(0, 20, 0, 0));

        ImageView foundationImage2View = new ImageView(foundationImage1);
        foundationImage2View.setFitWidth(100);
        foundationImage2View.setFitHeight(150);
        foundationImage2View.setImage(foundationImage1);
        foundationArray.add(foundationImage2View);

        hBox1.setMargin(foundationImage2View, new Insets(0, 20, 0, 0));

        ImageView foundationImage3View = new ImageView(foundationImage1);
        foundationImage3View.setFitWidth(100);
        foundationImage3View.setFitHeight(150);
        foundationImage3View.setImage(foundationImage1);
        foundationArray.add(foundationImage3View);

        hBox1.setMargin(foundationImage3View, new Insets(0, 20, 0, 0));

        ImageView foundationImage4View = new ImageView(foundationImage1);
        foundationImage4View.setFitWidth(100);
        foundationImage4View.setFitHeight(150);
        foundationImage4View.setImage(foundationImage1);
        foundationArray.add(foundationImage4View);

        hBox1.setMargin(foundationImage4View, new Insets(0, 20, 0, 0));

        // Add cards to the box for the top row.
        hBox1.getChildren().addAll(stockImageView, wasteImageView, foundationImage1View, 
        foundationImage2View, foundationImage3View, foundationImage4View);

        // Place each card for row 1 into hBox2
        ArrayList<ImageView> row1 = new ArrayList<ImageView>();
        for (int i = 0; i < 7; i++)
        {
            ImageView curr = new ImageView(stockImage);
            curr.setFitWidth(100);
            curr.setFitHeight(150);
            hBox2.setSpacing(20);
            curr.setImage(stockImage);
            hBox2.getChildren().add(curr);
            row1.add(curr);
        }

        // Place each card for row 2 into hBox3
        ArrayList<ImageView> row2 = new ArrayList<ImageView>();
        for (int i = 0; i < 7; i++)
        {
            ImageView curr = new ImageView(stockImage);
            curr.setFitWidth(100);
            curr.setFitHeight(150);
            hBox3.setSpacing(20);
            if (i < 1)
            {
                curr.setImage(null);
            }
            else
            {
                curr.setImage(stockImage);
            }
            hBox3.getChildren().add(curr);
            row2.add(curr);
        }

        // Place each card for row 3 into hBox4
        ArrayList<ImageView> row3 = new ArrayList<ImageView>();
        for (int i = 0; i < 7; i++)
        {
            ImageView curr = new ImageView(stockImage);
            curr.setFitWidth(100);
            curr.setFitHeight(150);
            hBox4.setSpacing(20);
            if (i < 2)
            {
                curr.setImage(null);
            }
            else
            {
                curr.setImage(stockImage);
            }
            hBox4.getChildren().add(curr);
            row3.add(curr);
        }

        // Place each card for row 4 into hBox5
        ArrayList<ImageView> row4 = new ArrayList<ImageView>();
        for (int i = 0; i < 7; i++)
        {
            ImageView curr = new ImageView(stockImage);
            curr.setFitWidth(100);
            curr.setFitHeight(150);
            hBox5.setSpacing(20);
            if (i < 3)
            {
                curr.setImage(null);
            }
            else
            {
                curr.setImage(stockImage);
            }
            hBox5.getChildren().add(curr);
            row4.add(curr);
        }

        // Place each card for row 5 into hBox6
        ArrayList<ImageView> row5 = new ArrayList<ImageView>();
        for (int i = 0; i < 7; i++)
        {
            ImageView curr = new ImageView(stockImage);
            curr.setFitWidth(100);
            curr.setFitHeight(150);
            hBox6.setSpacing(20);
            if (i < 4)
            {
                curr.setImage(null);
            }
            else
            {
                curr.setImage(stockImage);
            }
            hBox6.getChildren().add(curr);
            row5.add(curr);
        }

        // Place each card for row 6 into hBox7
        ArrayList<ImageView> row6 = new ArrayList<ImageView>();
        for (int i = 0; i < 7; i++)
        {
            ImageView curr = new ImageView(stockImage);
            curr.setFitWidth(100);
            curr.setFitHeight(150);
            hBox7.setSpacing(20);
            if (i < 5)
            {
                curr.setImage(null);
            }
            else
            {
                curr.setImage(stockImage);
            }
            hBox7.getChildren().add(curr);
            row6.add(curr);
        }

        // Place each card for row 7 into hBox8
        ArrayList<ImageView> row7 = new ArrayList<ImageView>();
        for (int i = 0; i < 7; i++)
        {
            ImageView curr = new ImageView(stockImage);
            curr.setFitWidth(100);
            curr.setFitHeight(150);
            hBox8.setSpacing(20);
            if (i < 6)
            {
                curr.setImage(null);
            }
            else
            {
                curr.setImage(stockImage);
            }
            hBox8.getChildren().add(curr);
            row7.add(curr);
        }

        // Add each row of cards to the Stack.
        stack.setMargin(hBox2, new Insets(165, 0, 0, 0));
        stack.setMargin(hBox3, new Insets(185, 0, 0, 0));
        stack.setMargin(hBox4, new Insets(205, 0, 0, 0));
        stack.setMargin(hBox5, new Insets(225, 0, 0, 0));
        stack.setMargin(hBox6, new Insets(245, 0, 0, 0));
        stack.setMargin(hBox7, new Insets(265, 0, 0, 0));
        stack.setMargin(hBox8, new Insets(285, 0, 0, 0));
        stack.getChildren().addAll(hBox1, hBox2, hBox3, hBox4, hBox5, hBox6, hBox7, hBox8);

        // Add each hBox to the vBox.
        vBox.getChildren().addAll(stack);
        return vBox;
    } 

    public static void main(String[] args)
    {
        launch(args);
    }
}
