package t154.CLIpboard.logic.commands;

import static t154.CLIpboard.logic.commands.CommandTestUtil.assertCommandFailure;
import static t154.CLIpboard.logic.commands.CommandTestUtil.assertCommandSuccess;
import static t154.CLIpboard.testutil.TypicalStudents.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import t154.CLIpboard.model.Model;
import t154.CLIpboard.model.ModelManager;
import t154.CLIpboard.model.UserPrefs;
import t154.CLIpboard.model.student.Student;
import t154.CLIpboard.testutil.StudentBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newStudent_success() {
        Student validStudent = new StudentBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addStudent(validStudent);

        assertCommandSuccess(new AddCommand(validStudent), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validStudent), expectedModel);
    }

    @Test
    public void execute_duplicateStudent_throwsCommandException() {
        Student studentInList = model.getAddressBook().getStudentList().get(0);
        assertCommandFailure(new AddCommand(studentInList), model, AddCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
