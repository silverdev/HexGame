package com.sam.hex;

import java.awt.image.BufferedImage;

import com.hex.core.GamePiece;

public class BoardTools {
    static double spaceH; // Horizontal
    static double spaceV; // Vertical

    public static double radiusCalculator(double w, double h, double n) {

        spaceV = (((n - 1) * 3 / 2) + 2);

        spaceH = n + (n - 1) / 2; // always bigger.
        spaceH = (w / (spaceH * Math.sqrt(3)));
        spaceV = (h / spaceV);
        if(spaceV < spaceH) {
            return spaceV;
        }
        return spaceH;

    }

    public static void setBackground(int w, int h) {
        Hexgame.window.hexBackground = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB); // the
                                                                                             // background
                                                                                             // is
                                                                                             // drawn
                                                                                             // to
                                                                                             // this
                                                                                             // buffered
                                                                                             // image.
        GamePiece[][] gamePeace = Hexgame.runningGame.gamePieces;
        double radius = BoardTools.radiusCalculator(Hexgame.window.cPolygons.getWidth(), Hexgame.window.cPolygons.getHeight(),
                Hexgame.gameInfo.options.gridSize);
        double hrad = radius * Math.sqrt(3) / 2; // Horizontal radius
        int yOffset = (int) ((Hexgame.window.cPolygons.getHeight() - ((3 * radius / 2) * (gamePeace[0].length - 1) + 2 * radius)) / 2);
        int xOffset = (int) ((Hexgame.window.cPolygons.getWidth() - (hrad * gamePeace.length * 2 + hrad * (gamePeace[0].length - 1))) / 2);
        int aX = xOffset;
        int aY = yOffset + (int) radius / 2;
        int bX = xOffset + (int) ((gamePeace.length - 1) * hrad + gamePeace.length * hrad * 2);
        int bY = yOffset + (int) (((gamePeace.length - 1) * radius * 3 / 2) + radius * 3 / 2);
        double slope1 = (double) (bY - aY) / (bX - aX);

        int cX = xOffset + (int) ((gamePeace.length - 1) * hrad + hrad / 2);
        int cY = yOffset + (int) (((gamePeace.length - 1) * radius * 3 / 2) + radius * 7 / 4);
        int dX = xOffset + (int) (gamePeace.length * hrad * 2 - hrad / 2);
        int dY = yOffset + (int) radius / 4;
        double slope2 = (double) (dY - cY) / (dX - cX);

        for(double x = 0; x < w; x++) {
            for(double y = 0; y < h; y++) {
                if(y > slope1 * x + (aY - aX * slope1) != y > slope2 * x + (cY - cX * slope2)) { // if
                                                                                                 // above
                                                                                                 // line
                                                                                                 // 1
                                                                                                 // ==
                                                                                                 // above
                                                                                                 // line
                    // 2
                    // if((y+x)/(((double)h+(double)w))<.5==((double)h/(double)w>y/x)){
                    Hexgame.window.hexBackground.setRGB((int) x, (int) y, Hexgame.gameInfo.player1.getColor());
                }
                else {
                    Hexgame.window.hexBackground.setRGB((int) x, (int) y, Hexgame.gameInfo.player2.getColor());
                }
                // (((h*w)-h)>y/x)
            }
        }

    }

}
