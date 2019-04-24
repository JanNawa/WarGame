package wargamedemo;
/**
 * 
 * @author Cory
 * @modified by Jan, 2019
 */
public class WarPlayer extends Player{

    private GroupOfCards playerHand;
    private GroupOfCards playedHand;
    
    public WarPlayer(String name) {
        super(name);
        playerHand = new GroupOfCards();
        playedHand = new GroupOfCards();
    }

    public Card playCard() {
        if (playerHand.getSize() > 0) {
            return playerHand.getMostRecentCard();
        } else if (playerHand.getSize() == 0) {
            changeGroupOfCards();
            return playerHand.getMostRecentCard();
        }
        return null;
    }
    
    public void collectCard(Card card){
        playedHand.addCard(card);
    }
    
    public void collectCards(GroupOfCards groupOfCard){
        playedHand.addCards(groupOfCard);
    }
    
    public void changeGroupOfCards(){
        //change pile
        playerHand.addCards(playedHand); 
        //clear the pile that already change
        playedHand.clear();
    }
    
    public int numberOfCards(){
        return playerHand.getSize() + playedHand.getSize();
    }

    @Override
    public String toString() {
        return getPlayerID() + " WarPlayer{" + "playerHand=" + playerHand + ", playedHand=" + playedHand + '}';
    }
    
}
