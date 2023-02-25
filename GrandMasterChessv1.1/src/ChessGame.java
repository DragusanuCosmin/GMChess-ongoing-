import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ChessGame extends JFrame {
    private ChessBoard chessBoard;
    private JPanel titleBar;

    public ChessGame() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int screenWidth = gd.getDisplayMode().getWidth();
        int screenHeight = gd.getDisplayMode().getHeight();
        setSize(screenHeight*5/6, screenHeight*5/6);
        setLocation(screenWidth/2-getWidth()/2,screenHeight/2-getHeight()/2);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);

        // Load the icon image
        BufferedImage iconImage = null;
        try {
            iconImage = ImageIO.read(getClass().getResourceAsStream("game_icon.png"));
        } catch (IOException ex) {
            System.out.println("Error loading icon image: " + ex.getMessage());
        }

        // Set the icon image for the frame
        if (iconImage != null) {
            setIconImage(iconImage);
        }

        // Create the custom title bar
        titleBar = new JPanel();
        titleBar.setLayout(new BorderLayout());
        titleBar.setBackground(Color.DARK_GRAY);

        // Create the exit button
        JButton exitButton = new JButton("X  ");
        exitButton.setFont(new Font("Arial", Font.BOLD, 16));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.addActionListener(e -> System.exit(0));
        exitButton.setBorder(null);
        titleBar.add(exitButton, BorderLayout.LINE_END);

        // Create the minimize button
        JButton minimizeButton = new JButton("  -");
        minimizeButton.setFont(new Font("Arial", Font.BOLD, 16));
        minimizeButton.setForeground(Color.WHITE);
        minimizeButton.setFocusPainted(false);
        minimizeButton.setContentAreaFilled(false);
        minimizeButton.addActionListener(e -> setState(Frame.ICONIFIED));
        minimizeButton.setBorder(null);
        titleBar.add(minimizeButton, BorderLayout.LINE_START);

        // Add the title bar and chess board to the frame
        add(titleBar, BorderLayout.PAGE_START);
        chessBoard = new ChessBoard();
        add(chessBoard, BorderLayout.CENTER);
    }

    public void startGame() {
        setVisible(true);
    }
}