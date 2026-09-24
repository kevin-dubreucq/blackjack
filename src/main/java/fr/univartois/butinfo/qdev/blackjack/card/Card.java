/**
 * Ce fichier fait partie du projet blackjack.
 *
 * (c) 2026 Drqke
 * Tous droits réservés.
 */

package fr.univartois.butinfo.qdev.blackjack.card;


/**
 * Le type Card
 *
 * @author Drqke
 *
 * @version 0.1.0
 */
public final class Card {

    private Value value;
    private Suit suit;
    private boolean isFaceUp;

    public Card(Value value, Suit suit) {
        this.value = value;
        this.suit = suit;
        this.isFaceUp = false;
    }

    public Value getValue() {
        return value;
    }

    public int getNumericalValue() {
        return value.getNumericalValue();
    }

    public Suit getSuit() {
        return suit;
    }

    public void flip() {
        this.isFaceUp = !this.isFaceUp;
    }

    public String toString() {
        if(isFaceUp) {
            return value.getNumericalValue() + " of " + suit.getSymbol();
        }
        else {
            return "**********";
        }
    }
}

