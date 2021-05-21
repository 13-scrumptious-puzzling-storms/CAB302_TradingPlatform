package TradingPlatform.Interfaces;

import TradingPlatform.TradeReconciliation.TradeRecon;

public interface TradeReconcileSource {
    /**
     * Inserts a trade reconciliation into the tradeRecon table
     * @param recon the reconciliation that will be inserted
     */
    void insertTradeRecon(TradeRecon recon);
}
