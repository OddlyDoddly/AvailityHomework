import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        while(true) {
            System.out.println("Please enter a string to be validated...");
            String input = scanner.nextLine();

            int unclosedParens = 0;
            for(int i = 0; i < input.length(); ++i) {
                char c = input.charAt(i);

                if(c == '(') {
                    unclosedParens++;
                } else if(c == ')') {
                    unclosedParens--;
                }
            }

            System.out.println("Your Input: " + input + "\n\n");

            if(unclosedParens == 0) {
                System.out.println("All parens have been properly opened and closed");
            } else if(unclosedParens < 0) {
                System.out.println("You have " + Math.abs(unclosedParens)  + " closed parenthesis which do not have a matching opening parenthesis");
            } else {
                System.out.println("You have " + unclosedParens  + " open parenthesis which do not have a matching closing parenthesis");
            }

            System.out.println("\n\n\n\n");
        }
    }
}
