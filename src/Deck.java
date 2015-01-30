// Deck class which populates itself with objects from card class.

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

	private ArrayList<Card> deck = new ArrayList<Card>();
	
	public Deck(){
		
	}
	
	public void initFullDeck(){
		deck.removeAll(deck);
		for(Rank r : Rank.values()){
			for(Suit s : Suit.values()){
				deck.add(new Card(s, r));
			}
		}
	}
	
	public void initEmptyDeck(){
		deck.removeAll(deck);
	}
	
	// Returns a copy of the deck. 
	public List<Card> getDeck(){
		return Collections.unmodifiableList(deck);
	}

	// Removes a card from a specific location in the deck.
	public Card removeCardDeck(int index){
		return deck.remove(index);
	}
	
	// Appends the new card to the end of the deck.
	public void addOneCard(Card c){
		deck.add(c); 
	}
	
	//Remove one card of the same rank and suit
	public void removeAParticularCard(Card c){
		for(int i=deck.size()-1;i>=0;i--){
			if(deck.get(i).getRank() == c.getRank() && deck.get(i).getSuit() == c.getSuit()){
				deck.remove(i);
				break;
			}
		}
	}
	
	// Removes all card of a specific rank. e.g. deck.removeAllCardsOfRank(Rank.FIVE)
	public void removeAllCardsOfRank(Rank r){
		for(int i =deck.size()-1; i>=0; i--){
			if(deck.get(i).getRank() == r){
				deck.remove(i);
			}
		}
	}
	
	// Overides the toString Function
	// Allows to create a custom string output when printing the deck.
	@Override
	public String toString(){
		String s = " ";
		for(Card c: deck){
			s = s + c.getSuit().toString() + " " + c.getRank().toString() + "\n";	
		}
		return s;
	}
	
	// Returns all the cards in a deck ordered by rank.
	public ArrayList<Card> getOrderedCards(){
		ArrayList<Card> tempDeck = new ArrayList<Card>();
		int currentRank = 1;
		
		for (int i=0; i<deck.size();i++){				// Outer loop - goes up the ranks
			for(int j = 0; j<deck.size();j++){ 			// Inner loop - collects the current rank
				if(deck.get(j).getRank().getRankNumber() == currentRank){	// Adding the same ranks then increasing r to go to outer loop to collect the next set of ranks
					tempDeck.add(deck.get(j));
				}
			}
			currentRank++;
		}
		return tempDeck;
	}
	
	// Returns the number of cards in the deck.
	public int getNumberOfCardsRemaining(){
		return deck.size();
	}
	
	// Removes and returns the top card from the deck
	public Card dealCard(){
		Card topCard = deck.get(0);
		deck.remove(0);
		return topCard;
	}
	
	// Shuffles the deck
	public void shuffle(){
		Collections.shuffle(deck);
    }
	
	// Creates an ArrayList and deals out 5 card poker hands. (removes card from the top of the deck.)
	public ArrayList<Card> dealHand(){
		int cardNum = 5;
		ArrayList<Card> cardDeal = new ArrayList<Card>();
		
		while(cardNum>0){
			cardDeal.add(deck.get(0));
			deck.remove(0);
			cardNum--;
		}
		return cardDeal;
	}
	
	// Compares two Arrays and determines a winner based on the sum of the ordinal numbers of each Card's Rank in the Array
	public int compareHands(ArrayList<Card> p1, ArrayList<Card> p2){
		int answer;
		int numOfCards = 5;
		int p1Score = 0, p2Score = 0;
		
		// Adding up the players cards, calculating scores
		for (int i=0;i<numOfCards;i++){
			p1Score += p1.get(i).getRank().getRankNumber();
			p2Score += p2.get(i).getRank().getRankNumber();
		}
		
		if(p1Score > p2Score)
			answer = 1;
		else if ( p1Score < p2Score)
			answer = 2;
		else
			answer = 0; // tie or something possibly went wrong
						// possible to add another clause with exception handling
		return answer;
	}
}
