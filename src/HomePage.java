import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class HomePage {
    private StackPane layout;
    private GridPane diaryGrid;
    private Pane createDiaryPane;  
    private String selectedCoverColor = "#FFB6C1";  

    public HomePage(Main mainApp) {
        layout = new StackPane();
        diaryGrid = new GridPane();  
        diaryGrid.setHgap(10);  
        diaryGrid.setVgap(10);  

        // 다이어리 생성 버튼
        Button createDiaryButton = new Button("+");
        createDiaryButton.getStyleClass().add("create-button");  // CSS 클래스 추가
        createDiaryButton.setOnAction(e -> openDiaryCreationPane());  

        // 왼쪽 하단에 버튼 배치
        VBox vbox = new VBox(diaryGrid);
        AnchorPane buttonContainer = new AnchorPane(createDiaryButton);
        AnchorPane.setBottomAnchor(createDiaryButton, 20.0);
        AnchorPane.setRightAnchor(createDiaryButton, 20.0);  // 버튼 위치 조정

        layout.getChildren().addAll(vbox, buttonContainer);
        layout.setStyle("-fx-padding: 20;");

        // 다이어리 생성 UI 생성
        createDiaryPane = createDiaryUI();
        layout.getChildren().add(createDiaryPane);
        createDiaryPane.setVisible(false);  
    }

    public void addDiary(String title, String cover) {
        // 다이어리 카드 생성
        DiaryCard diaryCard = new DiaryCard(title, cover); 
        int rowCount = diaryGrid.getChildren().size() / 2;  
        diaryGrid.add(diaryCard.getLayout(), diaryGrid.getChildren().size() % 2, rowCount);  
        createDiaryPane.setVisible(false);  
    }

    public String getSelectedCoverColor() {
        return selectedCoverColor;  
    }

    public StackPane getLayout() {
        return layout;
    }

    private Pane createDiaryUI() {
        VBox diaryCreationLayout = new VBox();
        diaryCreationLayout.getStyleClass().add("diary-modal");  // CSS 클래스 추가

        Button closeButton = new Button("X");
        closeButton.getStyleClass().add("close-button");  // CSS 클래스 추가
        closeButton.setOnAction(e -> createDiaryPane.setVisible(false));  

        TextField titleField = new TextField();
        titleField.setPromptText("다이어리 제목 입력");

        ColorPicker colorPicker = new ColorPicker();  
        colorPicker.setValue(javafx.scene.paint.Color.PINK);  

        Button createButton = new Button("다이어리 생성");
        createButton.setOnAction(e -> {
            String title = titleField.getText();
            if (!title.isEmpty()) {
                addDiary(title, toHex(colorPicker.getValue()));  
                titleField.clear();  
            }
        });

        diaryCreationLayout.getChildren().addAll(closeButton, titleField, colorPicker, createButton);
        return diaryCreationLayout;
    }

    private String toHex(javafx.scene.paint.Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    private void openDiaryCreationPane() {
        createDiaryPane.setVisible(true);  
    }
}
