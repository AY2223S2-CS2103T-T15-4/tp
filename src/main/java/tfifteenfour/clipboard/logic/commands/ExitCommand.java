package tfifteenfour.clipboard.logic.commands;

import tfifteenfour.clipboard.logic.CurrentSelected;
import tfifteenfour.clipboard.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Address Book as requested ...";

    public ExitCommand() {
        super(false);
    }

    @Override
    public CommandResult execute(Model model, CurrentSelected currentSelected) {
        return new CommandResult(this, MESSAGE_EXIT_ACKNOWLEDGEMENT, willModifyState);
    }

}
