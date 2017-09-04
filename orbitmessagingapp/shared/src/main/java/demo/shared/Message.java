package demo.shared;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    private Date dateTime;
    private String text;

    public Date getDateTime() { return dateTime; }

    public String getText() {
        return text;
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
