package tfifteenfour.clipboard.model.course;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Map;

import javafx.collections.ObservableList;
import tfifteenfour.clipboard.model.course.exceptions.StudentNotInSessionException;
import tfifteenfour.clipboard.model.student.Student;
import tfifteenfour.clipboard.model.student.UniqueStudentList;



/**
 * Represents a Session in the CLIpboard.
 */
public class Session {

    public static final String MESSAGE_CONSTRAINTS = "Session names should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    private final String sessionName;

    /**
     * A map that stores the attendance record for each student in the session.
     * The key is the Student object, and the value is an integer indicating the attendance status:
     * 0 for absent, 1 for present.
     */
    private Map<Student, Integer> attendance;

    /**
     * Constructs a {@code Session} with the given session name.
     *
     * @param sessionName The name of the session.
     */
    public Session(String sessionName) {
        requireNonNull(sessionName);
        this.sessionName = sessionName;
        attendance = new HashMap<>();
    }

    /**
     * Returns an unmodifiable list of the students who are in the session.
     * @return An unmodifiable list of the students who are in the session.
     */
    public ObservableList<Student> getUnmodifiableStudentList() {
        UniqueStudentList students = new UniqueStudentList();
        for (Student student : attendance.keySet()) {
            students.add(student);
        }
        return students.asUnmodifiableObservableList();
    }

    /**
     * Returns the name of the session.
     * @return The name of the session.
     */
    public String getSessionName() {
        System.out.println("########\n "
                + "ATTENDANCE for" + sessionName);
        System.out.println(attendance.toString());
        System.out.println("########");
        return this.sessionName;
    }

    /**
     * Returns the attendance record for each student in the session.
     * @return A map that stores the attendance record for each student in the session.
     */
    public Map<Student, Integer> getAttendance() {
        return this.attendance;
    }

    /**
     * Checks if the given Session object is the same as this Session object.
     * @param otherSession The Session object to be compared with this Session object.
     * @return True if the given Session object is the same as this Session object, false otherwise.
     */
    public boolean isSameSession(Session otherSession) {
        if (otherSession == this) {
            return true;
        }

        return otherSession != null
                && otherSession.getSessionName().equals(getSessionName());
    }

    /**
     * Checks if the given string is a valid session name.
     * @param test The string to be tested.
     * @return True if the given string is a valid session name, false otherwise.
     */
    public static boolean isValidSessionName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Sets the list of students who are in the session.
     * @param students The list of students who are in the session.
     */
    public void setStudents(UniqueStudentList students) {
        Map<Student, Integer> newAttendance = new HashMap<>();
        for (Student student : students) {
            newAttendance.put(student, attendance.getOrDefault(student, 0));
        }
        attendance = newAttendance;
        System.out.println(attendance.keySet());
    }

    /**
     * Marks the given student as present in this session.
     *
     * @param student The student to mark as present.
     * @throws StudentNotInSessionException If the given student is not enrolled in this session.
     */
    public void markPresent(Student student) throws StudentNotInSessionException {
        requireNonNull(student);

        if (!attendance.containsKey(student)) {
            throw new StudentNotInSessionException();
        }
        attendance.put(student, 1);
        System.out.println("Marked student " + student.getName() + " present in session " + sessionName);
    }

    /**
     * Marks the given student as absent in this session.
     *
     * @param student The student to mark as absent.
     * @throws StudentNotInSessionException If the given student is not enrolled in this session.
     */
    public void markAbsent(Student student) throws StudentNotInSessionException {
        requireNonNull(student);

        if (!attendance.containsKey(student)) {
            throw new StudentNotInSessionException();
        }
        attendance.put(student, 0);
        System.out.println("Marked student " + student.getName() + " absent in session " + sessionName);
    }

    @Override
    public String toString() {
        return this.sessionName;
    }
}
