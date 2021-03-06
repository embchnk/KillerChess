package com.killerchess.view.mainpanel;

import com.killerchess.core.dto.GameDTO;
import com.killerchess.core.dto.RankingRegistryDTO;
import com.killerchess.core.session.LocalSessionSingleton;
import com.killerchess.view.View;
import com.killerchess.view.game.GameBoard;
import com.killerchess.view.loging.LoginController;
import com.killerchess.view.utils.CustomAlert;
import com.killerchess.view.utils.Template;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;

import javax.swing.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static com.killerchess.core.controllers.app.RankingController.GET_USER_RANKING_PATH;
import static com.killerchess.core.controllers.app.RankingController.RANKING_PATH;
import static com.killerchess.core.controllers.game.GameController.*;
import static com.killerchess.core.controllers.user.UserController.GET_LOGIN_PATH;
import static com.killerchess.view.game.ImagesConstants.*;


public class MainPanelController {

    public Text rankingPointsForActualUser;
    public ImageView userAvatar;
    public Button createRoom;
    public ImageView accountImage;
    public TextArea rankingText;
    public ImageView rankingImage;
    public TextArea helpText;
    public ImageView helpImage;
    public ImageView firstPawnChoice;
    public ImageView secondPawnChoice;
    public ImageView thirdPawnChoice;
    public ImageView actualPawnChoice;
    public Text actualPawnChoiceText;
    public Text choosePawnText;
    public VBox roomsVBox;
    public TextArea roomInfo;
    public Button changeAvatarButton;
    public Text usernameText;
    public Button refreshRooms;
    private String userPoints;
    private boolean selectedAccountTab = true;
    private String username;
    private double panelWidth;
    private double panelHeight;
    private LocalSessionSingleton localSessionSingleton = LocalSessionSingleton.getInstance();

    @FXML
    public void initialize() {
        getPanelSize();
        setUserParameters();
        setRanking();
        initializeComponents();
        accountImageListener();
        if (selectedAccountTab) {
            accountListeners();
        }
        rankingImageListener();
        hideHelpInfo();
        helpImageListener();
        initializeRoomsVBox();
        addImageToRefreshButton();
    }

    private void addImageToRefreshButton() {
        Image image = new Image(IMAGES_LOCAL_PATH + REFRESH_BUTTON_FILEPATH);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        refreshRooms.setGraphic(imageView);

    }

    public void handleNewRoomButtonClicked() {
        try {
            View.getInstance().changeScene("/room_creator.fxml");
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode().is4xxClientError()) {
                CustomAlert.showAndWait(e.getResponseBodyAsString(), Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleAccountAvatarChange() {
        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(new Stage());
        if (file != null) {
            try {
                String mimeType = Files.probeContentType(file.toPath());
                if (mimeType != null && mimeType.equals(IMAGE_JPEG_MIME_TYPE)) {
                    boolean directoryExists;
                    File dir = new File(new JFileChooser().getFileSystemView().getDefaultDirectory().toString()
                            + IMAGES_FOLDER_FOR_AVATAR);
                    if (!dir.exists() || dir.exists() && !dir.isDirectory()) {
                        directoryExists = dir.mkdirs();
                    } else {
                        directoryExists = true;
                    }
                    if (directoryExists) {
                        File f = new File(new JFileChooser().getFileSystemView().getDefaultDirectory().toString()
                                + IMAGES_FOLDER_FOR_AVATAR + AVATAR_FILENAME_PREFIX + username +
                                JPG_FILE_TYPE_EXTENSION);
                        Files.copy(file.toPath(), f.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        Image image = new Image("file:" + f.toPath(), panelWidth / 3, panelHeight / 2, false,
                                false);
                        userAvatar.setImage(image);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void getPanelSize() {
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        this.panelWidth = visualBounds.getWidth();
        this.panelHeight = visualBounds.getHeight();
    }

    private void setUserParameters() {
        var responseEntity = localSessionSingleton.exchange(
                LoginController.HOST + GET_LOGIN_PATH,
                HttpMethod.GET, null, ResponseEntity.class);
        this.username = responseEntity.getHeaders().getFirst("username");

        ResponseEntity<RankingRegistryDTO> response = localSessionSingleton.exchange(
                LoginController.HOST + GET_USER_RANKING_PATH,
                HttpMethod.GET, null, RankingRegistryDTO.class);
        this.userPoints = String.valueOf(response.getBody().getPoints());
    }

    private void setRanking() {
        ResponseEntity<List<RankingRegistryDTO>> rankingResponse = localSessionSingleton.exchange(
                LoginController.HOST + RANKING_PATH, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<RankingRegistryDTO>>() {
                });
        List<RankingRegistryDTO> rankingList = rankingResponse.getBody();

        IntStream.range(0, rankingList.size()).forEach(i -> rankingText.setText(getRankingText(i, rankingList.get(i))));

        rankingText.setEditable(false);
        rankingText.setVisible(false);
    }

    private String getRankingText(int i, RankingRegistryDTO rankingRegistryDTO) {
        return rankingText.getText() + "\n" + (i + 1) + ") " + rankingRegistryDTO.getUsername()
                + " [" + rankingRegistryDTO.getPoints() + "]";
    }

    private void initializeComponents() {
        setNameAndPointsForUser();

        File file;
        String path;
        try {
            file = new File(new JFileChooser().getFileSystemView().getDefaultDirectory().toString()
                    + IMAGES_FOLDER_FOR_AVATAR + AVATAR_FILENAME_PREFIX + username + JPG_FILE_TYPE_EXTENSION);
            path = file.getPath();
            if (file.exists() && !file.isDirectory()) {
                Image image = new Image("file:" + path, panelWidth / 3, panelHeight / 2, false, false);
                userAvatar.setImage(image);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        setActualPawnTemplateImage();
    }

    private void setNameAndPointsForUser() {
        usernameText.setText(usernameText.getText() + " " + username);
        rankingPointsForActualUser.setText(rankingPointsForActualUser.getText() + " " + userPoints);
    }

    private void setActualPawnTemplateImage() {
        String actualPawn = localSessionSingleton.getParameter("template");
        if (actualPawn == null) {
            actualPawn = Template.FIRST.getTemplateFileName();
        }
        actualPawnChoice.setImage(generateActualImageForPawnTemplate(actualPawn));
    }

    private Image generateActualImageForPawnTemplate(String actualPawn) {
        if (actualPawn.equals(Template.FIRST.getTemplateFileName())) {
            localSessionSingleton.setParameter("template", Template.FIRST.getTemplateFileName());
            localSessionSingleton.setParameter("template_number", Integer.toString(Template.FIRST
                    .getChessmanStyleNumber()));
            return generateImageForPawnTemplates(Template.FIRST.getChessmanStyleNumber());
        }
        if (actualPawn.equals(Template.SECOND.getTemplateFileName())) {
            localSessionSingleton.setParameter("template", Template.SECOND.getTemplateFileName());
            localSessionSingleton.setParameter("template_number", Integer.toString(Template.SECOND
                    .getChessmanStyleNumber()));
            return generateImageForPawnTemplates(Template.SECOND.getChessmanStyleNumber());
        }
        if (actualPawn.equals(Template.THIRD.getTemplateFileName())) {
            localSessionSingleton.setParameter("template", Template.THIRD.getTemplateFileName());
            localSessionSingleton.setParameter("template_number", Integer.toString(Template.THIRD
                    .getChessmanStyleNumber()));
            return generateImageForPawnTemplates(Template.THIRD.getChessmanStyleNumber());
        }
        return null;
    }

    private Image generateImageForPawnTemplates(int index) {
        return new Image(IMAGES_LOCAL_PATH + PAWN_FILENAME_PREFIX + index + BLACK_BISHOP_SUFFIX);
    }

    private void accountImageListener() {
        accountImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            rankingText.setVisible(false);
            helpText.setVisible(false);
            enableAccountFunctions(true);
            event.consume();
        });
    }

    private void accountListeners() {
        firstPawnChoice.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            localSessionSingleton.setParameter("template", Template.FIRST.toString());
            actualPawnChoice.setImage(generateActualImageForPawnTemplate(Template.FIRST.toString()));
            event.consume();
        });

        secondPawnChoice.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            localSessionSingleton.setParameter("template", Template.SECOND.toString());
            actualPawnChoice.setImage(generateActualImageForPawnTemplate(Template.SECOND.toString()));
            event.consume();
        });

        thirdPawnChoice.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            localSessionSingleton.setParameter("template", Template.THIRD.toString());
            actualPawnChoice.setImage(generateActualImageForPawnTemplate(Template.THIRD.toString()));
            event.consume();
        });
    }

    private void enableAccountFunctions(boolean bool) {
        selectedAccountTab = bool;
        firstPawnChoice.setVisible(bool);
        secondPawnChoice.setVisible(bool);
        thirdPawnChoice.setVisible(bool);
        actualPawnChoice.setVisible(bool);
        choosePawnText.setVisible(bool);
        actualPawnChoiceText.setVisible(bool);
        changeAvatarButton.setVisible(bool);
        changeAvatarButton.setDisable(!bool);
    }

    private void rankingImageListener() {
        rankingImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            rankingText.setVisible(true);
            helpText.setVisible(false);
            enableAccountFunctions(false);
            event.consume();
        });
    }

    private void hideHelpInfo() {
        helpText.setEditable(false);
        helpText.setVisible(false);
    }

    private void helpImageListener() {
        helpImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            rankingText.setVisible(false);
            helpText.setVisible(true);
            enableAccountFunctions(false);
            event.consume();
        });
    }

    private void initializeRoomsVBox() {
        Text title = new Text(" Rooms:");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        getRoomsVBoxChildren().add(title);

        ResponseEntity<List<GameDTO>> roomsResponse = localSessionSingleton.exchange(
                LoginController.HOST + AVAILABLE_GAMES_PATH, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<GameDTO>>() {
                });

        List<GameDTO> gamesList = roomsResponse.getBody();
        List<TextArea> gamesOptions = new ArrayList<>();

        for (GameDTO gameDTO : gamesList) {
            TextArea gameOption = new TextArea();
            gameOption.setEditable(false);
            gameOption.setText("Room: " + gameDTO.getGameName() + "\n" + "host: " + gameDTO.getHost() + "\n");
            gameOption.setId(gameDTO.getGameId());
            gamesOptions.add(gameOption);
        }

        for (TextArea gameOption : gamesOptions) {
            VBox.setMargin(gameOption, new Insets(0, 0, 0, 8));
            getRoomsVBoxChildren().add(gameOption);
            createEventsForGames(gamesList, gameOption);
        }

        roomsVBox.setOnMouseClicked((e) -> roomsVBox.requestFocus());
    }

    private void createEventsForGames(List<GameDTO> gamesList, TextArea gameOption) {
        gameOption.setOnMouseClicked(event -> {
            Optional<GameDTO> gameForClickedRoom = gamesList.stream()
                    .filter(gameDTO -> gameDTO.getGameId().equals(gameOption.getId()))
                    .findFirst();
            boolean isGameForClickedRoomPresent = gameForClickedRoom.isPresent();
            GameDTO game = null;
            if (isGameForClickedRoomPresent) {
                game = gameForClickedRoom.get();
            }

            if (event.getClickCount() == 1 && isGameForClickedRoomPresent) {
                String gameGuest = game.getGuest();
                roomInfo.setText("Room name: " + game.getGameName() + "\n"
                        + "Host: " + game.getHost() + "\n"
                        + "Guest: " + (gameGuest == null ? "empty" : gameGuest) + "\n"
                        + "Unique game ID: " + game.getGameId());

            }

            if (event.getClickCount() == 2 && isGameForClickedRoomPresent) {
                MultiValueMap<String, String> joinGameParametersMap = new LinkedMultiValueMap<>();
                joinGameParametersMap.add(GAME_ID_PARAM, game.getGameId());
                var responseEntity = localSessionSingleton.exchange(LoginController.HOST + JOIN_GAME_PATH,
                        HttpMethod.POST, joinGameParametersMap, Integer.class);
                if (responseEntity.getStatusCode().is2xxSuccessful()) {
                    Stage stage = View.getInstance().getStage();
                    GameBoard gameBoard = GameBoard.getInstance();
                    try {
                        gameBoard.start(stage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    localSessionSingleton.setParameter("gameStateNumber", responseEntity.getBody().toString());
                }
            }
        });
    }


    private ObservableList<Node> getRoomsVBoxChildren() {
        return roomsVBox.getChildren();
    }

    public void handleRefreshRoomsButtonClicked() {
        roomsVBox.getChildren().clear();
        initializeRoomsVBox();
    }
}