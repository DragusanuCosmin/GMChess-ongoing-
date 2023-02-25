import javax.swing.*;
import java.awt.*;

public class ChessSquare extends JButton {
    private final int row;
    private final int col;
    private ChessPiece piece;

    public ChessSquare(int row, int col) {
        this.row = row;
        this.col = col;
        this.piece = null;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public ChessPiece getPiece() {
        return piece;
    }
    public void highlight(Color color) {
        setBackground(color);
    }
    public void setPiece(ChessPiece piece) {
        this.piece = piece;
        if (piece != null) {
            setIcon(piece.getIcon());
        } else {
            setIcon(null);
        }
    }
    public ChessPieceColor getColor() {
        if ((row + col) % 2 == 0) {
            return ChessPieceColor.WHITE;
        } else {
            return ChessPieceColor.BLACK;
        }
    }
    public int isOccupied(ChessPieceColor color) {
        if (piece == null) {
            return 0; // Not occupied
        } else if (piece.getColor() == color) {
            return 1; // Occupied by own piece
        } else {
            return 2; // Occupied by opponent's piece
        }
    }
}