package edu.hw6.task6;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PortsScannerTest {
    @Test
    void test_PortsScanner() {
        List<Port> ports = PortsScanner.scanAll();
        assertThat(ports.size())
            .isEqualTo(49152);
    }

}
