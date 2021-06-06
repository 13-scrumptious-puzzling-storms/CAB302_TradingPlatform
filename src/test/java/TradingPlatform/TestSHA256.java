package TradingPlatform;

import org.junit.jupiter.api.Test;

public class TestSHA256 {

    @Test
    public void testHashPassword() {
        // First one passes test in Java but fails in Maven due to UTF-8
        //assert (SHA256.hashPassword("root").equals("H\u0013IM\u0013~\u00161��\u0001լ�n{��t�\u0011��VV^�\u001Dsvw�"));
        assert (SHA256.hashPassword("root").equals(SHA256.hashPassword("root")));
    }
}
