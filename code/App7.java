package project;

import bag.InventoryUI;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import scenes.*;

import java.util.HashMap;
import java.util.Map;

public class App7 extends Application {
    
    private Scene mainScene;
    private Map<String, Parent> sceneCache = new HashMap<>();
    private Stage primaryStage;
    private TextArea storyBox;
    private InventoryUI inventoryUI; // 背包系统
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        Font.loadFont("file:///D:/resources/font/PressStart2P-Regular.ttf", 16);     
        // 初始化背包系统
        inventoryUI = new InventoryUI(); 
        // 只缓存一次开始界面
        sceneCache.put("start", createStartScreen());

        // 创建其他场景
        sceneCache.put("scene1", createScene("scene1", createBackgroundHBox("file:///D:/resources/images/scenes/room1.png")));
        sceneCache.put("scene2", createScene("scene2", createBackgroundHBox("file:///D:/resources/images/scenes/room2.png")));
        sceneCache.put("scene3", createScene("scene3", createBackgroundHBox("file:///D:/resources/images/scenes/room3.png")));
        sceneCache.put("scene4", createScene("scene4", createBackgroundHBox("file:///D:/resources/images/scenes/room4.png")));
        // 创建主场景容器
        mainScene = new Scene(sceneCache.get("start"), 400, 300);
        
        // 设置初始场景
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("House of Mist");
        primaryStage.getIcons().add(new Image("file:///D:/resources/images/items/key.png"));
        primaryStage.show();
    }
    
    private HBox createBackgroundHBox(String imageUrl) {
        HBox hbox = new HBox();
        hbox.setStyle("-fx-background-image: url('" + imageUrl + "');" +// 图像
                     "-fx-background-size: contain;" +
                     "-fx-background-repeat: no-repeat;" +
                     "-fx-background-position: center;");
        return hbox;
    }
    
    private Parent createStartScreen() {
        // 主容器
        StackPane root = new StackPane();
        
        // 背景设置
        ImageView background = new ImageView(new Image("file:///D:/resources/images/scenes/start.png"));
        background.setFitWidth(400);  // 匹配场景宽度
        background.setFitHeight(300);
        background.setOpacity(0.7);  // 半透明效果
        
        // 游戏标题
        Label title = new Label("HOUSE OF MIST");
        title.setStyle("-fx-font-family: 'Press Start 2P', cursive; " +  // 像素字体
                     "-fx-font-size: 24px; " +
                     "-fx-text-fill: #e74c3c; " +  // 红色像素文字
                     "-fx-effect: dropshadow(three-pass-box, #2c3e50, 5, 0, 0, 0);");
        StackPane.setAlignment(title, Pos.TOP_CENTER);
        StackPane.setMargin(title, new Insets(50, 0, 0, 0));

        // 开始按钮
        Button startButton = new Button("START");
        startButton.setStyle("-fx-font-family: 'Press Start 2P', cursive; " +
                           "-fx-font-size: 16px; " +
                           "-fx-background-color: #3498db; " +
                           "-fx-text-fill: white; " +
                           "-fx-padding: 8px 16px; " +
                           "-fx-border-color: #2980b9; " +
                           "-fx-border-width: 3px;");
        StackPane.setAlignment(startButton, Pos.BOTTOM_CENTER);
        StackPane.setMargin(startButton, new Insets(0, 0, 50, 0));
        
        // 按钮点击效果
        startButton.setOnMousePressed(e -> {
            startButton.setStyle(startButton.getStyle() + 
                               "-fx-translate-y: 2px; " +  // 按下下沉效果
                               "-fx-background-color: #2980b9;");
        });
        startButton.setOnMouseReleased(e -> {
            startButton.setStyle(startButton.getStyle()
                               .replace("-fx-translate-y: 2px; ", "")
                               .replace("-fx-background-color: #2980b9;", ""));
            soundPlayer.playBackgroundMusic("D:\\bgm\\backGroundMusic.wav");//音乐
            sceneCache.put("start", createScene("start", createBackgroundHBox("file:///D:/resources/images/scenes/start.png")));
            switchScene("start");  // 跳转到开场
        });

        root.getChildren().addAll(background, title, startButton);
        return root;
    }
    
    private Pane createScene(String title, HBox background) {
        // 主容器
        BorderPane rootPane = new BorderPane();
        
        // 设置背景
        StackPane backgroundPane = new StackPane(background);
        backgroundPane.setStyle("-fx-background-color: black;"); // 添加黑色背景以防图片未覆盖区域
        if(title.equals("start"))
            backgroundPane.getChildren().add(TheHouse());
        else if(title.equals("scene1"))
            backgroundPane.getChildren().add(JackRoom());
        else if(title.equals("scene2"))
            backgroundPane.getChildren().add(ParentRoom());
        else if(title.equals("scene3"))
            backgroundPane.getChildren().add(LivingRoom());
        else 
            backgroundPane.getChildren().add(FinalRoom());
        rootPane.setCenter(backgroundPane);
        
        // 创建左右箭头按钮容器
        HBox leftButtonBox = new HBox();
        leftButtonBox.setAlignment(Pos.CENTER_LEFT);
        leftButtonBox.setStyle("-fx-padding: 0 0 0 20;"); // 左侧内边距
        
        HBox rightButtonBox = new HBox();
        rightButtonBox.setAlignment(Pos.CENTER_RIGHT);
        rightButtonBox.setStyle("-fx-padding: 0 20 0 0;"); // 右侧内边距
        
        // 根据场景标题确定目标场景
        String leftTarget = title.equals("start") ? "start" : 
        		             title.equals("scene1") ? "scene1" : 
                              title.equals("scene2") ? "scene1" :
                                title.equals("scene3") ? "scene2" :
                                	title.equals("scene4") ? "scene3" :"scene4"
                                	;
                          
        
        String rightTarget = title.equals("start") ? "scene1" :
        		             title.equals("scene1") ? "scene2" : 
                              title.equals("scene2") ? "scene3" :
                        	   title.equals("scene3") ? "scene4" :
                        		   title.equals("scene4") ? "scene4" :"scene4"
                           	    ;
        
        // 左箭头按钮
        Button leftArrow = createPixelArrowButton("left");
        leftArrow.setOnAction(e -> switchScene(leftTarget));
        leftButtonBox.getChildren().add(leftArrow);
        
        // 右箭头按钮
        Button rightArrow = createPixelArrowButton("right");       
        rightArrow.setOnAction(e ->{ if(title.equals("start")) 
        	soundPlayer.playSoundEffect("D:\\bgm\\openDoor.wav");
        switchScene(rightTarget);});
        rightButtonBox.getChildren().add(rightArrow);
        
        // 将按钮容器放在左右两侧
        rootPane.setLeft(leftButtonBox);
        rootPane.setRight(rightButtonBox);
        
        return rootPane;
    }
    
    private Button createPixelArrowButton(String direction) {
        Button button = new Button();
        button.setPrefSize(40, 40);
        
        StringBuilder css = new StringBuilder()
            .append("-fx-background-color: #333333; ")
            .append("-fx-border-color: #666666; ")
            .append("-fx-border-width: 2px; ")
            .append("-fx-border-style: solid; ")
            .append("-fx-shape: ");
        
        if (direction.equals("left")) {
            css.append("\"M 20 0 L 0 10 L 20 20 Z\"");
        } else {
            css.append("\"M 0 0 L 20 10 L 0 20 Z\"");
        }
        
        css.append("; -fx-cursor: hand;");
        
        final String finalCss = css.toString();
        button.setStyle(finalCss);
        
        button.setOnMousePressed(e -> button.setStyle(finalCss + 
            " -fx-translate-y: 1px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 1, 0, 0, 0);"));
        button.setOnMouseReleased(e -> button.setStyle(finalCss));
        
        return button;
    }
    
    private void switchScene(String sceneId) {
        Parent root = sceneCache.get(sceneId);
        if (root != null) {
            // 当前根节点淡出
            FadeTransition fadeOut = new FadeTransition(Duration.millis(300), mainScene.getRoot());
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            
            fadeOut.setOnFinished(e -> {
                // 切换根节点
                mainScene.setRoot(root);
                // 新根节点淡入
                FadeTransition fadeIn = new FadeTransition(Duration.millis(300), root);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();
            });
            
            fadeOut.play();
        }
    }
    
    public Pane TheHouse() {
        // 主容器改用BorderPane，更适合这种布局
        BorderPane rootPane = new BorderPane();

        // 1. 创建房间内容（居中）
        VBox houseContent = new VBox(10);
        houseContent.setStyle("-fx-padding: 30; -fx-alignment: center;");
        Label message = new Label("You're going to knock.");
        message.setStyle("-fx-text-fill: white;"+"-fx-font-size: 26px; " );
        houseContent.getChildren().add(message);

        // 2. 创建底部对话框（storyBox）
        storyBox = createStoryBox();
        rootPane.setBottom(storyBox); // 固定在底部

        // 3. 创建敲门按钮
        Button startButton = new Button("Knock door");
        startButton.setStyle(buttonStyle());

        // 将按钮放在对话框上方
        StackPane buttonContainer = new StackPane(startButton);
        buttonContainer.setAlignment(Pos.TOP_CENTER);
        StackPane.setMargin(startButton, new Insets(10, 0, 0, 0));
        
        // 创建容器包装storyBox和按钮
        StackPane bottomContainer = new StackPane();
        bottomContainer.getChildren().addAll(storyBox, buttonContainer);
        rootPane.setBottom(bottomContainer);

        // 设置主内容区域
        rootPane.setCenter(houseContent);

        // 按钮点击效果
        startButton.setOnMousePressed(e -> {
            startButton.setStyle(startButton.getStyle() + 
                               "-fx-translate-y: 2px; " +
                               "-fx-background-color: #2980b9;");
        });

        startButton.setOnMouseReleased(e -> {
            startButton.setStyle(startButton.getStyle()
                               .replace("-fx-translate-y: 2px; ", "")
                               .replace("-fx-background-color: #2980b9;", ""));
            soundPlayer.playSoundEffect("D:\\bgm\\knockDoor.wav");
            appendStory(storyBox,"","Ken stands in front of this strange villa." ,
                    "'Jack hasn't come to class in weeks... I can't reach him.'" ,
                    "'As his high school teacher, I have to make sure he was safe. '" ,
                    "'Ms. Martha gave me this address, and if it's correct... This should be the place.'");
            message.setText("Click pixel arrow button to open door");
        });

        return rootPane;
    }
    
    public Pane JackRoom() {
        // 使用 BorderPane 作为主容器
        BorderPane rootPane = new BorderPane();

        // 房间内容放在中间
        VBox jackContent = new VBox(10);
        jackContent.setStyle("-fx-padding: 30; -fx-alignment: center;");
        Label message = new Label("This should be Jack's room ......");
        message.setStyle("-fx-text-fill: white;"+"-fx-font-size: 26px; " );
        jackContent.getChildren().add(message);

        // 创建底部对话框
        TextArea storyBox = createStoryBox();
        
        // 创建按钮    
        Button findSketchbook = new Button("View brochure");
        Button solvePuzzle = new Button("View box");
        Button openTextInputPuzzle = new Button("View closet");
        Button openInventory = new Button("backpack");
        
        // 应用按钮样式
        String buttonStyle = buttonStyle();
        findSketchbook.setStyle(buttonStyle);
        solvePuzzle.setStyle(buttonStyle);
        openTextInputPuzzle.setStyle(buttonStyle);
        openInventory.setStyle(buttonStyle);

        // 按钮事件处理
        findSketchbook.setOnAction(e -> {
            message.setText("The picture book depicts: closed doors, crying dolls, faceless parents.");
            appendStory(storyBox, 
                "Suddenly, you hear footsteps behind you.",
                "A girl stepped out of the shadows, 'Hi, I'm Lisa, Jack's classmate.'",
                "'I was worried about him too, so I came here. Maybe we can find him together.'",
                "You turn to Lisa.",
                "'Jack's been weird lately,' Lisa says, 'he's always mentioning this house and some weird dream.'",
                "'I think he might be in some kind of trouble, we need to find him.'"
            );
        });
        
        solvePuzzle.setOnAction(e -> {
            storyBox.setText("");
            Stage stage = new Stage();
            PasswordImagePuzzle puzzle = new PasswordImagePuzzle(inventoryUI);
            puzzle.show(stage);
        });
        
        openTextInputPuzzle.setOnAction(e -> {
            Stage stage = new Stage();
            TextInputPuzzle puzzle = new TextInputPuzzle(inventoryUI);
            puzzle.show(stage);
        });

        openInventory.setOnAction(e -> {
            // 创建并显示背包窗口
            Stage inventoryStage = new Stage();
            inventoryStage.setTitle("backpack");
            Pane inventoryPane = inventoryUI.createUI(); // 假设InventoryUI有一个createUI方法返回背包界面
            Scene inventoryScene = new Scene(inventoryPane, 400, 300);
            inventoryStage.setScene(inventoryScene);
            inventoryStage.show();
        });

        // 创建按钮容器并设置布局
        HBox buttonContainer = new HBox(10, findSketchbook, solvePuzzle, openTextInputPuzzle, openInventory);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(10));
        
        // 创建底部容器
        VBox bottomContainer = new VBox();
        bottomContainer.getChildren().addAll(buttonContainer, storyBox);
        rootPane.setBottom(bottomContainer);

        // 设置主内容区域
        rootPane.setCenter(jackContent);
        
        return rootPane;
    }

    public Pane ParentRoom() {
        // 使用 BorderPane 作为主容器
        BorderPane rootPane = new BorderPane();

        // 房间内容放在中间
        VBox parentContent = new VBox(10);
        parentContent.setStyle("-fx-padding: 30; -fx-alignment: center;");
        Label message = new Label("You enter Jack's parents' room and Lisa whispers that Jack can only talk in his dreams ......");
        message.setStyle("-fx-text-fill: white;"+"-fx-font-size: 26px; ");
        parentContent.getChildren().add(message);

        // 创建底部对话框
        TextArea storyBox = createStoryBox();
        
        // 创建按钮    
        Button findRecorder = new Button("Recording pen");
        Button checkCloset = new Button("View closet");
        Button talkWithLisa = new Button("Talk to Lisa");
        Button openDuodecimalPuzzle = new Button("Check the secret door");
        Button openInventory = new Button("backpack");
        
        // 应用按钮样式
        String buttonStyle = buttonStyle();
        findRecorder.setStyle(buttonStyle);
        checkCloset.setStyle(buttonStyle);
        talkWithLisa.setStyle(buttonStyle);
        openDuodecimalPuzzle.setStyle(buttonStyle);
        openInventory.setStyle(buttonStyle);
         
        talkWithLisa.setDisable(true);
        openDuodecimalPuzzle.setDisable(true);

        openDuodecimalPuzzle.setOnAction(e -> {
            Stage stage = new Stage();
            DuodecimalPuzzle puzzle = new DuodecimalPuzzle(inventoryUI);
            puzzle.show(stage);
        });

        // 按钮事件处理
        findRecorder.setOnAction(e -> {
            message.setText("You find a recorder.");
            appendStory(storyBox, 
                "You pick up the recorder on the desk and press play.",
                "'You must be perfect, no flaws.' A stern voice comes from what appears to be Jack's father.",
                "'But dad, I'm so tired...' Jack's voice was weak and shaky.",
                "'There are no excuses! You have to succeed like me!' The recording ends in a cacophony of noise."
            );
        });

        checkCloset.setOnAction(e -> {
            storyBox.setText("");
            message.setText("You open the closet.");
            appendStory(storyBox, 
                "You find a lot of neat suits hanging inside.",
                "Deep in the closet, you find a secret door.",
                "Lisa says next to me, 'This must lead somewhere else, maybe the living room.'"
            );
            talkWithLisa.setDisable(false);
        });

        talkWithLisa.setOnAction(e -> {
            storyBox.setText("");
            message.setText("You look to Lisa.");
            appendStory(storyBox, 
                "'This family must be under a lot of pressure,' Lisa says with a frown.",
                "'No wonder Jack is always so quiet. We've got to find him and help him out of this.'"
            );
            openDuodecimalPuzzle.setDisable(false);
        });

        openInventory.setOnAction(e -> {
            // 创建并显示背包窗口
            Stage inventoryStage = new Stage();
            inventoryStage.setTitle("backpack");
            Pane inventoryPane = inventoryUI.createUI(); // 假设InventoryUI有一个createUI方法返回背包界面
            Scene inventoryScene = new Scene(inventoryPane, 400, 300);
            inventoryStage.setScene(inventoryScene);
            inventoryStage.show();
        });

        // 创建按钮容器并设置布局
        HBox buttonContainer = new HBox(10, findRecorder, checkCloset, talkWithLisa, openDuodecimalPuzzle, openInventory);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(10));
        
        // 创建底部容器
        VBox bottomContainer = new VBox();
        bottomContainer.getChildren().addAll(buttonContainer, storyBox);
        rootPane.setBottom(bottomContainer);

        // 设置主内容区域
        rootPane.setCenter(parentContent);
        
        return rootPane;
    }
    
    public Pane LivingRoom() {
        // 使用 BorderPane 作为主容器
        BorderPane rootPane = new BorderPane();

        // 房间内容放在中间
        VBox livingContent = new VBox(10);
        livingContent.setStyle("-fx-padding: 30; -fx-alignment: center;");
        Label message = new Label("You enter the spacious living room. It is furnished with a television and some furniture.");
        message.setStyle("-fx-text-fill: white;"+"-fx-font-size: 26px; ");
        livingContent.getChildren().add(message);

        // 创建底部对话框
        TextArea storyBox = createStoryBox();
        
        // 创建按钮    
        Button inspectTV = new Button("Check the TV");
        Button talkWithLisa = new Button("Talk to Lisa");
        Button solvePuzzle = new Button("View Books");
        Button detect = new Button("Deduce");
        Button openInventory = new Button("backpack");
        
        // 应用按钮样式
        String buttonStyle = buttonStyle();
        inspectTV.setStyle(buttonStyle);
        talkWithLisa.setStyle(buttonStyle);
        solvePuzzle.setStyle(buttonStyle);
        detect.setStyle(buttonStyle);
        openInventory.setStyle(buttonStyle);
         
        talkWithLisa.setDisable(true);
        solvePuzzle.setDisable(true);      
        detect.setDisable(true);  

        inspectTV.setOnAction(e -> {
            message.setText("You turn on the TV and the screen starts to glow");
            storyBox.setText("");
            appendStory(storyBox, 
                "A face appears on the screen - it's Jack!",
                "Jack's voice came out of the TV:Thank you for dreaming with me ...... Now, stay.",
                "The TV suddenly glows intensely and you feel a wave of vertigo..."
            );
            talkWithLisa.setDisable(false);
        });

        talkWithLisa.setOnAction(e -> {
            message.setText("We are at Jack's level of consciousness ......");
            storyBox.setText("");
            appendStory(storyBox, 
                "'I'm getting worried about Jack,'' Lisa says.",
                "'We have to find him and help him come back to reality.'"
            );
            solvePuzzle.setDisable(false);
        });

        solvePuzzle.setOnAction(e -> {
            Stage stage = new Stage();
            ChooseBookImagePuzzle puzzle = new ChooseBookImagePuzzle(inventoryUI, stage);
            stage.setScene(puzzle.getScene());
            stage.setTitle("Book Choices");
            stage.show();   
            storyBox.setText("");
            appendStory(storyBox, 
                "You can't think of an answer.",
                "'Jack has a habit of writing down his passwords, maybe the answer is on the scrap of paper.' Lisa says."
            );
            detect.setDisable(false);
        });
        
        detect.setOnAction(e -> {
            storyBox.setText("");
            appendStory(storyBox, 
                "You've collected three pieces of shredded paper.",
                "faceless book."
            );
        });

        openInventory.setOnAction(e -> {
            // 创建并显示背包窗口
            Stage inventoryStage = new Stage();
            inventoryStage.setTitle("backpack");
            Pane inventoryPane = inventoryUI.createUI(); // 假设InventoryUI有一个createUI方法返回背包界面
            Scene inventoryScene = new Scene(inventoryPane, 400, 300);
            inventoryStage.setScene(inventoryScene);
            inventoryStage.show();
        });

        // 创建按钮容器并设置布局
        HBox buttonContainer = new HBox(10, inspectTV, talkWithLisa, solvePuzzle, detect, openInventory);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(10));
        
        // 创建底部容器
        VBox bottomContainer = new VBox();
        bottomContainer.getChildren().addAll(buttonContainer, storyBox);
        rootPane.setBottom(bottomContainer);

        // 设置主内容区域
        rootPane.setCenter(livingContent);
        
        return rootPane;
    }
    
    public Pane FinalRoom() {
        // 使用 BorderPane 作为主容器
        BorderPane rootPane = new BorderPane();

        // 房间内容放在中间
        VBox finalContent = new VBox(10);
        finalContent.setStyle("-fx-padding: 30; -fx-alignment: center;");
        Label message = new Label("You use the key to enter the basement. It's filled with all sorts of old stuff and lab equipment.");
        message.setStyle("-fx-text-fill: white;"+"-fx-font-size: 26px; ");
        finalContent.getChildren().add(message);

        // 创建底部对话框
        TextArea storyBox = createStoryBox();
        
        // 创建按钮    
        Button inspect = new Button("View equipment");
        Button talkWithLisa = new Button("Talk to Lisa");
        Button check = new Button("View Diary");
        Button openInventory = new Button("backpack");
        
        // 应用按钮样式
        String buttonStyle = buttonStyle();
        inspect.setStyle(buttonStyle);
        talkWithLisa.setStyle(buttonStyle);
        check.setStyle(buttonStyle);
        openInventory.setStyle(buttonStyle);
        
        talkWithLisa.setDisable(true);
        
        inspect.setOnAction(e -> {
            message.setText("You discover a complex machine.");
            storyBox.setText("");
            appendStory(storyBox, 
                "It is labeled 'Consciousness Projection System V3.0'.",
                "'It's the same device Jack uses to explore his dreams,' Lisa says, 'but it looks dangerous.'"
            );
        });

        check.setOnAction(e -> {            
            storyBox.setText("");
            appendStory(storyBox, 
                "You flip through a diary of Jack's dream experiments.",
                "'I finally made it,' the latest one reads, 'but I can't distinguish between reality and dreams anymore.'",
                "'Maybe I should stay in this perfect world forever...'",
                "'Lisa, I'm sorry, I can't go back.'"
            );
            talkWithLisa.setDisable(false);
        });
        
        talkWithLisa.setOnAction(e -> {
            message.setText("Lisa's expression turns serious.");
            storyBox.setText("");
            appendStory(storyBox, 
                "'Jack is in too deep already,' Lisa says, 'the system is eating away at his consciousness.'",
                "'We have to shut it down, but that will cut him off from reality forever.'",
                "''Or... I can take his place and bring him back to reality.'"
            );
            choose(storyBox);
        });

        openInventory.setOnAction(e -> {
            // 创建并显示背包窗口
            Stage inventoryStage = new Stage();
            inventoryStage.setTitle("backpack");
            Pane inventoryPane = inventoryUI.createUI(); // 假设InventoryUI有一个createUI方法返回背包界面
            Scene inventoryScene = new Scene(inventoryPane, 400, 300);
            inventoryStage.setScene(inventoryScene);
            inventoryStage.show();
        });

        // 创建按钮容器并设置布局
        HBox buttonContainer = new HBox(10, inspect, check, talkWithLisa, openInventory);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(10));
        
        // 创建底部容器
        VBox bottomContainer = new VBox();
        bottomContainer.getChildren().addAll(buttonContainer, storyBox);
        rootPane.setBottom(bottomContainer);

        // 设置主内容区域
        rootPane.setCenter(finalContent);
        
        return rootPane;
    }
    
    public void choose(TextArea storyBox) {
        Stage chooseStage = new Stage();  
        chooseStage.setTitle("option");

        // 创建标签
        Label label = new Label("You have to make a choice:");

        Button Jack = new Button("Shut down");
        Button Lisa = new Button("Save Jack");
        Jack.setStyle(buttonStyle());
        Lisa.setStyle(buttonStyle());
        
        // 设置按钮点击事件
        Jack.setOnAction(e -> {
            storyBox.setText("");
            appendStory(storyBox, 
                "You choose to shut down the system. The machine makes a shrill noise and then stops running.",
                "After a moment of vertigo, you find yourself back in the real world.",
                "Jack is still unconscious, but the doctors say he's stabilized.",
                "You know, his consciousness is trapped in that dream forever.",
                "Game over. You saved yourself, but lost Jack."
            );
        });
        Lisa.setOnAction(e -> {
            storyBox.setText("");
            appendStory(storyBox, 
                "You choose to have Lisa replace Jack. she smiles as she walks towards the machine.",
                "'It's for the best,' she says, 'Jack needs to come back to reality.'",
                "The machine starts up and Lisa's figure fades away.",
                "After a flash of bright light, you find yourself and Jack lying on the floor of the lab.",
                "Game over. You saved Jack, but lost Lisa."
            );
        });

        // 创建垂直布局
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(label, Jack, Lisa);

        Scene scene = new Scene(vbox, 350, 150);
        chooseStage.setScene(scene);
        chooseStage.show();
    }
    
    public String buttonStyle() {
        return "-fx-font-family: 'Press Start 2P'; " +
               "-fx-font-size: 16px; " +
               "-fx-background-color: #3498db; " +
               "-fx-text-fill: white; " +
               "-fx-padding: 8px 16px; " +
               "-fx-border-color: #2980b9; " +
               "-fx-border-width: 3px;";
    }
    
    public TextArea createStoryBox() {
        TextArea storyBox = new TextArea();
        storyBox.setWrapText(true);
        storyBox.setEditable(false);
        storyBox.setPrefHeight(200);
        storyBox.setStyle(
            "-fx-font-family: 'Press Start 2P'; " +
            "-fx-font-size: 16px; " +
            "-fx-background-color: #1a1a1a;" +
            "-fx-border-color: #4a4a4a #2a2a2a #2a2a2a #4a4a4a;" +
            "-fx-border-width: 4px;" +
            "-fx-effect: innershadow(three-pass-box, rgba(0,0,0,0.8), 8, 0, 0, 0);" +
            "-fx-background-insets: 0, 2;" +
            "-fx-background-radius: 0;" +
            "-fx-border-radius: 0;" +
            "-fx-padding: 15px;"
        );
        return storyBox;
    }

    // 辅助方法：添加故事文本
    private void appendStory(TextArea storyBox, String... lines) {
        for (String line : lines) {
            storyBox.appendText("\n" + line);
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}