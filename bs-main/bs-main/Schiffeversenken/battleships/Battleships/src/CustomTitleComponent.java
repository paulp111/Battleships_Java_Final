import javax.swing.*;
import java.awt.*;

public class CustomTitleComponent extends JComponent {
    private String text;
    private Font font;
    private Color shadowColor;
    private Color textColor;

    public CustomTitleComponent(String text, Font font, Color shadowColor, Color textColor) {
        this.text = text;
        this.font = font;
        this.shadowColor = shadowColor;
        this.textColor = textColor;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setFont(font);

        g2d.setColor(shadowColor);
        for (int i = 0; i < 9; i++) {  // Multiple layers
            g2d.drawString(text, 5 + i, 55 + i);
        }

        // Draw main text
        g2d.setColor(textColor);
        g2d.drawString(text, 5, 55);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(400, 100);
    }
}
