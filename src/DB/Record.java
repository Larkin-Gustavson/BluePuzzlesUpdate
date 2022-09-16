package DB;

public class Record {
    private String username;
    private String difficulty;
    private String time;

    public Record(String username, String difficulty, String time) {
        this.username = username;
        this.difficulty = difficulty;
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getTime() {
        return time;
    }
}