package models;

import java.sql.Timestamp;

public class Session {
    private Integer id;
    private Integer userId = null;
    private String token = null;
    private Timestamp entryTimestamp = null;
    private Timestamp exitTimestamp = null;

    public Session(int id, int userId, String token, Timestamp entryTimestamp) {
        this.id = id;
        this.userId = userId;
        this.token = token;
        this.entryTimestamp = entryTimestamp;
    }

    public Session(int id, int userId, String token, Timestamp entryTimestamp, Timestamp exitTimestamp) {
        this.id = id;
        this.userId = userId;
        this.token = token;
        this.entryTimestamp = entryTimestamp;
        this.exitTimestamp = exitTimestamp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public int getUserId() {
        return this.userId;
    }

    public String getToken() {
        return this.token;
    }

    public Timestamp getEntryTimestamp() {
        return this.entryTimestamp;
    }

    public Timestamp getExitTimestamp() {
        return this.exitTimestamp;
    }

    public void setEntryTimestamp(Timestamp entryTimestamp) {
        this.entryTimestamp = entryTimestamp;
    }

    public void setExitTimestamp(Timestamp exitTimestamp) {
        this.exitTimestamp = exitTimestamp;
    }
}
