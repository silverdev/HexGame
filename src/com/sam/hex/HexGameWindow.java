package com.sam.hex;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

//DEPRECATED: This class will not be ported to Android, just ignore it for now.

@SuppressWarnings("serial")
public class HexGameWindow extends JFrame {
	public static Canvas cPolygons = new Canvas();

	public HexGameWindow() {
		super("Hexgrid");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new GridBagLayout());

		getContentPane().add(
				cPolygons,
				new GridBagConstraints(1, 1, 1, 1, 1, 1,
						GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0), 5, 5));
		setSize(Global.windowWidth, Global.windowHeight);
		initRegular();

		setLocationRelativeTo(null);
		
		addMenus();
	}

	/*
	 * public static void main(String[] args) { HexGameWindow fr = new
	 * HexGameWindow(); fr.setVisible(true);
	 * 
	 * }
	 */

	protected void initRegular() {

		double radius = BoardTools.radiusCalculator(Global.windowWidth,
				Global.windowHeight, Global.gridSize);
		;
		Shape[][] hexes = Global.hexes;
		double hrad = radius * Math.sqrt(3) / 2; // Horizontal radius
		// radius =BoardTools.radiusCalculator(Canvas.HEIGHT,Canvas.WIDTH, 7);
		int yOffset = (int) ((Global.windowHeight - ((3 * radius / 2)
				* (hexes[0].length - 1) + 2 * radius)) / 2);
		int xOffset = (int) ((Global.windowWidth - (hrad * hexes.length)) / 2);

		for (int xc = 0; xc < Global.hexes.length; xc++) {
			for (int yc = 0; yc < hexes[0].length; yc++)
				hexes[xc][yc] = new RegularPolygonGameObject(
						((hrad + yc * hrad + 2 * hrad * xc) + xOffset), (1.5
								* radius * yc + radius)
								+ yOffset, radius, 6, Math.PI / 2);
		}
		cPolygons.setShapes(hexes, Color.blue);
	}

	protected static class Canvas extends JPanel {
		Shape[][] hexes;
		Color color;

		public void setShapes(Shape[][] shapes, Color color) {
			this.hexes = shapes;
			this.color = color;
			addMouseListener(new MyListener());
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.white);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(Color.black);
			g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
			((Graphics2D) g).drawImage(Global.background, null, 0, 0);
			// Global.background.createGraphics());
			g.setColor(Color.black);
			for (int i = 0; i < hexes.length; i++) {
				for (int q = 0; q < hexes[i].length; q++) {
					g.setColor(((RegularPolygonGameObject) hexes[i][q])
							.getColor());
					((Graphics2D) g).fill(hexes[i][q]);
					g.setColor(Color.black);
					((Graphics2D) g).draw(hexes[i][q]);

				}

			}

		}

		private class MyListener extends MouseAdapter {
			public void mousePressed(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				for (int xc = 0; xc < Global.gamePiece.length; xc++) {
					for (int yc = 0; yc < Global.gamePiece.length; yc++)
						if (Global.gamePiece[xc][yc].contains(x, y)) {
							GameAction.setPiece(new java.awt.Point(xc,yc));
						}
				}

			}
		}
	}
	
	private void addMenus(){
		JMenuBar menuBar = new JMenuBar();
		
		setJMenuBar(menuBar);
		
		JMenu fileMenu = new JMenu("File");
		JMenu player1Menu = new JMenu("Player 1");
		JMenu player2Menu = new JMenu("Player 2");
		menuBar.add(fileMenu);
		menuBar.add(player1Menu);
		menuBar.add(player2Menu);

		JMenuItem undoAction =  new JMenuItem("Undo");
		JMenuItem newgameAction =   new JMenuItem("New Game");
		JMenuItem gridAction =  new JMenuItem("Grid Size");
		JMenuItem exitAction =  new JMenuItem("Exit");
		JMenuItem p1NameAction =   new JMenuItem("Name");
		JMenuItem p1ColorAction =  new  JMenuItem("Color");
		JMenuItem p1ModeAction =  new JMenuItem("Mode");
		JMenuItem p2NameAction =   new JMenuItem("Name");
		JMenuItem p2ColorAction =  new  JMenuItem("Color");
		JMenuItem p2ModeAction =  new JMenuItem("Mode");

		fileMenu.add(undoAction);
		fileMenu.add(newgameAction);
		fileMenu.addSeparator();
		fileMenu.add(gridAction);
		fileMenu.addSeparator();
		fileMenu.add(exitAction);
		player1Menu.add(p1NameAction);
		player1Menu.add(p1ColorAction);
		player1Menu.add(p1ModeAction);
		player2Menu.add(p2NameAction);
		player2Menu.add(p2ColorAction);
		player2Menu.add(p2ModeAction);
		
		undoAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent act) {
				//undo pvp
				if (Global.gameType==0){
					Global.moveList.undo();
					GameAction.setPiece(new java.awt.Point(-1,-1));
				}
				//undo pvc
				if (Global.gameType==1||Global.gameType==2)	
					Global.moveList.undoTwo();
				//let ia's know of undo
				Global.player1.undoCalled();
				Global.player2.undoCalled();
				//undo if the game has ended
				if (Global.gameOver==true){
					Global.gameOver=false;
					Global.moveList.replay(0);
					GameObject RunningGame = new GameObject(true);
					
				}
			} 
		});
				
		newgameAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent act) {
				newGame();
			} 
		});
		
		gridAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent act) {
				final JFrame prompt = new JFrame("Grid Size");
				final JTextField text = new JTextField();
				JButton okay = new JButton("Save");
				text.addKeyListener(new KeyListener() {
					
					@Override
					public void keyTyped(KeyEvent e) {
						//Do nothing
					}
					
					@Override
					public void keyReleased(KeyEvent e) {
						if(e.getKeyCode() == KeyEvent.VK_ENTER){
							try{
								Global.gridSize = Integer.decode(text.getText());
								if(Global.gridSize<1) Global.gridSize = 1;
								newGame();
							}
							catch(Exception ex){}
							prompt.dispose();
						}
					}
					
					@Override
					public void keyPressed(KeyEvent e) {
						//Do nothing
					}
				});
				okay.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent act) {
						try{
							Global.gridSize = Integer.decode(text.getText());
							if(Global.gridSize<1) Global.gridSize = 1;
							newGame();
						}
						catch(Exception e){}
						prompt.dispose();
						
					}});
				prompt.getContentPane().setLayout(new BoxLayout(prompt.getContentPane(), BoxLayout.PAGE_AXIS));
				prompt.getContentPane().add(text, BorderLayout.CENTER);
				prompt.getContentPane().add(okay, BorderLayout.CENTER);
				prompt.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				prompt.setLocationRelativeTo(null);
				prompt.pack();
				prompt.setVisible(true);
			} 
		});
		
		exitAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent act) {
				dispose();
			} 
		});
		
		p1NameAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent act) {
				//Add action here
			} 
		});
		
		p1ColorAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent act) {
				//Add action here
			} 
		});
		
		p1ModeAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent act) {
				//Add action here
			} 
		});
		
		p2NameAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent act) {
				//Add action here
			} 
		});
		
		p2ColorAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent act) {
				//Add action here
			} 
		});
		
		p2ModeAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent act) {
				//Add action here
			} 
		});
	}
	
	private void newGame(){
		Global.runningGame.stop();
		initRegular();
		GameAction.fullUpdateBoard();
		GameObject RunningGame = new GameObject();
		//TODO Doesn't update the number of hexagons
	}
}
