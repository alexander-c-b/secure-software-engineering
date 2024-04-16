package edu.floridapoly.securesoftware.spring24.SocialEngineerGame;

public class User {
    private static String username = null;
    private static String passwordHash = null;

    public static void saveUserInfo(String username, String passwordHash) {
        User.username = username;
        User.passwordHash = passwordHash;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPasswordHash() {
        return passwordHash;
    }
}
