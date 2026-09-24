package fr.univartois.butinfo.qdev.blackjack.card;

public class House extends CardOwner{

    public House (){
        super("House Card");
    }

    @Override
    public boolean isHitting() {

        return getHandValue()<16;
    }



    public void flipFirstCard(){
        cards.get(1).flip();
    }


}
