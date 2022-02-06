package src.game;

import java.util.ArrayList;

public class Path extends ArrayList<Bounce> {
    private boolean isValid = true;

    public Path() {
        super();
    }

    public boolean getValid() {
        return isValid;
    }
    
    @Override
    public boolean add(Bounce newBounce) {
        for (Step step : newBounce) {
            for (Bounce bounce : this) {
                if (bounce.contains(step) || bounce.contains(step.getReverseStep())) {
                    this.isValid = false;
                    return false;             
                }
            }
        }
        super.add(newBounce);
        return true;
    }
}
