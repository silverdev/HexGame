package com.sam.hex;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.hex.core.Game;
import com.hex.core.GameAction;
import com.hex.core.Point;
import com.sam.hex.replay.Replay;

public class HexGameWindow extends JFrame {
    public static Canvas cPolygons = new Canvas();

    public static BufferedImage hexBackground;

    public HexGameWindow(int hight, int width) {
        super("Hexgrid");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new GridBagLayout());

        getContentPane().add(cPolygons,
                new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 5, 5));
        setSize(width, hight);
        // initRegular();

        setLocationRelativeTo(null);

        addMenus();
    }

    protected static class Canvas extends JPanel {
        private static final long serialVersionUID = -4797847956664594904L;
        RegularPolygonGameObject[][] hexes;
        private MyListener hexListener;

        public void setShapes(Game game, GameInfo info) {
            this.hexes = new RegularPolygonGameObject[info.options.gridSize][info.options.gridSize];

            double radius = BoardTools.radiusCalculator(super.getWidth(), super.getHeight(), info.options.gridSize);

            double hrad = radius * Math.sqrt(3) / 2; // Horizontal radius
            int yOffset = (int) ((super.getHeight() - ((3 * radius / 2) * (hexes[0].length - 1) + 2 * radius)) / 2);
            int xOffset = (int) ((super.getWidth() - (hrad * hexes.length * 2 + hrad * (hexes[0].length - 1))) / 2);

            for(int xc = 0; xc < hexes.length; xc++) {
                for(int yc = 0; yc < hexes[0].length; yc++)
                    hexes[xc][yc] = new RegularPolygonGameObject((hrad + yc * hrad + 2 * hrad * xc) + xOffset, (1.5 * radius * yc + radius) + yOffset, radius,
                            6, Math.PI / 2);
            }
            BoardTools.setBackground(super.getWidth(), super.getHeight());
            if(hexListener != null) {
                this.removeMouseListener(hexListener);
            }
            hexListener = new MyListener(this.hexes);
            addMouseListener(hexListener);
        }

        @Override
        public void paintComponent(Graphics g) {
            if(hexes == null) return;
            super.paintComponent(g);
            g.setColor(Color.white);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.black);
            g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
            ((Graphics2D) g).drawImage(hexBackground, null, 0, 0);
            // Global.background.createGraphics());
            g.setColor(Color.black);
            for(int i = 0; i < hexes.length; i++) {
                for(int q = 0; q < hexes[i].length; q++) {
                    g.setColor(new Color(Hexgame.gameInfo.players[Hexgame.runningGame.gamePiece[i][q].getTeam()].getColor()));
                    ((Graphics2D) g).fill(hexes[i][q]);
                    g.setColor(Color.black);
                    ((Graphics2D) g).draw(hexes[i][q]);

                }

            }

        }

        private class MyListener extends MouseAdapter {
            private RegularPolygonGameObject[][] hexes;

            public MyListener(RegularPolygonGameObject[][] hexes) {
                this.hexes = hexes;
            }

            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                for(int xc = 0; xc < this.hexes.length; xc++) {
                    for(int yc = 0; yc < this.hexes.length; yc++)
                        if(hexes[xc][yc].contains(x, y)) {
                            System.out.println(xc + " " + yc);
                            GameAction.setPiece(new Point(xc, yc), Hexgame.runningGame);

                        }
                }

            }
        }
    }

    private void addMenus() {
        JMenuBar menuBar = new JMenuBar();

        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        JMenu player1Menu = new JMenu("Player 1");
        JMenu player2Menu = new JMenu("Player 2");
        menuBar.add(fileMenu);
        menuBar.add(player1Menu);
        menuBar.add(player2Menu);

        JMenuItem undoAction = new JMenuItem("Undo");
        JMenuItem newgameAction = new JMenuItem("New Game");
        JMenuItem replayAction = new JMenuItem("Replay Game");
        JMenuItem loadReplayAction = new JMenuItem("Load a Replay");
        JMenuItem gridAction = new JMenuItem("Grid Size");
        JMenuItem resetAction = new JMenuItem("Reset Preferences");
        JMenuItem exitAction = new JMenuItem("Exit");
        JMenuItem p1NameAction = new JMenuItem("Name");
        JMenuItem p1ColorAction = new JMenuItem("Color");
        JMenuItem p1ModeAction = new JMenuItem("Mode");
        JMenuItem p2NameAction = new JMenuItem("Name");
        JMenuItem p2ColorAction = new JMenuItem("Color");
        JMenuItem p2ModeAction = new JMenuItem("Mode");

        fileMenu.add(undoAction);
        fileMenu.add(newgameAction);
        fileMenu.add(replayAction);
        fileMenu.add(loadReplayAction);
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
                GameAction.undo(0, Hexgame.runningGame);
                return;
            }

        });

        newgameAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent act) {
                Hexgame.restart();
            }
        });

        replayAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent act) {
                Hexgame.runningGame.replay(200);
                return;
            }
        });

        loadReplayAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent act) {

                // DialogBoxes.loadReplay();
                Thread replay = new Thread(new Replay());
                replay.start();
                return;
            }
        });

        gridAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent act) {
                DialogBoxes.chooseGridsize();
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
                DialogBoxes.chooseName1();
            }
        });

        p1ColorAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent act) {
                DialogBoxes.chooseColor1();
                Hexgame.window.invalidate();
            }
        });

        p1ModeAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent act) {

                DialogBoxes.chooseGameTypePlayer1();

            }
        });

        p2NameAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent act) {
                DialogBoxes.chooseName2();
            }
        });

        p2ColorAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent act) {
                DialogBoxes.chooseColor2();

            }
        });

        p2ModeAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent act) {
                DialogBoxes.chooseGameTypePlayer2();
            }
        });
    }
}
