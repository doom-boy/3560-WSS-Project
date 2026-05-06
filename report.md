# WSS Project - Final Report

## Team Members and Contributions

| Team Member | Contribution |
|---|---|
| [Team Member 1] | *[One sentence description]* |
| [Team Member 2] | *[One sentence description]* |
| [Team Member 3] | *[One sentence description]* |

---

## Project Summary

### What's Finished
- Complete terrain system with 5 unique terrain types and associated movement/resource costs
- Trader system with two distinct trader archetypes (Generous and Greedy) with patience-based negotiation mechanics
- Vision system with 4 different sight patterns for player information gathering
- Brain system with 3 decision-making strategies for autonomous player behavior
- Player stat management system (food, water, movement, gold) with encapsulated getters/setters
- Event system with repeatable and one-time interactions
- Path-finding and movement system with terrain cost calculations
- Full game loop with turn-based movement and state management

### What's Not Yet Finished
- *[Add any incomplete features or known limitations here]*
- *[Video/UI demonstrations]*

---

## Object-Oriented Design Application

This project demonstrates strong OOP principles throughout the architecture. The design leverages **abstraction** through abstract base classes (Event, Trader, Brain, Vision, Terrain) to define contracts for specialized behaviors. **Inheritance** is used extensively to create specialized subclasses (GenerousTrader vs GreedyTrader, Careful vs Determined vs Hoarder brains) that override behavior for different game variants. **Polymorphism** allows the game engine to treat different trader types or brain strategies uniformly through their abstract interfaces. **Encapsulation** protects state through private/protected fields and controlled access methods. **Composition** combines different components (Player contains both Vision and Brain) to build complex behaviors from simpler parts. This design makes the system highly maintainable and extensible—new trader types, vision patterns, or brain strategies can be added without modifying existing code.

---

## Game Design Questions & Answers

### ❓ How many types of terrain do you have?

**Answer: 5 types**

1. **Desert** - High movement cost, high water consumption
2. **Forest** - Moderate movement cost, high food cost
3. **Mountain** - High movement cost, balanced resource costs
4. **Plain** - Low movement cost, low resource consumption
5. **Swamp** - Very high movement cost, high water consumption

Each terrain type is defined in [src/map/terrain/](/src/map/terrain/) with its own movement and resource costs.

---

### ❓ How many types of traders did you implement?

**Answer: 2 types**

#### **GenerousTrader**
- **Offer Bonus:** Gives 40% more resources than base rate
- **Demand Discount:** Asks 30% less than base rate
- **Patience:** 5 (higher tolerance for rejections)
- **Trading Strategy:** Traders who want to build relationships; more willing to negotiate

#### **GreedyTrader**
- **Offer Cut:** Gives 40% less resources than base rate
- **Demand Hike:** Asks 50% more than base rate
- **Patience:** 2 (quick to abandon trades)
- **Trading Strategy:** Traders focused on extracting maximum value; impatient with negotiation

**Key Differences:**
Both traders inherit from the abstract `Trader` class and implement `offerAcceptable()` and `counterOffer()` methods with different multipliers. The GenerousTrader applies its discount/bonus multipliers favorably, while the GreedyTrader applies unfavorable multipliers. Their patience levels determine how many rejections they'll tolerate before refusing to trade. Players cannot initially identify trader types and must learn through interaction.

**Code References:**
- [GenerousTrader.java](src/event/GenerousTrader.java#L21-L32) - See `getOfferMultiplier()` and `getDemandMultiplier()` overrides
- [GreedyTrader.java](src/event/GreedyTrader.java#L14-L25) - Contrasting multiplier values

---

### ❓ How many types of vision do you have?

**Answer: 4 types**

1. **Cautious** - T-shaped vision (North, South, East, current position); very limited sight
2. **Farsight** - Extended cross pattern; can see far in cardinal directions
3. **Focused** - Direct forward vision; tunnel vision but good depth perception
4. **KeenEye** - Expanded radius around player; omni-directional awareness

Each vision subclass defines a different `getVisibleOffsets()` pattern that determines what tiles the player can see on their turn. This affects pathing decisions and resource discovery capability.

---

### ❓ How many types of brain do you have?

**Answer: 3 types**

#### **Careful Brain**
- **Movement Priority:** Prioritizes maintaining resources; seeks food/water before gold
- **Thresholds:** Becomes resource-seeking when below 40% max
- **Event Interaction:** Always interacts with available events (maximizes resource collection)
- **Trade Acceptance:** Accepts trades offering needed food/water
- **Play Style:** Conservative; safety first

Code: [Careful.java](src/logic/brain/Careful.java#L14-L44) - See `decideMove()` at line 16, where resource thresholds are checked first

#### **Determined Brain**
- **Movement Priority:** Prioritizes moving east; only seeks resources when critical
- **Thresholds:** Only resource-seeks at 1/3 max; only rests at 1/8 max movement
- **Event Interaction:** Only interacts when critically low (selective)
- **Trade Acceptance:** Only trades when critically low on food/water
- **Play Style:** Aggressive eastward progress despite resource concerns

Code: [Determined.java](src/logic/brain/Determined.java#L15-L28) - See critical threshold calculations (1/3 and 1/8 ratios)

#### **Hoarder Brain**
- **Movement Priority:** Minimizes travel costs; aggressively seeks gold; keeps resources near max
- **Thresholds:** Seeks resources at 80% max (keeps high reserves)
- **Event Interaction:** Interacts with everything (maximum collection)
- **Trade Acceptance:** Strategic trading—has resources so willing to trade gold for food/water at 90% max
- **Play Style:** Conservative yet greedy; optimizes for profit and efficiency

Code: [Hoarder.java](src/logic/brain/Hoarder.java#L26-L35) - See cost-benefit analysis where movement is avoided if path cost exceeds 50% of remaining movement

---

## Code Quality & Design Patterns

### Hierarchy Examples

**Hierarchy 1: Polymorphic Event System**
- **File:** [src/event/Event.java](src/event/Event.java)
- **Location:** Lines 10-30
- **Description:** Abstract `Event` class defines the contract for all events with `isAvailable()`, `trigger()`, and state management. This allows the game to handle diverse events (FoodBonus, WaterBonus, GoldBonus, Trader) uniformly as Event objects in a collection, with each subclass implementing their own `trigger()` logic without the game engine knowing the specific type.

**Hierarchy 2: Terrain Type System**
- **File:** [src/map/terrain/Terrain.java](src/map/terrain/Terrain.java)
- **Location:** Lines 1-20, contrasted with the 5 concrete implementations (Desert, Forest, Mountain, Plain, Swamp)
- **Description:** Abstract `Terrain` class establishes a common interface with movement/food/water costs. The 5 concrete terrain types inherit and define their specific cost values, allowing the map to treat all terrains polymorphically while maintaining terrain-specific behaviors.

---

### Inheritance Examples

**Inheritance 1: Trader Type Hierarchy**
- **File:** [src/event/GenerousTrader.java](src/event/GenerousTrader.java#L5-L10) and [src/event/GreedyTrader.java](src/event/GreedyTrader.java#L5-L10)
- **Location:** Constructor calls to `super()` demonstrating parent class initialization
- **Description:** Both `GenerousTrader` and `GreedyTrader` extend `Trader` and call `super()` to initialize the parent's protected fields (`stock`, `patience`). They inherit the abstract methods `offerAcceptable()` and `counterOffer()`, implementing them with their own trading logic while reusing the Trader's foundational structure.

**Inheritance 2: Player Specializations**
- **File:** [src/player/HungryPlayer.java](src/player/HungryPlayer.java), [src/player/SpeedyPlayer.java](src/player/SpeedyPlayer.java), [src/player/ThirstyPlayer.java](src/player/ThirstyPlayer.java)
- **Description:** All player types extend the base `Player` class, inheriting core stat management and position tracking while potentially overriding stat thresholds or penalties in specialized ways.

---

### Polymorphism Examples

**Polymorphism 1: Brain Decision-Making**
- **File:** [src/logic/brain/Brain.java](src/logic/brain/Brain.java#L15-L20)
- **Location:** Abstract methods `decideMove()`, `considerEvent()`, `decideTrade()`
- **Description:** The game engine calls `brain.decideMove()` without knowing if it's a Careful, Determined, or Hoarder brain. Each subclass implements its strategy differently, and the engine receives the appropriate decision. The same code path works for all brain types—pure polymorphism.

**Polymorphism 2: Vision Pathing**
- **File:** [src/logic/vision/Vision.java](src/logic/vision/Vision.java#L12-L24)
- **Location:** `getVisibleTiles()` calls abstract `getVisibleOffsets()`
- **Description:** Different vision types (Cautious, Farsight, Focused, KeenEye) each define their own sight patterns via `getVisibleOffsets()`. When `getVisibleTiles()` is called, it works with any vision subclass without modification, adapting to each vision type's unique perception pattern.

---

### Encapsulation Examples

**Encapsulation 1: Player Resource Protection**
- **File:** [src/player/Player.java](src/player/Player.java#L17-L25)
- **Location:** Private/protected resource fields with controlled access
- **Description:** Fields like `currFood`, `currWater`, `currMovement`, `currGold` are protected (not public). Access is controlled through methods like `addFood()`, `makeMove()`, `applyTileCost()` which enforce game rules (clamping values, applying modifiers) rather than allowing direct manipulation. This prevents cheating and maintains game state consistency.

**Encapsulation 2: Trader State Management**
- **File:** [src/event/Trader.java](src/event/Trader.java#L26-L30)
- **Location:** Protected `stock` and `patience` fields with method-based access
- **Description:** A trader's stock and patience aren't directly accessible; the game interacts through methods that enforce trading rules. This prevents players or other code from cheating by directly modifying trader state.

---

### Abstraction Examples

**Abstraction 1: Terrain Cost Abstraction**
- **File:** [src/map/terrain/Terrain.java](src/map/terrain/Terrain.java#L10-15)
- **Location:** Methods `getMovementCost()`, `getFoodCost()`, `getWaterCost()`
- **Description:** Each terrain type hides its internal cost calculation (if any). The game simply calls `getTile().getTerrain().getMovementCost()` without knowing whether it's a Desert or Swamp. The terrain type abstraction hides the implementation complexity.

**Abstraction 2: Event Interaction Abstraction**
- **File:** [src/event/Event.java](src/event/Event.java#L31-32)
- **Location:** Abstract method `trigger(Player player)`
- **Description:** The game engine doesn't know what happens when an event triggers—it could add food, add water, remove gold, or initiate a trade. The abstract `trigger()` method abstracts away event-specific logic while providing a uniform interface for the game to call on any event.

---

### Code Comments

**Excellent Comments 1: Trader Negotiation Logic**
- **File:** [src/event/Trader.java](src/event/Trader.java#L7-17)
- **Location:** Lines 7-17
- **Description:** Clear step-by-step explanation of the trading flow, including patience mechanics, player offers, counteroffers, and patience regeneration. This multi-line block comment explains complex game mechanics clearly.

**Excellent Comments 2: Player Stat Application**
- **File:** [src/player/Player.java](src/player/Player.java#L47-53)
- **Location:** Lines 47-53
- **Description:** Inline comments explaining how terrain modifiers are applied and why certain protective measures exist (checking for negative values). Clear variable names (`foodMod`, `terrainMod`) combined with comments make the calculation transparent.

---

## Impressive Features

- **Sophisticated Trader AI:** The patience-based negotiation system with multiplier-based trading logic creates emergent trading dynamics where the player must learn trader behavior types through repeated interactions.

- **Flexible Vision System:** Four distinct vision patterns create meaningful gameplay differences. A Farsight player has fundamentally different path-finding opportunities than a Cautious player, affecting core strategy.

- **Decision-Making Brains:** Three brain types with distinct personality archetypes (conservative, aggressive, opportunistic) show strong behavior differentiation through minimal code—each brain's strategy emerges from simple threshold comparisons and prioritization logic.

- **Modular Terrain System:** Five terrain types with independent cost profiles allow for rich map design where player routes become strategic trade-offs between movement efficiency, food consumption, and water consumption.

- **Polymorphic Design:** The extensive use of abstract classes and inheritance creates a codebase that's easily extensible. New trader types, vision patterns, or brain strategies can be added by creating new subclasses without modifying existing code—a hallmark of good OOP design.

- **Event System Flexibility:** The repeating vs. one-time event distinction with an `encountered` flag allows diverse event types (traders, resource bonuses) to coexist with appropriate lifecycle management.

---

## Program Output

*[Video/Text Output: Insert link to demonstration or console output here]*

Examples to include:
- Game turn sequence showing player movement
- Trade interaction between player and trader
- Resource gain/loss events
- End game stats or trajectory

---

## Conclusion

This project successfully demonstrates core Object-Oriented Design principles applied to a complex game system. Through careful use of abstraction, inheritance, polymorphism, and encapsulation, the codebase remains clean, maintainable, and extensible despite managing numerous interacting systems. The design allows for meaningful customization (brain strategies, vision types, trader personalities) while keeping the core game loop simple and elegant.
