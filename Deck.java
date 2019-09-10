/**
 * The Deck class contains information of implementation
 * of the deck of cards
 * @author: Annabelle Tang
 */
public class Deck {
    
	private Card[] cards;
	private int top; // the index of the top of the deck

    //instantiate the Deck object
	public Deck(){
		top = 0;
        // make a 52-card deck
		cards = new Card[52];
        int index =0;
		for(int i=0;i<4;i++){
            for(int j=0;j<13;j++){
                cards[index] = new Card(i+1,j+1);
                index++;
            }
        }
	}
	
    // shuffle the cards in the deck
	public void shuffle(){
        Card temp = new Card(0,0);
        int index = 0;
		for (int i=0; i<52;i++){
            temp = cards[i];
            index = (int)(Math.random()*(51-i) + i);
            cards[i] = cards[index];
            cards[index] = temp;
        }
	}
	
    // return the top card in the deck
	public Card deal(){	
		top++;
        return cards[top-1];
	}
    
}

