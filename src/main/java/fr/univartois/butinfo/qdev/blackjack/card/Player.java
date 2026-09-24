package fr.univartois.butinfo.qdev.blackjack.card;
import java.util.Scanner;

public class Player extends CardOwner{


    public Player(String name){
        super(name);

    }

    public void win(){
        System.out.println("Player win");
    }
    public void lose(){
        System.out.println("Player lose");
    }
    public void push(){
        System.out.println("Player push");
    }
    public void bust(){
        System.out.println("Player bust");
    }

    @Override
    public boolean isHitting() {

        System.out.println(
                "Voulez vous continuer vous avez" + getHandValue() + "y/n");

        Scanner scanner = new Scanner(System.in);
        if (scanner.nextLine().equals("n")) {
            return false;
        } else if (scanner.nextLine().equals("y")) {
            return true;

        } else {
            return false;
        }
    }


}
