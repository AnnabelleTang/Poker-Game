/**
 * The Player class contains elements and methods for the player 
 * to play the game
 * @author: Annabelle Tang 
 */

import java.util.ArrayList;
public class Player {
	
	private ArrayList<Card> hand; // the player's cards
	private double bankroll;
    private double bet;

    //instantiate a player object
	public Player(){		
	    bankroll = 10.0;
	    bet =0.0;
        hand = new ArrayList<Card>();
	}

    // add the card c to the player's hand
	public void addCard(Card c){
	    hand.add(c);
	}

	// remove the card c from the player's hand
	public void removeCard(Card c){
	    hand.remove(c);
    }
   
   // player makes a bet	
   public void bets(double amt){
        bet = amt;
        bankroll -= bet;
    }

    //adjust bankroll
    public void winnings(double odds){ 
        bankroll += odds*bet;
    }
    
    // return current balance of bankroll
    public double getBankroll(){ 
        return bankroll;
    }
    
    //return the arraylist hand
    public ArrayList<Card> getHand(){
        return hand;
    }
    
    //display the cards in player's hand
    public String displayCard(){
        String output ="";
        for(int i=0;i<hand.size();i++){
            output += hand.get(i).toString()+", ";
        }
        return output;
    }
       
}



