public class Session {
    private static String username = null;  // 로그인한 사용자 이름을 저장

    // 로그인 처리 (사용자 이름 저장)
    public static void login(String username) {
        Session.username = username;
        System.out.println("Logged in username: " + Session.getUsername());
    }

    // 로그아웃 처리 (세션 정보 초기화)
    public static void logout() {
        Session.username = null;
    }

    // 로그인 상태 확인
    public static boolean isLoggedIn() {
        return username != null;
    }

    // 현재 로그인된 사용자 이름 반환
    public static String getUsername() {
        return username;
    }
} 