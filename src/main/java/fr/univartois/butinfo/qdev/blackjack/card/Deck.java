package fr.univartois.butinfo.qdev.blackjack.card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cardList ;
    private Deck(){
        cardList = new ArrayList<>();

    };

    public static Deck create52CardsDeck() {

        Deck deck = new Deck();
        for (Suit suit : Suit.values()){
            for( Value value : Value.values()){
                deck.cardList.add(new Card(value, suit));
            }
        }
        return deck;

    }

    public void shuffle(){
        Collections.shuffle(cardList);
    }
    public void addCard(Card card){
        cardList.add(card);
    }

    public Card withdraw() throws Exception{

        if (cardList.isEmpty()){
            throw new Exception("No cards in Deck");
        }
        return cardList.removeFirst();

    }
}
