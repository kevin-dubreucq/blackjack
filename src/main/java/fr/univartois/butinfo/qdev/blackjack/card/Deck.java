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
 * Le type Deck
 *
 * @author Kevin
 *
 * @version 0.1.0
 */
public class Deck {

    /**
     * L'attribut cartesList...
     */
    private List<Card> cartesList;

    /**
     * Crée une nouvelle instance de Deck.
     */
    private Deck() {
        cartesList = new ArrayList<>();
    }

    /**
     * @return
     */
    public static Deck create52CardsDeck() {
        Deck deck = new Deck();

        for(Suit suit : Suit.values()) {
            for(Value value : Value.values()) {
                deck.cartesList.add(new Card(value, suit));
            }
        }
        return deck;
    }

    /**
     *
     */
    public void shuffle() {
        Collections.shuffle(cartesList);
    }

    /**
     * @param card
     */
    public void add(Card card) {
        if(!cartesList.contains(card))
            cartesList.add(card);
    }

    /**
     * @return
     * @throws Exception
     */
    public Card withdraw() throws Exception {
        if(cartesList.isEmpty()) {
            throw new Exception("Deck is empty");
        }
        return cartesList.removeLast();
    }

}

