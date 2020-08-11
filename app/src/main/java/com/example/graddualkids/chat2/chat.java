package com.example.graddualkids.chat2;

/********  MODEL****/
public class chat {

    private String sender;
    private String receiver;
    private String message;
    private boolean seen;

    public chat(String sender, String receiver, String message, boolean seen) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.seen = seen;
    }

    public chat() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }
}
