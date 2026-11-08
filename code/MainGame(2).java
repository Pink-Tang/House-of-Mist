package main;

import java.awt.Label;

import bag.InventoryUI;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import scenes.*;

public class MainGame extends Application {

    private InventoryUI inventoryUI;
    private Button facelessHouseButton; // 用于显示 Faceless House 的按钮

    @Override
    public void start(Stage primaryStage) {
        inventoryUI = new InventoryUI();  // 背包系统

        // 初始化 Faceless House 按钮
        facelessHouseButton = new Button("Faceless House");
        facelessHouseButton.setOnAction(e -> {
            Stage facelessStage = new Stage();
            Label label = new Label("Faceless House");
            label.getLocation();
            Scene scene = new Scene(new StackPane(), 300, 150);
            facelessStage.setScene(scene);
            facelessStage.setTitle("Faceless House");
            facelessStage.show();
        });
        facelessHouseButton.setVisible(false); // 初始时不显示按钮

        Button openBookPuzzle = new Button("打开图书谜题");
        openBookPuzzle.setOnAction(e -> {
            Stage stage = new Stage();
            ChooseBookImagePuzzle puzzle = new ChooseBookImagePuzzle(inventoryUI, stage);
            stage.setScene(puzzle.getScene());
            stage.setTitle("图书选择谜题");
            stage.show();
        });

        Button openDuodecimalPuzzle = new Button("打开十二进制谜题");
        openDuodecimalPuzzle.setOnAction(e -> {
            Stage stage = new Stage();
            DuodecimalPuzzle puzzle = new DuodecimalPuzzle(inventoryUI);
            puzzle.show(stage);
        });

        Button openPasswordPuzzle = new Button("打开密码图片谜题");
        openPasswordPuzzle.setOnAction(e -> {
            Stage stage = new Stage();
            PasswordImagePuzzle puzzle = new PasswordImagePuzzle(inventoryUI);
            puzzle.show(stage);
        });

        Button openFinalPuzzle = new Button("打开最终谜题（地下室）");
        openFinalPuzzle.setOnAction(e -> {
            Stage stage = new Stage();
            PuzzleGame puzzle = new PuzzleGame();
            puzzle.show(stage);
        });

        Button openTextInputPuzzle = new Button("打开地点输入谜题");
        openTextInputPuzzle.setOnAction(e -> {
            Stage stage = new Stage();
            TextInputPuzzle puzzle = new TextInputPuzzle(inventoryUI);
            puzzle.show(stage);
        });

        VBox controlPanel = new VBox(15, openBookPuzzle, openDuodecimalPuzzle, openPasswordPuzzle, openTextInputPuzzle, openFinalPuzzle);
        controlPanel.setStyle("-fx-padding: 15;");

        HBox mainLayout = new HBox(30, inventoryUI.createUI(), controlPanel);
        mainLayout.getChildren().add(facelessHouseButton); // 将 Faceless House 按钮添加到布局中

        Scene scene = new Scene(mainLayout, 1000, 500);

        primaryStage.setTitle("密室主界面");
        primaryStage.setScene(scene);
        primaryStage.show();

        // 检查是否使用了碎纸片3，并动态添加 Faceless House 按钮
        checkForClueDuoUsage(mainLayout);
    }

    private void checkForClueDuoUsage(HBox mainLayout) {
        // 定期检查标志位
        new Thread(() -> {
            while (true) {
                if (inventoryUI.isClueDuoUsed()) {
                    Platform.runLater(() -> {
                        facelessHouseButton.setVisible(true);
                        inventoryUI.isClueDuoUsed(); // 重置标志位
                    });
                }
                try {
                    Thread.sleep(500); // 每500毫秒检查一次
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}