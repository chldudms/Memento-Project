import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane;
import javafx.util.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ToDoPage {
    private StackPane layout;
    private VBox checklistContainer; // To hold the checklist items
    private ImageView addImageView;  // ImageView for the add button
    private VBox mainLayout; // Add mainLayout to manage the layout
    private Label dateLabel; // Label to display the current date and time

    public ToDoPage() {
        // Initialize layout and container for checklists
        layout = new StackPane();
        checklistContainer = new VBox(10); // 10 pixels spacing between items
        checklistContainer.setAlignment(Pos.CENTER); // Align checklist to the center

        // Load the image for the add button
        Image addImage = null;
        try {
            addImage = new Image("styles/plus.png");
        } catch (Exception e) {
            System.out.println("Image loading failed: " + e.getMessage());
        }

        if (addImage != null) {
            addImageView = new ImageView(addImage);
            addImageView.setFitWidth(40);  // 이미지 너비 조정
            addImageView.setFitHeight(35);  // 이미지 높이 조정
            addImageView.setOnMouseClicked(e -> addChecklistItem()); // 이미지 클릭 시 체크리스트 항목 추가
        } else {
            System.out.println("Image could not be loaded."); // 이미지가 null일 경우 메시지 출력
        }

        // Create a VBox for the main layout
        mainLayout = new VBox(8); // Use VBox for vertical layout
        mainLayout.setAlignment(Pos.TOP_CENTER); // Align main layout to the top center

        // Create a border pane for the title box
        BorderPane titleBox = new BorderPane();
        Label titleLabel = new Label("To Do"); // Title label
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-padding: 10; -fx-text-fill: white;"); // Title styling
        titleBox.setCenter(titleLabel); // Center the label in the title box
        titleBox.setStyle("-fx-background-color: #F875AA; -fx-border-width: 0; -fx-pref-height: 30; -fx-pref-width: 140;"); // Box styling

        // Create and configure the date label
        dateLabel = new Label(); // Label for displaying the current date
        dateLabel.setStyle("-fx-font-size: 25px; -fx-text-fill: #FF3EA5; -fx-text-weight: bold;"); // 스타일 설정
        updateDateTime(); // Initialize with the current date and time

        // Create a ScrollPane for the checklist container
        ScrollPane scrollPane = new ScrollPane(checklistContainer);
        scrollPane.setFitToWidth(true); // ScrollPane 너비에 맞춤
        scrollPane.setPrefHeight(350); // ScrollPane 높이 설정

        // Set the background color of the scroll pane to white and set transparent borders
        scrollPane.setStyle("-fx-background-color: white; -fx-border-color: transparent; -fx-background: white;");
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // Ensure the checklist container maintains a white background
        checklistContainer.setStyle("-fx-background-color: white;"); // Ensure the container's background is white

        // Add the title box, date label, add button, and scroll pane to the main layout
        mainLayout.getChildren().addAll(titleBox, dateLabel, addImageView, scrollPane); // Add title, date, add button, and scroll pane to the main layout

        layout.getChildren().add(mainLayout); // Set the main layout
        layout.setStyle("-fx-background-color: #FFFFFF;"); // Set background color

        // Update the date label every second
        Timeline clock = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateDateTime()));
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
    }

    // Method to add a checklist item
    private void addChecklistItem() {
        HBox checklistItemContainer = new HBox(10); // Create a container for the checklist item and line with spacing
        checklistItemContainer.setAlignment(Pos.CENTER); // Align checklist items to the center right
        CheckBox checklistItem = new CheckBox(); // Create a new checkbox
        checklistItem.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2; -fx-min-width: 12; -fx-min-height: 12;"); // Make sure the CheckBox is white

        // Create a StackPane to overlay the line and text field
        StackPane lineTextContainer = new StackPane();

        // Create a line below the checklist item
        Line line = new Line(0, 0, 200, 0); // Adjust length as needed
        line.setStrokeWidth(2);
        line.setStroke(Color.BLACK);

        // Move the line down
        line.setTranslateY(10); // Move line down by 10 pixels

        // Create a TextField object for user input
        TextField textField = new TextField(); // 사용자가 텍스트를 입력할 수 있는 필드
        textField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;"); // 투명 배경 및 테두리 설정
        textField.setFont(Font.font("Arial", 17));
        textField.setPrefWidth(190); // 텍스트 필드 너비 설정

        // Add the line and text field to the StackPane
        lineTextContainer.getChildren().addAll(line, textField); // Line 위에 텍스트 필드를 추가

        checklistItemContainer.getChildren().addAll(checklistItem, lineTextContainer); // Add item and line/text field to the container
        checklistContainer.getChildren().add(checklistItemContainer); // Add the container to the checklist

        // Add event handler to remove item when checked
        checklistItem.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) { // If checkbox is checked
                checklistContainer.getChildren().remove(checklistItemContainer); // Remove the checklist item
                  // Move the add button below the new checklist item if there are 8 or fewer items
                if (checklistContainer.getChildren().size() <= 7) {
                    checklistContainer.getChildren().remove(addImageView); // Remove from checklist container
                    checklistContainer.getChildren().add(addImageView); // Add button below the new checklist item
                    addImageView.setTranslateY(-5);
                }
                else {
                    // If there are more than 8 items, ensure the add button is in its original position
                    if (!mainLayout.getChildren().contains(addImageView)) {
                        checklistContainer.getChildren().remove(addImageView);
                        mainLayout.getChildren().add(addImageView);
                    }
                }
            }
        });

        // Move the add button below the new checklist item if there are 8 or fewer items
        if (checklistContainer.getChildren().size() <= 8) {
            checklistContainer.getChildren().remove(addImageView); // Remove from checklist container
            checklistContainer.getChildren().add(addImageView); // Add button below the new checklist item
        } 
        else {
            // If there are more than 8 items, ensure the add button is in its original position
            if (!mainLayout.getChildren().contains(addImageView)) {
                checklistContainer.getChildren().remove(addImageView);
                mainLayout.getChildren().add(addImageView);
            }
            addImageView.setTranslateY(-8);
        }
    }

    // Method to update the date and time
    private void updateDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        dateLabel.setText(now.format(formatter)); // Update the label with the current date and time
    }

    public StackPane getLayout() {
        return layout;
    }
}