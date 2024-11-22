import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cover extends VBox {

    public Cover(Runnable onCoverClick) {
        // 배경색이 #FFCDE1인 Rectangle 생성
        Rectangle background1 = new Rectangle(360, 640);
        background1.setFill(Color.web("#FFCDE1"));

        // 기본 이미지와 마우스 오버 이미지 경로
        Image image1 = new Image("styles/logo.png");
        ImageView imageView1 = new ImageView(image1);

        Image defaultImage = new Image("styles/nextBtn.png"); // 기본 아이콘 이미지
        Image hoverImage = new Image("styles/pinkNextBtn.png"); // 마우스 오버 아이콘 이미지
        ImageView imageView2 = new ImageView(defaultImage);

        // 이미지 크기 설정
        imageView1.setFitWidth(330);
        imageView1.setPreserveRatio(true);
        imageView2.setFitWidth(60);
        imageView2.setFitHeight(60);
        imageView2.setPreserveRatio(true);

        // 레이아웃 설정
        HBox topLayout = new HBox();
        topLayout.getChildren().add(imageView1);
        topLayout.getChildren().add(background1);
        topLayout.setStyle("-fx-background-color: #FFCDE1;");

        // imageView1 위치 조정
        imageView1.setTranslateX(20);

        StackPane stackPane = new StackPane();
        stackPane.setPrefSize(360, 640);

        // imageView2 및 이벤트 영역 확장
        StackPane buttonArea = new StackPane(); // 클릭 및 마우스 오버 이벤트를 감지하는 컨테이너
        buttonArea.setPrefSize(80, 80); // 확장된 영역 크기
        buttonArea.setTranslateX(-35); // 이미지 위치와 동일하게 설정
        buttonArea.setTranslateY(-75);
        buttonArea.setStyle("-fx-background-color: transparent;"); // 디버깅 중이라면 색상 추가 가능

        // buttonArea에 imageView2 추가
        buttonArea.getChildren().add(imageView2);
        stackPane.getChildren().add(buttonArea);

        // VBox에 레이아웃 추가
        this.getChildren().add(topLayout);
        this.getChildren().add(stackPane);

        // 이벤트 처리: 확장된 영역에서 동작
        buttonArea.setOnMouseEntered(event -> {
            imageView2.setImage(hoverImage); // 마우스 오버 시 이미지 교체
        });

        buttonArea.setOnMouseExited(event -> {
            imageView2.setImage(defaultImage); // 마우스가 떠나면 기본 이미지로 복원
        });

        buttonArea.setOnMouseClicked(event -> {
            onCoverClick.run(); // 클릭 시 메인 화면으로 전환
        });

        // VBox 클릭 이벤트 무시
        this.setOnMouseClicked(event -> {
        });
    }
}
