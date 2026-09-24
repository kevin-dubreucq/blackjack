/**
 * Ce fichier fait partie du projet blackjack.
 *
 * (c) 2026 melan
 * Tous droits réservés.
 */

package fr.univartois.butinfo.qdev.blackjack.card;


/**
 * Le type Value
 *
 * @author Drqke
 *
 * @version 0.1.0
 */
public enum Value {
    ACE(11),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    JACK(10),
    QUEEN(10),
    KING(10);

    private int numericalValue;

    private Value(int numericalValue) {
        this.numericalValue = numericalValue;
    }

    public int getNumericalValue() {
        return numericalValue;
    }

}

