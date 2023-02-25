import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ChessPiece {
    public enum Type {
        KING, QUEEN, ROOK, BISHOP, KNIGHT, PAWN
    }

    public enum Color {
        BLACK, WHITE
    }

    private final ChessPieceColor color;
    private final Type type;
    private final ImageIcon image;
    private int x;
    private int y;

    public ChessPiece(ChessPieceColor color, Type type, ImageIcon image) {
        this.color = color;
        this.type = type;
        this.image = image;
    }
    public ArrayList<ChessSquare> getPossibleMoves(ChessSquare[][] board, int row, int col, ChessPiece piece) {
        ArrayList<ChessSquare> possibleMoves = new ArrayList<>();
        switch(piece.getType()) { // Use getType() method of ChessPiece object
            case PAWN:
                int[][] offsets = {
                        {-1, -2}, {-2, -1}, {-2, 1}, {-1, 2},
                        {1, 2}, {2, 1}, {2, -1}, {1, -2}
                };

                for (int[] offset : offsets) {
                    int newRow = row + offset[0];
                    int newCol = col + offset[1];

                    if (newRow >= 0 && newRow < board.length && newCol >= 0 && newCol < board[0].length) {
                        int occupiedStatus = board[newRow][newCol].isOccupied(piece.getColor());
                        if (occupiedStatus == 0 || occupiedStatus == 2) {
                            possibleMoves.add(board[newRow][newCol]);
                        }
                    }
                }

                break;
            // other cases for different types of chess pieces
        }
        return possibleMoves;
    }


    public ChessPieceColor getColor() {
        return color;
    }

    public Type getType() {
        return type;
    }

    public ImageIcon getIcon() {
        return image;
    }

    public boolean canMove(int finalX, int finalY, ChessPiece[][] board) {
        // Check if the new position is within the bounds of the board
        if (finalX < 0 || finalX > 7 || finalY < 0 || finalY > 7) {
            return false;
        }

        // Check if the piece can move to the new position
        if (!isValidMove(this.x, this.y, finalX, finalY)) {
            return false;
        }

        // Check if the piece will not collide with other pieces in the way
        int xDiff = Math.abs(finalX - this.x);
        int yDiff = Math.abs(finalY - this.y);
        int xDir = finalX - this.x > 0 ? 1 : -1;
        int yDir = finalY - this.y > 0 ? 1 : -1;
        int x = this.x + xDir;
        int y = this.y + yDir;

        while (x != finalX || y != finalY) {
            if (board[x][y] != null) {
                return false;
            }
            x += xDiff > 0 ? xDir : 0;
            y += yDiff > 0 ? yDir : 0;
        }

        return true;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int[] getCoordinates() {
        return new int[]{this.x, this.y};
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public boolean isValidMove(int initialX, int initialY, int finalX, int finalY) {
        int dx = Math.abs(finalX - initialX);
        int dy = Math.abs(finalY - initialY);
        int maxDistance = Math.max(dx, dy);

        // Check if the move is diagonal
        if (dx == dy) {
            return true;
        }

        // Check if the move is horizontal or vertical
        if (dx == 0 || dy == 0) {
            return true;
        }

        // Check if the move is valid for a knight
        if (dx == 2 && dy == 1 || dx == 1 && dy == 2) {
            return true;
        }

        // All other moves are invalid
        return false;
    }
}