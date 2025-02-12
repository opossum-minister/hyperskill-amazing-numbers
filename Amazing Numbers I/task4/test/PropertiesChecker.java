import util.AbstractChecker;
import util.UserProgram;

public class PropertiesChecker extends AbstractChecker {
    private final long number;

    public PropertiesChecker(long number) {
        this.number = number;
        validator = this::test;
    }

    public boolean test(UserProgram program) {
        for (var property : NumberProperties.values()) {
            final var name = property.name();

            if (!program.getOutput().toLowerCase().contains(name.toLowerCase())) {
                feedback = "Property \"{0}\" not found in the output.";
                parameters = new Object[]{name};
                return false;
            }

            final var expected = property.test(number);
            final var actualValue = property.extractValue(program.getOutput());

            if (actualValue.isEmpty()) {
                feedback = "The value for property {0} was not found. Expected: {1}";
                parameters = new Object[]{name, expected};
                return false;
            }
            final var actual = actualValue.get();

            if (expected != actual) {
                feedback = "For property {0} the expected value is {1} but found {2}.";
                parameters = new Object[]{name, expected, actual};
                return false;
            }
        }
        return true;
    }
}
