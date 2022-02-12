package game;

import java.util.LinkedList;

public class Path extends LinkedList<Bounce> {
    private boolean isValid = true;

    public Path() {
        super();
    }

    public boolean getValid() {
        return isValid;
    }
    
    @Override
    public void addLast(Bounce newBounce) {
        for (Step step : newBounce) {
            for (Bounce bounce : this) {
                if (!bounce.checkCompatibility(step)) {
                    this.isValid = false;
                    return;
                } 
            }
        }
        super.addLast(newBounce);
    }
}
