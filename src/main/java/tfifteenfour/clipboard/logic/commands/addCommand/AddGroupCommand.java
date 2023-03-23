package tfifteenfour.clipboard.logic.commands.addCommand;

import static java.util.Objects.requireNonNull;

import tfifteenfour.clipboard.logic.CurrentSelected;
import tfifteenfour.clipboard.logic.commands.CommandResult;
import tfifteenfour.clipboard.logic.commands.exceptions.CommandException;
import tfifteenfour.clipboard.model.Model;
import tfifteenfour.clipboard.model.course.Course;
import tfifteenfour.clipboard.model.course.Group;

public class AddGroupCommand extends AddCommand {
	public static final String COMMAND_TYPE_WORD = "group";
	public static final String MESSAGE_USAGE = COMMAND_WORD + " " + COMMAND_TYPE_WORD
			+ ": Adds a group to the selected course. "
            + "Parameters: "
            + "GROUP_NAME\n"
            + "Example: " + COMMAND_WORD + " " + COMMAND_TYPE_WORD
            + " " + " T15-4";

    public static final String MESSAGE_SUCCESS = "New group added in %1$s: %2$s";
    public static final String MESSAGE_DUPLICATE_GROUP = "This group already exists in the course";

	private final Group groupToAdd;

	public AddGroupCommand(Group group) {
		this.groupToAdd = group;
	}

	public CommandResult execute(Model model, CurrentSelected currentSelected) throws CommandException {
		requireNonNull(model);

		Course targetCourse = currentSelected.getSelectedCourse();
		if (targetCourse.hasGroup(groupToAdd)) {
			throw new CommandException(MESSAGE_DUPLICATE_GROUP);
		}

		targetCourse.addGroup(groupToAdd);
		return new CommandResult(this, String.format(MESSAGE_SUCCESS, targetCourse, groupToAdd), willModifyState);
	}

	@Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddGroupCommand // instanceof handles nulls
                && groupToAdd.equals(((AddGroupCommand) other).groupToAdd));
    }
}
