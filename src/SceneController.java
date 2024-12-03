// package application;

// import java.io.IOException;
// import java.net.URL;
// import java.util.ResourceBundle;

// import javafx.fxml.FXML;
// import javafx.fxml.FXMLLoader;
// import javafx.fxml.Initializable;
// import javafx.scene.Parent;
// import javafx.scene.Scene;
// import javafx.scene.control.Button;
// import javafx.stage.Stage;

// public class SceneController implements Initializable {

//     @FXML
//     Button btn;

//     @Override
//     public void initialize(URL location, ResourceBundle resources) {
//         // TODO Auto-generated method stub

//     }

//     // 버튼 클릭시 하는 행동
//     public void movePage() {

//         try {
//             // 컨트롤 + 레이아웃
         
//             // 씬에 추가
//             Scene scene = new Scene(sub, 200, 200);
//             // 씬을 스테이지에 추가
//             // Stage stage = (Stage) btn.getScene().getWindow();
//             Stage stage = new Stage();
//             stage.setScene(scene);
//             // 스테이지 설정
//             stage.setTitle("서브페이지");
//             stage.setResizable(false);
//             // 스테이지 보여주기
//             stage.show();
//             Stage root = (Stage) btn.getScene().getWindow(); // 현재 윈도우를 가져온다는 뜻
//             root.close();
//         } catch (IOException e) {
//             // TODO Auto-generated catch block
//             e.printStackTrace();
//         }

//     }
