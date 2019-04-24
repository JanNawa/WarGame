package wargamedemo;

import java.util.Scanner;

/**
 * This class models the war game rule.
 * 
 * @author Jan, 2019
 * @modified Cory Salmon
 */
public class WarGame extends Game{
    
    private WarPlayer player1;
    private WarPlayer player2;

    /**
     * Constructor to create the player with the user input.
     */
    public WarGame() {
        super("War Game");
        //create new player
        player1 = new WarPlayer(userInput());
        player2 = new WarPlayer(userInput());
        //add player to player list in Game
        addPlayer(player1);
        addPlayer(player2);
    }
    
    /**
     * To get user input of player's name
     * @return String name of Player
     */
    private String userInput(){
        Scanner scan = new Scanner(System.in);
        String name;
        do {
            System.out.println("Enter Player Name: ");
            name = scan.nextLine();
        }while(name == null || name.trim().isEmpty());
        
        return name;
    }
    
    /**
     * The preparation of War Game
     * Create the card deck and shuffle the deck.
     * Deal the card to the players.
     */
    private void prepareWarGame(){
        //create the card deck
        GroupOfCards gameDeck = new GroupOfCards();
        //creates new unshuffled deck
        for (Standard.Suit suit : Standard.Suit.values()) {
            for (Standard.Value value : Standard.Value.values()) {
                gameDeck.addCard(new Standard(suit, value));
            }
        }
        //shuffles deck
        gameDeck.shuffle();
        //deal the card to both player
        while(gameDeck.getSize() >= 2){
            player1.collectCard(gameDeck.getMostRecentCard());
            player2.collectCard(gameDeck.getMostRecentCard());
        }
        //create the pile for each player
        player1.changeGroupOfCards();
        player2.changeGroupOfCards();
        printCheckPlayer();     
    }

    /**
     * Main models of playing war game included
     * Normal Turn for War Game (one-on-one turn)
     * War will be invoked from here if both player play the card with same value.
     */
    @Override
    public void play() { 
        prepareWarGame();
        //loop as long as there are still card present in both players
        while(hasCard(1)){
            //the player play the card
            Card card1 = player1.playCard();
            Card card2 = player2.playCard();
            System.out.println("Normal Turn");
            System.out.println(player1.getPlayerID() + " : " + card1);
            System.out.println(player2.getPlayerID() + " : " + card2);
            //compare the card, the higher value win
            if(card1.compareTo(card2) > 0){ // player1 wins
                winRound(player1, card1, card2);
            } else if (card1.compareTo(card2) < 0){ //player2 wins
                winRound(player2, card1, card2);
            } else { //otherwise, it is a war
                war(card1, card2);
            }
        }
        //when 1 player has no more cards, there are winner.
        declareWinner();
    }
    
    /**
     * Winner collect all the cards in that round
     * 
     * @param winner the player who has higher value
     * @param card1 the card from player1
     * @param card2 the card from player2
     */
    private void winRound(WarPlayer winner, Card card1, Card card2) {
        winner.collectCard(card1);
        winner.collectCard(card2);
        printCheckPlayer();
    }

    /**
     * Main models of war game.
     * First, check if both player have enough cards to play the war.
     * If not, we have the winner and end the war.
     * Otherwise, play the war, the player that have higher value win the war.
     * 
     * @param c1 card from player1 with the same rank as player2
     * @param c2 card from player2 with the same rank as player1
     */
    private void war(Card c1, Card c2){
        // group of cards for war
        GroupOfCards warCards = new GroupOfCards();
        Card card1 = c1;
        Card card2 = c2;
        // add the card with same rank from both player to warCards
        warCards.addCard(card1);
        warCards.addCard(card2);
        //keep track status of war
        boolean isDone = false; 
        //continue war as long as war is not done
        while(!isDone){ 
            //if one of the player doesn't have enough cards
            if(!hasCard(3)){
                // when player1 doesn't have enough cards to play war, all cards will be transfer to player2
                if(player1.numberOfCards() < 3) { 
                    transferAllCards(player2, player1, warCards);
                } else { // when player2 doesn't have enough cards to play war, all cards will be transfer to player1
                    transferAllCards(player1, player2, warCards);
                }
                //found winner as 1 player has no more card
                declareWinner();
                break;
            }
            //both players have enough cards, continue war
            System.out.println("War : Both players play 3 cards");
            //loop 3 times to allocate the cards for war
            for (int i = 1; i <= 3; i++){
                System.out.println("War " + i + " : ");
                card1 = player1.playCard();
                card2 = player2.playCard();
                System.out.println(player1.getPlayerID() + " : " + card1);
                System.out.println(player2.getPlayerID() + " : " + card2);
                warCards.addCard(card1);
                warCards.addCard(card2);
            }
            //compare the card, the higher value win
            if(card1.compareTo(card2) > 0){ // player1 wins
                player1.collectCards(warCards);
                isDone = true;
                printCheckPlayer();
            } else if (card1.compareTo(card2) < 0){ // player2 wins
                player2.collectCards(warCards);
                isDone = true;   
                printCheckPlayer();
            }
        }    
    }
    
    /**
     * Transfer the remaining cards to winner.
     * @param winner the player who has enough cards to continue war
     * @param loser the player who doesn't have enough cards to continue war
     * @param warCards the group of card for war game
     */
    private void transferAllCards(WarPlayer winner, WarPlayer loser, GroupOfCards warCards) {
        winner.collectCards(warCards);
        while (loser.numberOfCards() != 0) {
            winner.collectCard(loser.playCard());
        }
    }

    /**
     * Check if the players have enough cards to continue the game.
     * @param n minimum number of card the player need to have
     * @return true if both players have enough cards; otherwise false
     */
    private boolean hasCard(int n){
        return player1.numberOfCards() >= n && player2.numberOfCards() >= n;
    }

    /**
     * Declare winner after the game end.
     * The winner is the one who has all the 52 cards in their hand.
     */
    @Override
    public void declareWinner() {
        String winner = (player2.numberOfCards() == 0) ? player1.getPlayerID() : player2.getPlayerID();
        System.out.println("The Winner is : " + winner);
    }

    /**
     * Check the cards that each player has.
     */
    private void printCheckPlayer() {
        System.out.println(player1);
        System.out.println(player2);
    }    
}