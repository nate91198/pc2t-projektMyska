import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		ArrayList<Kniha> knihy = new ArrayList<>();
		DatabaseManager dbManager = new DatabaseManager();

		String nazevKnihy = "-";
		String autor = "-";
		int rokVydani = 0;
		boolean dostupnost = false;
		String zanr = "-";
		int vhodneProRocnik = -1;
		dbManager.connect();
		dbManager.createTable();
		dbManager.loadData(knihy);
		dbManager.disconnect();

		System.out.println("\033[32m####################################\033[0m");
		System.out.println("\033[32m####################################\033[0m");
		System.out.println("\033[32m### APLIKACE PRO SPRÁVU KNIHOVNY ###\033[0m");
		System.out.println("\033[32m####################################\033[0m");
		System.out.println("\033[32m####################################\033[0m");
		Funkce.waitTime(2000);
		System.out.println("Probíhá načítání programu: 0%");
		for (int i = 1; i <= 9; i++) {
			System.out.println("Probíhá načítání programu: " + i + "0%");
			Funkce.waitTime(75);
		}
		boolean run = true;
		while (run) {
			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
			System.out.println("\033[32m#########################################\033[0m");
			System.out.println("\033[32m###               MENU                ###\033[0m");
			System.out.println("\033[32m#########################################\033[0m");
			System.out.println("\033[32m###  1.     Přidat novou knihu        ###\033[0m");
			System.out.println("\033[32m###  2.     Upravit knihu             ###\033[0m");
			System.out.println("\033[32m###  3.     Smazat knihu              ###\033[0m");
			System.out.println("\033[32m###  4.     Označit knihu             ###\033[0m");
			System.out.println("\033[32m###  5.     Vyhledat knihu            ###\033[0m");
			System.out.println("\033[32m###  6.     Vypsat všechny knihy      ###\033[0m");
			System.out.println("\033[32m###  7.     Vypsat knihy (Autor)      ###\033[0m");
			System.out.println("\033[32m###  8.     Vypsat knihy (Žánr)       ###\033[0m");
			System.out.println("\033[32m###  9.     Vypsat vypůjčené knihy    ###\033[0m");
			System.out.println("\033[32m###  10.    Uložit info do souboru    ###\033[0m");
			System.out.println("\033[32m###  11.    Načíst info ze souboru    ###\033[0m");
			System.out.println("\033[32m###  12.    Ukončit program           ###\033[0m");
			System.out.println("\033[32m#########################################\033[0m");// DATABAZE

			System.out.println("Váš výběr: ");

			int vyber = Funkce.checkIntNumber(1, 13);

			switch (vyber) {
			case 1:
				System.out.println("\033[32mVyberte typ nové knihy: \033[0m");
				System.out.println("1 .. Román\n2 .. Učebnice");
				int typ = Funkce.checkIntNumber(1, 2);
				if (typ == 1)
					System.out.println("Vybral jste Román");
				else
					System.out.println("Vybral jste Učebnici");

				System.out.println("\033[32mZadejte název nové knihy: \033[0m");
				nazevKnihy = Funkce.checkString("Zadal jste špatný název knihy");

				for (int i = 0; i < knihy.size(); i++) {
					if (knihy.get(i).getNazevKnihy().equals(nazevKnihy)) {
						System.out.println("\033[32mTato kniha již existuje!\033[0m");
						Funkce.waitTime(1200);
					}
				}
				System.out.println("\033[32mZadejte jméno autora: \033[0m");
				autor = Funkce.checkString("Zadal jste špatný název autora");

				System.out.println("\033[32mZadejte rok vydání knihy " + "'" + nazevKnihy + "': \033[0m");
				rokVydani = Funkce.checkIntNumber(1500, 2024);
				dostupnost = true;

				if (typ == 1) {
					zanr = Funkce.vyberZanr();

					knihy.add(new Roman(nazevKnihy, autor, rokVydani, dostupnost, zanr));
					System.out.println("\n\n\033[32mPřidal jste knihu!\033[0m");
					System.out.println("Název knihy: " + nazevKnihy);
					System.out.println("Autor: " + autor);
					System.out.println("Rok vydání: " + rokVydani);
					System.out.println("Žánr: " + zanr);

					Funkce.waitTime(1200);

				}

				else if (typ == 2) {
					System.out.println("\033[32mZadejte vhodný ročník pro učebnici: \033[0m" + nazevKnihy);
					vhodneProRocnik = Funkce.checkIntNumber(1, 150);

					knihy.add(new Ucebnice(nazevKnihy, autor, rokVydani, dostupnost, vhodneProRocnik));
					System.out.println("\n\n\033[32mPřidal jste knihu!\033[0m");
					System.out.println("Název knihy: " + nazevKnihy);
					System.out.println("Autor: " + autor);
					System.out.println("Rok vydání: " + rokVydani);
					System.out.println("Doporučeno pro věk: " + vhodneProRocnik);

					Funkce.waitTime(2000);
				}
				break;
			case 2:
				if (knihy.isEmpty()) {
					System.out.print("\033[32mV databázi nejsou žádné knihy!\033[0m");
					Funkce.waitTime(1200);
					break;
				}
				System.out.println("\033[32m#############################################\033[0m");
				System.out.println("Název knihy | Autor | Rok vydání | Dostupnost");
				System.out.println("\033[32m---------------------------------------------\033[0m");
				for (int i = 0; i < knihy.size(); i++) {
					nazevKnihy = knihy.get(i).getNazevKnihy();
					autor = knihy.get(i).getAutor();
					rokVydani = knihy.get(i).getRokVydani();
					dostupnost = knihy.get(i).getDostupnost();
					String dostupnostString;
					if (dostupnost == true) {
						dostupnostString = "K dispozici";
					} else {
						dostupnostString = "Vypůjčená";
					}
					System.out.println(
							(i + 1) + ". " + nazevKnihy + " | " + autor + " | " + rokVydani + " | " + dostupnostString);
				}
				System.out.println("\033[32m#############################################\033[0m");
				System.out.println("Váš výběr: ");
				vyber = Funkce.checkIntNumber(1, knihy.size());
				System.out.println("\033[32mJaký parametr chcete upravit u knihy: \033[0m" + nazevKnihy);
				System.out.println("1. Autor");
				System.out.println("2. Rok vydání");
				System.out.println("3. Dostupnost");
				System.out.println("4. Zpět do menu");

				System.out.println("Váš výběr: ");
				int vyberupravy = Funkce.checkIntNumber(1, 4);
				switch (vyberupravy) {
				case 1:
					System.out.println("\033[32mZadejte nového autora: \033[0m");
					autor = Funkce.checkString("Zadal jste špatný autora");
					knihy.get(vyber - 1).setAutor(autor);
					System.out.println("\n\n\033[32mUpravil jste autora knihy!\033[0m");
					Funkce.waitTime(1200);
					break;
				case 2:
					System.out.println("\033[32mZadejte nový rok vydání: \033[0m");
					rokVydani = Funkce.checkIntNumber(1500, 2024);
					knihy.get(vyber - 1).setRokVydani(rokVydani);
					System.out.println("\n\n\033[32mUpravil jste rok vydání knihy!\033[0m");
					Funkce.waitTime(1200);
					break;
				case 3:
					knihy.get(vyber - 1).changeDostupnost();
					System.out.print("\n\n\033[32mZměnil jste dostupnost knihy! Stav knihy je nyní: \033[0m");
					String dostupnostString = "";
					dostupnost = knihy.get(vyber - 1).getDostupnost();
					if (dostupnost == true) {
						dostupnostString = "K dispozici";
					} else {
						dostupnostString = "Vypůjčená";
					}
					System.out.println(dostupnostString);
					Funkce.waitTime(1200);
					break;
				case 4:
					break;
				}

				break;
			case 3:
				if (knihy.isEmpty()) {
					System.out.print("\033[32mV databázi nejsou žádné knihy!\033[0m");
					Funkce.waitTime(1200);
					break;
				}
				System.out.println("\033[32mVyberte knihu, kterou chcete smazat:\033[0m");
				System.out.println("\033[32m####################################\033[0m");
				for (int i = 0; i < knihy.size(); i++) {
					nazevKnihy = knihy.get(i).getNazevKnihy();
					System.out.println((i + 1) + ". " + nazevKnihy);
				}
				System.out.println("\033[32m####################################\033[0m");
				System.out.println("Váš výběr: ");
				vyber = Funkce.checkIntNumber(1, knihy.size());
				System.out.println("\n\033[32mVybral jste knihu: \033[0m" + nazevKnihy);
				System.out.println("\033[32mOpravdu ji chcete smazat?\033[0m");
				System.out.println("\n1. Ano");
				System.out.println("2. Ne");
				System.out.println("\nVáš výběr: ");
				int anone = Funkce.checkIntNumber(1, 2);
				if (anone == 1) {
					knihy.remove(vyber - 1);
					System.out.println("\n\n\033[32mSmazal jste knihu '" + nazevKnihy + "'\033[0m");
					Funkce.waitTime(1200);
				} else if (anone == 2) {
					break;
				}

				break;
			case 4:
				if (knihy.isEmpty()) {
					System.out.print("\033[32mV databázi nejsou žádné knihy!\033[0m");
					Funkce.waitTime(1200);
					break;
				}
				System.out.println("\033[32mVyberte knihu, u které chcete změnit dostupnost:\033[0m");
				if (knihy.isEmpty()) {
					System.out.print("\n\033[32mV databázi nejsou žádné knihy!\033[0m");
					Funkce.waitTime(1200);
					break;
				}
				System.out.println(
						"\033[32m#########################################################################\033[0m");
				System.out.println("Název knihy | Autor | Rok vydání | žánr | Doporučeno pro věk | Dostupnost");
				System.out.println(
						"\033[32m-------------------------------------------------------------------------\033[0m");
				for (int i = 0; i < knihy.size(); i++) {
					nazevKnihy = knihy.get(i).getNazevKnihy();
					autor = knihy.get(i).getAutor();
					rokVydani = knihy.get(i).getRokVydani();
					zanr = knihy.get(i).getZanr();
					vhodneProRocnik = knihy.get(i).getVhodneProRocnik();
					dostupnost = knihy.get(i).getDostupnost();
					String dostupnostString;
					if (dostupnost == true) {
						dostupnostString = "K dispozici";
					} else {
						dostupnostString = "Vypůjčená";
					}
					System.out.println((i + 1) + ". " + nazevKnihy + " | " + autor + " | " + rokVydani + " | " + zanr
							+ " | " + vhodneProRocnik + " | " + dostupnostString);
				}
				System.out.println(
						"\033[32m#########################################################################\033[0m");
				System.out.println("Váš výběr: ");
				vyber = Funkce.checkIntNumber(1, knihy.size());
				knihy.get(vyber - 1).changeDostupnost();
				System.out.print("\n\n\033[32mZměnil jste dostupnost knihy! Stav knihy je nyní: \033[0m");
				String dostupnostString = "";
				dostupnost = knihy.get(vyber - 1).getDostupnost();
				if (dostupnost == true) {
					dostupnostString = "K dispozici";
				} else {
					dostupnostString = "Vypůjčená";
				}
				System.out.println(dostupnostString);
				Funkce.waitTime(1200);
				break;
			case 5:
				System.out.println("\033[32mZadejte název hledané knihy: \033[0m");
				String searchTerm = Funkce.checkString("Zadal jste špatný název knihy");
				ArrayList<String> seznamKnih = new ArrayList<String>();
				for (int i = 0; i < knihy.size(); i++) {
					nazevKnihy = knihy.get(i).getNazevKnihy();
					if (nazevKnihy.toLowerCase().contains(searchTerm.toLowerCase())) {
						seznamKnih.add(nazevKnihy);
					}
				}
				if (seznamKnih.isEmpty()) {
					System.out.println("\n\033[32mV databázi nejsou žádné knihy s názvem '" + searchTerm + "'\033[0m");
					Funkce.waitTime(1200);
					break;
				}
				System.out.println("\n\n\033[32m################################\033[0m");
				System.out.println("\033[32mHledané slovo: \033[0m" + searchTerm);

				System.out.println("\033[32m--------------------------------\033[0m");

				for (int i = 0; i < seznamKnih.size(); i++) {
					System.out.println((i + 1) + ". " + seznamKnih.get(i));
				}
				System.out.println("\033[32m################################\033[0m");
				Funkce.stiskEnter();
				break;
			case 6:
				if (knihy.isEmpty()) {
					System.out.print("\n\033[32mV databázi nejsou žádné knihy!\033[0m");
					Funkce.waitTime(1200);
					break;
				}
				ArrayList<Kniha> knihyAbecedne = new ArrayList<>(knihy);
				Collections.sort(knihyAbecedne);

				System.out.println(
						"\033[32m#########################################################################\033[0m");
				System.out.println("Název knihy | Autor | Rok vydání | žánr | Doporučeno pro věk | Dostupnost");
				System.out.println(
						"\033[32m-------------------------------------------------------------------------\033[0m");
				for (int i = 0; i < knihyAbecedne.size(); i++) {
					nazevKnihy = knihyAbecedne.get(i).getNazevKnihy();
					autor = knihyAbecedne.get(i).getAutor();
					rokVydani = knihyAbecedne.get(i).getRokVydani();
					zanr = knihyAbecedne.get(i).getZanr();
					vhodneProRocnik = knihyAbecedne.get(i).getVhodneProRocnik();
					dostupnost = knihyAbecedne.get(i).getDostupnost();
					String dostupnostString2;
					if (dostupnost == true) {
						dostupnostString2 = "K dispozici";
					} else {
						dostupnostString2 = "Vypůjčená";
					}
					System.out.println((i + 1) + ". " + nazevKnihy + " | " + autor + " | " + rokVydani + " | " + zanr
							+ " | " + vhodneProRocnik + " | " + dostupnostString2);
				}
				System.out.println(
						"\033[32m#########################################################################\033[0m");
				Funkce.stiskEnter();
				break;
			case 7:
				if (knihy.isEmpty()) {
					System.out.println("\n\033[32mV databázi nejsou žádné knihy!\033[0m");
					Funkce.waitTime(1200);
					break;
				}

				System.out.println("\033[32mZadejte jméno autora: \033[0m");
				String hledanyAutor = Funkce.checkString("Zadal jste špatné jméno autora!");

				ArrayList<Kniha> knihyChron = new ArrayList<>();
				for (int i = 0; i < knihy.size(); i++) {
					autor = knihy.get(i).getAutor();
					if (hledanyAutor.toLowerCase().equals(autor.toLowerCase())) {
						knihyChron.add(knihy.get(i));
					}
				}

				if (knihyChron.isEmpty()) {
					System.out.println("\n\033[32mAutor nenalezem nebo nemá žádné knihy!\033[0m");
					Funkce.waitTime(1200);
					break;
				}

				Collections.sort(knihyChron, new Comparator<Kniha>() {
					public int compare(Kniha o1, Kniha o2) {
						return Integer.compare(o1.getRokVydani(), o2.getRokVydani());
					}
				});

				System.out.println(
						"\n\033[32m#########################################################################\033[0m");
				System.out.println("\033[32mKnihy autora \033[0m" + hledanyAutor);
				System.out.println(
						"\033[32m-------------------------------------------------------------------------\033[0m");
				System.out.println("Název knihy | Autor | Rok vydání | žánr | Doporučeno pro věk | Dostupnost");

				for (int i = 0; i < knihyChron.size(); i++) {
					Kniha kniha = knihyChron.get(i);
					System.out.println((i + 1) + ". " + kniha.getNazevKnihy() + " | " + kniha.getAutor() + " | "
							+ kniha.getRokVydani() + " | " + kniha.getZanr() + " | " + kniha.getVhodneProRocnik()
							+ " | " + kniha.getDostupnost());
				}

				System.out.println(
						"\033[32m#########################################################################\033[0m");
				Funkce.stiskEnter();
				break;

			case 8:
				if (knihy.isEmpty()) {
					System.out.println("\n\033[32mV databázi nejsou žádné knihy!\033[0m");
					Funkce.waitTime(1200);
					break;
				}
				String hledanyZanr = Funkce.vyberZanr();
				ArrayList<Kniha> knihyZanr = new ArrayList<>();
				for (int i = 0; i < knihy.size(); i++) {
					zanr = knihy.get(i).getZanr();
					if (zanr.equals(hledanyZanr)) {
						knihyZanr.add(knihy.get(i));
					}
				}

				if (knihyZanr.isEmpty()) {
					System.out.println("\n\033[32mV databázi nejsou žádné knihy s žánrem: \033[0m" + hledanyZanr);
					Funkce.waitTime(1200);
					break;
				}

				System.out.println(
						"\n\033[32m#########################################################################\033[0m");
				System.out.println("\033[32mKnihy s žánrem \033[0m: " + hledanyZanr);
				System.out.println(
						"\033[32m-------------------------------------------------------------------------\033[0m");
				System.out.println("Název knihy | Autor | Rok vydání | žánr | Doporučeno pro věk | Dostupnost");
				for (int i = 0; i < knihyZanr.size(); i++) {
					Kniha kniha = knihyZanr.get(i);
					System.out.println((i + 1) + ". " + kniha.getNazevKnihy() + " | " + kniha.getAutor() + " | "
							+ kniha.getRokVydani() + " | " + kniha.getZanr() + " | " + kniha.getVhodneProRocnik()
							+ " | " + kniha.getDostupnost() + ";");
				}
				System.out.println(
						"\033[32m#########################################################################\033[0m");
				Funkce.stiskEnter();
				break;

			case 9:
				if (knihy.isEmpty()) {
					System.out.println("\n\033[32mV databázi nejsou žádné knihy!\033[0m");
					Funkce.waitTime(1200);
					break;
				}
				ArrayList<Kniha> knihyDostupna = new ArrayList<>();
				for (int i = 0; i < knihy.size(); i++) {
					if (!knihy.get(i).getDostupnost()) {
						knihyDostupna.add(knihy.get(i));
					}
				}

				if (knihyDostupna.isEmpty()) {
					System.out.println("\n\033[32mV databázi nejsou žádné knihy vypůjčené!\033[0m");
					Funkce.waitTime(1200);
					break;
				}
				System.out.println(
						"\n\033[32m#########################################################################\033[0m");
				System.out.println("\033[32mVypůjčené knihy:\033[0m");
				System.out.println(
						"\033[32m-------------------------------------------------------------------------\033[0m");
				System.out.println("Název knihy | Druh knihy");
				String druhKnihy = null;
				for (int i = 0; i < knihyDostupna.size(); i++) {
					if (knihyDostupna.get(i) instanceof Ucebnice) {
						druhKnihy = "Učebnice";
					} else {
						druhKnihy = "Román";
					}
					System.out.println((i + 1) + ". " + knihyDostupna.get(i).getNazevKnihy() + " | " + druhKnihy);
				}
				System.out.println(
						"\033[32m#########################################################################\033[0m");
				Funkce.stiskEnter();
				break;
			case 10:
				if (knihy.isEmpty()) {
					System.out.println("\n\033[32mV databázi nejsou žádné knihy!\033[0m");
					Funkce.waitTime(1200);
					break;
				}
				ArrayList<Kniha> knihyAbecedne2 = new ArrayList<>(knihy);
				Collections.sort(knihyAbecedne2);

				System.out.println(
						"\033[32m#########################################################################\033[0m");
				System.out.println("Název knihy | Autor | Rok vydání | žánr | Doporučeno pro věk | Dostupnost");
				System.out.println(
						"\033[32m-------------------------------------------------------------------------\033[0m");
				int zpet = 0;
				for (int i = 0; i < knihyAbecedne2.size(); i++) {
					nazevKnihy = knihyAbecedne2.get(i).getNazevKnihy();
					autor = knihyAbecedne2.get(i).getAutor();
					rokVydani = knihyAbecedne2.get(i).getRokVydani();
					zanr = knihyAbecedne2.get(i).getZanr();
					vhodneProRocnik = knihyAbecedne2.get(i).getVhodneProRocnik();
					dostupnost = knihyAbecedne2.get(i).getDostupnost();
					String dostupnostString3;
					if (dostupnost == true) {
						dostupnostString3 = "K dispozici";
					} else {
						dostupnostString3 = "Vypůjčená";
					}
					System.out.println((i + 1) + ". " + nazevKnihy + " | " + autor + " | " + rokVydani + " | " + zanr
							+ " | " + vhodneProRocnik + " | " + dostupnostString3);
					zpet = i + 2;
				}
				System.out.println(zpet + ". Zpět do menu");
				System.out.println(
						"\033[32m#########################################################################\033[0m");
				System.out.println("\033[32mNapište číslo knihy, kterou chcete uložit do souboru: \033[0m");
				int vybranaKniha = Funkce.checkIntNumber(1, zpet) - 1;

				if (vybranaKniha == knihyAbecedne2.size()) {
					System.out.println("\n\033[32mZrušil jste akci.\033[0m");
					Funkce.waitTime(1200);
					break;
				}

				String nazevSouboru = knihyAbecedne2.get(vybranaKniha).getNazevKnihy() + ".txt";

				Kniha kniha = knihyAbecedne2.get(vybranaKniha);
				String dostupny = kniha.getDostupnost() == true ? "true" : "false";
				try (BufferedWriter writer = new BufferedWriter(new FileWriter(nazevSouboru))) {
					writer.write(kniha.getNazevKnihy() + "," + kniha.getAutor() + "," + kniha.getRokVydani() + ","
							+ kniha.getZanr() + "," + kniha.getVhodneProRocnik() + "," + dostupny);
					writer.close();
					System.out.println("\n\033[32mKniha byla uložena do souboru.\033[0m");
					Funkce.waitTime(1200);
					break;
				} catch (IOException e) {
					e.printStackTrace();
				}
			case 11:
				System.out.println("\033[32mZadejte název knihy, kterou chcete nahrát ze souboru: \033[0m");
				String nazevSouboru1 = Funkce.checkString("Musíte zadat název souboru!") + ".txt";
				try {
					File soubor = new File(nazevSouboru1);
					Scanner sc = new Scanner(soubor);

					while (sc.hasNextLine()) {
						String radek = sc.nextLine();
						String[] data = radek.split(",");

						nazevKnihy = data[0];
						autor = data[1];
						rokVydani = Integer.parseInt(data[2]);
						zanr = data[3];
						vhodneProRocnik = Integer.parseInt(data[4]);
						dostupnost = Boolean.parseBoolean(data[5]);

						if (zanr.equals("-")) {
							knihy.add(new Ucebnice(nazevKnihy, autor, rokVydani, dostupnost, vhodneProRocnik));
							System.out.println("\n\033[32mKniha byla přidána do databáze.\033[0m");
							Funkce.waitTime(1200);
							break;
						} else {
							knihy.add(new Roman(nazevKnihy, autor, rokVydani, dostupnost, zanr));
							System.out.println("\n\033[32mKniha byla přidána do databáze.\033[0m");
							Funkce.waitTime(1200);
							break;
						}
					}
					sc.close();
				} catch (FileNotFoundException e) {
					System.out.println("Soubor nebyl nalezen: " + e.getMessage());
					Funkce.waitTime(2000);

				}
				break;
			case 12:
				if (knihy.isEmpty()) {
					System.out.println("\n\033[32mDatabáze je prázdná.\033[0m");
					Funkce.waitTime(1200);
				}
				dbManager.connect();
				dbManager.dropTable();
				Funkce.waitTime(100);
				dbManager.createTable();
				dbManager.insertRecord(knihy);
				dbManager.disconnect();
				System.out.println("\n\033[32mProgram ukončen.\033[0m");
				Funkce.waitTime(2000);
				System.exit(0);
			}
		}
	}
}

/*
 * System.out.println(); ArrayList<String> seznamKnih = new ArrayList<String>();
 * System.out.println("\033[32m####################################\033[0m");
 * System.out.println("\033[32mTEXT\033[0m");
 */