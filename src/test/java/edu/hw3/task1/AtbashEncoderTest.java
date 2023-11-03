package edu.hw3.task1;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class AtbashEncoderTest {

    @ParameterizedTest
    @CsvSource({
        "'', ''",
        "'Hello world!', 'Svool dliow!'",
        "'Any fool can write code that a computer can understand. Good programmers write code that humans can understand. ― Martin Fowler', 'Zmb ullo xzm dirgv xlwv gszg z xlnkfgvi xzm fmwvihgzmw. Tllw kiltiznnvih dirgv xlwv gszg sfnzmh xzm fmwvihgzmw. ― Nzigrm Uldovi'"
    })
    void test_AtbashEncoder_on_normal_data(String str, String expected) {
        assertThat(AtbashEncoder.atbash(str))
            .isEqualTo(expected);
    }

    @ParameterizedTest
    @NullSource
    void test_AtbashEncoder_on_null_data(String str) {
        assertThatThrownBy(() -> AtbashEncoder.atbash(str))
            .isInstanceOf(NullPointerException.class);
    }
}
