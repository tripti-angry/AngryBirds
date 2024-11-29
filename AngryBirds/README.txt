IN our Angry Birds game, we have implemented HomeScreen, MenuScreen which consists of setting and play icon.Setting icon consist
of notifaction on/off, sound on/off, save/load game options which are from ui skin. Then we have implement 3 levels on level screen whivh
consists of three types of birds(red,black,blue) which extends bird class, three types of pigs(small,medium,large) which extends pig class and
three types of structures(wooden,steel,glass) which extends structure class. For the launching of bird, we have made a projectile class andc catapult class to handle the trajectory.
We have win/lose screen which appears when the user win or looses the game.

Screens like HomeScreen, PlayGameScreen, WinScreen, LoseScreen, and SettingsScreen inherit from the Screen class, which is provided by libGDX which covers inheritance.
game.setScreen() method can switch between different screen types like HomeScreen, PlayGameScreen, and SettingsScreen as all these classes implement the Screen interface and this can conclude as polymorphism.
Screen interface is used to define methods like show(), render(), resize(), etc which can be used by other classes as well.
Inheritance is also defined when three types of birds,pigs,structure,levels extends bird,pig,structure,level class respectively.

For serialization, we have used savedata and savemanager classes and for Junittesting we have made tests for bird and structure classes.



# AngryBirds

## Overview
This is a 2D game developed using LibGDX, featuring interactive screens for a menu, pause, and gameplay.

## Screens
The game has multiple screens, each offering unique functionalities:

1. <<Menu Screen>>: Includes options to start the game, adjust settings, or exit.
2. <<Pause Screen>>: Allows the player to resume or go back to the main menu.
3. <<Play Game Screen>>: Main gameplay area where the player controls the bird.
4. <<Win Screen>>: Displays when the player successfully completes a level, showing a congratulatory message and options to proceed to the next level or return to the main menu.
5 <<Lose Screen>>: Displays when the player fails to complete a level, providing options to retry the level or return to the main menu.

## Gameplay
- <<Objective>>: Use the slingshot to launch the bird and hit targets.
- <<Controls>>: Tap on the screen to adjust the bird's trajectory and interact with buttons.

## Project Structure

### Main Components

- `MainGame` - The main class that handles game initialization and screen switching.
- `MenuScreen` - Displays the game menu with options for playing, settings, and exit.
- `PauseScreen` - Screen to pause the game and choose between resuming or exiting.
- `PlayGameScreen` - Core gameplay area, handling input and rendering game elements.
- `WinScreen`: Shows the win message and options to continue or return to the menu.
- `LoseScreen`: Displays the lose message and options to retry or go back to the menu.

### Assets
The game assets are located in the `assets` folder and include:
- Background textures
- Button textures (play, pause, exit, etc.)
- Bird, slingshot, and structural textures
- Win and lose screen images

### Controls
- Play Button: Starts the game from the menu screen.
- Exit Button: Exits the game from the menu or pause screen.
- Pause Button: Pauses the game during gameplay.
- Replay Button: Restarts the level from the gameplay screen.
- Continue Button: Proceeds to the next level after a win.
- Retry Button: Restarts the current level after a loss.

### Characters
- Bird: The main character controlled by the player, launched from a slingshot to hit targets.
- Targets: Various structures and enemies that the player must hit to progress through levels.

## Requirements
- Java Development Kit (JDK)
- LibGDX library
- An IDE like IntelliJ IDEA or Eclipse

## Setup and Run
1. Clone the repository:
    ```bash
    git clone https://github.com/tripti-angry/AngryBirds.git
    cd AngryBirds
    ```
2. Open the project in your preferred IDE.
3. Import LibGDX as a project dependency.
4. Run the `MainGame` class to start the game.

## Controls
- **Play Button**: Starts the game from the menu screen.
- **Exit Button**: Exits the game from the menu or pause screen.
- **Pause Button**: Pauses the game during gameplay.
- **Replay Button**: Restarts the level from the gameplay screen.

## Acknowledgments
- [LibGDX](https://libgdx.badlogicgames.com/) for providing the framework.
- Asset creators for textures and images used in the game.



