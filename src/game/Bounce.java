package src.game;

import java.util.LinkedList;

public class Bounce extends LinkedList<Step> {
    private boolean isValid = true;

    public boolean getValid() {
        return isValid;
    }

    public boolean checkCompatibility(Step step) {
        return size() == 0 || (!contains(step) && !contains(step.getReverseStep()));
    }
    
    @Override
    public void addLast(Step step) {
        if (checkCompatibility(step)) {
            super.addLast(step);
            return;
        }
        isValid = false;
    }
}