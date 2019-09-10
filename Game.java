/**
 * The Game class provides the relevant rules and 
 * functionalities for playing the video poker game.
 * @author: Annabelle Tang 
 */

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
public class Game {
	private Player p;
	private Deck cards;
    private double token;
    private double payout;
    private boolean nextRound;
    
	//constructor that takes an explicit parameter from a command-line argument
	//format input like: s1 s13 s12 s11 s10
	public Game(String[] testHand){
		p = new Player();
        cards = new Deck();
        token = 0.0;
        payout = 0.0;
        nextRound = true;
		for(int i=0;i<testHand.length;i++){
            if (testHand[i].substring(0,1).equals("s")){         
                p.addCard(new Card
                          (4,Integer.parseInt(testHand[i].substring(1))));
            }else if (testHand[i].substring(0,1).equals("h")){         
                p.addCard(new Card
                          (3,Integer.parseInt(testHand[i].substring(1))));
            }else if (testHand[i].substring(0,1).equals("d")){         
                p.addCard(new Card
                          (2,Integer.parseInt(testHand[i].substring(1))));
            }else if (testHand[i].substring(0,1).equals("c")){         
                p.addCard(new Card
                          (1,Integer.parseInt(testHand[i].substring(1))));
            }
        }
	}
    
	// This no-argument constructor is to actually play a normal game
	public Game(){	
		p = new Player();
        cards = new Deck();
        token = 0.0;
        payout = 0.0;
        nextRound = true;
	}
	
    // this method plays the game
	public void play(){
		// 1. greeting
		Scanner input = new Scanner(System.in);
		System.out.println("Welcome to the Video Poker game");
        while(nextRound){
            //2. Ask the Player to bet
            System.out.println("Please choose the amount of" 
                               +" token you wish to bet (1-5)");
            token = input.nextInt();
            p.bets(token);
            //shuffle and deal cards if no info is provided from command line
            if(p.getHand().size()==0){
                //3. shuffle the card
                cards.shuffle();
                //4. Deal 5 cards to player and print out the card
                dealCard();
                System.out.println("Your cards are: " + p.displayCard());
                //5. redraw cards
                redraw();
            }
            //6.evaluate hand
            System.out.println(checkHand(p.getHand()));
            //7. shows player the new bankroll
            System.out.println(payoutResult());
            if(p.getBankroll()<1){
                System.out.println("Sorry, you have no payroll left.");
                nextRound=false;
            }else{
                //8. ask whether want to replay
                System.out.println("Want to play another round? Y/N");
                String anotherRound = input.next();
                if(anotherRound.equals("N")){
                    nextRound = false;
                }else{
                    emptyHand();
                }
            }
        }       
        System.out.println("Thanks for playing");
	}
	
    //evaluates hands: determine the type and payout
	private String checkHand(ArrayList<Card> hand){
		if(royalFlush(hand)){
            payout=250.0;
            return "This is a Royal Flush.";
        } else if (straightFlush(hand)){
            payout=50;
            return "This is a Straight Flush.";
        } else if (fourOfAKind(hand)){
            payout=25;
            return "This is a four of a kind.";
        }else if (fullHouse(hand)){
            payout=6;
            return "This is a full House.";
        }else if (flush(hand)){
            payout=5;
            return "This is a flush.";
        }else if (straight(hand)){
            payout=4;
            return "This is a Straight.";
        }else if (threeOfAKind(hand)){
            payout=3;
            return "This is a Three of a kind.";
        }else if (twoPair(hand)){
            payout=2;
            return "This is a Two Pairs.";
        }else if (pair(hand)){
            payout=1;
            return "This is One Pair.";
        }else{
            payout=0;
            return "This is nothing.";
        }
	}
    
    //returns player's bankroll after each play
    private String payoutResult(){
        p.winnings(payout);
        return "Your current bankroll is " + p.getBankroll();
    }
	
	//returns true if the hand has at least one pair
	private boolean pair(ArrayList<Card> c){
        Collections.sort(c);
        for(int i=0; i<4;i++){
                if (c.get(i).getRank()==c.get(i+1).getRank()){
                    return true;
                }
        }
        return false;
    }
    
    //returns true if there is at least two pairs
    private boolean twoPair(ArrayList<Card> c){
        Collections.sort(c);
        int count =0;
         for(int i=0; i<4;i++){
                if (c.get(i).getRank() == c.get(i+1).getRank()){
                    count++;
                    i =i+2;
                }
            } 
        if(count==2){
            return true;
        }else{
            return false;
        }
    }
    
    //returns true if there is at least three cards of the same value
    private boolean threeOfAKind(ArrayList<Card> c){
        Collections.sort(c);
        for(int i=0; i<3;i++){
            if (c.get(i).getRank()==c.get(i+1).getRank()
                && c.get(i).getRank()==c.get(i+2).getRank()){
                return true;
            }
        }
        return false;
    }
   
    //returns true if five cards are of consecutive value
    private boolean straight(ArrayList<Card> c){
        Collections.sort(c);
        if(!pair(c) && (c.get(4).getRank()-c.get(0).getRank()==4 
                        ||(( c.get(4).getRank()-c.get(0).getRank()==12) && 
                            c.get(4).getRank()-c.get(1).getRank()==3))){
            return true;
        }
        return false;
    }
    
    //returns true if five cards are all in the same suit
    private boolean flush(ArrayList<Card> c){
        Collections.sort(c);
        if(c.get(0).getSuit()==c.get(1).getSuit()&& 
           c.get(0).getSuit()==c.get(2).getSuit() &&
           c.get(0).getSuit()==c.get(3).getSuit() &&
           c.get(0).getSuit()==c.get(4).getSuit()){
            return true;
        }
        return false;
    }
       
    //returns true if the cards has a three of a kind and a pair
    private boolean fullHouse(ArrayList<Card> c){
        if (threeOfAKind(c) && twoPair(c)){
            return true;
        }
        return false;
    }
    
    //returns true if four cards of the same value
    private boolean fourOfAKind(ArrayList<Card> c){
        Collections.sort(c);
        for(int i=0; i<2;i++){
            if (c.get(i).getRank()==c.get(i+1).getRank() 
                && c.get(i).getRank()==c.get(i+2).getRank() 
                && c.get(i).getRank()==c.get(i+3).getRank()){
                return true;
            }
        }
        return false;
    }
    
    //returns true if cards has a straight and a flush
    private boolean straightFlush(ArrayList<Card> c){
        if (straight(c)&&flush(c)){
            return true;
        }
        return false;
    }
    
    //returns true if cards has a 10,J,Q,K,A all of the same suit
    private boolean royalFlush(ArrayList<Card> c){
        Collections.sort(c);
        if (flush(c)==true && straight(c)==true && c.get(4).getRank()==13){
            return true;
        }
        return false;
    }
    
    //deals the player five cards
	private void dealCard(){
        for(int i=0; i<5;i++){
             p.addCard(cards.deal());
         } 
    } 
    
    //redraws cards if desired
	private void redraw(){
        Scanner input = new Scanner(System.in);
        System.out.println("Would you like to redraw? Answer Y or N");
        String ans = input.next();
        if (ans.equals("Y")){
            System.out.println("Enter the order of cards you wish to replace."+
                          " e.g  1,3,5");
            String[] indexArray = input.next().split(",");
            int index = Integer.parseInt(indexArray[0])-1;
            int counter=1;
            //remove cards from hand
            for(int i=0;i<indexArray.length;i++){
                p.removeCard(p.getHand().get(index));              
                if(i+1<indexArray.length){
                    index=Integer.parseInt(indexArray[i+1])-1-counter;
                    counter++;
                }
            }
            //deal new cards to hand
            while(p.getHand().size()<5){
                p.getHand().add(cards.deal());
            }
            //print out new hand
            System.out.println("Your new cards are: " + p.displayCard()); 
        }
    }
    
    //empty the hand of the player for replay purpose
    private void emptyHand(){
        for(int i=0;i<5;i++){
            p.removeCard(p.getHand().get(0));
        }
    }
}

