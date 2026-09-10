package fr.univartois.butinfo.qdev.blackjack.card;

public final class Card {
    private boolean isFaceUp;
    private Suit suit;
    private Value value;
    public Card(Value value, Suit suit){
        this.value = value;
        this.suit = suit;
    }

    public Value getValue(){
        return value;
    }
    public Suit getSuit(){
        return suit;
    }
    public int NumericalValue(){

    }
}
