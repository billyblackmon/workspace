package carma;


import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList; 

/**
A player moves 1m every second and starts out in a random place on the playing area. 
A referee has to give a yellow card to a player if that player moves within 
2m of another player. It is the player that moves to within 2m gets the card. 

#TO DO:
If a player gets 2 yellow cards, the player is ejected from the game 
for 10 seconds. 
When a player is off 10 secs the player needs to ask the referee if they are eligible to 
play again. 

The referee will let the player return to playing the first time this happens, 
but not subsequent times. The last player left playing is the winner.
 
Write a console application that emulates this activity. 
 * @author Bill Blackmon
 * 
 *
 */



import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

public class CarmaController<E> {
	
	// tunable variables
	final static int MaxPlayers = 10;		// spec. says 10
	final static int XFieldPos = 100;		// spec. says 100
	final static int YFieldPos = 100;		// spec. says 100
	final static int RunInterval = 1000;	// 10 - 10000 seconds, WAY too long.
	
	// lists for players
	List<Player> playerList = new CopyOnWriteArrayList();
	List<Player> newPlayerList = new CopyOnWriteArrayList();
	List<Player> restrictedPlayerList = new CopyOnWriteArrayList();
	
	// 10 second timer
	Timer timer = new Timer();
	
	// randomizer for x and y positions
	Random randInitPos = new Random();
	
	public CarmaController() {
		generatePlayers();
	}	// end constr
	
	/**
	 * Generate the initial players
	 */
	private void generatePlayers(){
		
				for(int i = 0; i < MaxPlayers; i++) {
					// set initial position
					int  x = randInitPos.nextInt(XFieldPos) + 1;	// was 100
					int  y = randInitPos.nextInt(YFieldPos) + 1;	// was 100
					Player player = new Player("player" + "_" + i, x, y);
					playerList.add(player);
				}
				
				if(playerList.size() > 0) {
					// kick-off processes
					timer();
				}
	}	// end generatePlayers()
	
	/**
	 * Timer that processes players at interval defined by
	 * RunInterval
	 */
	private void timer() {
			
		if(playerList.size() != 0) {
			
			 new java.util.Timer().schedule(new TimerTask(){
				 	int runCount  =0;
				 	
			        public void run() {
			        	runCount++;
						LocalTime time = LocalTime.now(); 
System.out.println("\n\nrun() - time now : " + time);
System.out.println("run() - timer #runCount: " + runCount + " - calling TimerTask - playerCount is " + playerList.size());
						// reinsert restricted players
			            removeFromRestriction();
			            // shift positions
			            shiftPlayers();
			            // detect player collisions
			            detectCollisions();
			            
				        if(playerList.size() == 1) {
				        	// kill thread
				        	timer.cancel();
				        	timer.purge();
				        	
			            	System.out.println("\nrun() TIMER CANCELLED-  ONE PLAYER LEFT - exiting to end()\n");
			            	System.out.println("LAST PLAYER STANDING IS: " + playerList.get(0).name );
			            	
			            	// exit system
			            	exit();
			            }	// end if
			        }	// end run
				 
		    },RunInterval*1,RunInterval*1); // end timerTask was 10000*1, 10000*1 - ten seconds

		}	// end if
		return;
	}	// end timer()
	
	/**
	A referee has to give a yellow card to a player if that player moves within 
    2m of another player. 
    Determine position of players relative to each other and process accordingly
    Players that are on the edge or out-of-bounds are sent back to the center of the 
    field.
	 */
	private void detectCollisions(){
	// loop through both collections, compare x and y for both
		Iterator<Player> it = playerList.iterator();
		
		Player pTempOut;
		Player pTempIn;
		while(it.hasNext()) {
			Iterator<Player> newIt = newPlayerList.iterator();
			pTempOut = it.next();
			int xOut = pTempOut.getxPos();
			int yOut = pTempOut.getyPos();
			
			// if on edge of boundary or out of bounds reset to center
			if( (xOut < 1) || (yOut < 1) ) {
				System.out.println("\n\ndetectCollisions() out of bounds - player is: " + pTempOut.getName() );	
				System.out.println("x and y positions are: " + xOut + ", " + yOut);
				pTempOut.setxPos(50);
				pTempOut.setyPos(50);
			}
			
			while(newIt.hasNext()) {
				pTempIn = newIt.next();
				int xIn = pTempIn.getxPos();
				int yIn = pTempIn.getyPos();
				
				xIn = pTempIn.getxPos();
				yIn = pTempIn.getyPos();
				
				// calculate diff between out and in x position
				int xDiff = xIn - xOut;
				
				if( (xDiff < 3) && (xDiff > 0) ) {
					System.out.println("\nx COLLISION - xIn and xOut are: " + xIn + ", " + xOut);
					System.out.println("diff between in and out is: " + xDiff);
					pTempOut.markCount++;
					System.out.println("Incrementing markCount on: " + pTempOut.getName());

					if(pTempOut.markCount > 1) {
						// evict from list
						System.out.println("\nx COLLISION - xIn and xOut are: " + xIn + ", " + xOut);
						System.out.println("EVICTING - player: " + pTempOut.name);
						System.out.println("EVICTING - playerList size is: " + playerList.size());
						playerList.remove(pTempOut);
						newPlayerList.remove(pTempOut);
						System.out.println("EVICTED - playerList size is: " + playerList.size());
						System.out.println("EVICTED - newplayerList size is: " + newPlayerList.size());
					} else if (pTempOut.markCount == 1){
						playerList.remove(pTempOut);
						putOnRestriction(pTempOut);
					}
				}
				
				int yDiff = yIn - yOut;
				if( (yDiff < 3) && (yDiff > 0) ) {
					System.out.println("\nY COLLISION - yIn and yOut are: " + yIn + ", " + yOut);
					System.out.println("yDiff between in and out is: " + yDiff);
					pTempOut.markCount++;
					System.out.println("Incrementing markCount on: " + pTempOut.getName());
					
					if(pTempOut.markCount> 1) {
						// evict from list
						System.out.println("EVICTING - playerList size is: " + playerList.size());
						System.out.println("EVICTING - player: " + pTempOut.name);
						playerList.remove(pTempOut);
						newPlayerList.remove(pTempOut);
						System.out.println("EVICTED - playerList size is: " + playerList.size());
						System.out.println("EVICTED - newPlayerList size is: " + newPlayerList.size());
					} else if (pTempOut.markCount == 1){
						playerList.remove(pTempOut);
						putOnRestriction(pTempOut);
					}	// end if
				}	// end if
			}	// end while
		}	// end while
	}	// end detectCollisions
	
	/**
	 * Insert player with a flag into the the restrictedPlayerList
	 * @param player
	 */
	private synchronized void putOnRestriction(Player player) {
		LocalTime time = LocalTime.now(); 
		
		System.out.println("putOnRestriction() - player and marked flag count is: " + player + ", " + player.getMarkCount());
		System.out.println("putOnRestriction() - size of restricted list is: " + restrictedPlayerList.size() );
		playerList.remove(player);
		restrictedPlayerList.add(player);
		System.out.println("putOnRestriction() - PLAYER ADDED: NEW size of restricted list is: " + restrictedPlayerList.size() );
	}	// end putOnRestriction()
	
	/**
	 * Remove all players from restrictedPlayerList that have only one flag.
	 * Polled at RunInterval interval
	 */
	private synchronized void removeFromRestriction() {
		Iterator it = restrictedPlayerList.iterator();
		
		LocalTime time = LocalTime.now(); 
		
		while(it.hasNext()) {
			Player p = (Player) it.next();
			System.out.println("removeFromRestriction() -playerList size is: " + playerList.size() );
			playerList.add(p);
			restrictedPlayerList.remove(p);
			System.out.println("removeFromRestriction() - removed from restriction - NEW playerList size is: " + playerList.size() );
		}
	}	// end removeFromRestriction()
	
	/*
	 * Shift player positions randomly to:
	 * Up, Down, Left or Right. 
	 * add altered players to new newPlayerList
	 */
	private void shiftPlayers() {
		// clear existing list
		newPlayerList.clear();
		Iterator playerListIt = playerList.iterator();
		Iterator newPlayListIt = newPlayerList.iterator();
		
		
		while(playerListIt.hasNext() ) {
		    Player player = (Player) playerListIt.next();
		    
		    Random randNewPos = new Random();
			int  newPos = randNewPos.nextInt(4);
			
			// new pos is 0-3
			// 0 = up
			// 1 = down
			// 2 = left
			// 3 = right
			
			switch(newPos) {
			   case 0 :
				  player.xPos++;
			      break; // optional
			   
			   case 1 :
				  player.xPos--;
			      break; 
			   
			   case 2 :
				   player.yPos--;
				   break; 
				   
			   case 3 :
				   player.yPos++;
			       break;    
			}
			
			// add newly positioned player to list.
			newPlayerList.add(player);
		}	// end iteration
	}	// end shiftPlayers()
	
	/**
	 * Exit the program with time and player count
	 */
	private void exit() {
		LocalTime time = LocalTime.now(); 
		System.out.println("\n\nend() - time now : " + time);
		System.out.println("end() - EXITING program - returning - playerCount is: " + playerList.size());
		System.exit(0);
	}

	
}
