package com.killerchess.view.roomcreator;

import com.killerchess.core.chessboard.scenarios.GameScenariosEnum;
import com.killerchess.core.session.LocalSessionSingleton;
import com.killerchess.view.View;
import com.killerchess.view.game.GameBoard;
import com.killerchess.view.loging.LoginController;
import com.killerchess.view.utils.CustomAlert;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Optional;
import java.util.UUID;

import static com.killerchess.core.controllers.game.GameController.*;

public class RoomCreatorController {

    public Button createRoomButton;
    public TextField roomNameTextField;
    public VBox gameSchemasRadioButtonsVBox;

    private ToggleGroup toggleGroupForSchemasRadioButtons;

    @FXML
    public void initialize() {
        initializeControllerFields();
        initializeComponents();
    }

    private void initializeControllerFields() {
        toggleGroupForSchemasRadioButtons = new ToggleGroup();
    }

    private void initializeComponents() {
        initializeVBoxWithGameScenarios();
    }

    public void handleCreateRoomButtonClick() throws Exception {
        var roomName = roomNameTextField.getCharacters().toString();
        var roomDatabaseId = String.format("%s_%s", roomName, UUID.randomUUID());
        var selectedScenario = (RadioButton) toggleGroupForSchemasRadioButtons.getSelectedToggle();
        var scenarioId = selectedScenario.getId();
        createNewGame(roomName, roomDatabaseId, scenarioId);
        new GameBoard().start(View.getInstance().getStage());
    }

    private void createNewGame(String roomName, String roomDatabaseId, String scenarioId) throws Exception {
        MultiValueMap<String, String> roomCreationParametersMap = new LinkedMultiValueMap<>();
        roomCreationParametersMap.add(GAME_ID_PARAM, roomDatabaseId);
        roomCreationParametersMap.add(GAME_NAME_PARAM, roomName);

        var session = LocalSessionSingleton.getInstance();
        var responseEntity = session.exchange(
                LoginController.HOST + NEW_GAME_PATH, HttpMethod.POST,
                roomCreationParametersMap,
                ResponseEntity.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            createInitialMoveInGame(roomDatabaseId, scenarioId);
        } else {
            CustomAlert.showAndWait("Błąd przy tworzeniu gry", Alert.AlertType.ERROR);
        }
    }

    private void createInitialMoveInGame(String roomDatabaseId, String scenarioId)
            throws Exception {
        Optional<String> scenarioArrangement = GameScenariosEnum.getAllEnumConstants().stream()
                .filter(gameScenariosEnum -> gameScenariosEnum.getId().toString().equals(scenarioId))
                .findFirst()
                .map(GameScenariosEnum::getArrangement);

        if (scenarioArrangement.isPresent()) {
            MultiValueMap<String, String> gameStateCreationParametersMap = new LinkedMultiValueMap<>();
            gameStateCreationParametersMap.add(STATE_PARAM, scenarioArrangement.get());
            gameStateCreationParametersMap.add(GAME_ID_PARAM, roomDatabaseId);

            var session = LocalSessionSingleton.getInstance();
            var responseEntity = session.exchange(LoginController.HOST + FIRST_GAME_STATE_PATH,
                    HttpMethod.POST, gameStateCreationParametersMap, Integer.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                session.setParameter(GAME_STATE_NUMBER_PARAM, responseEntity.getBody().toString());
                changeSceneToChessBoard();
            } else {
                CustomAlert.showAndWait("Nie udało się stworzyć pierwszego ruchu.", Alert.AlertType.ERROR);
            }
        } else {
            CustomAlert.showAndWait("Błąd przy wyborze scenariusza.", Alert.AlertType.ERROR);
        }
    }

    private void changeSceneToChessBoard() throws Exception {
        View.getInstance().changeScene("/main_screen.fxml");
    }

    private void initializeVBoxWithGameScenarios() {
        var vBoxChildren = gameSchemasRadioButtonsVBox.getChildren();
        GameScenariosEnum.getAllEnumConstants()
                .forEach(gameScenario ->
                        vBoxChildren.add(createRadioButtonForScenarioInToggleGroup(gameScenario)));
        fireFirstButton(vBoxChildren);
    }

    private RadioButton createRadioButtonForScenarioInToggleGroup(GameScenariosEnum gameScenariosEnum) {
        var radioButtonForSchema = new RadioButton(gameScenariosEnum.getDescription());
        radioButtonForSchema.setToggleGroup(toggleGroupForSchemasRadioButtons);
        radioButtonForSchema.setId(gameScenariosEnum.getId().toString());
        return radioButtonForSchema;
    }

    private void fireFirstButton(ObservableList<Node> vBoxChildren) {
        RadioButton firstButton = (RadioButton) vBoxChildren.get(0);
        firstButton.fire();
    }


}
