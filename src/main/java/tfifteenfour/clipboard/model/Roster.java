package tfifteenfour.clipboard.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import tfifteenfour.clipboard.model.course.Course;
import tfifteenfour.clipboard.model.course.UniqueCoursesList;
import tfifteenfour.clipboard.model.student.Student;
import tfifteenfour.clipboard.model.student.UniqueStudentList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameStudent comparison)
 */
public class Roster implements ReadOnlyRoster {

    private final UniqueStudentList students;
    private final UniqueCoursesList courses;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        students = new UniqueStudentList();
    }

    {
        courses = new UniqueCoursesList();
    }

    public Roster() {}

    /**
     * Creates an Roster using the Students in the {@code toBeCopied}
     */
    public Roster(ReadOnlyRoster toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the student list with {@code students}.
     * {@code students} must not contain duplicate students.
     */
    public void setStudents(List<Student> students) {
        this.students.setStudents(students);
    }

    /**
     * Resets the existing data of this {@code Roster} with {@code newData}.
     */
    public void resetData(ReadOnlyRoster newData) {
        requireNonNull(newData);

        setStudents(newData.getUnmodifiableStudentList());
    }

    //// student-level operations

    /**
     * Returns true if a student with the same identity as {@code student} exists in the address book.
     */
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return students.contains(student);
    }

    public boolean hasCourse(Course course) {
        requireNonNull(course);
        return courses.contains(course);
    }

    /**
     * Adds a student to the address book.
     * The student must not already exist in the address book.
     */
    public void addStudent(Student p) {
        students.add(p);
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    /**
     * Replaces the given student {@code target} in the list with {@code editedStudent}.
     * {@code target} must exist in the address book.
     * The student identity of {@code editedStudent} must not be the same as another existing student
     * in the address book.
     */
    public void setStudent(Student target, Student editedStudent) {
        requireNonNull(editedStudent);

        students.setStudent(target, editedStudent);
    }

    /**
     * Removes {@code key} from this {@code Roster}.
     * {@code key} must exist in the address book.
     */
    public void removeStudent(Student key) {
        students.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return students.asUnmodifiableObservableList().size() + " students";
        // TODO: refine later
    }

    @Override
    public ObservableList<Student> getUnmodifiableStudentList() {
        return students.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Student> getModifiableStudentList() {
        return students.asModifiableObservableList();
    }

    public ObservableList<Course> getModifiableCourseList() {
        return courses.asModifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Roster // instanceof handles nulls
                && students.equals(((Roster) other).students));
    }

    @Override
    public int hashCode() {
        return students.hashCode();
    }


    /**
     * Copies this roster and its current state
     * @return a copy of this roster
     */
    public Roster copy() {
        Roster copy = new Roster();
        copy.setStudents(students.asUnmodifiableObservableList());

        return copy;
    }
}
