package DB;

public class TimeAttackRecord {
    private String username;
    private int points;

    public TimeAttackRecord(String username, int points) {
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