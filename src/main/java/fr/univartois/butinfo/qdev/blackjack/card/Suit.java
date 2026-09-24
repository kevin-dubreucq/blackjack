package fr.univartois.butinfo.qdev.blackjack.card;

public enum Suit {
    CLUBS("Trèfle"),
    DIAMONDS("Carreau"),
    HEARTS("Coeur"),
    SPADES("Pique");

    private final String symbol ;

    Suit(String symbol){

        this.symbol = symbol ;
    }
    public String getSymbol(){
        return symbol ;
    }
}
