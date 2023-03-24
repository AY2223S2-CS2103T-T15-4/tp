// ####### NOT IN USE ###############

// package tfifteenfour.clipboard.storage;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static tfifteenfour.clipboard.storage.JsonAdaptedStudent.MISSING_FIELD_MESSAGE_FORMAT;
// import static tfifteenfour.clipboard.testutil.Assert.assertThrows;
// import static tfifteenfour.clipboard.testutil.TypicalStudents.BENSON;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.stream.Collectors;

// import org.junit.jupiter.api.Test;

// import tfifteenfour.clipboard.commons.exceptions.IllegalValueException;
// import tfifteenfour.clipboard.model.student.Email;
// import tfifteenfour.clipboard.model.student.Name;
// import tfifteenfour.clipboard.model.student.Phone;
// import tfifteenfour.clipboard.model.student.StudentId;

// public class JsonAdaptedStudentTest {
//     private static final String INVALID_NAME = "R@chel";
//     private static final String INVALID_PHONE = "+651234";
//     private static final String INVALID_ADDRESS = " ";
//     private static final String INVALID_EMAIL = "example.com";
//     private static final String INVALID_TAG = "#friend";

//     private static final String VALID_NAME = BENSON.getName().toString();
//     private static final String VALID_PHONE = BENSON.getPhone().toString();
//     private static final String VALID_EMAIL = BENSON.getEmail().toString();
//     private static final String VALID_ADDRESS = BENSON.getStudentId().toString();
//     private static final List<JsonAdaptedModuleCode> VALID_MODULES = BENSON.getModules().stream()
//             .map(JsonAdaptedModuleCode::new)
//             .collect(Collectors.toList());
//     private static final String VALID_REMARK = BENSON.getRemark().toString();
//     private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
//             .map(JsonAdaptedTag::new)
//             .collect(Collectors.toList());

//     @Test
//     public void toModelType_validStudentDetails_returnsStudent() throws Exception {
//         JsonAdaptedStudent student = new JsonAdaptedStudent(BENSON);
//         assertEquals(BENSON, student.toModelType());
//     }

//     @Test
//     public void toModelType_invalidName_throwsIllegalValueException() {
//         JsonAdaptedStudent student =
//                 new JsonAdaptedStudent(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_MODULES,
//                         VALID_REMARK, VALID_TAGS);
//         String expectedMessage = Name.MESSAGE_CONSTRAINTS;
//         assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
//     }

//     @Test
//     public void toModelType_nullName_throwsIllegalValueException() {
//         JsonAdaptedStudent student = new JsonAdaptedStudent(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
//                 VALID_MODULES, VALID_REMARK, VALID_TAGS);
//         String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
//         assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
//     }

//     @Test
//     public void toModelType_invalidPhone_throwsIllegalValueException() {
//         JsonAdaptedStudent student =
//                 new JsonAdaptedStudent(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_MODULES,
//                         VALID_REMARK, VALID_TAGS);
//         String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
//         assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
//     }

//     @Test
//     public void toModelType_nullPhone_throwsIllegalValueException() {
//         JsonAdaptedStudent student = new JsonAdaptedStudent(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS,
//                 VALID_MODULES, VALID_REMARK, VALID_TAGS);
//         String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
//         assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
//     }

//     @Test
//     public void toModelType_invalidEmail_throwsIllegalValueException() {
//         JsonAdaptedStudent student =
//                 new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_MODULES,
//                         VALID_REMARK, VALID_TAGS);
//         String expectedMessage = Email.MESSAGE_CONSTRAINTS;
//         assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
//     }

//     @Test
//     public void toModelType_nullEmail_throwsIllegalValueException() {
//         JsonAdaptedStudent student = new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS,
//                 VALID_MODULES, VALID_REMARK, VALID_TAGS);
//         String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
//         assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
//     }

//     @Test
//     public void toModelType_invalidAddress_throwsIllegalValueException() {
//         JsonAdaptedStudent student =
//                 new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_MODULES,
//                         VALID_REMARK, VALID_TAGS);
//         String expectedMessage = StudentId.MESSAGE_CONSTRAINTS;
//         assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
//     }

//     @Test
//     public void toModelType_nullAddress_throwsIllegalValueException() {
//         JsonAdaptedStudent student = new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_EMAIL, null,
//                 VALID_MODULES, VALID_REMARK, VALID_TAGS);
//         String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, StudentId.class.getSimpleName());
//         assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
//     }

//     @Test
//     public void toModelType_invalidTags_throwsIllegalValueException() {
//         List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
//         invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
//         JsonAdaptedStudent student =
//                 new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_MODULES,
//                         VALID_REMARK, invalidTags);
//         assertThrows(IllegalValueException.class, student::toModelType);
//     }
// }
