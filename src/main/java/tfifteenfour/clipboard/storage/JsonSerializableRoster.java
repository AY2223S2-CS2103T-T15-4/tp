package tfifteenfour.clipboard.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import tfifteenfour.clipboard.commons.exceptions.IllegalValueException;
import tfifteenfour.clipboard.model.ReadOnlyRoster;
import tfifteenfour.clipboard.model.Roster;
import tfifteenfour.clipboard.model.student.Student;

/**
 * An Immutable Roster that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableRoster {

    public static final String MESSAGE_DUPLICATE_PERSON = "Students list contains duplicate student(s).";

    private final List<JsonAdaptedStudent> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableRoster} with the given persons.
     */
    @JsonCreator
    public JsonSerializableRoster(@JsonProperty("persons") List<JsonAdaptedStudent> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyRoster} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableRoster}.
     */
    public JsonSerializableRoster(ReadOnlyRoster source) {
        persons.addAll(source.getStudentList().stream().map(JsonAdaptedStudent::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code Roster} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Roster toModelType() throws IllegalValueException {
        Roster addressBook = new Roster();
        for (JsonAdaptedStudent jsonAdaptedStudent : persons) {
            Student student = jsonAdaptedStudent.toModelType();
            if (addressBook.hasStudent(student)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addStudent(student);
        }
        return addressBook;
    }

}
