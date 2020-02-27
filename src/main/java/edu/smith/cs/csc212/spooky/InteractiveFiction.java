package edu.smith.cs.csc212.spooky;

import java.util.List;

/**
 * This is our main class for SpookyMansion.
 * It interacts with a GameWorld and handles user-input.
 * It can play any game, really.
 *
 * @author jfoley
 *
 */
public class InteractiveFiction {

	/**
	 * This method actually plays the game.
	 * @param input - a helper object to ask the user questions.
	 * @param game - the places and exits that make up the game we're playing.
	 * @return where - the place the player finished.
	 */
	static String runGame(TextInput input, GameWorld game) {
		// This is the current location of the player (initialize as start).
		Player player = new Player(game.getStart());
		
		System.out.println("Type 'help' if you don't know what you're doing.");

		// Play the game until quitting.
		// This is too hard to express here, so we just use an infinite loop with breaks.
		while (true) {
			// Print the description of where you are.
			Place here = game.getPlace(player.getPlace());
			
			System.out.println();
			System.out.println("... --- ...");
			System.out.println(here.getDescription());
			
			if (player.hasBeenHearBefore()) {
				System.out.println("This place feels familiar.");
			}
			
			player.rememberThisPlace();
		

			// Game over after print!
			if (here.isTerminalState()) {
				break;
			}
			


			// Show a user the ways out of this place.
			List<Exit> exits = here.getVisibleExits();

			for (int i=0; i<exits.size(); i++) {
				Exit e = exits.get(i);
				System.out.println(" "+i+". " + e.getDescription());
			}
			
			
			// Show the user the items of the place
//			for (String i : here.items) {
//				System.out.println(i);
//			}

			


			// Figure out what the user wants to do, for now, only "quit" is special.
			List<String> words = input.getUserWords("?");
			if (words.size() > 1) {
				System.out.println("Only give the system 1 word at a time!");
				continue;
			}

			// Get the word they typed as lowercase, and no spaces.
			// Do not uppercase action -- I have lowercased it.
			
			String action = words.get(0).toLowerCase().trim();

			if (action.equals("quit") || action.equals("escape") || action.equals("q")) {
				if (input.confirm("Are you sure you want to quit?")) {
					// quit!
					break;
				} else {
					// go to the top of the game loop!
					continue;
				}
			}
			
			// Show the place the items of the place only after they turn the light on 
			
			if (action.equals("light")) {
				System.out.println("The light is on! \n" + "Now you can see!");
				for (String i : here.items) {
				System.out.println(i);
				}}
				
			
			// help 
			if (action.equals("help")) {
				System.out.println("To play the game, enter the number of the room.");
				System.out.println("To quit the game, type in 'escape', 'q' or 'quit'.");
				System.out.println("Type 'search' when you want to furthe explore the place.");
				System.out.println("Type 'take' to pick stuff up while exploring the room.");
				System.out.println("Type 'stuff' to see what you've found so far.");
				System.out.println("Type 'light' to get some light when the room is dark.");
				continue;
			} 
			
			// search method on all exits 
			
			if (action.equals("search")) {
				here.search();
				System.out.println("You search the room for additional exits.");
				continue;
			}
			
			// take method

			if (action.equals("take")) {
				for (String i : here.items) {
					System.out.println("You take the " + i +".");
				}
				
				player.stuff.addAll(here.items);
				here.items.removeAll(here.items);
				

				continue; 
			}
			
			// stuff
			
			if (action.equals("stuff")) {
				if (player.stuff.size() != 0) {
					for (String i : player.stuff) {
						System.out.println("You have a "+ i +".");			
					}
				} else {
				System.out.println("You have nothing");	
			}
				continue;
			}



			// From here on out, what they typed better be a number!
			Integer exitNum = null;
			try {
				exitNum = Integer.parseInt(action);
			} catch (NumberFormatException nfe) {
				System.out.println("That's not something I understand! Try a number!");
				continue;
			}

			if (exitNum < 0 || exitNum >= exits.size()) {
				System.out.println("I don't know what to do with that number!");
				continue;
			}

			// Move to the room they indicated.
			Exit destination = exits.get(exitNum);
			if (destination.canOpen(player)) {
				player.moveTo(destination.getTarget());
			} else {
				System.out.println("You cannot unlock that right now. Maybe with a key?");
			}
		}

		return player.getPlace();
	}

	/**
	 * This is where we play the game.
	 * @param args
	 */
	public static void main(String[] args) {
		// This is a text input source (provides getUserWords() and confirm()).
		TextInput input = TextInput.fromArgs(args);

		// This is the game we're playing.
		//GameWorld game = new SpookyMansion();
		GameWorld game = new HuntedLibrary();

		// Actually play the game.
		runGame(input, game);

		// You get here by typing "quit" or by reaching a Terminal Place.
		System.out.println("\n\n>>> GAME OVER <<<");
	}

}
