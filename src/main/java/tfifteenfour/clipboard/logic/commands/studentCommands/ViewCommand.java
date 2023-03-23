package tfifteenfour.clipboard.logic.commands.studentCommands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import tfifteenfour.clipboard.commons.core.Messages;
import tfifteenfour.clipboard.commons.core.index.Index;
import tfifteenfour.clipboard.logic.commands.Command;
import tfifteenfour.clipboard.logic.commands.CommandResult;
import tfifteenfour.clipboard.logic.commands.exceptions.CommandException;
import tfifteenfour.clipboard.model.Model;
import tfifteenfour.clipboard.model.student.Student;

/**
 * Views a student in the student list.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views the student identified by the index number used in the displayed student list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final Index targetIndex;

    /**
     * Creates a ViewCommand to view a student at the specified index
     */
    public ViewCommand(Index targetIndex) {
        super(false);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // refresh list before viewing
        List<Student> studentList = model.getUnmodifiableFilteredStudentList();

        if (targetIndex.getZeroBased() >= studentList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Student studentToView = studentList.get(targetIndex.getZeroBased());
        //model.updateFilteredStudentList(isTargetStudent(studentToView));
        //model.updateViewedStudent(studentToView);
        model.updateViewedStudent(isTargetStudent(studentToView));

        return new CommandResult(this, generateSuccessMessage(studentToView), willModifyState);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewCommand)) {
            return false;
        }

        // state check
        ViewCommand e = (ViewCommand) other;
        return targetIndex.equals(e.targetIndex);
    }

    private String generateSuccessMessage(Student student) {
        return String.format("Viewing: %s, (%s)", student.getName(), student.getStudentId());
    }

    private Predicate<Student> isTargetStudent(Student targetStudent) {
        return student -> student.isSameStudent(targetStudent);
    }
}
