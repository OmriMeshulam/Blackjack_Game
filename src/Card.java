 // Card class, contains 2 objects with getter and setter methods.

public class Card {
	private Suit suit;
	private Rank rank;
	
	public Card(Suit suit,Rank rank){
		this.suit = suit;
		this.rank = rank;
	}
	
	public Suit getSuit(){
		return suit;
	}
	
	public void setSuit(Suit suit){
		this.suit = suit;
	}
	
	public Rank getRank(){
		return rank;
	}
	
	public void setRank(Rank rank){
		this.rank = rank;
	}
	
	// Compares two cards and returns true if they have the same rank and suit.
	public static boolean compare(Card c1, Card c2){
		if(c1.rank == c2.rank)
			if (c1.suit == c2.suit) return true;
		return false;
	}
	
	// Compares two cards and return true if they have the same rank.
	public static boolean compareRank(Card c1, Card c2){
		if(c1.rank == c2.rank) return true;
		else return false;
	}
	
	// Compares two cards and returns true if they have the same suit
	public static boolean compareSuit(Card c1, Card c2){
		if(c1.suit == c2.suit) return true;
		else return false;
	}
	
}
