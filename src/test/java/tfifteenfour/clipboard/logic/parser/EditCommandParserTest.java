// package tfifteenfour.clipboard.logic.parser;

// import static tfifteenfour.clipboard.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
// import static tfifteenfour.clipboard.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
// import static tfifteenfour.clipboard.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
// import static tfifteenfour.clipboard.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
// import static tfifteenfour.clipboard.logic.commands.CommandTestUtil.INVALID_MODULE_DESC;
// import static tfifteenfour.clipboard.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
// import static tfifteenfour.clipboard.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
// import static tfifteenfour.clipboard.logic.commands.CommandTestUtil.INVALID_STUDENTID_DESC;
// import static tfifteenfour.clipboard.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
// import static tfifteenfour.clipboard.logic.commands.CommandTestUtil.MODULE_DESC_CS2103;
// import static tfifteenfour.clipboard.logic.commands.CommandTestUtil.MODULE_DESC_CS2105;
// import static tfifteenfour.clipboard.logic.commands.CommandTestUtil.NAME_DESC_AMY;
// import static tfifteenfour.clipboard.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
// import static tfifteenfour.clipboard.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
// import static tfifteenfour.clipboard.logic.commands.CommandTestUtil.STUDENTID_DESC_AMY;
// import static tfifteenfour.clipboard.logic.commands.CommandTestUtil.STUDENTID_DESC_BOB;
// import static tfifteenfour.clipboard.logic.commands.CommandTestUtil.TAG_DESC_TEAM1;
// import static tfifteenfour.clipboard.logic.commands.CommandTestUtil.TAG_DESC_TEAM2;
// import static tfifteenfour.clipboard.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
// import static tfifteenfour.clipboard.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
// import static tfifteenfour.clipboard.logic.commands.CommandTestUtil.VALID_MODULE_CS2103;
// import static tfifteenfour.clipboard.logic.commands.CommandTestUtil.VALID_MODULE_CS2105;
// import static tfifteenfour.clipboard.logic.commands.CommandTestUtil.VALID_NAME_AMY;
// import static tfifteenfour.clipboard.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
// import static tfifteenfour.clipboard.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
// import static tfifteenfour.clipboard.logic.commands.CommandTestUtil.VALID_STUDENTID_AMY;
// import static tfifteenfour.clipboard.logic.commands.CommandTestUtil.VALID_STUDENTID_BOB;
// import static tfifteenfour.clipboard.logic.commands.CommandTestUtil.VALID_TAG_TEAM1;
// import static tfifteenfour.clipboard.logic.commands.CommandTestUtil.VALID_TAG_TEAM2;
// import static tfifteenfour.clipboard.logic.parser.CliSyntax.PREFIX_COURSE;
// import static tfifteenfour.clipboard.logic.parser.CliSyntax.PREFIX_TAG;
// import static tfifteenfour.clipboard.logic.parser.CommandParserTestUtil.assertParseFailure;
// import static tfifteenfour.clipboard.logic.parser.CommandParserTestUtil.assertParseSuccess;
// import static tfifteenfour.clipboard.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
// import static tfifteenfour.clipboard.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
// import static tfifteenfour.clipboard.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

// import org.junit.jupiter.api.Test;

// import tfifteenfour.clipboard.commons.core.index.Index;
// import tfifteenfour.clipboard.logic.commands.editcommand.EditCommand;
// import tfifteenfour.clipboard.logic.parser.EditCommandParser.EditStudentDescriptor;
// import tfifteenfour.clipboard.model.course.Course;
// import tfifteenfour.clipboard.model.student.Email;
// import tfifteenfour.clipboard.model.student.Name;
// import tfifteenfour.clipboard.model.student.Phone;
// import tfifteenfour.clipboard.model.student.StudentId;
// import tfifteenfour.clipboard.model.tag.Tag;
// import tfifteenfour.clipboard.testutil.EditStudentDescriptorBuilder;

// public class EditCommandParserTest {

//     private static final String TAG_EMPTY = " " + PREFIX_TAG;
//     private static final String MODULE_EMPTY = " " + PREFIX_COURSE;

//     private static final String MESSAGE_INVALID_FORMAT =
//             String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

//     private EditCommandParser parser = new EditCommandParser();

//     @Test
//     public void parse_missingParts_failure() {
//         // no index specified
//         assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

//         // no field specified
//         assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

//         // no index and no field specified
//         assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
//     }

//     @Test
//     public void parse_invalidPreamble_failure() {
//         // negative index
//         assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

//         // zero index
//         assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

//         // invalid arguments being parsed as preamble
//         assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

//         // invalid prefix being parsed as preamble
//         assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
//     }

//     @Test
//     public void parse_invalidValue_failure() {
//         assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
//         assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
//         assertParseFailure(parser, "1" + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email
//         assertParseFailure(parser, "1" + INVALID_STUDENTID_DESC, StudentId.MESSAGE_CONSTRAINTS); // invalid sid
//         assertParseFailure(parser, "1" + INVALID_MODULE_DESC, Course.MESSAGE_CONSTRAINTS); // inv mod code
//         assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

//         // invalid phone followed by valid email
//         assertParseFailure(parser, "1" + INVALID_PHONE_DESC + EMAIL_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

//         // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
//         // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
//         assertParseFailure(parser, "1" + PHONE_DESC_BOB + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

//         // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Student} being edited,
//         // parsing it together with a valid tag results in error
//         assertParseFailure(parser, "1" + TAG_DESC_TEAM1 + TAG_DESC_TEAM2 + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
//         assertParseFailure(parser, "1" + TAG_DESC_TEAM1 + TAG_EMPTY + TAG_DESC_TEAM2, Tag.MESSAGE_CONSTRAINTS);
//         assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_TEAM1 + TAG_DESC_TEAM2, Tag.MESSAGE_CONSTRAINTS);

//         // multiple invalid values, but only the first invalid value is captured
//         assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_STUDENTID_AMY
//                         + VALID_PHONE_AMY, Name.MESSAGE_CONSTRAINTS);
//     }

//     @Test
//     public void parse_allFieldsSpecified_success() {
//         Index targetIndex = INDEX_SECOND_PERSON;
//         String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + TAG_DESC_TEAM1
//                 + EMAIL_DESC_AMY + STUDENTID_DESC_AMY + NAME_DESC_AMY + TAG_DESC_TEAM2
//                 + MODULE_DESC_CS2103;

//         EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder().withName(VALID_NAME_AMY)
//                 .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).withStudentId(VALID_STUDENTID_AMY)
//                 .withModules(VALID_MODULE_CS2103).withTags(VALID_TAG_TEAM1, VALID_TAG_TEAM2).build();
//         EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

//         assertParseSuccess(parser, userInput, expectedCommand);
//     }

//     @Test
//     public void parse_someFieldsSpecified_success() {
//         Index targetIndex = INDEX_FIRST_PERSON;
//         String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + EMAIL_DESC_AMY;

//         EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder().withPhone(VALID_PHONE_BOB)
//                 .withEmail(VALID_EMAIL_AMY).build();
//         EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

//         assertParseSuccess(parser, userInput, expectedCommand);
//     }

//     @Test
//     public void parse_oneFieldSpecified_success() {
//         // name
//         Index targetIndex = INDEX_THIRD_PERSON;
//         String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
//         EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder().withName(VALID_NAME_AMY).build();
//         EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
//         assertParseSuccess(parser, userInput, expectedCommand);

//         // phone
//         userInput = targetIndex.getOneBased() + PHONE_DESC_AMY;
//         descriptor = new EditStudentDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
//         expectedCommand = new EditCommand(targetIndex, descriptor);
//         assertParseSuccess(parser, userInput, expectedCommand);

//         // email
//         userInput = targetIndex.getOneBased() + EMAIL_DESC_AMY;
//         descriptor = new EditStudentDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
//         expectedCommand = new EditCommand(targetIndex, descriptor);
//         assertParseSuccess(parser, userInput, expectedCommand);

//         // student id
//         userInput = targetIndex.getOneBased() + STUDENTID_DESC_AMY;
//         descriptor = new EditStudentDescriptorBuilder().withStudentId(VALID_STUDENTID_AMY).build();
//         expectedCommand = new EditCommand(targetIndex, descriptor);
//         assertParseSuccess(parser, userInput, expectedCommand);

//         // modules
//         userInput = targetIndex.getOneBased() + MODULE_DESC_CS2103;
//         descriptor = new EditStudentDescriptorBuilder().withModules(VALID_MODULE_CS2103).build();
//         expectedCommand = new EditCommand(targetIndex, descriptor);
//         assertParseSuccess(parser, userInput, expectedCommand);

//         // tags
//         userInput = targetIndex.getOneBased() + TAG_DESC_TEAM1;
//         descriptor = new EditStudentDescriptorBuilder().withTags(VALID_TAG_TEAM1).build();
//         expectedCommand = new EditCommand(targetIndex, descriptor);
//         assertParseSuccess(parser, userInput, expectedCommand);
//     }

//     @Test
//     public void parse_multipleRepeatedFields_acceptsLast() {
//         Index targetIndex = INDEX_FIRST_PERSON;
//         String userInput = targetIndex.getOneBased() + PHONE_DESC_AMY + STUDENTID_DESC_AMY + EMAIL_DESC_AMY
//                 + TAG_DESC_TEAM1 + PHONE_DESC_AMY + STUDENTID_DESC_AMY + EMAIL_DESC_AMY + TAG_DESC_TEAM1
//                 + PHONE_DESC_BOB + STUDENTID_DESC_BOB + EMAIL_DESC_BOB + TAG_DESC_TEAM2
//                 + MODULE_DESC_CS2103 + MODULE_DESC_CS2105;

//         EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder().withPhone(VALID_PHONE_BOB)
//                 .withEmail(VALID_EMAIL_BOB).withStudentId(VALID_STUDENTID_BOB)
//                 .withTags(VALID_TAG_TEAM1, VALID_TAG_TEAM2)
//                 .withModules(VALID_MODULE_CS2103, VALID_MODULE_CS2105).build();
//         EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

//         assertParseSuccess(parser, userInput, expectedCommand);
//     }

//     @Test
//     public void parse_invalidValueFollowedByValidValue_success() {
//         // no other valid values specified
//         Index targetIndex = INDEX_FIRST_PERSON;
//         String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;
//         EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder().withPhone(VALID_PHONE_BOB).build();
//         EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
//         assertParseSuccess(parser, userInput, expectedCommand);

//         // other valid values specified
//         userInput = targetIndex.getOneBased() + EMAIL_DESC_BOB + INVALID_PHONE_DESC + STUDENTID_DESC_BOB
//                 + PHONE_DESC_BOB;
//         descriptor = new EditStudentDescriptorBuilder().withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
//                 .withStudentId(VALID_STUDENTID_BOB).build();
//         expectedCommand = new EditCommand(targetIndex, descriptor);
//         assertParseSuccess(parser, userInput, expectedCommand);
//     }

//     @Test
//     public void parse_resetTags_success() {
//         Index targetIndex = INDEX_THIRD_PERSON;
//         String userInput = targetIndex.getOneBased() + TAG_EMPTY;

//         EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder().withTags().build();
//         EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

//         assertParseSuccess(parser, userInput, expectedCommand);
//     }
// }
