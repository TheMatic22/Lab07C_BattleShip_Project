import javax.swing.*;
import java.awt.*;

public class BattleshipGUI extends JFrame {

    private final BattleshipGame game = new BattleshipGame();
    private final JButton[][] buttons = new JButton[Board.SIZE][Board.SIZE];
    private final JLabel missLabel = new JLabel();
    private final JLabel strikeLabel = new JLabel();
    private final JLabel totalMissLabel = new JLabel();
    private final JLabel totalHitLabel = new JLabel();

    public BattleshipGUI() {
        setTitle("Trey Sims Battleship Game");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel("BATTLESHIP", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        add(title, BorderLayout.NORTH);

        buildBoard();
        add(buildStatusPanel(), BorderLayout.SOUTH);
        updateStatus();
        game.debugPrintShips();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void buildBoard() {
        JPanel grid = new JPanel(new GridLayout(Board.SIZE, Board.SIZE));

        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                JButton button = new JButton("~");
                button.setPreferredSize(new Dimension(50, 50));
                button.setFont(new Font("SansSerif", Font.BOLD, 20));
                button.setBackground(new Color(173, 216, 230));
                button.setOpaque(true);
                button.setBorderPainted(false);

                buttons[row][col] = button;
                final int r = row;
                final int c = col;
                button.addActionListener(e -> handleClick(r, c));

                drawCell(row,col, CellState.BLANK);
                grid.add(button);
            }
        }
        add(grid, BorderLayout.CENTER);
    }

    private void drawCell(int row, int col, CellState state) {
        JButton button = buttons[row][col];

        switch (state) {
            case BLANK:
                button.setText("~");
                button.setForeground(Color.WHITE);
                button.setBackground(new Color(173, 216, 230));
                break;
            case MISS:
                button.setText("<html><font color='yellow'>M</font></html>");
                button.setForeground(Color.YELLOW);
                button.setBackground(new Color(40, 40, 60));
                break;
            case HIT:
                button.setText("<html><font color='red'>X</font></html>");
                button.setForeground(Color.RED);
                button.setBackground(new Color(40, 40, 60));
                break;
        }
    }
    private void handleClick(int row, int col) {
        CellState result = game.fire(row, col);
        drawCell(row, col, result);
        buttons[row][col].setEnabled(false);
        updateStatus();

        if (result == CellState.HIT && game.isShipSunkAt(row, col)) {
            JOptionPane.showMessageDialog(
                    this,
                    "You sunk a ship!",
                    "Battleship",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        if (game.isWon()) {
            promptReplay("YOU WIN! You sank every ship.");
        } else if (game.isLost()) {
            promptReplay("GAME OVER. You got 3 strikes.");
        }
    }
    private JPanel buildStatusPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.add(missLabel);
        panel.add(strikeLabel);
        panel.add(totalMissLabel);
        panel.add(totalHitLabel);

        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.addActionListener(e -> playAgain());
        panel.add(playAgainButton);

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> quit());
        panel.add(quitButton);

        return panel;
    }
    private void updateStatus() {
        missLabel.setText("MISS: " + game.getMissCount());
        strikeLabel.setText("STRIKE: " + game.getStrikeCount());
        totalMissLabel.setText("TOTAL MISS: " + game.getTotalMiss());
        totalHitLabel.setText("TOTAL HIT: " + game.getTotalHit());
    }
    private void startNewGame() {
        game.newGame();
        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                drawCell(row, col, CellState.BLANK);
                buttons[row][col].setEnabled(true);
            }
        }
        updateStatus();
        game.debugPrintShips();
    }
    private void playAgain() {
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to start a new game?",
                "Play Again",
                JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            startNewGame();
        }
    }
    private void promptReplay(String message) {
        int choice = JOptionPane.showConfirmDialog(
                this,
                message + "\n\nWould you like to play again?",
                "Battleship",
                JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            startNewGame();
        } else {
            System.exit(0);
        }
    }
    public void quit() {
        promptReplay("You chose to quit this game.");
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(BattleshipGUI::new);
    }
}
