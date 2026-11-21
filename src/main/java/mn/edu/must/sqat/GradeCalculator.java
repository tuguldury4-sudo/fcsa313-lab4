package mn.edu.must.sqat;

public class GradeCalculator {
    public String letterGrade(double score) {
        validate("score", score, 100);
        if (score >= 90) return "A";
        if (score >= 80) return "B";
        if (score >= 70) return "C";
        if (score >= 60) return "D";
        return "F";
    }

    public double totalScore(double attendance, double lab, double quiz1, double quiz2, double exam) {
        validate("attendance", attendance, 10);
        validate("lab", lab, 40);
        validate("quiz1", quiz1, 10);
        validate("quiz2", quiz2, 10);
        validate("exam", exam, 30);
        return attendance + lab + quiz1 + quiz2 + exam;
    }

    private void validate(String name, double score, double maximum) {
        if (!Double.isFinite(score) || score < 0 || score > maximum) {
            throw new IllegalArgumentException(name + " must be between 0 and " + maximum);
        }
    }
}
