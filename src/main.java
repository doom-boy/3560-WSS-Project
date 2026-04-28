// main.java

import map.Map;

public class Main {

    public static void main(String[] args) {

        // Initialize map
        Map gameMap = Map.generateMap();

        // Initialize player at western edge (x = 0, y = 0)
        Player player = new Player(0, 0);

        int game_state = 0; // 0 = nothing, 1 = player has lost, 2 = player has won and reached the eastern edge
        int turn_count = 0;

        // Main game loop
        while (game_state = 0) {

            
            
            // will move the win/lose cons into the turn logic
            
            // Win con: reached eastern edge ** ts can be fixed up later on, just a placeholder for now**
            if (player.getX() >= gameMap.getWidth() - 1) {
                System.out.println("Reached the eastern edge. Success.");
                break;
            }

            // Lose condition
            if (player.getFood() <= 0 || player.getWater() <= 0) {
                System.out.println("Player has perished.");
                break;
            }




            System.out.println(turn_count++);

            // Execute one turn
            game_state = takeTurn(player, gameMap);

            turnMessage(game_state);

            
            
        }
    }

    // Take turn logic (to be expanded later)
    public static void takeTurn(Player player, Map gameMap) {

        // These are tentative

        // Brain decides on move 

        // if want to move to new tile:
        //     update weather
        //     update player location
        //     get weather stat modification
        //     get tile stat cost
        //     update player stat
        // else
        //     update weather
        //     get weather stat modification
        //     get half of tile stat cost
        //     update player stat


        // check win / lose conditions
        if player.food < 0 || player.water < 0:
            gameover
            return 1 // use to break out of steps function, have a detect for 1 as gameover
        
        if tile.current = eastern edge:
            gamewin
            return 2 // have detect for 2 as gamewin

        // event checking
        if tile.eventExist = true:
            if event_interaction != done
                get first event of tile.event_list
                brain.consider event
                if (brain.interactEvent = true && event is unvisited)
                    mark as visited in tile.event_list
                    collect bonus
                else if (brain.interactEvent = true && event is repeatable)
                    if event = trade:
                        trade.initialize_Trade(event_list.tradeEvent.traderType)
                    else:
                        collect bonus // update player stat
        

        //console output stuff
        System.out.println("Moved to (" + newX + ", " + newY + ")");
        System.out.println("Food: " + player.getFood() + " | Water: " + player.getWater());
        // what event interacted w/ how many times, event outcomes

        return 0 // have detect where 0 is normal end of turn with no win or lose cons met




        // // Move player one tile east ** change to get what brain wants to do
        // int newX = player.getX() + 1;
        // int newY = player.getY();

        // // Get tile traversal cost
        // int cost = gameMap.getTileCost(newX, newY);

        // //Apply cost to player resources
        // player.setFood(player.getFood() - cost);
        // player.setWater(player.getWater() - cost);

        // // Update player position
        // player.setPosition(newX, newY);

        // Handle tile events (if any)
        // gameMap.triggerEvent(newX, newY, player);

        
    }

    public static void turnMessage(int game_state) {
        if (game_state = 1) {
            System.out.println("game lost");
        }
        else if (game_state = 2) {
            System.out.println("game win!!");
        }
        else {
            System.out.println("===============================")
        }
    }
}