import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ChessBoard extends JPanel {
    private JButton[][] squares;
    private ChessPiece[][] pieces;

    public ChessBoard() {
        setLayout(new GridLayout(8, 8));

        squares = new JButton[8][8];
        pieces = new ChessPiece[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JButton square = new JButton();
                square.setBorder(null);
                squares[i][j] = square;
                add(square);

                if ((i + j) % 2 == 0) {
                    square.setBackground(Color.WHITE);
                } else {
                    square.setBackground(new Color(150, 32, 60));
                }

                square.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ChessSquare[][] board =new ChessSquare[8][8];
                        for (int row = 0; row < 8; row++) {
                            for (int col = 0; col < 8; col++) {
                                board[row][col] = new ChessSquare(row, col);
                            }
                        }
                        ChessSquare sourceSquare = (ChessSquare) e.getSource();
                        ChessPiece piece = sourceSquare.getPiece();
                        if (piece == null) {
                            return;
                        }
                        ArrayList<ChessSquare> possibleMoves = piece.getPossibleMoves(board, sourceSquare.getRow(), sourceSquare.getCol(),piece);
                        for (ChessSquare move : possibleMoves) {
                            move.highlight(Color.GREEN);
                        }
                    }
                });
            }
        }
        initializeBoard();
    }

    public void initializeBoard() {
        // Initialize the pieces
        ImageIcon blackRookIcon = new ImageIcon("black_rook.png");
        ImageIcon blackKnightIcon = new ImageIcon("black_knight.png");
        ImageIcon blackBishopIcon = new ImageIcon("black_bishop.png");
        ImageIcon blackKingIcon = new ImageIcon("black_king.png");
        ImageIcon blackQueenIcon = new ImageIcon("black_queen.png");
        ImageIcon blackPawnIcon = new ImageIcon("black_pawn.png");
        ImageIcon whiteRookIcon = new ImageIcon("white_rook.png");
        ImageIcon whitePawnIcon = new ImageIcon("white_pawn.png");
        ImageIcon whiteKnightIcon = new ImageIcon("white_knight.png");
        ImageIcon whiteBishopIcon = new ImageIcon("white_bishop.png");
        ImageIcon whiteKingIcon = new ImageIcon("white_king.png");
        ImageIcon whiteQueenIcon = new ImageIcon("white_queen.png");
        pieces[0][0] = new ChessPiece(ChessPieceColor.BLACK, ChessPiece.Type.ROOK,blackRookIcon);
        pieces[0][1] = new ChessPiece(ChessPieceColor.BLACK, ChessPiece.Type.KNIGHT, blackKnightIcon);
        pieces[0][2] = new ChessPiece(ChessPieceColor.BLACK, ChessPiece.Type.BISHOP, blackBishopIcon);
        pieces[0][3] = new ChessPiece(ChessPieceColor.BLACK, ChessPiece.Type.QUEEN,blackQueenIcon);
        pieces[0][4] = new ChessPiece(ChessPieceColor.BLACK, ChessPiece.Type.KING, blackKingIcon);
        pieces[0][5] = new ChessPiece(ChessPieceColor.BLACK, ChessPiece.Type.BISHOP,blackBishopIcon);
        pieces[0][6] = new ChessPiece(ChessPieceColor.BLACK, ChessPiece.Type.KNIGHT, blackKnightIcon);
        pieces[0][7] = new ChessPiece(ChessPieceColor.BLACK, ChessPiece.Type.ROOK, blackRookIcon);
        for (int i = 0; i < 8; i++) {
            pieces[1][i] = new ChessPiece(ChessPieceColor.BLACK, ChessPiece.Type.PAWN, blackPawnIcon);
        }

        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                pieces[i][j] = null;
            }
        }

        for (int i = 0; i < 8; i++) {
            pieces[6][i] = new ChessPiece(ChessPieceColor.BLACK, ChessPiece.Type.PAWN, whitePawnIcon);
        }
        pieces[7][0] = new ChessPiece(ChessPieceColor.BLACK, ChessPiece.Type.ROOK, whiteRookIcon);
        pieces[7][1] = new ChessPiece(ChessPieceColor.WHITE, ChessPiece.Type.KNIGHT, whiteKnightIcon);
        pieces[7][2] = new ChessPiece(ChessPieceColor.WHITE, ChessPiece.Type.BISHOP, whiteBishopIcon);
        pieces[7][3] = new ChessPiece(ChessPieceColor.WHITE, ChessPiece.Type.QUEEN, whiteQueenIcon);
        pieces[7][4] = new ChessPiece(ChessPieceColor.WHITE, ChessPiece.Type.KING, whiteKingIcon);
        pieces[7][5] = new ChessPiece(ChessPieceColor.WHITE, ChessPiece.Type.BISHOP, whiteBishopIcon);
        pieces[7][6] = new ChessPiece(ChessPieceColor.WHITE, ChessPiece.Type.KNIGHT, whiteKnightIcon);
        pieces[7][7] = new ChessPiece(ChessPieceColor.WHITE, ChessPiece.Type.ROOK, whiteRookIcon);

        // Set the pieces on the board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (pieces[i][j] != null) {
                    squares[i][j].setIcon(pieces[i][j].getIcon());
                }
            }
        }
    }
}