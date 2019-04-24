/**
 * SYST 17796 Project Winter 2019 Base code.
 * Students can modify and extend to implement their game.
 * Add your name as a modifier and the date!
 */
package wargamedemo;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A concrete class that represents any grouping of cards for a Game.
 * HINT, you might want to subclass this more than once.
 * The group of cards has a maximum size attribute which is flexible for reuse.
 * @author dancye
 * @modified by Jan, 2019
 */
public class GroupOfCards 
{
   
    //The group of cards, stored in an ArrayList
    private ArrayList <Card> cards;
    private int size;//the size of the grouping

    /**
     * Creates new card Grouping
     */
    public GroupOfCards() {
        cards = new ArrayList<>();
    }
    
    /**
     * This is for game comparisons that require cards from the top of a deck
     * @return Most recent card
     */   
    public Card getMostRecentCard (){
        Card card = cards.remove(cards.size() - 1);
        updateSize();
        return card;
    }
    
    /**
     * Adds card to group of cards
     * @param addedCard Card added
     */
    public void addCard(Card addedCard){
        cards.add(addedCard);
        updateSize();
    }
    
    public void addCards(GroupOfCards groupOfCards){
        while(groupOfCards.getSize() > 0){
            addCard(groupOfCards.getMostRecentCard());
        }
    }
    
    //Updates size to accurate amount
    private void updateSize(){
        this.size = cards.size();
    }
    
    public void shuffle()
    {
        Collections.shuffle(cards);
    }

    /**
     * @return the size of the group of cards
     */
    public int getSize() {
        updateSize();
        return size;
    }
    
    public void clear(){
        cards.clear();
        updateSize();
    }

    @Override
    public String toString() {
        return "GroupOfCards{" + "cards=" + cards + ", size=" + size + '}';
    }
    
}//end class
