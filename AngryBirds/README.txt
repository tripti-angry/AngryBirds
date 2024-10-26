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



