package com.sam.hex;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class HexGameWindow extends JFrame {
	private static final long serialVersionUID = 7740144563399961702L;
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

	protected void initRegular() {
		Global.gamePiece=new RegularPolygonGameObject[Global.gridSize][Global.gridSize];
		Global.hexes=Global.gamePiece;
		
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
		private static final long serialVersionUID = -4797847956664594904L;
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
		JMenuItem replayAction =   new JMenuItem("Replay Game");
		JMenuItem gridAction =  new JMenuItem("Grid Size");
		JMenuItem resetAction =  new JMenuItem("Reset Preferences");
		JMenuItem exitAction =  new JMenuItem("Exit");
		JMenuItem p1NameAction =   new JMenuItem("Name");
		JMenuItem p1ColorAction =  new  JMenuItem("Color");
		JMenuItem p1ModeAction =  new JMenuItem("Mode");
		JMenuItem p2NameAction =   new JMenuItem("Name");
		JMenuItem p2ColorAction =  new  JMenuItem("Color");
		JMenuItem p2ModeAction =  new JMenuItem("Mode");

		fileMenu.add(undoAction);
		fileMenu.add(newgameAction);
		fileMenu.add(replayAction);
		fileMenu.addSeparator();
		fileMenu.add(gridAction);
		fileMenu.addSeparator();
		fileMenu.add(resetAction);
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
				if (Global.player1Type==0 && Global.player2Type==0){
					Global.moveList.undo();
					GameAction.setPiece(new java.awt.Point(-1,-1));
				}
				//undo pvc
				if (Global.player1Type==1 || Global.player2Type==1 && !(Global.player1Type==1 && Global.player2Type==1))
					Global.moveList.undoTwo();
				//let ai know of undo
				Global.player1.undoCalled();
				Global.player2.undoCalled();
				//undo if the game has ended
				if (Global.gameOver==true){
					Global.gameOver=false;
					Global.moveList.replay(0);
					Global.playerturn = Global.playerturn%2+1;
					new GameObject(true);
				}
			} 
		});
				
		newgameAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent act) {
				GameAction.stopGame();
				initRegular();
				GameAction.fullUpdateBoard();
				new GameObject();
			} 
		});
		
		replayAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent act) {
				Thread replay = new Thread(new Replay());
				replay.start();
				return;
			} 
		});
		
		gridAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent act) {
				int size = Global.gridSize;
				DialogBoxes.chooseGridsize();
				if(size!=Global.gridSize){
					GameAction.stopGame();
					initRegular();
					GameAction.fullUpdateBoard();
					new GameObject();
				}
			} 
		});
		
		resetAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent act) {
				DialogBoxes.resetGameOption();
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
				Global.playerOneName=DialogBoxes.chooseName1();
			} 
		});
		
		p1ColorAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent act) {
				DialogBoxes.chooseColor1();
				initRegular();
				GameAction.fullUpdateBoard();
				Global.moveList.replay(0);
				GameAction.checkedFlagReset();
				GameAction.checkWinPlayer1();
				GameAction.checkWinPlayer2();
			} 
		});
		
		p1ModeAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent act) {
				int type = Global.player1Type;
				DialogBoxes.chooseGameTypePlayer1();
				if(type!=Global.player1Type){
					GameAction.stopGame();
					initRegular();
					GameAction.fullUpdateBoard();
					new GameObject();
				}
			} 
		});
		
		p2NameAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent act) {
				Global.playerTwoName=DialogBoxes.chooseName2();
			} 
		});
		
		p2ColorAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent act) {
				DialogBoxes.choseColor2();
				initRegular();
				GameAction.fullUpdateBoard();
				Global.moveList.replay(0);
				GameAction.checkedFlagReset();
				GameAction.checkWinPlayer1();
				GameAction.checkWinPlayer2();
			} 
		});
		
		p2ModeAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent act) {
				int type = Global.player2Type;
				DialogBoxes.chooseGameTypePlayer2();
				if(type!=Global.player2Type){
					GameAction.stopGame();
					initRegular();
					GameAction.fullUpdateBoard();
					new GameObject();
				}
			} 
		});
	}
	
	public static void announceWinner(int team){
		DialogBoxes.announce(team);
	}
}