package fr.univartois.butinfo.qdev.blackjack.card;

import java.util.List;

public abstract class CardOwner {
    private String name;
    protected List<Card> cards;


    public CardOwner(String cardOwner){
        this.name = cardOwner;

    }
    public abstract boolean isHitting();

    public void receive(Card card){
        cards.add(card);
    }

    public int getHandValue(){
        int value = 0;
        for(Card card : cards){
            if (card.getValue().getNumericalValue() == 11 &&( (value + 11)> 21)){
                value+=1;
            }
            else{
                value += card.getValue().getNumericalValue();
            }


        }
        return value;
    }

    public boolean isBusted(){
        return getHandValue() > 21 ;
    }

    public String toString(){
        return "name :"+ name + "cards : " + cards + "value : " + getHandValue();
    }


}
