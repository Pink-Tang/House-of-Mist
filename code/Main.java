
package bag;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import scenes.DuodecimalPuzzle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        InventoryUI inventoryUI = new InventoryUI();
     // 初始化物品
        inventoryUI.addItem(new Item("apple", "Apple", "A fresh red apple"));
        inventoryUI.addItem(new Item("knife", "Knife", "A sharp blade for cutting"));
        inventoryUI.addItem(new Item("stick", "Stick", "A strong wooden stick"));
        inventoryUI.addItem(new Item("stone", "Stone", "A rough-edged stone"));

        // 创建 UI 和按钮
        BorderPane root = (BorderPane) inventoryUI.createUI();

        Button openPuzzleButton = new Button("打开父母房间谜题");
        openPuzzleButton.setOnAction(e -> {
            DuodecimalPuzzle puzzle = new DuodecimalPuzzle(inventoryUI);
            Stage puzzleStage = new Stage();
            puzzle.show(puzzleStage);
        });

        root.setBottom(openPuzzleButton);
        BorderPane.setMargin(openPuzzleButton, new Insets(10));
        
        Button openDuoPuzzleButton = new Button("打开十二进制谜题");
        openDuoPuzzleButton.setOnAction(e -> {
        	DuodecimalPuzzle duoPuzzle = new DuodecimalPuzzle(inventoryUI);
            Stage duoStage = new Stage();
            duoPuzzle.show(duoStage);
        });

        root.setBottom(openDuoPuzzleButton);
        BorderPane.setMargin(openDuoPuzzleButton, new Insets(10));

        Scene scene = new Scene(root, 800, 450);
        primaryStage.setTitle("Inventory System + 谜题");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
       