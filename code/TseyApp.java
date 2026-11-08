package bag;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TseyApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane rootPane = new BorderPane();

        // 模拟一个简单的 inventoryUI.createUI() 返回 HBox
        HBox inventoryPane = createInventoryUI();

        // 设置属性
        inventoryPane.setMaxHeight(100);
        inventoryPane.setPrefHeight(100);
        inventoryPane.setStyle("-fx-background-color: lightgray;");
        BorderPane.setMargin(inventoryPane, new Insets(0));
        rootPane.setTop(inventoryPane);

        // 创建场景
        Scene scene = new Scene(rootPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Inventory UI 示例");
        primaryStage.show();
    }

    // 模拟的背包界面
    private HBox createInventoryUI() {
        HBox hbox = new HBox(10);
        hbox.setPadding(new Insets(10));
        hbox.getChildren().addAll(new Label("背包栏位 1"), new Label("背包栏位 2"));
        return hbox;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
