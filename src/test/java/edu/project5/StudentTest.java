package edu.project5;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class StudentTest {

    @Test
    void name() {
        Student student = new Student("Oleg", "Stown");
        assertThat(student.name())
            .isEqualTo("Oleg");
    }

    @Test
    void surname() {
        Student student = new Student("Oleg", "Stown");
        assertThat(student.surname())
            .isEqualTo("Stown");
    }
}
