package src.game;

import java.util.ArrayList;

public class Bounce extends ArrayList<Step> {
    private boolean isValid = true;

    public boolean getValid() {
        return isValid;
    }

    public Step getLastStep() {
        return get(size() - 1);
    }
    
    @Override
    public boolean add(Step step) {
        if (size() == 0 || (!contains(step) && !contains(step.getReverseStep()))) {
            super.add(step);
            return true;
        }
        isValid = false;
        return false;
    }
}