package code;

public class Code {

    // Returns "Hello World!"
    public static String helloWorld() {
        return "Hello World!";
    }

    // Take a single-spaced <sentence>, and capitalize every <n> word starting with <offset>.
    public static String capitalizeEveryNthWord(String sentence, Integer offset, Integer n) {

        // Verify data
        if (null == sentence || sentence.isEmpty() ||
                null == offset || null == n) {
            return sentence;
        }

        // Create work areas
        StringBuffer sb = new StringBuffer();
        String[] words = sentence.split(" ");

        // Uppercase the correct words.
        for (int i = offset; i < words.length; i += n) {
            words[i] = words[i].substring(0, 1).toUpperCase() +
                    words[i].substring(1).toLowerCase();
        }

        // Add to buffer
        for (String word : words) {
            sb.append(word + " ");
        }

        // Remove last un-needed space
        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }

    // Determine if a number is prime
    public static Boolean isPrime(Integer n) {

        // I found this math formula on https://en.wikipedia.org/wiki/Primality_test
        // and converted it to java code
        if (n <= 1) {
            return false;
        }
        else if (n <= 3) {
            return true;
        }
        else if (n % 2 == 0 || n % 3 == 0) {
            return false;
        }
        int i = 5;
        while (i * i <= n) {
            if (n % i == 0 || n % (i + 2) == 0) {
                return false;
            }
            i = i + 6;
        }

        return true;
    }

    // Calculate the golden ratio.
    // Given two numbers a and b with a > b > 0, the ratio is b / a.
    // Let c = a + b, then the ratio c / b is closer to the golden ratio.
    // Let d = b + c, then the ratio d / c is closer to the golden ratio.
    // Let e = c + d, then the ratio e / d is closer to the golden ratio.
    // If you continue this process, the result will trend towards the golden ratio.
    public static Double goldenRatio(Double a, Double b) {

        // Validate data
        if (null == a || a == 0.0 || null == b || b == 0.0) {
            return 0.0;
        }

        // Test parametera (Golden Ration proximity)
        double ratioLow = 1.61800;
        double ratioHigh = 1.61806;

        double result = 0.0;
        double c = 0.0;

        // Average and divide
        for (int i = 0; i < 25; i++) {
            c = a + b;
            result = c / a;

            // Break if we hit between the parameters
            if (result >= ratioLow && result <= ratioHigh) {
                return result;
            }
            else {
                b = a;
                a = c;
            }
        }

        return result;
    }

    // Give the nth Fibionacci number
    // Starting with 1 and 1, a Fibionacci number is the sum of the previous two.
    public static Integer fibionacci(Integer n) {

        if (null == n) {
            return 0;
        }
        else if (n == 0) {
            return 0;
        }
        else if (n == 1) {
            return 1;
        }

        // work areas
        int fib = 0;
        int first = 0;
        int second = 1;

        // Loop and compute
        for (int i = 1; i < n; i++) {
            fib = first + second;
            first = second;
            second = fib;
        }

        return fib;
    }

    // Give the square root of a number
    // Using a binary search algorithm, search for the square root of a given number.
    // Do not use the built-in square root function.
    public static Double squareRoot(Double n) {

        // Validate data
        if (null == n) {
            return 0.0;
        }
        else if(n == 0) {
            return n;
        }
        else if(n < 0) {
            n = -n;
        }

        // Work
        double workRoot = 0.0;

        // Use divide and average formula
        double squareRoot = n / 2;
        do {
            workRoot = squareRoot;
            squareRoot = (workRoot + (n / workRoot)) /2;
        }
        while((workRoot - squareRoot) != 0);

        return squareRoot;
    }
}
