<img src="images/gameName.jpg" width="100%" alt="Title">
<h3 align = "center">A treasure hunting game.</h3>
<p align = "center">
<b>CS 2105 - Group 9 </b> <br/>
Manalo, John Danver Z. <br/>
Mendoza, Goldwyn Daine Kierzene D. <br/>
Villegas, Lemuel L.
</p>

## 📝 Project Description
Treasure Rush is a simple 2D Java game where you explore, avoid monsters, collect axes, and clear obstacles to find the treasure hidden somewhere on the map.

## 🎮 How to Play

### 🕹️ Controls
<img src="images/Controls.png" width="100%" alt="Controls Screenshot">
<br/>

### 🎯 Objectives
#### 1. Explore the map filled with:
&nbsp;&nbsp;&nbsp;🌳    Trees<br/>
&nbsp;&nbsp;&nbsp;👾    Monsters<br/>
&nbsp;&nbsp;&nbsp;🧪    Speed Potions<br/>
&nbsp;&nbsp;&nbsp;🗺️    Hidden Treasures<br/>

#### 2. Collect Axes
- Axes are required to cut down obstacles blocking your path.<br/>
- Each tree cut consumes 1 axe from the inventory.

#### 3. Navigate the map
- Travel through the map to find the hidden treasure in the woods.<br/>
- The level ends once you found the hidden treasure. The game will take you back to the Title Screen.

## ⚙️ Game Features
- 2D top-down movement<br/>
- WASD controls<br/>
- Monster encounters (non-combat)<br/>
- Collectible treasure<br/>
- Resource management (axes)<br/>
- Obstacle-cutting mechanic<br/>
- Hidden treasure win condition<br/>
- Title screen and game loop<br/>

## 🛠️ How to Run the Game

1. Download the Treasure Rush v1.0 in Release.
2. Enjoy playing!

## 🗂️ Project Structure
```
📂 src/
├── 📂 main/
|   ├── ☕ AssetSetter.java          
|   ├── ☕ CollisionChecker.java
|   ├── ☕ GamePanel.java
|   ├── ☕ KeyHandler.java          
|   ├── ☕ Main.java
|   ├── ☕ Sound.java
|   ├── ☕ UI.java          
|   └── ☕ UtilityTool.java
|
├── 📂 entity/
|   ├── ☕ Entity.java          
|   └── ☕ Player.java
|
├── 📂 monster/
|   ├── ☕ MON_BlueKingSlime.java          
|   ├── ☕ MON_BlueSlime.java
|   ├── ☕ MON_GreenKingSlime.java
|   └── ☕ MON_GreenSlime.java          
|
├── 📂 object/
|   ├── ☕ OBJ_Axe.java        
|   ├── ☕ OBJ_Chest.java
|   ├── ☕ OBJ_DeadTree.java
|   └── ☕ OBJ_SpeedPotion.java         
|
└── 📂 tile/
    ├── ☕ Tile.java          
    └── ☕ TileManager.java
```
<table border="1" cellspacing="0" cellpadding="6">
  <tr>
    <th>File / Class</th>
    <th>Description</th>
  </tr>

  <tr>
    <td>Main.java</td>
    <td>Game entry point; creates the window and starts the GamePanel.</td>
  </tr>

  <tr>
    <td>GamePanel.java</td>
    <td>Runs the game loop and manages all game states.</td>
  </tr>

  <tr>
    <td>KeyHandler.java</td>
    <td>Reads player keyboard input (movement + control keys).</td>
  </tr>

  <tr>
    <td>CollisionChecker.java</td>
    <td>Checks and prevents invalid collisions with tiles/objects.</td>
  </tr>

  <tr>
    <td>Sound.java</td>
    <td>Loads and handles playback of sound effects.</td>
  </tr>

  <tr>
    <td>UI.java</td>
    <td>Draws UI elements like the title screen, HUD, and messages.</td>
  </tr>

  <tr>
    <td>AssetSetter.java</td>
    <td>Places items, objects, and monsters in the world.</td>
  </tr>

  <tr>
    <td>UtilityTool.java</td>
    <td>Helps load and scale images.</td>
  </tr>

  <tr>
    <td>Entity.java</td>
    <td>Base class defining shared attributes and behavior for all game actors.</td>
  </tr>

  <tr>
    <td>Player.java</td>
    <td>Handles player movement, interactions, and status effects.</td>
  </tr>

  <tr>
    <td>MON_*Slime.java</td>
    <td>Slime enemy variants with unique stats and simple random AI.</td>
  </tr>

  <tr>
    <td>OBJ_Axe.java</td>
    <td>Collectible axe allowing the player to chop trees.</td>
  </tr>

  <tr>
    <td>OBJ_Chest.java</td>
    <td>Victory object; interacting ends the game.</td>
  </tr>

  <tr>
    <td>OBJ_DeadTree.java</td>
    <td>Solid obstacle removable with an axe.</td>
  </tr>

  <tr>
    <td>OBJ_SpeedPotion.java</td>
    <td>Grants a temporary speed boost.</td>
  </tr>

  <tr>
    <td>Tile.java</td>
    <td>Holds a tile’s image and collision flag.</td>
  </tr>

  <tr>
    <td>TileManager.java</td>
    <td>Loads map data and renders the tile-based world.</td>
  </tr>
</table>


## 💡 OOP Principles

### 💊 Encapsulation
Encapsulation was used in this project in a way that the game data such as player stats, inventory (axes), position, and movement speed are stored in private fields. For instance, `Entity` contains movement variables, speed, direction, sprites, etc.<br/><br/>
With the help of encapsulation, the class' internal data is protected by keeping variables organized inside the object, instead of exposing them globally.

### 🧬 Inheritance

This project used inheritance heavily. For instance, `Player` and all monsters <b>extends</b> `Entity` which allows shared features like speed, worldX, `update()`, and sprite images to come from the parent class, avoiding rewriting the same code repeatedly and providing a cleaner code.

### 👁️‍🗨️ Abstraction

Abstraction was used in this project to hide complex logic behind simple method calls. For instance, `GamePanel` only calls `player.update()`, `monster.update()`, and `tileManager.draw()`. It does not need to know how collision detection, animation, or movement calculations work.<br/><br/>
This makes the project easier to expand and maintain.

### 🎭 Polymorphism

Polymorphism was used in this project to let different classes share the same method name but have different behaviors. For instance, `update()` and `draw()` are defined in `Entity`. `Player`, monsters, and object classes override these methods. The game loop calls `.update()` on every entity without needing to know which specific class it belongs to.<br><br/>
This allows the player, enemies, and items to behave differently while keeping the overall game loop simple and flexible.

## 📸 Screenshots

### Title Screen

<img src="/images/titleScreen.jpg" alt="Title Screen of Treasure Rush" width="100%">

### Searching for Treasure (Gameplay)

<img src="/images/treasureHunting.jpg" alt="Gameplay of Treasure Rush" width="100%">

### Victory Screen

<img src="/images/victory.jpg" alt="Victory Screen" width="100%">

### Game Over Screen

<img src="/images/defeat.jpg" alt="Game Over Screen" width="100%">

## 👥 Contributors

<table style="border-collapse: collapse; width: 100%; border: 1px solid #999;">
  <tr>
    <th style="border: 1px solid #999; padding: 12px; text-align: left;">Name</th>
    <th style="border: 1px solid #999; padding: 12px; text-align: center;">&nbsp;</th>
  </tr>

  <tr>
    <td style="border: 1px solid #999; padding: 15px; font-size: 16px;">
      <strong>Manalo, John Danver Z.</strong>
    </td>
    <td style="border: 1px solid #999; padding: 15px; text-align: center;">
      <img src="./images/manalo.jpg" width="120" height="120" style="border-radius: 50%;" alt="Contributor 1">
    </td>
  </tr>

  <tr>
    <td style="border: 1px solid #999; padding: 15px; font-size: 16px;">
      <strong>Mendoza, Goldwyn Daine Kierzene D.</strong>
    </td>
    <td style="border: 1px solid #999; padding: 15px; text-align: center;">
      <img src="./images/mendoza.jpg" width="120" height="120" style="border-radius: 50%;" alt="Contributor 2">
    </td>
  </tr>

  <tr>
    <td style="border: 1px solid #999; padding: 15px; font-size: 16px;">
      <strong>Villegas, Lemuel L.</strong>
    </td>
    <td style="border: 1px solid #999; padding: 15px; text-align: center;">
      <img src="./images/villegas.jpg" width="120" height="120" style="border-radius: 50%;" alt="Contributor 3">
    </td>
  </tr>
</table>

## 💜 Credits 

* **Asset Pack: Ninja Adventure**
    * Creator: Pixel-boy
    * Source: https://pixel-boy.itch.io/

* **Asset Pack: 16x16 Mini World Sprites and Assorted RPG Icons**
    * Creator: Shade
    * Source: https://merchant-shade.itch.io

* **Special Thanks to:**
    * Ryisnow (YouTube tutorials) - https://www.youtube.com/@RyiSnow

## ✍️ Acknowledgment
Our group would like to extend our outstanding gratitude to our professor, Ma'am Fatima Marie P. Agdon for helping us accomplish this project. Her constant support helped a lot on the completion of this project. 















