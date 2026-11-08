package project;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class soundTest extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 创建按钮
        Button bgmButton = new Button("backGroundMusic");
        Button openButton = new Button("openDoor");
        Button clickButton = new Button("click");
        Button successButton = new Button("correct");
        Button knockButton = new Button("knockDoor");

        // 绑定事件
        bgmButton.setOnAction(e -> soundPlayer.playBackgroundMusic("D:\\bgm\\backGroundMusic.wav"));
        openButton.setOnAction(e -> soundPlayer.playSoundEffect("D:\\bgm\\openDoor.wav"));
        clickButton.setOnAction(e -> soundPlayer.playSoundEffect("D:\\bgm\\click.wav"));
        successButton.setOnAction(e -> soundPlayer.playSoundEffect("D:\\bgm\\correct.wav"));
        knockButton.setOnAction(e -> soundPlayer.playSoundEffect("D:\\bgm\\knockDoor.wav"));

        // 垂直排列按钮
        VBox root = new VBox(10); // 10 是按钮间距
        root.getChildren().addAll(bgmButton, openButton, clickButton, successButton, knockButton);

        // 创建场景和窗口
        Scene scene = new Scene(root, 300, 250);
        primaryStage.setTitle("音效测试 - JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}