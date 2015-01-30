public class Controller {
	MessagePanel msg;
	boolean playing;
	State state;
	
	public Controller(){
	}
	
	public void init(){
		state = new State();
		// Send message to player1
		if(state.getPlayerBlackjack(1)){
			msg.outputMessage(1, Constants.BLACKJACK + "Your Hand: " + state.getPlayer(1).getHandString());
			state.nextTurn();
			if(state.getPlayerBlackjack(2)){
				msg.outputMessage(2, Constants.BLACKJACK + "Your Hand: " + state.getPlayer(2).getHandString());
				state.nextTurn();
				if(state.getPlayerBlackjack(3)){
					msg.outputMessage(3, Constants.BLACKJACK + "Your Hand: " + state.getPlayer(3).getHandString());
					state.nextTurn();
					computeDealer();
				}else{
					msg.outputMessage(3, "It is your turn. Your hand: " + state.getPlayer(3).getHandString());
				}
			}else{
				msg.outputMessage(2, "It is your turn. Your hand: " + state.getPlayer(2).getHandString());
			}
		}else{
			msg.outputMessage(1, "It is your turn. Your hand: "
				+ state.getPlayer(1).getHandString()
				+ " Dealer's hand: "
				+ state.getDealer().getHandString());
	
			msg.outputMessage(2, "It is NOT your turn." /* Your hand: "
				+ state.getPlayer(2).getHandString() */
				+ " Dealer's hand: "
				+ state.getDealer().getHandString());
			msg.outputMessage(3, "It is NOT your turn." /* Your hand: " 
				+ state.getPlayer(3).getHandString() */
				+ " Dealer's hand: "
				+ state.getDealer().getHandString());
		}
	}
	
	public void clientRequest(char c){
		if(playing){
			// Decide based on c
			changeState(c);
			return;
		}
		init();
		playing = true;
	}
	
	public void setMessenger(MessagePanel message){
		msg = message;
		msg.showMenu();
	}
	
	private void showErrorMsgPlayerKey(char c){
		int player = -1;
		if (c == 'q' || c == 'w'){
			player = 1;
		}
		else if(c == 'a' || c == 's'){
			player  = 2;
		}else if(c == 'z' || c == 'x'){
			player = 3;
		}
		msg.outputMessage(player, Constants.WRONG_TURN_MSG);
	}
	
	private void changeState(char c){
		boolean loop = false;
		// Your assignment #2
		
		do{
			if (c == 'q' || c == 'w'){
				if(state.getTurn() == 1){
					if(c == 'q'){
						state.givePlayerCard(1);
						state.setPlayersTotalHand(1, state.computeBlackJackHand(1));
						msg.outputMessage(1, "Your hand: " + state.getPlayer(1).getHandString());
						
						if(state.getPlayersTotalHand(1) > 21){ //lost == next
							state.nextTurn();
							msg.outputMessage(1, "You busted your hand: " + state.getPlayer(1).getHandString());
							state.setPlayerBusted(1, true);
							if(state.getPlayerBlackjack(2)){
								msg.outputMessage(2, Constants.BLACKJACK + " Your hand: " + state.getPlayer(2).getHandString());
								state.nextTurn();
								if(state.getPlayerBlackjack(3)){
									msg.outputMessage(3, Constants.BLACKJACK + " Your hand: " + state.getPlayer(3).getHandString());
									state.nextTurn();
									computeDealer();
								}else{
									msg.outputMessage(3, "It is your turn. Your hand: " + state.getPlayer(3).getHandString());
								}
							}else{
								msg.outputMessage(2, "It is your turn. Your hand: " + state.getPlayer(2).getHandString());
							}
						}
						if(state.getPlayersTotalHand(1) == 21){ //21, now waiting on dealer hand
							msg.outputMessage(1, "Congradulations, 21! Your hand: " + state.getPlayer(1).getHandString() + ". Waiting for dealer's hand.");
							state.nextTurn();
							if(state.getPlayerBlackjack(2)){
								msg.outputMessage(2, Constants.BLACKJACK + " Your hand: " + state.getPlayer(2).getHandString());
								state.nextTurn();
								if(state.getPlayerBlackjack(3)){
									msg.outputMessage(3, Constants.BLACKJACK + " Your hand: " + state.getPlayer(2).getHandString());
									state.nextTurn();
									computeDealer();
								}else{
									msg.outputMessage(3, "It is your turn. Your hand: " + state.getPlayer(3).getHandString());
								}
							}else{
								msg.outputMessage(2, "It is your turn. Your hand: " + state.getPlayer(2).getHandString());
							}
						}
					}
					else if(c == 'w'){
						msg.outputMessage(1, "Your held hand: " + state.getPlayer(1).getHandString());
						state.nextTurn();
						if(state.getPlayerBlackjack(2)){
							msg.outputMessage(2, Constants.BLACKJACK + " Your hand: " + state.getPlayer(2).getHandString());
							state.nextTurn();
							if(state.getPlayerBlackjack(3)){
								msg.outputMessage(3, Constants.BLACKJACK + " Your hand: " + state.getPlayer(3).getHandString());
								state.nextTurn();
								computeDealer();
							}else{
								msg.outputMessage(3, "It is your turn. Your hand: " + state.getPlayer(3).getHandString());
							}
						}else{
							msg.outputMessage(2, "It is your turn. Your hand: " + state.getPlayer(2).getHandString());
						}
						
					}			
				}
				else{ if (state.getTurn() == 4){
						init();
					}else 
						showErrorMsgPlayerKey(c);
				}
			}
			else if (c == 'a' || c == 's'){
				if(state.getTurn() == 2){
					if(c == 'a'){
						state.givePlayerCard(2);
						state.setPlayersTotalHand(2, state.computeBlackJackHand(2));
						msg.outputMessage(2, "Your hand: " + state.getPlayer(2).getHandString());
						
						if(state.getPlayersTotalHand(2) > 21){ //lost == next
							msg.outputMessage(2, "You busted your hand: " + state.getPlayer(2).getHandString());
							state.setPlayerBusted(2, true);
							state.nextTurn();
							if(state.getPlayerBlackjack(3)){
								msg.outputMessage(3, Constants.BLACKJACK + " Your hand: " + state.getPlayer(3).getHandString());
								state.nextTurn();
								computeDealer();
							}else{
								msg.outputMessage(3, "It is your turn. Your hand: " + state.getPlayer(3).getHandString());
							}						}
						else if(state.getPlayersTotalHand(2) == 21){ //21, now waiting on dealer hand
							state.nextTurn();
							msg.outputMessage(2, "Congradulations, 21! Your hand: " + state.getPlayer(2).getHandString() + ". Waiting for dealer's hand.");
							if(state.getPlayerBlackjack(3)){
								msg.outputMessage(3, Constants.BLACKJACK + " Your hand: " + state.getPlayer(3).getHandString());;
								state.nextTurn();
								computeDealer();
							}else{
								msg.outputMessage(3, "It is your turn. Your hand: " + state.getPlayer(3).getHandString());
							}						}
					}
					else if(c == 's'){
						msg.outputMessage(2, "You held hand: " + state.getPlayer(2).getHandString());
						state.nextTurn();
						if(state.getPlayerBlackjack(3)){
							msg.outputMessage(3, Constants.BLACKJACK + " Your hand: " + state.getPlayer(3).getHandString());
							state.nextTurn();
							computeDealer();
						}else{
							msg.outputMessage(3, "It is your turn. Your hand: " + state.getPlayer(3).getHandString());
						}
					}
					
				}
				else{
					if (state.getTurn() == 4){
						init();
					}else 
					showErrorMsgPlayerKey(c);
				}
			}
			else if (c == 'z' || c == 'x'){
				if(state.getTurn() == 3){
					if(c == 'z'){
						state.givePlayerCard(3);
						state.setPlayersTotalHand(3, state.computeBlackJackHand(3));
						msg.outputMessage(3, "Your hand: " + state.getPlayer(3).getHandString());

						if(state.getPlayersTotalHand(3) > 21){ //lost == next
							state.nextTurn();
							state.setPlayerBusted(3, true);
							msg.outputMessage(3, "You busted your hand: " + state.getPlayer(3).getHandString());
							computeDealer();
						} 
						else if(state.getPlayersTotalHand(3) == 21){ //21, now waiting on dealer hand
							state.nextTurn();
							msg.outputMessage(3, "Congradulations, 21! Your hand: " + state.getPlayer(3).getHandString() + ". Waiting for dealer's hand.");
							computeDealer();
						}
					}
						else if(c == 'x'){
							computeDealer();
							state.nextTurn();
						}
					
				}
				else{ 
					if (state.getTurn() == 4){
						init();
					}else 
						showErrorMsgPlayerKey(c);
				}
				
			}else{ if (state.getTurn() == 4){
						init();
					}else{
						System.out.println("Wrong Key.");
					}
			}
		}while(loop);
	}
	
	// computes which how many cards the dealer should be dealt and outputting results
	public void computeDealer(){
		boolean keepGoing = true;
		state.givePlayerCard(0); // giving dealer 2nd card
		state.setPlayersTotalHand(0, state.computeBlackJackHand(0));
		computations:
			while(keepGoing){
				if(state.getPlayersTotalHand(0) == 21){
					if(!state.getPlayerBlackjack(1)){msg.outputMessage(1, Constants.LOST);}
					if(!state.getPlayerBlackjack(2)){msg.outputMessage(2, Constants.LOST);}
					if(!state.getPlayerBlackjack(3)){msg.outputMessage(3, Constants.LOST);}
					break computations;
				}else if(state.getPlayersTotalHand(0)<17){ 
					while(state.getPlayersTotalHand(0)<17){
						state.givePlayerCard(0);
						state.setPlayersTotalHand(0, state.computeBlackJackHand(0));
					}
				}else if(state.getPlayersTotalHand(0)<21 && !isDealerGreatest()){
					if((state.getPlayersTotalHand(1) >= state.getPlayersTotalHand(0)) && (state.getPlayersTotalHand(1) <= 21)
							|| (state.getPlayersTotalHand(2) >= state.getPlayersTotalHand(0)) && (state.getPlayersTotalHand(2) <= 21)
							|| (state.getPlayersTotalHand(3) >= state.getPlayersTotalHand(0)) && (state.getPlayersTotalHand(3) <= 21)){
								state.givePlayerCard(0);
								state.setPlayersTotalHand(0, state.computeBlackJackHand(0));
							}
				}
				else{ //dealer over 21 or highest winning
						keepGoing = false;
						if(state.getPlayersTotalHand(0)<21){
							if(!state.getPlayerBlackjack(1)){
								if(state.getPlayersTotalHand(0) > state.getPlayersTotalHand(1)){
									msg.outputMessage(1, Constants.LOST);
								}else if(state.getPlayersTotalHand(0) < state.getPlayersTotalHand(1)){
									msg.outputMessage(1, Constants.WON);
								}else {
									msg.outputMessage(1, Constants.PUSH);
								}
							}
							if(!state.getPlayerBlackjack(2)){
								if(state.getPlayersTotalHand(0) > state.getPlayersTotalHand(2)){
									msg.outputMessage(2, Constants.LOST);
								}else if(state.getPlayersTotalHand(0) < state.getPlayersTotalHand(2)){
									msg.outputMessage(2, Constants.WON);
								}else {
									msg.outputMessage(2, Constants.PUSH);
								}
							}
							if(!state.getPlayerBlackjack(3)){
								if(state.getPlayersTotalHand(0) > state.getPlayersTotalHand(3)){
									msg.outputMessage(3, Constants.LOST);
								}else if(state.getPlayersTotalHand(0) < state.getPlayersTotalHand(3)){
									msg.outputMessage(3, Constants.WON);
								}else {
									msg.outputMessage(3, Constants.PUSH);
								}
							}
						}else if(state.getPlayersTotalHand(0)>21){
							if(state.getPlayerBusted(1)){
								msg.outputMessage(1, Constants.LOST);
							}else { if(!state.getPlayerBlackjack(1)){msg.outputMessage(1, Constants.WON);}}
							if(state.getPlayerBusted(2)){
								msg.outputMessage(2, Constants.LOST);
							}else { if(!state.getPlayerBlackjack(2)){msg.outputMessage(2, Constants.WON);}}
							if(state.getPlayerBusted(3)){
								msg.outputMessage(3, Constants.LOST);
							}else { if(!state.getPlayerBlackjack(3)){msg.outputMessage(3, Constants.WON);}}
						}
					}
				}

		if(state.getPlayerBlackjack(1))
			msg.outputMessage(1, Constants.BLACKJACK + " Your Hand: " + state.getPlayer(1).getHandString() + " Dealer's Hand: " + state.getDealer().getHandString()); 
		else 
			msg.outputMessage(1, msg.message[0] + " Your Hand: " + state.getPlayer(1).getHandString() + " Dealer's Hand: " + state.getDealer().getHandString()); 

		if(state.getPlayerBlackjack(2))
			msg.outputMessage(2, Constants.BLACKJACK + " Your Hand: " + state.getPlayer(2).getHandString() + " Dealer's Hand: " + state.getDealer().getHandString()); 
		else 
			msg.outputMessage(2, msg.message[1] + " Your Hand: " + state.getPlayer(2).getHandString() + " Dealer's Hand: " + state.getDealer().getHandString());

		if(state.getPlayerBlackjack(3))
			msg.outputMessage(3, Constants.BLACKJACK + " Your Hand: " + state.getPlayer(3).getHandString() + " Dealer's Hand: " + state.getDealer().getHandString()); 
		else 
			msg.outputMessage(3, msg.message[2] + " Your Hand: " + state.getPlayer(3).getHandString() + " Dealer's Hand: " + state.getDealer().getHandString()); 

			}		
	public boolean isDealerGreatest(){ // checks to see if dealer hand is greatest among all players
		if(state.getPlayersTotalHand(0) >= state.getPlayersTotalHand(3)
			&& state.getPlayersTotalHand(0) >= state.getPlayersTotalHand(2)
			&& state.getPlayersTotalHand(0) >= state.getPlayersTotalHand(1)){
			return true;
		}
		else return false;
	}
}
