import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class BattleshipUI extends JFrame {
    private static final int GRID_SIZE = 10;
    private static final int CELL_SIZE = 50;
    private JButton[][] playerButtons;
    private JButton[][] opponentButtons;
    private BattleshipGame game;

    public BattleshipUI() {
        String name = JOptionPane.showInputDialog(this, "Enter your name:", "Player Name", JOptionPane.PLAIN_MESSAGE);
        game = new BattleshipGame(this);

        if (name != null && !name.trim().isEmpty()) {
            game.setPlayerName(name);
        }

        setTitle("Battleship");
        setSize(GRID_SIZE * CELL_SIZE * 2 + 100, GRID_SIZE * CELL_SIZE + 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        showStartScreen();
    }

    private void showStartScreen() {
        StartScreen startScreen = new StartScreen(this);
        setContentPane(startScreen);
        revalidate();
        repaint();
    }

    public void startGame() {
        setContentPane(new JPanel()); // Remove start screen
        showGameScreen();
    }

    private void showGameScreen() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // spaceing

        // Black border for the buttons
        Border blackLine = BorderFactory.createLineBorder(Color.BLACK);

        // Player grid
        JPanel playerPanel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE));
        playerButtons = new JButton[GRID_SIZE][GRID_SIZE];
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
                button.setBackground(Color.LIGHT_GRAY);
                button.setBorder(blackLine);
                button.setFocusPainted(false); // Disable focus Paint
                final int x = i;
                final int y = j;
                button.addActionListener(e -> game.placeShip(x, y));
                playerButtons[i][j] = button;
                playerPanel.add(button);
            }
        }

        // Opponent grid
        JPanel opponentPanel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE));
        opponentButtons = new JButton[GRID_SIZE][GRID_SIZE];
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
                button.setBackground(Color.LIGHT_GRAY);
                button.setBorder(blackLine);
                button.setFocusPainted(false); // Disable focus paint
                final int x = i;
                final int y = j;
                button.addActionListener(e -> game.playerAttack(x, y));
                opponentButtons[i][j] = button;
                opponentPanel.add(button);
            }
        }

        gbc.gridx = 0;
        mainPanel.add(playerPanel, gbc);

        gbc.gridx = 1;
        mainPanel.add(opponentPanel, gbc);

        add(mainPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        JButton horizontalButton = new JButton("Horizontal");
        horizontalButton.setBackground(Color.BLACK);
        horizontalButton.setForeground(Color.WHITE);

        JButton verticalButton = new JButton("Vertical");
        verticalButton.setBackground(Color.BLACK);
        verticalButton.setForeground(Color.WHITE);

        JButton startGameButton = new JButton("Start Game");
        startGameButton.setBackground(Color.RED);
        startGameButton.setForeground(Color.WHITE);

        JCheckBox hardModeCheckbox = new JCheckBox("Hard Mode");
        hardModeCheckbox.addActionListener(e -> game.enableHardMode());

        controlPanel.add(horizontalButton);
        controlPanel.add(verticalButton);
        controlPanel.add(startGameButton);
        controlPanel.add(hardModeCheckbox);

        horizontalButton.addActionListener(e -> game.setHorizontal(true));
        verticalButton.addActionListener(e -> game.setHorizontal(false));
        startGameButton.addActionListener(e -> game.startGame());

        add(controlPanel, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    public void markShipOnBoard(int x, int y, int length, boolean horizontal, boolean isPlayer) {
        JButton[][] buttons = isPlayer ? playerButtons : opponentButtons;
        for (int i = 0; i < length; i++) {
            if (horizontal) {
                buttons[x + i][y].setBackground(Color.BLACK);
            } else {
                buttons[x][y + i].setBackground(Color.BLACK);
            }
        }
    }

    public void markHitOnPlayerBoard(int x, int y, boolean hit) {
        playerButtons[x][y].setBackground(hit ? Color.RED : Color.DARK_GRAY);
    }

    public void markHitOnOpponentBoard(int x, int y, boolean hit) {
        opponentButtons[x][y].setBackground(hit ? Color.RED : Color.DARK_GRAY);
        opponentButtons[x][y].setEnabled(false);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BattleshipUI ui = new BattleshipUI();
            ui.setVisible(true);
        });
    }
}
