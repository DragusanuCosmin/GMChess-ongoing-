public class ChessGameLogic {
    private ChessPiece[][] board;

    public ChessGameLogic() {
        board = new ChessPiece[8][8];

        // TODO: initialize the board with pieces in their starting positions
    }


    public boolean isValidMove(ChessPiece piece, int newX, int newY) {
        // Check if the new position is within the bounds of the board
        if (newX < 0 || newX > 7 || newY < 0 || newY > 7) {
            return false;
        }

        // Check if the piece can move to the new position
        if (!piece.canMove(newX, newY, board)) {
            return false;
        }

        // Check if the piece will not collide with other pieces in the way
        int xDiff = Math.abs(newX - piece.getX());
        int yDiff = Math.abs(newY - piece.getY());
        int xDir = newX - piece.getX() > 0 ? 1 : -1;
        int yDir = newY - piece.getY() > 0 ? 1 : -1;
        int x = piece.getX() + xDir;
        int y = piece.getY() + yDir;

        while (x != newX || y != newY) {
            if (board[x][y] != null) {
                return false;
            }
            x += xDiff > 0 ? xDir : 0;
            y += yDiff > 0 ? yDir : 0;
        }

        return true;
    }

    public void movePiece(ChessPiece piece, int newX, int newY) {
        if (isValidMove(piece, newX, newY)) {
            board[piece.getX()][piece.getY()] = null;
            board[newX][newY] = piece;
            piece.setX(newX);
            piece.setY(newY);
        }
    }
    public boolean isCheck(ChessPieceColor color) {
        // Find the king of the specified color
        ChessPiece king = null;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board[i][j];
                if (piece.getType() == ChessPiece.Type.KING && piece.getColor() == color) {
                    king = piece;
                    break;
                }
            }
        }

        // Check if any of the opponent's pieces can attack the king
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board[i][j];
                if (piece != null && piece.getColor() != color) {
                    if (isValidMove(piece, king.getX(), king.getY())) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
    public boolean isCheckmate(ChessPieceColor color) {
        // First, check if the current player is in check
        if (!isCheck(color)) {
            return false;
        }

        // Find the king of the current player
        ChessPiece king = null;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board[i][j];
                if (piece.getType() == ChessPiece.Type.KING && piece.getColor() == color) {
                    king = piece;
                    break;
                }
            }
            if (king != null) {
                break;
            }
        }

        // Check if any of the player's pieces can capture the checking piece, block its path,
        // or move the king to safety
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board[i][j];
                if (piece != null && piece.getColor() == color) {
                    for (int x = 0; x < 8; x++) {
                        for (int y = 0; y < 8; y++) {
                            if (isValidMove(piece, x, y)) {
                                // Check if the move puts the player in check
                                ChessPiece capturedPiece = board[x][y];
                                movePiece(piece, x, y);
                                boolean safe = !isCheck(color);
                                undoMove(piece, i, j, capturedPiece);
                                if (safe) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }

        // If none of the player's pieces can capture the checking piece, block its path,
        // or move the king to safety, then it's a checkmate
        return true;
    }

    private void undoMove(ChessPiece piece, int x, int y, ChessPiece capturedPiece) {
        board[piece.getX()][piece.getY()] = piece;
        piece.setX(x);
        piece.setY(y);
        board[x][y] = capturedPiece;
    }
}