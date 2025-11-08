package balise;

import announcer.AbstractEvent;

public class BaliseMoveEvent extends AbstractEvent {
    private static final long serialVersionUID = 1L;

    public BaliseMoveEvent(Object source) {
        super(source);
    }

    @Override
    public void sentTo(Object target) {
        ((BaliseListener) target).onBaliseMove(this);
    }
}
