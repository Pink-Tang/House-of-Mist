package scenes;
//livingRoom

import bag.InventoryUI;
import bag.Item;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import project.soundPlayer;

import java.io.FileInputStream;

public class ChooseBookImagePuzzle {

    private final Scene scene;

    public ChooseBookImagePuzzle(InventoryUI inventory, Stage stage) {
        HBox bookBox = new HBox(20);
        bookBox.setAlignment(Pos.CENTER);

        addBookButton(bookBox, "The Midnight Hour", "resources/book1.jpg", false, inventory, stage);
        addBookButton(bookBox, "Faceless House", "resources/book2.jpg", true, inventory, stage);
        addBookButton(bookBox, "Labyrinth of Night", "resources/book3.jpg", false, inventory, stage);

        VBox layout = new VBox(30, bookBox);
        layout.setAlignment(Pos.CENTER);
        scene = new Scene(layout, 600, 400);
    }

    public Scene getScene() {
        return scene;
    }

    private void addBookButton(HBox container, String title, String imagePath, boolean isCorrect, InventoryUI inventory, Stage stage) {
        ImageView view;
        try {
            Image image = new Image(new FileInputStream(imagePath), 150, 200, true, true);
            view = new ImageView(image);
        } catch (Exception e) {
            view = new ImageView();
            System.out.println("加载图书图片失败：" + imagePath);
        }

        Button button = new Button("", view);
        button.setOnAction(e -> {
            if (isCorrect) {
                showInfo("Success", "You found the key!");
                soundPlayer.playSoundEffect("D:\\bgm\\correct.wav");//成功音效
                inventory.addItem(new Item("key", "key", "Clues dropped from books"));
                stage.close(); // 关闭窗口表示谜题完成
            } else {
                showInfo("Try Again", "It's not the right book, try again!");
            }
        });

        container.getChildren().add(button);
    }

    private void showInfo(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}