import javax.swing.*;
import java.awt.*;

public class StartScreen extends JPanel {

    public StartScreen(BattleshipUI mainUI) {
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        CustomTitleComponent titleComponent = new CustomTitleComponent(
                "BATTLESHIPS",
                new Font("SansSerif", Font.BOLD, 48),
                Color.MAGENTA, // Shadow color for the neon effect
                Color.CYAN // Main text color
        );

        // Center title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(Color.BLACK);
        titleComponent.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.add(Box.createVerticalGlue());
        titlePanel.add(titleComponent);
        titlePanel.add(Box.createVerticalGlue());

        // Subtitle centered
        JLabel subtitleLabel = new JLabel("Press Start to Play", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 24));
        subtitleLabel.setForeground(Color.WHITE);

        // Panel for the start button centered
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.BLACK);

        JButton startButton = new JButton("Start");
        startButton.setFont(new Font("SansSerif", Font.BOLD, 24));
        startButton.setForeground(Color.BLACK);
        startButton.setBackground(Color.CYAN);
        startButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        startButton.setFocusPainted(false);
        startButton.addActionListener(_ -> mainUI.startGame());
        buttonPanel.add(startButton);

        // panels
        add(titlePanel, BorderLayout.NORTH);
        add(subtitleLabel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
