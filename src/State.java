public class State {
	private byte turn; // 1 - 2 - 3
	private Player [] players; // players[0] is the dealer
	private Deck deck;
	private int numOfPlayers = 4; // number of players including the dealer.
	private int [] playersTotalHand = new int [numOfPlayers]; 
	private boolean [] playerBlackjack = new boolean [numOfPlayers]; //if initial 2 card were blackjack 21.
	private boolean [] playerBusted = new boolean [numOfPlayers];
	
	public State(){
		init();
	}
	public void init(){ // Initial state
		turn = 1;
		deck = new Deck();
		deck.initFullDeck();
		deck.shuffle();
		players = new Player[numOfPlayers]; // dealer + 3 players;
		players[0] = new Player(); //dealer
		players[0].receiveCard(deck.dealCard());
		// Give 2 cards to each player
		for(int i = 1; i< Constants.MAX_NUM_PLAYERS; i++){ 
			players[i] = new Player();
			players[i].receiveCard(deck.dealCard());
			players[i].receiveCard(deck.dealCard());
			playerBusted[i] = false;
		}
		for(int i = 1; i< Constants.MAX_NUM_PLAYERS; i++){ 
			setPlayersTotalHand(i,computeBlackJackHand(i));
			if(getPlayersTotalHand(i)==21) setPlayerBlackjack(i,true);
			else setPlayerBlackjack(i,false);
			
		}
	}
	
	public Player getPlayer(int playerNum){
		return players[playerNum];
	}
	
	// We could just use get Player but this method improves readability
	public Player getDealer(){
		return players[0];
	}
	public Deck getDeck(){ return deck; }
	public byte getTurn(){ return turn; }
	public void nextTurn(){ turn++; }
	
	public void setPlayersTotalHand(int index, int val){
		playersTotalHand[index] = val;
	}
	public int getPlayersTotalHand(int index){
		return playersTotalHand[index];
	}
	public void setPlayerBlackjack(int i, boolean var){ //sets to win if initial 2 cards == 21
		playerBlackjack[i] = var;
	}
	public void setPlayerBusted(int i, boolean var){ //
		playerBusted[i] = var;
	}
	public boolean getPlayerBlackjack(int i){
		return playerBlackjack[i];
	}
	public boolean getPlayerBusted(int i){
		return playerBusted[i];
	}
	
	public int computeBlackJackHand(int playerNum){
		int total = 0;
		int aces = 0; // value 11
		int aces2 = 0;  // value 1
		for(int e = 0; e < players[playerNum].getHand().size(); e++){ //iterating through players cards
			if(players[playerNum].getHand().get(e).getRank() == Rank.ACE ){
				aces += 1;
			}
			else if(players[playerNum].getHand().get(e).getRank() == Rank.JACK
					|| players[playerNum].getHand().get(e).getRank() == Rank.QUEEN 
					|| players[playerNum].getHand().get(e).getRank() == Rank.KING){
				total += 10;
			}else{
				total += players[playerNum].getHand().get(e).getRank().getRankNumber();
			}
		}
		aceCount:
			if ((total + (aces * 11)) > 21){
				for (int i = aces; i>0; i--){
					if (aces == 0){
						total += aces2;
						break aceCount;
					}else if ((total + (i * 11) + aces2) <= 21){
						total = total + (i * 11) + aces2;
						break aceCount;
					}else if ((total + (i * 11) + aces2) > 21){
						aces2++;
					}
				}
			}else if ((total + (aces * 11)) <= 21){
				total += (aces * 11);
			}
		return total;
	}
	public void givePlayerCard(int index){
		players[index].receiveCard(deck.dealCard());
	}
}
