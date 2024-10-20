import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

public class HomePage {
    private StackPane layout;

    public HomePage() {
        // Home Page content
        layout = new StackPane();
        layout.getChildren().add(new Text("Welcome to Home Page!"));
        layout.setStyle("-fx-background-color:  #FFFFFF;");

        // 플러스 버튼을 왼쪽 하단에 추가
        Button createDiaryButton = new Button("+");
        createDiaryButton.setStyle("-fx-background-color:#AEDEFC; -fx-font-size: 24px; -fx-border-radius: 50%; -fx-pref-width: 50px; -fx-pref-height: 50px;");
        
        // 버튼을 왼쪽 하단에 배치
        HBox buttonBox = new HBox(createDiaryButton);
        buttonBox.setAlignment(Pos.BOTTOM_RIGHT);  // 버튼을 하단 왼쪽에 정렬
        buttonBox.setPadding(new Insets(10));    // 약간의 여백 추가

        // 레이아웃에 버튼을 추가
        layout.getChildren().add(buttonBox);
    }

    public StackPane getLayout() {
        return layout;
    }
}
