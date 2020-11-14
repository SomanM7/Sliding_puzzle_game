package uk.ac.man.cs.puzzle.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import uk.ac.man.cs.puzzle.logic.Model;

public class GUI extends JPanel {

	private static final long serialVersionUID = 1L;
	private final Model puzzleModel;
	private GraphicsPanel puzzleGraphics;
	private int ROWS;
	private int COLS;
	JLabel currentMovesLabel;
	JLabel Moves;


	public GUI(int rows, int cols) {
		// Create a button. Add a listener to it.
		JButton newGameButton = new JButton("New Game");
		newGameButton.addActionListener(new NewGameAction());

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
		
	
		Moves = new JLabel();
		Moves.setText("Moves: ");
;


		// Set the layout and add the components
		this.setLayout(new BorderLayout());
		this.add(controlPanel, BorderLayout.NORTH);
		this.add(Moves,BorderLayout.SOUTH);
		this.add(puzzleGraphics, BorderLayout.CENTER);
	
	}

	Model getPuzzleModel() {
		return puzzleModel;
	}

	GraphicsPanel getGraphicsPanel() {
		return puzzleGraphics;
	}

	public class NewGameAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			currentMovesLabel.setText("0");
			puzzleModel.reset();
			puzzleModel.shuffle();
			puzzleGraphics.repaint();
			puzzleGraphics.setBackground(Color.black);
		}
	}
}
