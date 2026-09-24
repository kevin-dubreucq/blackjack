/**
 * Ce fichier fait partie du projet blackjack.
 *
 * (c) 2026 Drqke
 * Tous droits réservés.
 */

package fr.univartois.butinfo.qdev.blackjack.card;

import fr.univartois.butinfo.qdev.blackjack.card.Deck;
import fr.univartois.butinfo.qdev.blackjack.card.House;
import fr.univartois.butinfo.qdev.blackjack.card.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Le type BlackjackGame
 *
 * @author Drqke
 *
 * @version 0.1.0
 */
public class BlackjackGame {

    private final Deck deck;
    private final House house;
    private final List<Player> players;

    public BlackjackGame() {
        this.deck = Deck.create52CardsDeck();
        this.deck.shuffle();
        this.house = new House();
        this.players = new ArrayList<>();
    }

    public void addPlayer(String name) {
        Player tmp = new Player(name);
        players.add(tmp);
    }

    public void play() {
        for (Player player : players) {
            Card card1;
            try {
                card1 = deck.withdraw();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            player.receive(card1);
            Card card2;
            try {
                card2 = deck.withdraw();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            player.receive(card2);
        }

        Card houseCard1;
        try {
            houseCard1 = deck.withdraw();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        house.receive(houseCard1);
        Card houseCard2;
        try {
            houseCard2 = deck.withdraw();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        house.receive(houseCard2);
        house.flipFirstCard();

        for (Player player : players) {
            String handString = player.toString();
            System.out.println(handString);
        }
        String houseHandString = house.toString();
        System.out.println(houseHandString);

        for (Player player : players) {
            while (player.isHitting() && !player.isBusted()) {
                Card card;
                try {
                    card = deck.withdraw();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                player.receive(card);
                String handString = player.toString();
                System.out.println(handString);
            }
        }

        house.flipFirstCard();
        String houseString = house.toString();
        System.out.println(houseString);

        while (house.isHitting() && !house.isBusted()) {
            Card card;
            try {
                card = deck.withdraw();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            house.receive(card);
            String handString = house.toString();
            System.out.println(handString);
        }

        boolean houseBusted = house.isBusted();
        int houseValue = house.getHandValue();

        for (Player player : players) {
            boolean playerBusted = player.isBusted();

            if (playerBusted) {
                player.bust();
            } else if (houseBusted) {
                player.win();
            } else {
                int playerValue = player.getHandValue();

                if (playerValue > houseValue) {
                    player.win();
                } else if (playerValue < houseValue) {
                    player.lose();
                } else {
                    player.push();
                }
            }
        }
    }

}

