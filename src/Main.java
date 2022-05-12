import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    static final String ROMAN_NUMBERS = "^(M{0,4})(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$";
    static final String ARAB_POS_NUMBERS = "[0-9]+";
    static final int[] decimal = {1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};
    static final String[] letters = {"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M"};

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String inputString = null;
        System.out.println("> Введите выражение: ");
        do {

            inputString = scanner.nextLine();
            inputListen(inputString);

        } while (!inputString.matches("(^exit)"));



    }

    static String calc(String input) {

        String[] inputNumbers = input.split("\\s+"); //использую разделитель ОДИН ИЛИ БОЛЕЕ пробелов для комфорта. как в приведенных к заданию примерах

        int number1 = 0;
        int number2 = 0;


        if (inputNumbers.length < 3)
            throw new IllegalArgumentException("Cтрока не является математической операцией");

        if (inputNumbers.length > 3)
            throw new IllegalArgumentException("Формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");

        if (!inputNumbers[1].matches("[-+/*]"))
            throw new IllegalArgumentException("Неправильный оператор.");

        if (inputNumbers[0].matches(ARAB_POS_NUMBERS) && inputNumbers[2].matches(ARAB_POS_NUMBERS)) {

            number1 = Integer.parseInt(inputNumbers[0]);
            number2 = Integer.parseInt(inputNumbers[2]);

            if (number2 < 1 || number2 > 10 || number1 < 1 || number1 > 10)
                throw new IllegalArgumentException("Неверный операнд(за пределами условий задачи).");

            System.out.println("Введены валидные арабские цифры.");
            return String.valueOf(calculateArabNumbers(number1, number2, inputNumbers[1]));

        } else if (inputNumbers[0].matches(ROMAN_NUMBERS) && inputNumbers[2].matches(ROMAN_NUMBERS)) {

            number1 = romanToInteger(inputNumbers[0]);
            number2 = romanToInteger(inputNumbers[2]);

            if (number2 < 1 || number2 > 10 || number1 < 1 || number1 > 10)
                throw new IllegalArgumentException("Неверный операнд(за пределами условий задачи).");

            System.out.println("Введены валидные римские цифры.");

            int checkResult = calculateArabNumbers(number1,number2,inputNumbers[1]);

            if (checkResult < 1) throw new IllegalArgumentException("Результат за пределами условий задачи (меньше единицы).");

            return integerToRoman(checkResult);

        } else
            throw new IllegalArgumentException("Неверный операнд(необходимо чтобы оба числа были либо римскими, либо арабскими числами).");
    }

    static int calculateArabNumbers(int number1, int number2, String operand) {

        return switch (operand) {
            case "+" -> number1 + number2;
            case "-" -> number1 - number2;
            case "/" -> number1 / number2;
            case "*" -> number1 * number2;
            default -> 0;
        };
    }


    public static int romanToInteger(String roman) {
        Map<Character, Integer> numbersMap = new HashMap<>();
        numbersMap.put('I', 1);
        numbersMap.put('V', 5);
        numbersMap.put('X', 10);
        numbersMap.put('L', 50);
        numbersMap.put('C', 100);
        numbersMap.put('D', 500);
        numbersMap.put('M', 1000);

        int result = 0;

        for (int i = 0; i < roman.length(); i++) {
            char ch = roman.charAt(i);      //Current Roman Character

            //Case 1
            if (i > 0 && numbersMap.get(ch) > numbersMap.get(roman.charAt(i - 1))) {
                result += numbersMap.get(ch) - 2 * numbersMap.get(roman.charAt(i - 1));
            }

            //Case 2: just add the corresponding number to result.
            else
                result += numbersMap.get(ch);
        }

        return result;
    }

    static String integerToRoman(int num) {
        String roman = "";

        if (num < 1 || num > 3999) {
            System.out.println("Ошибка результата. Римское число вне диапазона 1 - 3999");
        }

        while (num > 0) {
            int maxFound = 0;
            for (int i = 0; i < decimal.length; i++) {
                if (num >= decimal[i]) {
                    maxFound = i;
                }
            }
            roman += letters[maxFound];
            num -= decimal[maxFound];
        }

        return roman;
    }


    static void inputListen(String inputString) {

        if (!inputString.matches("(^exit)")) {
            System.out.print("> Проверка ввода: ");
            System.out.println("> Результат: " + calc(inputString));
        }
    }

}









