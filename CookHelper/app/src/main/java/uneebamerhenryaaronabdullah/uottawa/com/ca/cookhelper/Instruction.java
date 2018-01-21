package uneebamerhenryaaronabdullah.uottawa.com.ca.cookhelper;

/**
 * Created by Uneeb on 05/12/2016.
 */

public class Instruction {

    private String direction;
    private int stepNum;

    public Instruction(String direction, int stepNum) {
        this.direction = direction;
        this.stepNum = stepNum;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getStepNum() {
        return stepNum;
    }

    public void setStepNum(int stepNum) {
        this.stepNum = stepNum;
    }
}
