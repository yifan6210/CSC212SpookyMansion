package edu.smith.cs.csc212.spooky;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class represents all of the game state needed to understand the player.
 * At the beginning of this project, it is just the ID or name of a place.
 * 
 * @author jfoley
 *
 */
public class Player {
	/**
	 * The ID of the Place object where the player is currently.
	 */
	private String place;
	
	/**
	 * Player will add places that they have visited.
	 */
	
	private Set<String> visited;
	
	/**
	 * List of items that the player collects
	 * 
	 */
	
	public List<String> stuff;

	/**
	 * A player is created at the start of a game with just an initial place.
	 * @param initialPlace - where do we start?
	 */
	public Player(String initialPlace) {
		this.place = initialPlace;
		this.visited = new HashSet<>(); //initialized the set 
		this.stuff = new ArrayList<>();
	}

	/**
	 * Get access to the place instance variable from outside this class.
	 * @return the id of the current location.
	 */
	public String getPlace() {
		return place;
	}

	/**
	 * Call this method when the player moves to a new place.
	 * @param place - the place we are now located at.
	 */
	public void moveTo(String place) {
		this.place = place;
	}
	
	/**
	 * This add places that one has been to the Set visited.
	 */
	
	public void rememberThisPlace() {
		this.visited.add(place);	
	}
	
	/**
	 *  Return true if the player's current position is a place in the set visited. 
	 */
	
	public boolean hasBeenHearBefore() {
		return this.visited.contains(this.getPlace()); 
		
	}
	
	
}
