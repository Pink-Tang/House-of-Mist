package scenes;
//jackRoom

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import bag.InventoryUI;
import bag.Item;
public class PasswordImagePuzzle {

    private static final String CORRECT_PASSWORD = "7201";
    private final InventoryUI inventory;

    public PasswordImagePuzzle(InventoryUI inventory) {
        this.inventory = inventory;
    }

    public void show(Stage stage) {
        stage.setTitle("release");

        // 顶部图片
        ImageView imageView = new ImageView();
        try {
            Image image = new Image(new FileInputStream("resources/clue.jpg"));
            imageView.setImage(image);
            imageView.setFitWidth(300);
            imageView.setPreserveRatio(true);
        } catch (FileNotFoundException e) {
            System.out.println("Image not found, please check the path!");
        }

        // 中间区域：密码输入
        Label promptLabel = new Label("Please enter a four-digit password:");
        TextField passwordField = new TextField();
        passwordField.setPromptText("Example: 1234");
        passwordField.setMaxWidth(120);

        VBox centerBox = new VBox(10, promptLabel, passwordField);
        centerBox.setAlignment(Pos.CENTER);

        // 底部按钮
        Button submitButton = new Button("submit");
        submitButton.setOnAction(e -> {
            String userInput = passwordField.getText().trim();
            if (CORRECT_PASSWORD.equals(userInput)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "The password is correct! You found a new shredded note!");
                inventory.addItem(new Item("clue_duo", "Paper 3", "Clues from the Open Cipher Picture Puzzle"));
                stage.close();
            } else {
                showAlert(Alert.AlertType.ERROR, "Incorrect", "Wrong password, please try again.");
                passwordField.clear();
            }
        });

        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);
        root.getChildren().addAll(imageView, centerBox, submitButton);

        Scene scene = new Scene(root, 400, 400);
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}