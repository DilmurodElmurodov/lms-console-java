package lms.service;

import lms.repository.*;

public class LMSContext {

    private final StudentRepository    studentRepo    = new StudentRepository();
    private final TeacherRepository    teacherRepo    = new TeacherRepository();
    private final SubjectRepository    subjectRepo    = new SubjectRepository();
    private final AttendanceRepository attendanceRepo = new AttendanceRepository();
    private final GradeRepository      gradeRepo      = new GradeRepository();

    public final StudentService    studentService    = new StudentService(studentRepo);
    public final TeacherService    teacherService    = new TeacherService(teacherRepo);
    public final SubjectService    subjectService    = new SubjectService(subjectRepo);
    public final AttendanceService attendanceService = new AttendanceService(attendanceRepo);
    public final GradeService      gradeService      = new GradeService(gradeRepo);
}