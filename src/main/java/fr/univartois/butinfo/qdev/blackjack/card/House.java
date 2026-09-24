/**
 * Ce fichier fait partie du projet blackjack.
 *
 * (c) 2026 Drqke
 * Tous droits réservés.
 */

package fr.univartois.butinfo.qdev.blackjack.card;


/**
 * Le type House
 *
 * @author Kevin
 *
 * @version 0.1.0
 */
public class House extends CardOwner {

    public House() {
        super("House");
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.qdev.blackjack.CardOwner#isHitting()
     */
    @Override
    public boolean isHitting() {
        if(getHandValue() > 16) {
            return false;
        }
        else {
            return true;
        }
    }

    public void flipFirstCard() {
        getCard(0).flip();
    }



}

