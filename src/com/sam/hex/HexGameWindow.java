package com.sam.hex;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
					for (RegularPolygonGameObject hex : Global.gamePiece[xc])
						if (hex.contains(x, y)) {
							GameAction.setPiece(hex);
						}
				}

			}
		}
	}
	
	private void addMenus(){
JMenuBar menuBar = new JMenuBar();
		
		setJMenuBar(menuBar);
		
		JMenu fileMenu = new JMenu("File");
		JMenu editMenu = new JMenu("Edit");
		JMenu viewMenu = new JMenu("View");
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(viewMenu);
		
		
		JMenuItem newAction =   new JMenuItem("New");
		JMenuItem openAction =  new JMenuItem("Open");
		JMenuItem exitAction =  new JMenuItem("Exit");
		JMenuItem cutAction =   new JMenuItem("Cut");
		JMenuItem copyAction =  new  JMenuItem("Copy");
		JMenuItem pasteAction =  new JMenuItem("Paste");
		JMenuItem toolbarsAction= new JMenuItem("toolbarsAction");
		fileMenu.add(newAction);
		fileMenu.add(openAction);
		fileMenu.addSeparator();
		fileMenu.add(exitAction);
		editMenu.add(cutAction);
		editMenu.add(copyAction);
		editMenu.add(pasteAction);
		editMenu.addSeparator();
		viewMenu.add(toolbarsAction);
		
		newAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent act) {
				//Add action here
			} 
		});
		
		openAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent act) {
				System.out.println(act.getActionCommand());
			} 
		});
	}
}
