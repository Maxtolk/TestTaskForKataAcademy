import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

enum RomanNumeral {
    I(1), IV(4), V(5), IX(9), X(10),
    XL(40), L(50), XC(90), C(100);

    private int value;

    RomanNumeral(int value) {
        this.value = value;
    }

    public static List<RomanNumeral> getReverseSortedValues() {
        RomanNumeral[] romanVal = values();
        return Arrays.stream(romanVal)
                .sorted(Comparator.comparing((RomanNumeral e) -> e.value).reversed())
                .collect(Collectors.toList());
    }

    public int getValue() {
        return value;
    }
}

public class Calculate {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        String[] elements = scan.nextLine()
                .split(" ");

        if (elements.length != 3) {
            throw new IllegalArgumentException("Ошибка! Работа калькулятора прекращена! ");
        }

        String first = elements[0];
        String operation = elements[1];
        String second = elements[2];
        int a;
        int b;
        boolean firstArabic = false;
        boolean secondArabic = false;

        try {
            a = Integer.parseInt(first);
        } catch (NumberFormatException e) {
            a = romanToArabic(first);
            firstArabic = true;
        }

        try {
            b = Integer.parseInt(second);
        } catch (NumberFormatException e) {
            b = romanToArabic(second);
            secondArabic = true;
        }

        if (firstArabic != secondArabic) {
            throw new IllegalArgumentException("Ошибка! Используются разные системы счисления. ");
        }

        if (a < 1 || a > 10 || b < 1 || b > 10) {
            throw new IllegalArgumentException("Числа должны быть меньше 10! Работа калькулятора прекращена.");
        }

        switch (operation) {
            case "+" -> addition(a, firstArabic, b);
            case "-" -> substraction(a, firstArabic, b);
            case "*" -> multiplication(a, firstArabic, b);
            case "/" -> division(a, firstArabic, b);
            default -> throw new IllegalArgumentException("Ошибка! Неизвестная операция " + "\'" + operation + "\'");
        }
    }

    private static void division(Integer a, boolean firstArabic, Integer b) {
        int res = a / b;
        if (firstArabic) {
            if (res == 0) {
                throw new IllegalArgumentException("Римские числа должны быть положительными!");
            }
            System.out.println(getRomanNumber(res));
        } else {
            System.out.println(res);
        }
    }

    private static void multiplication(Integer a, boolean firstArabic, Integer b) {
        int res = a * b;
        if (firstArabic) {
            System.out.println(getRomanNumber(res));
        } else {
            System.out.println(res);
        }
    }

    private static void substraction(Integer a, boolean firstArabic, Integer b) {
        int res = a - b;
        if (firstArabic) {
            if (res <= 0) {
                throw new IllegalArgumentException("Римские числа не могут быть отрицательными!");
            }
            System.out.println(getRomanNumber(res));
        } else {
            System.out.println(res);
        }
    }

    private static void addition(Integer a, boolean firstArabic, Integer b) {
        int res = a + b;
        if (firstArabic) {
            System.out.println(getRomanNumber(res));
        } else {
            System.out.println(res);
        }
    }

    public static int romanToArabic(String input) {
        String romanNumeral = input.toUpperCase();
        int result = 0;

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();
        int i = 0;

        while ((romanNumeral.length() > 0) && (i < romanNumerals.size())) {
            RomanNumeral symbol = romanNumerals.get(i);
            if (romanNumeral.startsWith(symbol.name())) {
                result += symbol.getValue();
                romanNumeral = romanNumeral.substring(symbol.name().length());
            } else {
                i++;
            }
        }

        if (romanNumeral.length() > 0) {
            throw new IllegalArgumentException(input + " Ошибка! Неверный формат числа ");
        }

        return result;
    }

    public static String getRomanNumber(int number) {
        return "I".repeat(number)
                .replace("IIIII", "V")
                .replace("IIII", "IV")
                .replace("VV", "X")
                .replace("VIV", "IX")
                .replace("XXXXX", "L")
                .replace("XXXX", "XL")
                .replace("LL", "C")
                .replace("LXL", "XC");
    }
}
