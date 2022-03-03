package com.example.battleshipgame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class BattleshipGameRunner extends Application {

    private Image icon = new Image("file:resources/com/example/battleshipgame/img/icons/battleship_icon.png");

    private Board boardPlayer = new Board();
    private Player player = new Player();
    private Player computerPlayer = new Player();

    private Board boardComputer = new Board();

    private Button newGameButton;

    private Label winLabel, loseLabel;


    private Position position = new Position();

    private BattleshipGame battleshipGame = new BattleshipGame();


    public Label addConsoleMessage(String message, int isPlayer) {

        //isPlayer
        //1 = player
        //2 = computer
        //3 = console

        Label messageLabel = new Label();
        messageLabel.setText(message);
        if (isPlayer == 1)
            messageLabel.setStyle("-fx-background-color: rgba(0,77,255,0.35);");
        else if (isPlayer == 2) {
            messageLabel.setStyle("-fx-background-color: rgba(255,0,0,0.35);");
        } else if (isPlayer == 3)
            messageLabel.setStyle("-fx-background-color: rgba(253,218,0,0.35);");


        return messageLabel;
    }

    private Board setUpComputerShips() {
        //Computer sets up ships
        List<Position> testPositionList = position.randomPositionList(50);


        //dodajemy losowo wybrane pozycja dla statkow przeciwnika
        for (Position randomPosition : testPositionList) {
            boardComputer.addShipPosition(randomPosition);
        }

        return boardComputer;
    }

    private void resetBoardColors(List<Node> buttonsList) {
        for (Node field : buttonsList) {
            Button button = (Button) field;
            button.setStyle("-fx-background-color: transparent; ");
            button.setText("");
            button.setDisable(false);
        }
    }


    @Override
    public void start(Stage stage) throws IOException {


        FXMLLoader fxmlLoader = new FXMLLoader(BattleshipGameRunner.class.getResource("eloszka.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        winLabel = (Label) fxmlLoader.getNamespace().get("winsLabelID");
        loseLabel = (Label) fxmlLoader.getNamespace().get("loseLabelID");
        //konsola w ktorej beda pozkazywaly sie informacje o stanie gry
        ListView<Label> console = (ListView) fxmlLoader.getNamespace().get("consoleListViewID");
        console.setFocusTraversable(false);


        newGameButton = (Button) fxmlLoader.getNamespace().get("newGameButtonID");
        newGameButton.setOnAction(event -> {
            console.getItems().add(addConsoleMessage("Game started!", 3));
            console.getItems().add(addConsoleMessage("Set up your ships", 3));

            boardPlayer.clearBoards();
            boardComputer.clearBoards();

            player.resetShipsDestryed();
            computerPlayer.resetShipsDestryed();

            battleshipGame.setSettingShipsStage(true);
            battleshipGame.setGameStarted(true);
            battleshipGame.setIsGameOver(false);


            boardComputer = setUpComputerShips();

            System.out.println("Player board");
            System.out.println(boardPlayer);
            System.out.println("Computer board");
            System.out.println(boardComputer);

            //Dodajemy do obiektu gridPane gracza (czyli nasza plansze)
            GridPane gridPanePlayer = (GridPane) fxmlLoader.getNamespace().get("playerBoardID");
            //Lista przyciskow gracza
            List<Node> playerBoardList = (gridPanePlayer.getChildren().stream().collect(Collectors.toList()));
            //resetujemy kolory przyciskow planszy gracza
            resetBoardColors(playerBoardList);

            //Dodajemy do obiektu gridPane gracza (czyli nasza plansze)
            GridPane gridPaneComputer = (GridPane) fxmlLoader.getNamespace().get("computerBoardID");
            //Lista przyciskow gracza
            List<Node> computerBoardList = (gridPaneComputer.getChildren().stream().collect(Collectors.toList()));
            //resetujemy kolory przyciskow planszy przeciwnkika
            resetBoardColors(computerBoardList);


        });


        //Dodajemy do obiektu gridPane gracza (czyli nasza plansze)
        GridPane gridPanePlayer = (GridPane) fxmlLoader.getNamespace().get("playerBoardID");

        //Lista przyciskow gracza
        List<Node> playerBoardList = (gridPanePlayer.getChildren().stream().collect(Collectors.toList()));

        //kontrola przyciskow planszy gracza


        for (Node field : playerBoardList) {
            Button button = (Button) field;
            button.setOnAction(event -> {

                //setting up ships stage
                if (battleshipGame.getIsSettingShipsStage() && battleshipGame.getGameStarted()) {

                    //get button position form button id
                    position.buttonIdToPosition(button.getId());


                    //adding ship position to board
                    if (boardPlayer.addShipPosition(position)) {
                        button.setStyle("-fx-background-color: rgba(0,77,255,0.35); ");

                    }

                    //sprawdzaym czy limit statkow zostal wyczerpany
                    //jesli zostal wyczerpany zakonczony zostaje etap ustawiania
                    battleshipGame.setSettingShipsStage(!boardPlayer.isShipsLimitReached());
                    System.out.println(battleshipGame.getIsSettingShipsStage());

                    if (!battleshipGame.getIsSettingShipsStage())
                        console.getItems().add(addConsoleMessage("Setting up ships finished", 3));


                }

            });
        }


        //Sprobojemy sie dostac do GridPane przeciwnika (plansze przeciwnika)
        GridPane gridPaneEnemy = (GridPane) fxmlLoader.getNamespace().get("computerBoardID");
        List<Node> computerBoardFieldList = (gridPaneEnemy.getChildren().stream().collect(Collectors.toList()));

        for (Node field : computerBoardFieldList) {
            Button button = (Button) field;
            button.setOnAction((event) -> {


                //jesli gracze rozlozyli swoje statki i gra sie nie skonczyla
                if (!battleshipGame.getIsSettingShipsStage() && !battleshipGame.getIsGameOver()) {

                    //konwertujemy id przycisku na klase position
                    position.buttonIdToPosition(button.getId());

                    //w klasie board dodajemy nowy strzal
                    boardPlayer.addShotPosition(position);

                    System.out.println("pozycja strzalu: " + position);


                    //sprawdzamy w planszy przeciwnika czy jest statek pod wybrana przez uzytkownika pozycja
                    if (boardComputer.containsShip(position)) {
                        button.setText("X");

                        //adding point for destryed ship
                        player.addDestroyedShip();


                        button.setStyle("-fx-background-color: rgba(255,0,0,0.35);");
                        console.getItems().add(addConsoleMessage("Player shot at " + position + " and hit", 1));

                        //jesli gracz zniszyl 10 statkow wygrywa
                        if (player.getShipsDestroyed() == 10) {
                            battleshipGame.setIsGameOver(true);
                            console.getItems().add(addConsoleMessage("Player won", 3));
                            player.addWin();
                            winLabel.setText("Wins: " + player.getWins());

                        }

                    } else {

                        //Gdy nie trafimy w statek przeciwnika
                        button.setText("O");

                        console.getItems().add(addConsoleMessage("Player shot at " + position + " and missed", 1));

                        boolean computerHasShotted = false;
                        while (!computerHasShotted) {

                            //jesli gracze ustawili swoje statki oraz nie jest to tura gracza
                            //komputer strzela


                            //komputer losuje losowo pozycje
                            Position randomShot = position.getRandomPosition();
                            System.out.println("Random Shot: " + randomShot);

                            //jesli jeszcze w nia nie strzelal
                            if (!boardComputer.checkIfShotted(randomShot)) {
                                //wykonaj strzal (dodaj do planszy komputera)
                                boardComputer.addShotPosition(randomShot);

                                //jesli plansza gracza zawiera statek w danej pozycji
                                if (boardPlayer.containsShip(randomShot)) {

                                    for (Node boardButton : playerBoardList) {

                                        if (boardButton.getId().equals(randomShot.positionToButtonId())) {
                                            System.out.println("contains ship ID: " + boardButton.getId());
                                            Button buttonTemp = (Button) boardButton;
                                            buttonTemp.setStyle("-fx-background-color: rgba(255,0,0,0.35); ");
                                            buttonTemp.setText("X");

                                            computerPlayer.addDestroyedShip();
                                            console.getItems().add(addConsoleMessage("Computer shot at " + randomShot + " and hit", 2));

                                            if (computerPlayer.getShipsDestroyed() == 10) {
                                                battleshipGame.setIsGameOver(true);
                                                computerPlayer.addWin();
                                                loseLabel.setText("Lose: " + computerPlayer.getWins());
                                                computerHasShotted = true;
                                                console.getItems().add(addConsoleMessage("Computer won", 3));
                                            }

                                        }
                                    }
                                } else {
                                    for (Node boardButton : playerBoardList) {
                                        if (boardButton.getId().equals(randomShot.positionToButtonId())) {
                                            System.out.println("no ship ID: " + boardButton.getId());
                                            Button buttonTemp = (Button) boardButton;
                                            buttonTemp.setText("O");
                                            console.getItems().add(addConsoleMessage("Computer shot at " + randomShot + " and missed", 2));
                                            computerHasShotted = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    button.setDisable(true);
                }
            });
        }


        //css
        scene.getStylesheets().add(this.getClass().getResource("application.css").toExternalForm());


        stage.setTitle("Battleship");
        stage.setMaximized(true);
        stage.setResizable(false);
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }
}