package TradingPlatform;

import org.junit.jupiter.api.Test;

class TestSHA256 {

    @Test
    void testHashPassword() {
        assert (SHA256.hashPassword("root").equals("H\u0013IM\u0013~\u00161��\u0001լ�n{��t�\u0011��VV^�\u001Dsvw�"));
    }
}
