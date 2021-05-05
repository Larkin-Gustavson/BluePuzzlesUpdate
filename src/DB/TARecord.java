package DB;

public class TARecord {


    private String userName;
    private int points;

    public TARecord(String user, int points) {
        this.userName = user;
        this.points = points;

    }

    public String getUserName() {
        return userName;
    }

    public int getPoints() {
        return points;
    }
}
