package carma;

/**
 *
 * 
 * Game Exercise
===========================
A playing area is 100m x 100m. A game has a referee and 10 players. 
A player moves 1m every second and starts out in a random place on the playing area. 
A referee has to give a yellow card to a player if that player moves within 
2m of another player. 

It is the player that moves to within 2m gets the card. 

If a player gets 2 yellow cards, the player is ejected from the game for 10 seconds.
 
When a player is off 10 secs the player needs to ask the referee if they are eligible to play again. 

The referee will let the player return to playing the first time this happens, 

but not subsequent times. The last player left playing is the winner.
 
Write a console application that emulates this activity. 
 * 
 * 
 * @author Bill Blackmon - 11/18/2017
 *
 */


public class CarmaMain {

	public static void main(String[] args) {
			CarmaController controller =  new CarmaController();
	}	// end main()
}
