import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import util.*;

import java.util.Random;
import java.util.stream.LongStream;

public final class NumbersTest extends StageTest {
    private static final Random random = new Random();
    private static final long RANDOM_TESTS = 20;
    private static final long FIRST_NUMBERS = 15;

    private static final Checker ASK_FOR_NUMBER = new RegexChecker(
            "enter( a)? natural number",
            "The program should ask the user to enter a natural number."
    );
    private static final Checker ERROR_MESSAGE = new RegexChecker(
            "number is( not|n't) natural",
            "Number {0} is not natural. The program should print an error message."
    );

    private final long[] notNaturalNumbers = {0, -1, -2, -3, -4, -5};

    @DynamicTest(data = "notNaturalNumbers", order = 5)
    CheckResult notNaturalNumbersTest(final long number) {
        return new UserProgram()
                .start()
                .check(ASK_FOR_NUMBER)
                .execute(number)
                .check(ERROR_MESSAGE)
                .finished()
                .result();
    }

    private long[] numbers() {
        return LongStream.concat(
                LongStream.range(1, FIRST_NUMBERS),
                random.longs(RANDOM_TESTS, 1, Short.MAX_VALUE)
        ).toArray();
    }

    @DynamicTest(data = "numbers", order = 10)
    CheckResult buzzTest(long number) {
        return new UserProgram()
                .start()
                .check(ASK_FOR_NUMBER)
                .execute(number)
                .check(new LinesChecker(4))
                .check(new RegexChecker("number is (even|odd)",
                        "The program should calculate and print the parity of the given number."))
                .contains(number % 2 == 0 ? "even" : "odd",
                        "Number's parity is incorrect. Number {0} should be {1}.")
                .check(new BuzzChecker(number))
                .finished()
                .result();
    }

}
