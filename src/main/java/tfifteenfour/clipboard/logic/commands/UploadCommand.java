package tfifteenfour.clipboard.logic.commands;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import tfifteenfour.clipboard.logic.CurrentSelection;
import tfifteenfour.clipboard.logic.commands.exceptions.CommandException;
import tfifteenfour.clipboard.model.Model;

/**
 * Uploads a file to CLIpboard. If file already exists in CLIpboard, old file will be overwritten.
 * File will be saved to the data folder.
 */
public class UploadCommand extends Command {

    public static final String COMMAND_WORD = "upload";
    public static final String DESTINATION_FILEPATH = "./data";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Uploads a file from a specified absolute file path to CLIpboard.\n"
            + "Parameters: FILEPATH (must be a non-empty and valid file path)\n"
            + "Example: " + COMMAND_WORD + " /Users/AlexYeoh/Desktop/A0123456X.png";

    public static final String MESSAGE_INVALID_FILEPATH = "File path is not valid!";

    private final Path sourcePath;
    private final Path destPath;

    /**
     * Creates an UploadCommand to upload a file from a specified source to a specified destination
     *
     * @param sourcePath of target file that will be uploaded.
     * @param destPath of folder of where file will be uploaded to.
     */
    public UploadCommand(Path sourcePath, Path destPath) {
        super(true);
        requireNonNull(sourcePath);
        requireNonNull(destPath);
        this.sourcePath = sourcePath;
        this.destPath = destPath;
    }

    /**
     * Creates an UploadCommand to upload a file from a specified source to the default destination
     *
     * @param sourcePath of target file that will be uploaded.
     */
    public UploadCommand(Path sourcePath) {
        super(true);
        requireNonNull(sourcePath);
        this.sourcePath = sourcePath;
        this.destPath = Paths.get(DESTINATION_FILEPATH);
    }

    @Override
    public CommandResult execute(Model model, CurrentSelection currentSelection) throws CommandException {
        try {
            Path sourcePath = this.sourcePath;
            Path destPath = this.destPath;
            Files.copy(sourcePath, destPath.resolve(sourcePath.getFileName()), REPLACE_EXISTING);
            return new CommandResult(this, generateSuccessMessage(sourcePath), willModifyState);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_INVALID_FILEPATH);
        }
    }

    /**
     * Generates a command execution success message based on the
     * name of file that has been uploaded.
     * {@code addedPath}.
     */
    private String generateSuccessMessage(Path addedPath) {
        return "File " + addedPath.getFileName() + " successfully added to CLIpboard";
    }


    /**
     * Deletes the file that was uploaded by execute(), to support UndoCommand.
     *
     * @throws CommandException
     */
    public void deleteUploadedFile() throws CommandException {
        try {
            Path uploadedFilePath = destPath.resolve(sourcePath.getFileName());
            Files.delete(uploadedFilePath);
        } catch (IOException e) {
            throw new CommandException("Error deleting uploaded file");
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UploadCommand)) {
            return false;
        }

        // state check
        UploadCommand e = (UploadCommand) other;
        return sourcePath.equals(e.sourcePath) && destPath.equals(e.destPath);
    }

}
