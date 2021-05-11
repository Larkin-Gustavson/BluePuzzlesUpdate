package DB;

public class TARecord {


    private String username;
    private int points;

    public TARecord(String user, int points) {
        this.username = user;
        this.points = points;

    }

    public String getUsername() {
        return username;
    }

    public int getPoints() {
        return points;
    }
}
