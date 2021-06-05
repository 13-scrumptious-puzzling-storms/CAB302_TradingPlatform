package TradingPlatform.NetworkProtocol;

/**
 * Provides functionality of alerting thread completion.
 *
 * @author https://stackoverflow.com/a/702460.
 */
public interface ThreadCompleteListener {
    /**
     * Notifies class of thread completion.
     * @param thread the completed thread.
     */
    void notifyOfThreadComplete(final Thread thread);
}
