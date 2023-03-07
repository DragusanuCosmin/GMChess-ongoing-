import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class ChessBoard extends JButton {
    private JButton[][] squares;
    private ChessPiece[][] pieces;
    private JPanel panel1;
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    int screenWidth = gd.getDisplayMode().getWidth();
    int screenHeight = gd.getDisplayMode().getHeight();

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

                square.addMouseListener(new MouseAdapter() {
                    public void MouseClicked(MouseEvent e) {
                        ChessSquare[][] board =new ChessSquare[8][8];
                        for (int row = 0; row < 8; row++) {
                            for (int col = 0; col < 8; col++) {
                                board[row][col] = new ChessSquare(row, col);
                            }
                        }
                        int currentrow=e.getX()/square.getWidth();
                        int currentcolumn=e.getY()/square.getHeight();
                        ChessSquare sourceSquare=board[currentrow][currentcolumn];
                        ChessPiece piece = sourceSquare.piece;
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
        BufferedImage blackRookIcon=null ,blackKnightIcon=null,blackBishopIcon=null,blackKingIcon=null,blackQueenIcon=null,blackPawnIcon=null,whiteRookIcon=null,whitePawnIcon=null,whiteKnightIcon=null,whiteBishopIcon=null,whiteKingIcon=null,whiteQueenIcon = null;
        try {
             blackRookIcon = ImageIO.read(getClass().getResourceAsStream("black_rook.png"));
             blackKnightIcon = ImageIO.read(getClass().getResourceAsStream("black_knight.png"));
             blackBishopIcon = ImageIO.read(getClass().getResourceAsStream("black_bishop.png"));
             blackKingIcon =  ImageIO.read(getClass().getResourceAsStream("black_king.png"));
             blackQueenIcon =  ImageIO.read(getClass().getResourceAsStream("black_queen.png"));
             blackPawnIcon =  ImageIO.read(getClass().getResourceAsStream("black_pawn.png"));
             whiteRookIcon =  ImageIO.read(getClass().getResourceAsStream("white_rook.png"));
             whitePawnIcon =  ImageIO.read(getClass().getResourceAsStream("white_pawn.png"));
             whiteKnightIcon =  ImageIO.read(getClass().getResourceAsStream("white_knight.png"));
             whiteBishopIcon =  ImageIO.read(getClass().getResourceAsStream("white_bishop.png"));
             whiteKingIcon =  ImageIO.read(getClass().getResourceAsStream("white_king.png"));
             whiteQueenIcon =  ImageIO.read(getClass().getResourceAsStream("white_queen.png"));
        } catch (IOException ex) {
            System.out.println("Error loading icon image: " + ex.getMessage());
        }

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
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (pieces[i][j] != null) {
                    squares[i][j].setIcon(new ImageIcon(pieces[i][j].getImage()));
                }
            }
        }
    }
}