
package fr.univartois.butinfo.qdev.blackjack.card;

import fr.univartois.butinfo.qdev.blackjack.card.BlackjackGame;

import java.util.Scanner;




public class Blackjack {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BlackjackGame game = new BlackjackGame();

        System.out.println("=== BIENVENUE AU BLACKJACK ===");
        System.out.print("Combien de joueurs (1 à 7) ? : ");

        int nbPlayers = 1;
        try {
            nbPlayers = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            nbPlayers = 1;
        }

        nbPlayers = Math.max(1, Math.min(7, nbPlayers));

        for (int i = 1; i <= nbPlayers; i++) {
            System.out.print("Nom du joueur " + i + " : ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                name = "Joueur " + i;
            }
            game.addPlayer(name);
        }

        // Lancement de la partie via la classe BlackjackGame
        game.play();
    }
}

