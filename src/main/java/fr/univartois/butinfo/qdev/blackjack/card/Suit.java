/**
 * Ce fichier fait partie du projet blackjack.
 *
 * (c) 2026 Drqke
 * Tous droits réservés.
 */

package fr.univartois.butinfo.qdev.blackjack.card;


/**
 * Le type Suit
 *
 * @author Drqke
 *
 * @version 0.1.0
 */
public enum Suit {
    CLUBS("clubs"),
    DIAMONDS("diamonds"),
    HEARTS("hearts"),
    SPADES("spades");

    private String symbol;

    private Suit(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}

