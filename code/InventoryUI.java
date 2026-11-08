package bag;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import scenes.FacelessHousePuzzle;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;

import java.util.*;

public class InventoryUI {
    private final GridPane itemGrid = new GridPane();
    private final Label detailLabel = new Label("Select an item");
    private final Button useButton = new Button("Use");
    private final Button dropButton = new Button("Discard");
    private BorderPane inventoryPane;
    private VBox itemsContainer;

    private final List<Item> items = new ArrayList<>();
    private Button facelessRoomButton; // 用于显示 Faceless Room 的按钮

    public Pane createUI() {
        BorderPane root = new BorderPane();

        itemGrid.setHgap(10);
        itemGrid.setVgap(10);
        itemGrid.setPadding(new Insets(10));

        VBox rightPanel = new VBox(10);
        rightPanel.setPadding(new Insets(10));
        rightPanel.getChildren().addAll(detailLabel, useButton, dropButton);

        useButton.setDisable(true);
        dropButton.setDisable(true);

        root.setLeft(itemGrid);
        root.setCenter(rightPanel);

       

        updateGrid();

        return root;
    }

    private void updateGrid() {
        itemGrid.getChildren().clear();
        if (items.isEmpty()) {
            // 如果没有物品，至少显示一个提示
            Label hint = new Label("（The backpack is empty.）");
            itemGrid.add(hint, 0, 0);
        }

        int cols = 4;

        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            Button btn = new Button(item.getName());
            btn.setPrefSize(100, 60);

            int col = i % cols;
            int row = i / cols;
            itemGrid.add(btn, col, row);

            // Show detail
            btn.setOnAction(e -> selectItem(item));

            // Drag start
            btn.setOnDragDetected(e -> {
                Dragboard db = btn.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putString(item.getId());
                db.setContent(content);
                db.setDragView(btn.snapshot(null, null));
                e.consume();
            });

            // Drag over
            btn.setOnDragOver(e -> {
                if (e.getGestureSource() != btn && e.getDragboard().hasString()) {
                    e.acceptTransferModes(TransferMode.MOVE);
                }
                e.consume();
            });

            // Drop
            btn.setOnDragDropped(e -> {
                Dragboard db = e.getDragboard();
                if (db.hasString()) {
                    Item sourceItem = findItemById(db.getString());
                    Item targetItem = item;
                    tryCombine(sourceItem, targetItem);
                    updateGrid();
                    e.setDropCompleted(true);
                } else {
                    e.setDropCompleted(false);
                }
                e.consume();
            });

            // Context menu
            ContextMenu contextMenu = new ContextMenu();
            MenuItem use = new MenuItem("Use");
            MenuItem drop = new MenuItem("Discard");
            MenuItem combine = new MenuItem("Try Combine");

            use.setOnAction(e -> {
                if ("Shredded Paper 3".equals(item.getId())) {
                    // 启用 Faceless Room 按钮
                    facelessRoomButton.setDisable(false);
                } else {
                    System.out.println("Used: " + item.getName());
                }
            });

            drop.setOnAction(e -> {
                items.remove(item);
                updateGrid();
            });
            combine.setOnAction(e -> detailLabel.setText("Drag another item here to combine"));

            contextMenu.getItems().addAll(use, combine, drop);

            btn.setOnContextMenuRequested(e -> contextMenu.show(btn, e.getScreenX(), e.getScreenY()));
        }
    }

    private void selectItem(Item item) {
        detailLabel.setText("Name: " + item.getName() + "\nDescription: " + item.getDescription());
        useButton.setDisable(false);
        dropButton.setDisable(false);

        useButton.setOnAction(e -> {
            System.out.println("Used: " + item.getName());

            // 特定物品“碎纸片3”的特殊行为
            if ("Paper 3".equals(item.getName())) {
                // 方案一：在背包界面下方显示信息
                detailLabel.setText("facelessroom");

                // 方案二：弹窗提示
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("hint");
                alert.setHeaderText(null);
                alert.setContentText("faceless book");
                alert.showAndWait();
            }
           
        });

        dropButton.setOnAction(e -> {
            items.remove(item);
            updateGrid();
            detailLabel.setText("Select an item");
            useButton.setDisable(true);
            dropButton.setDisable(true);
        });
    }

    private Item findItemById(String id) {
        for (Item item : items) {
            if (item.getId().equals(id)) return item;
        }
        return null;
    }

    private void tryCombine(Item item1, Item item2) {
        if (item1 == null || item2 == null || item1 == item2) return;

        String comboKey = item1.getId() + "+" + item2.getId();
        String reverseKey = item2.getId() + "+" + item1.getId();

        String resultId = null;
        switch (comboKey) {
            case "knife+apple":
            case "apple+knife":
                resultId = "peeled_apple";
                break;
            case "stick+stone":
            case "stone+stick":
                resultId = "axe";
                break;
        }

        if (resultId != null) {
            System.out.println("Combined to create: " + resultId);
            items.remove(item1);
            items.remove(item2);
            items.add(new Item(resultId, resultId.replace("_", " ").toUpperCase(), "A newly combined item"));
        } else {
            System.out.println("Combination failed");
        }
    }

    public void addItem(Item item) {
        items.add(item);
        updateGrid();
    }

    public List<Item> getItems() {
        return items;
    }

    public boolean isClueDuoUsed() {
        // TODO Auto-generated method stub
        return false;
    }
}