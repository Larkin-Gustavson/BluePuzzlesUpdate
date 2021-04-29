package DB;

public class Record {


    private String userName;
    private String difficulty;
    private String time;

    public Record(String userName, String difficulty, String time) {
        this.userName = userName;
        this.difficulty = difficulty;
        this.time = time;
    }

    public String getUserName() {
        return userName;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getTime() {
        return time;
    }
}
