package tfifteenfour.clipboard.logic.commands;

import static java.util.Objects.requireNonNull;

import tfifteenfour.clipboard.model.Model;
import tfifteenfour.clipboard.model.Roster;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setRoster(new Roster());
        return new CommandResult(MESSAGE_SUCCESS, true);
    }
}
