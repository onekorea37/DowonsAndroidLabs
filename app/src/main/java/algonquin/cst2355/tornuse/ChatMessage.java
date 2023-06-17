package algonquin.cst2355.tornuse;

public class ChatMessage {
    private String message;
    private boolean sendOrReceive;
    private String timeSent;

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
}

