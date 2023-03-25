package tfifteenfour.clipboard.logic;

import tfifteenfour.clipboard.model.course.Course;
import tfifteenfour.clipboard.model.course.Group;
import tfifteenfour.clipboard.model.course.Session;
import tfifteenfour.clipboard.model.student.Email;
import tfifteenfour.clipboard.model.student.Name;
import tfifteenfour.clipboard.model.student.Phone;
import tfifteenfour.clipboard.model.student.Remark;
import tfifteenfour.clipboard.model.student.Student;
import tfifteenfour.clipboard.model.student.StudentId;

/**
 * Class to keep track of current selections.
 */
public class CurrentSelection {

    public static final Student NON_EXISTENT_STUDENT = emptyStudentBuilder();
    private static final Course NON_EXISTENT_COURSE = new Course("NONEXISTENTCOURSE");
    private static final Group NON_EXISTENT_GROUP = new Group("NONEXISTENTGROUP");
    private static final Session NON_EXISTENT_SESSION = new Session("NONEXISTENTSESSION");

    private PageType currentPage;

    private Course selectedCourse = NON_EXISTENT_COURSE;
    private Group selectedGroup = NON_EXISTENT_GROUP;
    private Student selectedStudent = NON_EXISTENT_STUDENT;
    private Session selectedSession = NON_EXISTENT_SESSION;

    public CurrentSelection() {
        this.currentPage = PageType.COURSE_PAGE;
    }

    private static Student emptyStudentBuilder() {
        Name name = new Name("NONEXISTENT STUDENT");
        Phone phone = new Phone("123456");
        Email email = new Email("empty@empty.com");
        StudentId sid = new StudentId("EMPTY99");
        // Set<Course> modules = new HashSet<>();
        Remark remark = new Remark("");
        // Set<Tag> tags = new HashSet<>();
        return new Student(name, phone, email, sid, remark);
    }

    public PageType getCurrentPage() {
        return currentPage;
    }

    /**
     * Setter for selectedCourse.
     * @param course to be set.
     */
    public void selectCourse(Course course) {
        this.selectedCourse = course;
        this.currentPage = PageType.GROUP_PAGE;
    }

    /**
     * Setter for selectedGroup.
     * @param group to be set.
     */
    public void selectGroup(Group group) {
        this.selectedGroup = group;
        this.currentPage = PageType.STUDENT_PAGE;
    }

    public void selectSession(Session session) {
        this.selectedSession = session;
        this.currentPage = PageType.SESSION_STUDENT_PAGE;
    }

    public void selectStudent(Student student) {
        this.selectedStudent = student;
    }

    public void setCurrentPage(PageType newPage) {
        this.currentPage = newPage;
    }

    public Course getSelectedCourse() {
        return this.selectedCourse;
    }

    public Group getSelectedGroup() {
        return this.selectedGroup;
    }

    public Student getSelectedStudent() {
        return this.selectedStudent;
    }

    public Session getSelectedSession() {
        return this.selectedSession;
    }

    /**
     * Navigates current page from group page to course page.
     */
    public void navigateBackFromGroupPage() {
        this.selectedCourse = NON_EXISTENT_COURSE;
        this.currentPage = PageType.COURSE_PAGE;
    }

    /**
     * Navigates current page from student page to group page.
     */
    public void navigateBackFromStudentPage() {
        this.selectedGroup = NON_EXISTENT_GROUP;
        this.currentPage = PageType.GROUP_PAGE;
    }

    public void navigateBackFromSessionPage() {
        this.selectedGroup = NON_EXISTENT_GROUP;
        this.currentPage = PageType.GROUP_PAGE;
    }

    public void navigateBackFromSessionStudentPage() {
        this.selectedSession = NON_EXISTENT_SESSION;
        this.currentPage = PageType.SESSION_PAGE;
    }
    /**
     * Resets current selected page to main course page.
     */
    public void navigateBackToCoursePage() {
        navigateBackFromStudentPage();
        navigateBackFromGroupPage();
    }

    public void emptySelectedStudent() {
        selectedStudent = emptyStudentBuilder();
    }
}
