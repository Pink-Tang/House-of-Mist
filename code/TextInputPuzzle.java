package scenes;  // jackRoom

import javafx.geometry.Insets;
import bag.InventoryUI;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import project.soundPlayer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import bag.Item;

public class TextInputPuzzle {

    private static final String CORRECT_ANSWER = "PARK";
    private final InventoryUI inventory;

    public TextInputPuzzle(InventoryUI inventory) {
        this.inventory = inventory;
    }
    public void show(Stage primaryStage) {
        primaryStage.setTitle("location");

        // 图片部分
        ImageView imageView = new ImageView();
        try {
            Image image = new Image(new FileInputStream("resources/clue3.jpg"));
            imageView.setImage(image);
            imageView.setFitWidth(280);
            imageView.setPreserveRatio(true);
        } catch (FileNotFoundException e) {
            System.out.println("No pictures found：resources/clue3.jpg");
        }

        // 提示文字
        Label clueLabel = new Label("The place I went to secretly with my friends");
        clueLabel.setFont(Font.font("Arial", 18));

        // 输入框
        TextField inputField = new TextField();
        inputField.setPromptText("Please enter a location");
        inputField.setMaxWidth(300);
        inputField.setFont(Font.font("Arial", 16));

        // 确认按钮
        Button confirmButton = new Button("OK");
        confirmButton.setFont(Font.font("Arial", 16));
        confirmButton.setStyle("-fx-background-color: #3c8250; -fx-text-fill: white;");

        // 事件处理
        confirmButton.setOnAction(e -> {
            String userInput = inputField.getText().trim().toUpperCase();
            if (CORRECT_ANSWER.equals(userInput)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Correct! You found a new shredded note!");
                inventory.addItem(new Item("clue_duo", "Paper 1", "Clues from location inputs"));
                soundPlayer.playSoundEffect("D:\\bgm\\correct.wav");//成功音效
                primaryStage.close();
            } else {
                showAlert(Alert.AlertType.ERROR, "Wrong", "Wrong answer, please try again.");
                inputField.clear();
            }
        });
    

        VBox centerBox = new VBox(15);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(20));
        centerBox.getChildren().addAll(clueLabel, inputField, confirmButton);

        VBox root = new VBox(20);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(imageView, centerBox);

        Scene scene = new Scene(root, 500, 420);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
