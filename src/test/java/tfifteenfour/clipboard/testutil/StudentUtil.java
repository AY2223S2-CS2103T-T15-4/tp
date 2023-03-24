package tfifteenfour.clipboard.testutil;

import static tfifteenfour.clipboard.logic.parser.CliSyntax.PREFIX_COURSE;
import static tfifteenfour.clipboard.logic.parser.CliSyntax.PREFIX_EMAIL;
import static tfifteenfour.clipboard.logic.parser.CliSyntax.PREFIX_NAME;
import static tfifteenfour.clipboard.logic.parser.CliSyntax.PREFIX_PHONE;
import static tfifteenfour.clipboard.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static tfifteenfour.clipboard.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import tfifteenfour.clipboard.logic.commands.addCommand.AddCommand;
import tfifteenfour.clipboard.logic.commands.studentCommands.EditCommand.EditStudentDescriptor;
import tfifteenfour.clipboard.model.course.Course;
import tfifteenfour.clipboard.model.student.Student;
import tfifteenfour.clipboard.model.tag.Tag;

/**
 * A utility class for Student.
 */
public class StudentUtil {

    /**
     * Returns an add command string for adding the {@code student}.
     */
    public static String getAddCommand(Student student) {
        return AddCommand.COMMAND_WORD + " " + getStudentDetails(student);
    }

    /**
     * Returns the part of command string for the given {@code student}'s details.
     */
    public static String getStudentDetails(Student student) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + student.getName().fullName + " ");
        sb.append(PREFIX_PHONE + student.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + student.getEmail().value + " ");
        sb.append(PREFIX_STUDENTID + student.getStudentId().value + " ");
        // student.getModules().stream().forEach(
        //         m -> sb.append(PREFIX_COURSE + m.courseCode + " ")
        // );
        // student.getTags().stream().forEach(
        //     s -> sb.append(PREFIX_TAG + s.tagName + " ")
        // );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditStudentDescriptor}'s details.
     */
    public static String getEditStudentDescriptorDetails(EditStudentDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getStudentId().ifPresent(address -> sb.append(PREFIX_STUDENTID).append(address.value).append(" "));

        Set<Course> modules = descriptor.getModules().get();
        modules.forEach(s -> sb.append(PREFIX_COURSE).append(s.courseCode).append(" "));

        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
