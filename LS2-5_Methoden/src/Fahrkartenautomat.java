import java.util.Scanner;

class Fahrkartenautomat {
	public static void main(String[] args) {
		Scanner tastatur = new Scanner(System.in);

		double eingezahlterGesamtbetrag;
		double zuZahlenderBetrag;
		
		begreussung();
		zuZahlenderBetrag = fahrkartenbestellungErfassen(tastatur);
		eingezahlterGesamtbetrag = fahrkartenBezahlen(tastatur, zuZahlenderBetrag);
		fahrkartenAusgeben();
		rueckgeldAusgeben(zuZahlenderBetrag, eingezahlterGesamtbetrag);

		tastatur.close();
	}

	private static void begreussung() {
		System.out.println("Herzlich Willkommen!\n");
	}

	private static double fahrkartenbestellungErfassen(Scanner tastatur) {
		int anzahlTickets;
		int ticketArt;
		double zwischensumme = 0.0;
		double zuZahlenderBetrag = 0.0;
		boolean abbruch = false;
		boolean ersterVorgang = true;
		
		// Ticketpreise
		double kurzstrecke = 2.00;
		double einzelfahrausweis = 3.00;
		double vierFahrtenKarte = 9.40;
		double tageskarte = 8.80;
		
		while(!abbruch) {
			System.out.println("Wählen Sie ihre Wunschfahrkarte für Berlin AB aus:");
			System.out.println("Kurzstrecke AB [2,00 EUR]: (1)");
			System.out.println("Einzelfahrausweis AB [3,00 EUR]: (2)");
			System.out.println("4-Fahrten-Karte AB [9,40 EUR]: (3)");
			System.out.println("24-Stunden-Karte AB [8,80 EUR]: (4)\n");
			
			if(!ersterVorgang) {
				System.out.println("Bezahlen (9)\n");
			}
			
			// Ticketauswahl
			do {
				System.out.print("Ihre Wahl: ");
				ticketArt = tastatur.nextInt();
				
				if((ticketArt < 1 || ticketArt > 4) && ticketArt != 9) {
					System.out.println(">> Falsche Eingabe <<");
				}
			} while((ticketArt < 1 || ticketArt > 4) && ticketArt != 9);
			
			// Anzahl der Tickets
			if(ticketArt != 9) {
				do {
					System.out.print("Anzahl der Tickets: ");
					anzahlTickets = tastatur.nextInt();
					
					if(anzahlTickets < 1 || anzahlTickets > 10) {
						System.out.println(">> Wählen Sie bitte eine Anzahl von 1 bis 10 Tickets aus. <<");
					}
				}while(anzahlTickets < 1 || anzahlTickets > 10);
				
				// Berechnung des zu zalenden Betrages
				if(ticketArt == 1) {
					zwischensumme = kurzstrecke * anzahlTickets;
				} else if(ticketArt == 2) {
					zwischensumme = einzelfahrausweis * anzahlTickets;
				} else if(ticketArt == 3) {
					zwischensumme = vierFahrtenKarte * anzahlTickets;
				} else {
					zwischensumme = tageskarte * anzahlTickets;
				}
				
				zuZahlenderBetrag += zwischensumme;
			} else {
				abbruch = true;
			}
			
			System.out.printf("\nZwischensumme: %.2f EUR\n\n", zuZahlenderBetrag);
			ersterVorgang = false;
		}
		return zuZahlenderBetrag;
	}

	private static double fahrkartenBezahlen(Scanner tastatur, double zuZahlenderBetrag) {
		double eingezahlterGesamtbetrag = 0.0;
		double nochZuZahlen = 0.0;
		double eingeworfeneMuenze;
		
		while (eingezahlterGesamtbetrag < zuZahlenderBetrag) {
			nochZuZahlen = zuZahlenderBetrag - eingezahlterGesamtbetrag;
			System.out.printf("Noch zu zahlen: %.2f Euro\n", nochZuZahlen);
			System.out.print("Eingabe (mind. 5 Cent, höchstens 20 Euro): ");
			eingeworfeneMuenze = tastatur.nextDouble();
			if(eingeworfeneMuenze == 20 || eingeworfeneMuenze == 10 || eingeworfeneMuenze == 5 || eingeworfeneMuenze == 2 
					|| eingeworfeneMuenze == 1 || eingeworfeneMuenze == 0.5 || eingeworfeneMuenze == 0.2 || eingeworfeneMuenze == 0.1 
					|| eingeworfeneMuenze == 0.05 || eingeworfeneMuenze == 0.02 || eingeworfeneMuenze == 0.01) {
				eingezahlterGesamtbetrag = eingezahlterGesamtbetrag + eingeworfeneMuenze;
			} else {
				System.out.println(">> Kein gültiges Zahlungsmittel. <<");
			}
		}
		return eingezahlterGesamtbetrag;
	}
	
	private static void fahrkartenAusgeben() {
		System.out.println("\nFahrschein wird ausgegeben");
		for (int i = 0; i < 8; i++) {
			System.out.print("=");
			try {
				Thread.sleep(200);
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("\n\n");
	}
	
	private static void rueckgeldAusgeben(double zuZahlenderBetrag, double eingezahlterGesamtbetrag) {
		double rueckgabebetrag = 0.0;
		boolean scheine = false;
		
		rueckgabebetrag = eingezahlterGesamtbetrag - zuZahlenderBetrag;
		if(rueckgabebetrag > 0.0) {
			System.out.printf("Der Rückgabebetrag in Höhe von %.2f Euro\n", rueckgabebetrag);
			
			if (rueckgabebetrag > 4.99) {
				scheine = true;
				System.out.println("wird in folgenden Scheinen ausgezahlt:");
				
				rueckgabebetrag = scheinRueckgabe(rueckgabebetrag);
			}
			
			if(scheine) {
				System.out.println("\n... und in folgenden Münzen:");
			} else {
				System.out.println("Wird in folgenden Münzen ausgezahlt:");
			}
			
			muenzRueckgabe(rueckgabebetrag);
		}

		System.out.println("\nVergessen Sie nicht, den Fahrschein\n" + "vor Fahrtantritt entwerten zu lassen!\n"
				+ "Wir wünschen Ihnen eine gute Fahrt.");
	}

	private static double scheinRueckgabe(double rueckgabebetrag) {
		while (rueckgabebetrag >= 19.99) { // 20-Euro-Scheine
			System.out.println("20 Euro");
			rueckgabebetrag = rueckgabebetrag - 20.0;
		}
		while (rueckgabebetrag >= 9.99) { // 10-Euro-Scheine
			System.out.println("10 Euro");
			rueckgabebetrag = rueckgabebetrag - 10.0;
		}
		while (rueckgabebetrag >= 4.99) { // 5-Euro-Scheine
			System.out.println("5 Euro");
			rueckgabebetrag = rueckgabebetrag - 5.0;
		}
		return rueckgabebetrag;
	}

	private static void muenzRueckgabe(double rueckgabebetrag) {
		while (rueckgabebetrag >= 1.99) { // 2-Euro-Münzen
			System.out.println("2 Euro");
			rueckgabebetrag = rueckgabebetrag - 2.0;
		}
		while (rueckgabebetrag >= 0.99) { // 1-Euro-Münzen
			System.out.println("1 Euro");
			rueckgabebetrag = rueckgabebetrag - 1.0;
		}
		while (rueckgabebetrag >= 0.49) { // 50-Cent-Münzen
			System.out.println("50 Cent");
			rueckgabebetrag = rueckgabebetrag - 0.5;
		}
		while (rueckgabebetrag >= 0.19) { // 20-Cent-Münzen
			System.out.println("20 Cent");
			rueckgabebetrag = rueckgabebetrag - 0.2;
		}
		while (rueckgabebetrag >= 0.09) { // 10-Cent-Münzen
			System.out.println("10 Cent");
			rueckgabebetrag = rueckgabebetrag - 0.1;
		}
		while (rueckgabebetrag >= 0.04) { // 5-Cent-Münzen
			System.out.println("5 Cent");
			rueckgabebetrag = rueckgabebetrag - 0.05;
		}
	}
}