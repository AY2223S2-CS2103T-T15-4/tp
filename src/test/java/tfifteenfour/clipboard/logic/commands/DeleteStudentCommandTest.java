package tfifteenfour.clipboard.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tfifteenfour.clipboard.logic.commands.CommandTestUtil.assertCommandFailure;
import static tfifteenfour.clipboard.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tfifteenfour.clipboard.logic.commands.CommandTestUtil.showStudentAtIndex;
import static tfifteenfour.clipboard.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static tfifteenfour.clipboard.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static tfifteenfour.clipboard.testutil.TypicalStudents.getTypicalRoster;

import org.junit.jupiter.api.Test;

import tfifteenfour.clipboard.commons.core.Messages;
import tfifteenfour.clipboard.commons.core.index.Index;
import tfifteenfour.clipboard.logic.commands.deleteCommand.DeleteStudentCommand;
import tfifteenfour.clipboard.model.Model;
import tfifteenfour.clipboard.model.ModelManager;
import tfifteenfour.clipboard.model.UserPrefs;
import tfifteenfour.clipboard.model.student.Student;


/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteStudentCommand}.
 */
public class DeleteStudentCommandTest {

    private Model model = new ModelManager(getTypicalRoster(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Student studentToDelete = model.getUnmodifiableFilteredStudentList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteStudentCommand deleteCommand = new DeleteStudentCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteStudentCommand.MESSAGE_SUCCESS, studentToDelete);

        ModelManager expectedModel = new ModelManager(model.getRoster(), new UserPrefs());
        expectedModel.deleteStudent(studentToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getUnmodifiableFilteredStudentList().size() + 1);
        DeleteStudentCommand deleteCommand = new DeleteStudentCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showStudentAtIndex(model, INDEX_FIRST_PERSON);

        Student studentToDelete = model.getUnmodifiableFilteredStudentList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteStudentCommand deleteCommand = new DeleteStudentCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteStudentCommand.MESSAGE_SUCCESS, studentToDelete);

        Model expectedModel = new ModelManager(model.getRoster(), new UserPrefs());
        expectedModel.deleteStudent(studentToDelete);
        showNoStudent(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showStudentAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getRoster().getUnmodifiableStudentList().size());

        DeleteStudentCommand deleteCommand = new DeleteStudentCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteStudentCommand deleteFirstCommand = new DeleteStudentCommand(INDEX_FIRST_PERSON);
        DeleteStudentCommand deleteSecondCommand = new DeleteStudentCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteStudentCommand deleteFirstCommandCopy = new DeleteStudentCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different student -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoStudent(Model model) {
        model.updateFilteredStudentList(p -> false);

        assertTrue(model.getUnmodifiableFilteredStudentList().isEmpty());
    }
}
