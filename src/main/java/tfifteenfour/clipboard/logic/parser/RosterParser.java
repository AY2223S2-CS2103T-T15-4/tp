package tfifteenfour.clipboard.logic.parser;

import static tfifteenfour.clipboard.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tfifteenfour.clipboard.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tfifteenfour.clipboard.logic.CurrentSelection;
import tfifteenfour.clipboard.logic.commands.BackCommand;
import tfifteenfour.clipboard.logic.commands.ClearCommand;
import tfifteenfour.clipboard.logic.commands.Command;
import tfifteenfour.clipboard.logic.commands.ExitCommand;
import tfifteenfour.clipboard.logic.commands.HelpCommand;
import tfifteenfour.clipboard.logic.commands.HomeCommand;
import tfifteenfour.clipboard.logic.commands.SelectCommand;
import tfifteenfour.clipboard.logic.commands.UndoCommand;
import tfifteenfour.clipboard.logic.commands.UploadCommand;
import tfifteenfour.clipboard.logic.commands.addcommand.AddCommand;
import tfifteenfour.clipboard.logic.commands.deletecommand.DeleteCommand;
import tfifteenfour.clipboard.logic.commands.studentcommands.EditCommand;
import tfifteenfour.clipboard.logic.commands.studentcommands.FindCommand;
import tfifteenfour.clipboard.logic.commands.studentcommands.RemarkCommand;
import tfifteenfour.clipboard.logic.commands.studentcommands.SortCommand;
import tfifteenfour.clipboard.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class RosterParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public static Command parseCommand(String userInput, CurrentSelection currentSelection) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {


        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        // case ModuleCommand.COMMAND_WORD:
        //     return new ModuleCommandParser().parse(arguments);

        // case ListCommand.COMMAND_WORD:
        //     return new ListCommand();

        // case ViewCommand.COMMAND_WORD:
        //     return new ViewCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case RemarkCommand.COMMAND_WORD:
            return new RemarkCommandParser().parse(arguments);

        case UploadCommand.COMMAND_WORD:
            return new UploadCommandParser().parse(arguments);

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case BackCommand.COMMAND_WORD:
            return new BackCommand(currentSelection);

        case HomeCommand.COMMAND_WORD:
            return new HomeCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
