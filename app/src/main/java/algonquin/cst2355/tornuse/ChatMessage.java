package algonquin.cst2355.tornuse;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ChatMessage {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    public long id;

    @ColumnInfo(name="Message")
    private String message;

    @ColumnInfo(name="SendOrReceive")
    private boolean sendOrReceive;

    @ColumnInfo(name="TimeSent")
    private String timeSent;

    public ChatMessage() {}

    public ChatMessage(String message, String timeSent, boolean sendOrReceive) {
        this.message = message;
        this.timeSent = timeSent;
        this.sendOrReceive = sendOrReceive;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSend() {
        return sendOrReceive;
    }

    public String getTimeSent() {
        return timeSent;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSendOrReceive() {
        return sendOrReceive;
    }

    public void setSendOrReceive(boolean sendOrReceive) {
        this.sendOrReceive = sendOrReceive;
    }

    public void setTimeSent(String timeSent) {
        this.timeSent = timeSent;
    }
}

