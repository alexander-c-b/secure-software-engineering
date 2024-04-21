package edu.floridapoly.securesoftware.spring24.SocialEngineerGame;

public class User {
    private static String username = null;
    private static String passwordHash = null;
    private static String passwordHashSalted = null;

    public static void saveUserInfo(
      String username, String passwordHash, String passwordHashSalted
    ) {
        User.username = username;
        User.passwordHash = passwordHash;
        User.passwordHashSalted = passwordHashSalted;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPasswordHash() {
        return passwordHash;
    }

    public static String getPasswordHashSalted() {
        return passwordHashSalted;
    }
}
