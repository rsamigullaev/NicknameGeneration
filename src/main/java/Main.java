import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger counter1 = new AtomicInteger();
    public static AtomicInteger counter2 = new AtomicInteger();
    public static AtomicInteger counter3 = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        Thread thread1 = new Thread(() -> {
            for (String text : texts) {
                if (isPalindrome(text) && !isSameChar(text)) {
                    isCounter(text.length());
                }
            }
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            for (String text : texts) {
                if (isSameChar(text)) {
                    isCounter(text.length());
                }
            }
        });
        thread2.start();

        Thread thread3 = new Thread(() -> {
            for (String text : texts) {
                if (isSameChar(text) && isAscendingOrder(text)) {
                    isCounter(text.length());
                }
            }
        });
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();

        System.out.println("Красивых слов с длиной 3: " + counter1 + " шт");
        System.out.println("Красивых слов с длиной 4: " + counter2 + " шт");
        System.out.println("Красивых слов с длиной 5: " + counter3 + " шт");
    }

    public static boolean isPalindrome(String text) {
        return text.equals(new StringBuilder(text).reverse().toString());
    }

    public static boolean isSameChar(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) != text.charAt(i - 1))
                return false;
        }
        return true;
    }

    public static boolean isAscendingOrder(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) < text.charAt(i - 1))
                return false;
        }
        return true;
    }

    public static void isCounter(int textLength) {
        if (textLength == 3) {
            counter1.getAndIncrement();
        } else if (textLength == 4) {
            counter2.getAndIncrement();
        } else {
            counter3.getAndIncrement();
        }
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}
