/**
 * The Card class contains information of implementation
 * of each card of the poker
 * @author: Annabelle Tang
 * @date: Nov.5th
 */
public class Card implements Comparable<Card>{
	
	private int suit; // use integers 1-4 to encode the suit: C<D<H<S
	private int rank; // use integers 1-13 to encode the rank
	
	public Card(int s, int r){
		//make a card with suit s and value v
		suit = s;
        rank = r;
	}
	
    //compare cards base on rank first, then suit
	public int compareTo(Card c){
		if (this.rank>c.getRank()){
            return 1; //this card is greater than card c
        } else if(this.rank <c.getRank()){
            return -1; //this card is smaller than card c
        } else{
            if(this.suit>c.getSuit()){
                return 1;//this card is greater than card c
            }
            else{
                return -1;//this card is smaller than card c
            }
        }
	}
	
    //print out the info of a Card object in string
	public String toString(){
		if(suit==1){
            return rank + " of Club";
        } else if(suit==2){
            return rank + " of Diamond";
        }if(suit==3){
            return rank + " of Heart";
        } else{
            return rank + " of Space";
        }
	}
    
	//return the rank of a card
    public int getRank(){
        return rank;
    }
    
    //return the suit of a card
    public int getSuit(){
        return suit;
    }
}

