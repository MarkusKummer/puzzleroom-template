package at.edu.c02.puzzleroom.fields;

import at.edu.c02.puzzleroom.Direction;
import at.edu.c02.puzzleroom.GameBoard;
import at.edu.c02.puzzleroom.Player;

/**
 * A path field. The player can freely walk onto this field, without any special effects.
 */
public class FieldIceField extends BaseField {
    public FieldIceField(GameBoard gameBoard, char name, int row, int col) {
        super(gameBoard, name, row, col);
    }

    public void initialize() {
    }

    public boolean enterField(Direction direction) {
        Player player = gameBoard.getPlayer();
        int playerPosCol = player.getCol();
        int playerPosRow = player.getRow();

        Position nextStep = checkNextField(direction, playerPosRow, playerPosCol);
        Field nextField = gameBoard.getField(nextStep.row, nextStep.col);
        if(nextField instanceof FieldIceField) {
            player.setPosition(nextStep.row, nextStep.col);
        } else {
            nextField.enterField(direction);
        }
        return true;
    }

    public boolean leaveField(Direction direction) {
        gameBoard.getPlayer().walkStep();
        setPlayerPositionToField();
        return true;
    }

    private Position checkNextField(Direction direction,  int playerPosRow, int playerPosCol) {
        Position pos = new Position(playerPosRow, playerPosCol);

        switch (direction) {
            case Up:
                pos.row--;
                break;
            case Down:
                pos.row++;
                break;
            case Right:
                pos.col++;
                break;
            case Left:
                pos.col--;
                break;
        };
        Field nextField = gameBoard.getField(pos.row, pos.col);
        if(nextField instanceof FieldIceField) {
            pos = checkNextField(direction, pos.row, pos.col);
        } else if (nextField instanceof FieldWall){
            return new Position(playerPosRow, playerPosCol);
        }

        return pos;
    }

}

class Position{
    public int row;
    public int col;
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }
}
