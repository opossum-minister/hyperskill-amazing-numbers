package numbers;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.LongStream;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to Amazing Numbers!");
        printHelp();

        while (true) {
            System.out.printf("%nEnter a request: ");
            final var data = scanner.nextLine().split(" ");
            System.out.println();
            if (data[0].isBlank()) {
                printHelp();
                continue;
            }
            final var start = getNaturalNumber(data[0]);
            if (start == 0) {
                break;
            }
            if (start < 0) {
                System.out.println("This number is not natural!");
                printHelp();
                continue;
            }
            if (data.length == 1) {
                System.out.print(NumberProperties.fullProperties(start));
                continue;
            }
            final var count = getNaturalNumber(data[1]);
            if (count < 1) {
                System.out.println("The count should be a natural number.");
                printHelp();
                continue;
            }

            if (data.length == 2) {
                LongStream.range(start, start + count)
                        .mapToObj(NumberProperties::shortProperties)
                        .forEach(System.out::println);
                continue;
            }

            final var property = data[2].toUpperCase();
            final var notFound = NumberProperties.stream().map(Enum::name).noneMatch(property::equals);
            if (notFound) {
                System.out.printf("The property '%s' is incorrect.%n", property);
                System.out.println("Available properties: " + Arrays.toString(NumberProperties.values()));
                continue;
            }
            final var condition = NumberProperties.valueOf(property);
            LongStream.iterate(start, n -> n + 1)
                    .filter(condition)
                    .limit(count)
                    .mapToObj(NumberProperties::shortProperties)
                    .forEach(System.out::println);
        }
        System.out.println("Goodbye!");
    }

    private static long getNaturalNumber(final String input) {
        for (final char symbol : input.toCharArray()) {
            if (!Character.isDigit(symbol)) {
                return -1;
            }
        }
        return Long.parseLong(input);
    }

    private static void printHelp() {
        System.out.println();
        System.out.println("Supported requests:");
        System.out.println("- one natural number to print its properties;");
        System.out.println("- two natural numbers separated by space:");
        System.out.println("  - a starting number for the list;");
        System.out.println("  - a count of numbers in the list;");
        System.out.println("- two natural numbers and property to search for;");
        System.out.println("- 0 for the exit. ");
    }
}