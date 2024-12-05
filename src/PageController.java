// import javafx.scene.Scene;
// import javafx.scene.layout.BorderPane;
// import javafx.stage.Stage;

// public class PageController {
//     private Stage primaryStage;
//     private BorderPane mainLayout;
//     HomePage homePage = new HomePage();
//     private NavigationBar navigationBar;

//     // Constructor
//     public PageController(Stage primaryStage) {
//         this.primaryStage = primaryStage;
//         this.mainLayout = new BorderPane(); // 전체 앱 레이아웃
//     }

//     // 메인 화면 로드
//     public void loadMain(HomePage homePage, NavigationBar navigationBar) {
//         if (homePage == null || homePage.getLayout() == null) {
//             System.out.println("Error: HomePage or its layout is null.");
//             return;
//         }
//         if (navigationBar == null || navigationBar.getLayout() == null) {
//             System.out.println("Error: NavigationBar or its layout is null.");
//             return;
//         }

//         mainLayout.setCenter(homePage.getLayout());
//         mainLayout.setBottom(navigationBar.getLayout());

//         Scene mainScene = new Scene(mainLayout, 800, 600);
//         primaryStage.setScene(mainScene);
//         primaryStage.setTitle("Main Page");
//         primaryStage.show();
//     }

//     // 특정 페이지 로드
//     public void loadPage(Object page) {
//         if (page instanceof HomePage) {
//             mainLayout.setCenter(((HomePage) page).getLayout());
//            // mainLayout.setCenter(homePage.getLayout());
//           //  mainLayout.setBottom(navigationBar.getLayout());
//             mainLayout.setCenter(((ToDoPage) page).getLayout());
//         } else if (page instanceof LoginPage) {
//             mainLayout.setCenter(((LoginPage) page).getLayout());
//         }
//     }

//     // 다이어리 페이지 로드
//     public void loadDiaryPage(DiaryPage diaryPage) {
//         mainLayout.setCenter(diaryPage.getScene().getRoot()); // 다이어리 페이지 씬 로드
//     }

//     public BorderPane getMainLayout() {
//         return mainLayout;
//     }
// }
