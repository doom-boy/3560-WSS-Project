package app;
// main.java

import map.Map;
import map.Position;
import map.Tile;
import map.WeatherSystem;
import player.Player;
import logic.brain.Brain;
import logic.vision.Vision;

import java.util.Scanner;

import event.Event;

// need brain, vision, weather, tile, map, event

/**
 * 
 * Correction:
 * ask user for map size instead of prompting difficulty; difficulty to determine map gen
 * 
 */

public class WSS {

    private int turnCount; //start @ 0 and iteratively grow each cycle
    private int difficulty;
    private Player player;
    private Map map;  //need to build out
    
    private Object weatherSystem; // WeatherSystem placeholder

    // difficulty: 0=easy, 1=normal, 2=hard (easy like 15x15, normal = 30x30, hard =50x50)
    public WSS(int difficulty) {
        this.difficulty = difficulty;
        this.turnCount = 0;
    }

    // public void init() {
    //     generateMap(difficulty);
    //     Position start = new Position(0, map.getHeight() / 2);
    //     // Brain/Vision placeholders, nulls for now
    //     player = new Player(start, null, null);
    // }

    public void generateMap(int mapSize) {
        // difficulty derived from size; <20 = easy, <40=normal, else hard
        this.difficulty = mapSize < 20 ? 0 : mapSize < 40 ? 1 : 2;
        map = new Map(mapSize, mapSize, difficulty);
        map.generate();
    }

    
    // simplied win/lose con check for alive
    public void run() {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter map size (e.g. 15, 30, 50): ");
        int size = sc.nextInt();
        generateMap(size);
        Position start = new Position(0, map.getHeight() / 2);
        player = new Player(start, null, null);
        // init() replaced by inline setup
//  above
        while (true) {
            boolean alive = takeTurn();
            if (!alive) {
                System.out.println("Game Over (Turn " + turnCount + ")");
                break;
            }
            if (player.getPosition().getX() == map.getWidth() - 1) { // if on the eastern edge
                System.out.println("Game Win (Turn " + turnCount + ")");
                break;
            }
        }
    }

    //returns false if player dies this turn (food or water < 0)
    public boolean takeTurn() {
        turnCount++;
        System.out.println("\n--- Turn " + turnCount + " ---");

        // Brain decides next move
        //#############################
        // placeholder - Brain.decideMove() ; implement later
        //######################
        Tile targetTile = null; // Brain returns this, return curr tile even if staying; need to wipe to null after decision act

        boolean moving = (targetTile != null); 

        //Apply weather stat modification
        //################################## # #
        // WeatherSystem.progressWeather() placeholder; need to figure out weather sys
        // double weatherFoodMod = weatherSystem.getFoodMod();
        // double weatherWaterMod = weatherSystem.getWaterMod();
        ///############################

        if (moving) {
            // Update player location
            player.makeMove(targetTile);
            // Get tile stat modification
            Tile current = map.getTile(player.getPosition());
            if (current != null) {
                player.applyTileCost(current);
            }
        } else {
            // resting: regain 2 movement, half food/water cost
+            player.rest();
            Tile current = map.getTile(player.getPosition());
            if (current != null) {
                player.applyHalfTileCost(current);
            }
        }

        //print stats
        printPlayerStats();

        // CHECK lose cons
        if (player.getCurrFood() <= 0 || player.getCurrWater() <= 0) {
            return false;
        }

        //Check current tile for eastern edge (check win con)
        Tile tile = map.getTile(player.getPosition());
        if (player.getPosition().getX() == map.getWidth() - 1) {
            return true; // win handled in run()
        }

        //Get tile events; one event interaction per turn
        if (tile != null && tile.getEvent() != null) {
            Event event = tile.getEvent();
            if (event.isAvailable()) {
                boolean interact = true; // Brain.considerEvent() will decide
                if (interact) {
                    event.trigger(player);
                    if (!event.isRepeating()) event.setEncountered(true);
                }
            }
        }

        return true;
    }








    private void printPlayerStats() {
        System.out.printf("Pos(%d,%d) | Food: %d/%d | Water: %d/%d | Move: %d/%d | Gold: %d | Turn: %d%n",
            player.getPosition().getX(), player.getPosition().getY(),
            player.getCurrFood(), player.getMaxFood(),
            player.getCurrWater(), player.getMaxWater(),
            player.getCurrMovement(), player.getMaxMovement(),
            player.getCurrGold(), turnCount);
    }


    public static void main(String[] args) {
        int difficulty = 1; // normal
        new WSS(difficulty).run();
    }



//     public static void main(String[] args) {

//         // Initialize map
//         Map gameMap = Map.generateMap();

//         // Initialize player at western edge (x = 0, y = 0)
//         Player player = new Player(0, 0);

//         int game_state = 0; // 0 = nothing, 1 = player has lost, 2 = player has won and reached the eastern edge
//         int turn_count = 0;

//         // Main game loop
//         while (game_state = 0) {

            
            
//             // will move the win/lose cons into the turn logic
            
//             // Win con: reached eastern edge ** ts can be fixed up later on, just a placeholder for now**
//             if (player.getX() >= gameMap.getWidth() - 1) {
//                 System.out.println("Reached the eastern edge. Success.");
//                 break;
//             }

//             // Lose condition
//             if (player.getFood() <= 0 || player.getWater() <= 0) {
//                 System.out.println("Player has perished.");
//                 break;
//             }




//             System.out.println(turn_count++);

//             // Execute one turn
//             game_state = takeTurn(player, gameMap);

//             turnMessage(game_state);

            
            
//         }
//     }

//     // Take turn logic (to be expanded later)
//     public static void takeTurn(Player player, Map gameMap) {

//         // These are tentative

//         // Brain decides on move 

//         // if want to move to new tile:
//         //     update weather
//         //     update player location
//         //     get weather stat modification
//         //     get tile stat cost
//         //     update player stat
//         // else
//         //     update weather
//         //     get weather stat modification
//         //     get half of tile stat cost
//         //     update player stat


//         // check win / lose conditions
//         if player.food < 0 || player.water < 0:
//             gameover
//             return 1 // use to break out of steps function, have a detect for 1 as gameover
        
//         if tile.current = eastern edge:
//             gamewin
//             return 2 // have detect for 2 as gamewin

//         // event checking
//         if tile.eventExist = true:
//             if event_interaction != done
//                 get first event of tile.event_list
//                 brain.consider event
//                 if (brain.interactEvent = true && event is unvisited)
//                     mark as visited in tile.event_list
//                     collect bonus
//                 else if (brain.interactEvent = true && event is repeatable)
//                     if event = trade:
//                         trade.initialize_Trade(event_list.tradeEvent.traderType)
//                     else:
//                         collect bonus // update player stat
        

//         //console output stuff
//         System.out.println("Moved to (" + newX + ", " + newY + ")");
//         System.out.println("Food: " + player.getFood() + " | Water: " + player.getWater());
//         // what event interacted w/ how many times, event outcomes

//         return 0 // have detect where 0 is normal end of turn with no win or lose cons met




//         // // Move player one tile east ** change to get what brain wants to do
//         // int newX = player.getX() + 1;
//         // int newY = player.getY();

//         // // Get tile traversal cost
//         // int cost = gameMap.getTileCost(newX, newY);

//         // //Apply cost to player resources
//         // player.setFood(player.getFood() - cost);
//         // player.setWater(player.getWater() - cost);

//         // // Update player position
//         // player.setPosition(newX, newY);

//         // Handle tile events (if any)
//         // gameMap.triggerEvent(newX, newY, player);

        
//     }

//     public static void turnMessage(int game_state) {
//         if (game_state = 1) {
//             System.out.println("game lost");
//         }
//         else if (game_state = 2) {
//             System.out.println("game win!!");
//         }
//         else {
//             System.out.println("===============================")
//         }
//     }
}