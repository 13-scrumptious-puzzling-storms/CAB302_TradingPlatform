package TradingPlatform.NetworkProtocol;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Responsible for alerting the completion of threads
 * that it stores. Also adds and removes the listeners required
 * to achieve this.
 *
 * @author https://stackoverflow.com/a/702460.
 */
public abstract class NotifyingThread extends Thread {
    private final Set<ThreadCompleteListener> listeners = new CopyOnWriteArraySet<ThreadCompleteListener>();

    /**
     * Adds a ThreadCompleteListener to the set of listeners.
     * @param listener
     */
    public final void addListener(final ThreadCompleteListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes a ThreadCompleteListener from the set of listeners.
     * @param listener
     */
    public final void removeListener(final ThreadCompleteListener listener) {
        listeners.remove(listener);
    }

    /**
     * Notify each listener of 'this' thread completing.
     */
    private final void notifyListeners() {
        for (ThreadCompleteListener listener : listeners) {
            listener.notifyOfThreadComplete(this);
        }
    }

    /**
     * Instead of run(), require doRun() and invoke
     * notifyListeners upon completion.
     */
    @Override
    public final void run() {
        try {
            doRun();
        } finally {
            notifyListeners();
        }
    }

    public abstract void doRun();
}
