import java.io.IOException;
import java.util.Scanner;

public class Funkce {
	static Scanner sc = new Scanner(System.in);

	public static String checkString(String errorMessage) {
		String userInput = sc.nextLine().trim();
		while (userInput.isEmpty()) {
			System.out.println(errorMessage);
			userInput = sc.nextLine().trim();
		}
		return userInput;
	}

	public static int checkIntNumber(int min, int max) {
		Scanner sc = new Scanner(System.in);
		int input = 0;
		boolean validInput = false;
		while (!validInput) {
			if (sc.hasNextInt()) {
				input = sc.nextInt();
				if ((input >= min) & (input <= max)) {
					validInput = true;
				} else {
					System.out.println("Zadali jste špatnou vstupní hodnotu! Zkuste to znovu.");
					System.out.println("Musíte zadat hodnotu od " + min + " do " + max);
				}
			} else {
				System.out.println("Zadali jste špatný typ vstupu! Zkuste to znovu.");
				System.out.println("Musíte zadat celé číslo.");
				sc.next();
			}
		}
		return input;
	}

	public static String vyberZanr() {
		String zanr = "";
		System.out.println("\033[32mVyberte si jeden z žánrů: \033[0m");
		System.out.println("\033[32m+--------------------+\033[0m");
		System.out.println("\033[32m| 1.       KOMEDIE   |\033[0m");
		System.out.println("\033[32m| 2.       DRAMA     |\033[0m");
		System.out.println("\033[32m| 3.       AKCNI     |\033[0m");
		System.out.println("\033[32m| 4.       HORROR    |\033[0m");
		System.out.println("\033[32m| 5.       FANTASY   |\033[0m");
		System.out.println("\033[32m+--------------------+\033[0m");

		System.out.println("Váš výběr: ");
		int vyber = checkIntNumber(1, 5);
		switch (vyber) {
		case 1:
			zanr = "Komedie";
			System.out.println("\033[32mVybral jste žánr: \033[0m" + zanr);
			break;
		case 2:
			zanr = "Drama";
			System.out.println("\033[32mVybral jste žánr: \033[0m" + zanr);
			break;
		case 3:
			zanr = "Akcni";
			System.out.println("\033[32mVybral jste žánr: \033[0m" + zanr);
			break;
		case 4:
			zanr = "Horror";
			System.out.println("\033[32mVybral jste žánr: \033[0m" + zanr);
			break;
		case 5:
			zanr = "Fantasy";
			System.out.println("\033[32mVybral jste žánr: \033[0m" + zanr);
			break;
		}
		return zanr;
	}

	public static void waitTime(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void stiskEnter() {
		System.out.println("\n\n\033[32mStisknutím klávesy 'ENTER' se vrátíte do menu.\033[0m");
		try {
			int back = System.in.read(new byte[2]);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
