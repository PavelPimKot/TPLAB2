package dto;

public class GuessTheNumberMessage {
    public String message;
    public int number;
    public boolean startEndGameFlag;

    public GuessTheNumberMessage(String message, int number, boolean startEndGameFlag){
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
