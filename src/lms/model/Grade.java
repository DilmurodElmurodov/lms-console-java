package lms.model;

public class Grade {
    private static int idCounter = 1;

    private int id;
    private Student student;
    private Subject subject;
    private double midtermScore;
    private double finalScore;
    private double assignmentScore;
    private String letterGrade;

    public Grade(Student student, Subject subject) {
        this.id = idCounter++;
        this.student = student;
        this.subject = subject;
        this.midtermScore = 0;
        this.finalScore = 0;
        this.assignmentScore = 0;
        this.letterGrade = "N/A";
    }

    public int getId() { return id; }
    public Student getStudent() { return student; }
    public Subject getSubject() { return subject; }
    public double getMidtermScore() { return midtermScore; }
    public double getFinalScore() { return finalScore; }
    public double getAssignmentScore() { return assignmentScore; }
    public String getLetterGrade() { return letterGrade; }

    public void setMidtermScore(double v) { this.midtermScore = v; calcGrade(); }
    public void setFinalScore(double v) { this.finalScore = v; calcGrade(); }
    public void setAssignmentScore(double v) { this.assignmentScore = v; calcGrade(); }

    public double getOverallScore() {
        return (midtermScore * 0.30) + (finalScore * 0.50) + (assignmentScore * 0.20);
    }

    private void calcGrade() {
        double o = getOverallScore();
        if (o >= 90)      letterGrade = "A+";
        else if (o >= 85) letterGrade = "A";
        else if (o >= 80) letterGrade = "A-";
        else if (o >= 75) letterGrade = "B+";
        else if (o >= 70) letterGrade = "B";
        else if (o >= 65) letterGrade = "B-";
        else if (o >= 60) letterGrade = "C+";
        else if (o >= 55) letterGrade = "C";
        else if (o >= 50) letterGrade = "D";
        else              letterGrade = "F";
    }

    @Override
    public String toString() {
        return String.format("[%s] %s | %s | Mid:%.1f Final:%.1f Assign:%.1f | Overall:%.1f | %s",
                student.getStudentCode(), student.getFullName(), subject.getName(),
                midtermScore, finalScore, assignmentScore, getOverallScore(), letterGrade);
    }
}