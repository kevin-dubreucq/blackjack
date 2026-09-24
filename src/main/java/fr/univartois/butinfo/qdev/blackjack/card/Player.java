/**
 * Ce fichier fait partie du projet blackjack.
 *
 * (c) 2026 Drqke
 * Tous droits réservés.
 */

package fr.univartois.butinfo.qdev.blackjack.card;

import java.util.Scanner;

/**
 * Le type Player
 *
 * @author Drqke
 *
 * @version 0.1.0
 */
public class Player extends CardOwner {

    /**
     * Crée une nouvelle instance de Player.
     * @param name Le nom du joueur.
     */
    protected Player(String name) {
        super(name);
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.qdev.blackjack.CardOwner#isHitting()
     */
    @Override
    public boolean isHitting() {
        if (isBusted()) {
            return false;
        }

        System.out.print("Voulez-vous tirer une carte ? (o/n) : ");
        Scanner scanner = new Scanner(System.in);
        String response = scanner.next();
        return response.equalsIgnoreCase("o") || response.equalsIgnoreCase("oui");
    }

    public void win() {
        System.out.println("Vous avez gagné !");
    }

    public void push() {
        System.out.println("Égalité dommage");
    }

    public void lose() {
        System.out.println("Perdu");
    }

    public void bust() {
        System.out.println("Vous avez dépassé 21");
    }


}
