package de.testcenter.testcenterbackend.models;

public class Message {
    private String code;
    private String text;

    public Message(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public String getText() {
        return text;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setText(String text) {
        this.text = text;
    }
}
