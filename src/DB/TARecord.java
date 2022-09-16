package DB;

public class TARecord {
    private String username;
    private int points;

    public TARecord(String username, int points) {
        this.username = username;
        this.points = points;

    }

    public String getUsername() {
        return username;
    }

    public int getPoints() {
        return points;
    }
}