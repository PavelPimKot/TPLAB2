package dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "GuessThe" +
        "NumberMessage")
@XmlRootElement
public class GuessTheNumberMessage {
    @XmlElement(name = "number", required = true)
    public int number;

    @XmlElement(name = "message", required = false)
    public String message;

    @XmlElement(name = "startEndGameFlag", required = false)
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
