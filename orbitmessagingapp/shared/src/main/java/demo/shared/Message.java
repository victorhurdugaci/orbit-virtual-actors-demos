package demo.shared;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Message implements Serializable {
    private UUID id;
    private Date dateTime;
    private String text;

    public UUID getId() {
        return id;
    }

    public Date getDateTime() { return dateTime; }

    public String getText() {
        return text;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", dateTime, text);
    }
}
