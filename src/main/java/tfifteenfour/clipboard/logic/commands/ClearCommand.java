package tfifteenfour.clipboard.logic.commands;

import static java.util.Objects.requireNonNull;

import tfifteenfour.clipboard.logic.CurrentSelected;
import tfifteenfour.clipboard.model.Model;
import tfifteenfour.clipboard.model.Roster;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";

    public ClearCommand() {
        super(false);
    }

    @Override
    public CommandResult execute(Model model, CurrentSelected currentSelected) {
        requireNonNull(model);
        model.setRoster(new Roster());
        return new CommandResult(this, MESSAGE_SUCCESS, willModifyState);
    }
}
