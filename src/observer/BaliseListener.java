package observer;

import observer.BaliseMoveEvent;

public interface BaliseListener {
    void onBaliseMove(BaliseMoveEvent event);
    void SynchroBalise (BaliseSynchroEvent event);
}