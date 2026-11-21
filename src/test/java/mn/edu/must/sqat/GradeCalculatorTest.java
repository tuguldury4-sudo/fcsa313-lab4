package mn.edu.must.sqat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class GradeCalculatorTest {
    private final GradeCalculator calculator = new GradeCalculator();

    @Test
    @DisplayName("90 оноо A дүн")
    void ninetyIsA() {
        double score = 90;
        String grade = calculator.letterGrade(score);
        assertEquals("A", grade);
    }

    @Test
    @DisplayName("Сөрөг нийлбэр оноог татгалзана")
    void rejectsNegativeGrade() {
        double score = -1;
        assertThrows(IllegalArgumentException.class, () -> calculator.letterGrade(score));
    }

    @Test
    @DisplayName("100-аас их оноог татгалзана")
    void rejectsGradeAboveMaximum() {
        double score = 101;
        assertThrows(IllegalArgumentException.class, () -> calculator.letterGrade(score));
    }

    @Test
    @DisplayName("Тодорхойгүй болон хязгааргүй оноог татгалзана")
    void rejectsNonFiniteGrades() {
        double undefined = Double.NaN;
        double infinite = Double.POSITIVE_INFINITY;
        assertAll(
            () -> assertThrows(IllegalArgumentException.class, () -> calculator.letterGrade(undefined)),
            () -> assertThrows(IllegalArgumentException.class, () -> calculator.letterGrade(infinite))
        );
    }

    @Test
    @DisplayName("Сөрөг ирцийн оноог татгалзана")
    void rejectsNegativeAttendance() {
        double attendance = -5;
        assertThrows(IllegalArgumentException.class, () -> calculator.totalScore(attendance, 40, 10, 10, 30));
    }

    @Test
    @DisplayName("40-өөс их лабораторийн оноог татгалзана")
    void rejectsLabAboveMaximum() {
        double lab = 41;
        assertThrows(IllegalArgumentException.class, () -> calculator.totalScore(10, lab, 10, 10, 30));
    }

    @ParameterizedTest
    @DisplayName("Дүнгийн ангиллын заагууд")
    @CsvSource({"100,A", "90,A", "89.99,B", "80,B", "79.99,C", "70,C", "69.99,D", "60,D", "59.99,F", "0,F"})
    void letterBoundaries(double score, String expected) {
        String grade = calculator.letterGrade(score);
        assertEquals(expected, grade);
    }

    @ParameterizedTest
    @DisplayName("Бүрэлдэхүүн оноонуудын нийлбэр")
    @CsvSource({"0,0,0,0,0,0", "10,40,10,10,30,100", "7.5,33.5,6,9,25,81"})
    void totals(double attendance, double lab, double quiz1, double quiz2, double exam, double expected) {
        double actual = calculator.totalScore(attendance, lab, quiz1, quiz2, exam);
        assertEquals(expected, actual, 0.00001);
    }
}
