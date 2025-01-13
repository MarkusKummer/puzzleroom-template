package at.edu.c02.puzzleroom.commands;

import at.edu.c02.puzzleroom.GameBoard;
import at.edu.c02.puzzleroom.Player;
import at.edu.c02.puzzleroom.exceptions.PuzzleRoomException;
import at.edu.c02.puzzleroom.exceptions.PuzzleRoomInvalidArgumentsException;
import at.edu.c02.puzzleroom.exceptions.PuzzleRoomInvalidMoveException;

/**
 * This command allows the player to move up/down/left/right one step
 * Example usage: `move left`
 */
public class CommandFastMove implements Command {
    private final String[] direction;

    public CommandFastMove(String[] arguments) throws PuzzleRoomException {
        if(arguments.length == 0) {
            throw new PuzzleRoomInvalidArgumentsException();
        }
        direction = arguments;
    }

    public void execute(GameBoard gameBoard) throws PuzzleRoomException {
        // The player handles all movement logic, we just parse the input and
        // call the correct function
        Player player = gameBoard.getPlayer();
        if(player == null) {
            throw new PuzzleRoomInvalidMoveException();
        }
        for(int i = 0; i < direction.length; i++) {
            boolean success = switch(direction[i]) {
                case "l" -> player.moveLeft();
                case "r" -> player.moveRight();
                case "u" -> player.moveUp();
                case "d" -> player.moveDown();
                default -> throw new PuzzleRoomInvalidArgumentsException();
            };

            if(!success) {
                throw new PuzzleRoomInvalidMoveException();
            }
        }
        CommandShow showCommand = new CommandShow();
        showCommand.execute(gameBoard);
    }
}