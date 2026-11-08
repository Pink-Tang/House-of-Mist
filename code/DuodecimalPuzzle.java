// scenes/DuodecimalPuzzle.java
package scenes;
//parentRoom


import bag.InventoryUI;
import bag.Item;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import project.soundPlayer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class DuodecimalPuzzle {
    private static final String CORRECT_ANSWER = "10";
    private final InventoryUI inventory;

    public DuodecimalPuzzle(InventoryUI inventory) {
        this.inventory = inventory;
    }

    public void show(Stage stage) {
        stage.setTitle("Choose the right number");

        // 图片区域
        ImageView imageView;
        try {
            Image image = new Image(new FileInputStream("resources/clue2.jpg"), 400, 200, true, true);
            imageView = new ImageView(image);
        } catch (FileNotFoundException e) {
            imageView = new ImageView();
            System.out.println("图片加载失败，请检查路径：resources/clue2.jpg");
        }

        VBox imageBox = new VBox(imageView);
        imageBox.setAlignment(Pos.CENTER);
        imageBox.setPadding(new Insets(15, 0, 0, 0));

        // 按钮区域
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(30));

        for (String num : new String[]{"2", "4", "6", "10"}) {
            Button button = new Button(num);
            button.setPrefSize(80, 50);
            button.setStyle("-fx-background-color: #4682B4; -fx-text-fill: white;");
            button.setOnAction(e -> {
                if (num.equals(CORRECT_ANSWER)) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Correct! You found a new shredded note!");
                    soundPlayer.playSoundEffect("D:\\bgm\\correct.wav");//成功音效
                    inventory.addItem(new Item("clue_duo", "Paper 2", "Clues from a duodecimal puzzle"));
                    stage.close();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Incorrect", "Wrong number, please try again.");
                }
            });
            buttonBox.getChildren().add(button);
        }

        VBox root = new VBox(20, imageBox, buttonBox);
        root.setAlignment(Pos.TOP_CENTER);

        Scene scene = new Scene(root, 500, 400);
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
