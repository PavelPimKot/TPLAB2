package dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "GuessTheNumberMessage")
@XmlRootElement
public class GuessTheNumberMessage {
    public String message;
    public int number;
    public boolean startEndGameFlag;

    public GuessTheNumberMessage() {
    }

    public GuessTheNumberMessage(String message, int number, boolean startEndGameFlag) {
        this.message = message;
        this.number = number;
        this.startEndGameFlag = startEndGameFlag;
    }

    @Override
    public String toString() {
        return "GuessTheNumberMessage{" +
                "message='" + message + '\'' +
                ", number=" + number +
                '}';
    }
}
