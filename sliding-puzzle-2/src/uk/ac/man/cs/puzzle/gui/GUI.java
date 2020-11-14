package uk.ac.man.cs.puzzle.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import uk.ac.man.cs.puzzle.logic.Model;

public class GUI extends JPanel {

	private static final long serialVersionUID = 1L;
	private final Model puzzleModel;
	private GraphicsPanel puzzleGraphics;
	private Timer gameTimer;
	private int ROWS;
	private int COLS;
	JLabel currentMovesLabel;
	JLabel MovesLabel;


	public GUI(int rows, int cols) {
		// Create a button. Add a listener to it.
		JButton newGameButton = new JButton("New Game");
		newGameButton.addActionListener(new NewGameAction());

		// Create game timer components
		JLabel timerLabel = new JLabel("Time: ", JLabel.LEADING);
		final JLabel currentTimeLabel = new JLabel(" __ ", JLabel.CENTER);
		currentTimeLabel.setText(String.valueOf("0"));
		JLabel unitsLabel = new JLabel(" seconds", JLabel.LEADING);

		// Create control panel
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());
		controlPanel.add(newGameButton);

		// Create graphics panel
		ROWS = rows;
		COLS = cols;
		puzzleModel = new Model(ROWS, COLS);
		puzzleGraphics = new GraphicsPanel(puzzleModel, this,rows, cols);
		

		currentMovesLabel = new JLabel();
		currentMovesLabel.setText(""+ puzzleModel.getMoveCount());
		currentMovesLabel.setBounds(55, 232, 100, 100);
		add(currentMovesLabel);
		
	
		MovesLabel = new JLabel();
		MovesLabel.setText("Moves: ");
		
		JPanel MovesCounterPanel = new JPanel();
		MovesCounterPanel.setLayout(new FlowLayout());
		MovesCounterPanel.add(MovesLabel);
		MovesCounterPanel.add(currentMovesLabel);
		
		



		// Create game timer panel
		JPanel gameTimerPanel = new JPanel();
		gameTimerPanel.setLayout(new FlowLayout());
		gameTimerPanel.add(timerLabel);
		gameTimerPanel.add(currentTimeLabel);
		gameTimerPanel.add(unitsLabel);
		
		JPanel MenuPanel = new JPanel();
		MenuPanel.setLayout(new BoxLayout(MenuPanel,BoxLayout.Y_AXIS));
		MenuPanel.add(gameTimerPanel);
		MenuPanel.add(MovesCounterPanel);

		// Set the layout and add the components
		this.setLayout(new BorderLayout());
		this.add(controlPanel, BorderLayout.NORTH);
		this.add(puzzleGraphics, BorderLayout.CENTER);
		this.add(MenuPanel, BorderLayout.SOUTH);
		

		// Set up the Swing timer
		gameTimer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (puzzleModel.gameOver()) {
					if (gameTimer.isRunning())
						gameTimer.stop();
				} else {
					puzzleModel.incrementGameTime();
					currentTimeLabel.setText(String.valueOf(puzzleModel.getGameTime()));
					unitsLabel.setText(puzzleModel.getGameTime() == 1 ? " second" : " seconds");
				}
			}
		});

		// Start the timer for the first game round
		gameTimer.start();

	
	}

	Model getPuzzleModel() {
		return puzzleModel;
	}

	GraphicsPanel getGraphicsPanel() {
		return puzzleGraphics;
	}

	Timer getGameTimer() {
		return gameTimer;
	}

	public class NewGameAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			currentMovesLabel.setText("0");
			puzzleModel.reset();
			puzzleModel.shuffle();
			puzzleGraphics.repaint();
			puzzleGraphics.setBackground(Color.black);

			gameTimer.restart();
		}
	}
}
