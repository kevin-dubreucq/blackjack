/**
 * Ce fichier fait partie du projet blackjack.
 *
 * (c) 2026 Drqke
 * Tous droits réservés.
 */

package fr.univartois.butinfo.qdev.blackjack.card;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;


/**
 * Le type CardOwner
 *
 * @author Drqke
 *
 * @version 0.1.0
 */
public abstract class CardOwner {

    protected String name;

    private List<Card> hand;

    protected CardOwner(String name) {
        this.name = name;
        hand = new ArrayList<>();
    }

    public abstract boolean isHitting();

    public void receive(Card card) {
        hand.add(card);
    }

    public int getHandValue() {
        int tmp = 0;
        for(int i = 0; i < hand.size(); i++) {
            tmp += hand.get(i).getValue().getNumericalValue();
            if(hand.get(i).getValue().getNumericalValue() == 11 && tmp > 21) {
                tmp -= 10;
            }
        }
        return tmp;
    }

    public boolean isBusted() {
        return getHandValue() > 21;
    }

    public String toString() {
        return "name : " + name + " Hand value : " + getHandValue();
    }

    protected Card getCard(int index) {
        return hand.get(index);
    }
}

