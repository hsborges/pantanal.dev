package dev.pantanal;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UtilsTests {

    @Test
    void toObjects() {
        byte[] bytes = { 1, 2, 3, 4, 5 };
        Byte[] oBytes = Utils.toObjects(bytes);
        assert oBytes.length == bytes.length;
        for (int i = 0; i < bytes.length; i++) {
            assert oBytes[i] == bytes[i];
        }
    }

    @Test
    void toPrimitives() {
        Byte[] oBytes = { 1, 2, 3, 4, 5 };
        byte[] bytes = Utils.toPrimitives(oBytes);
        assert bytes.length == oBytes.length;
        for (int i = 0; i < oBytes.length; i++) {
            assert bytes[i] == oBytes[i];
        }
    }

}
