package com.sam.hex;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class RegularPolygonGameObject implements Shape {

    private RegularPolygon hex;

    double x;
    double y;
    double radius;

    public RegularPolygonGameObject() {
        x = 0;
        y = 0;
        radius = 0;
    }

    public RegularPolygonGameObject(double x, double y, double r) {
        this.x = x;
        this.y = y;
        radius = r;
    }

    public void set(double x, double y, double r) {
        this.x = x;
        this.y = y;
        radius = r;
        update(x, y, r, 6, Math.PI / 2);
    }

    public RegularPolygonGameObject(double x, double y, double r, int vertexCount) {
        hex = new RegularPolygon(x, y, r, vertexCount);
    }

    public RegularPolygonGameObject(double x, double y, double r, int vertexCount, double startAngle) {
        hex = new RegularPolygon(x, y, r, vertexCount, startAngle);
    }

    public void update(double x, double y, double r, int vertexCount, double startAngle) {
        hex = new RegularPolygon(x, y, r, vertexCount, startAngle);
    }

    public boolean contains(double x, double y) {
        return hex.contains((int) x, (int) y);
    }

    @Override
    public boolean contains(Point2D p) {
        return hex.contains(p);
    }

    @Override
    public boolean contains(Rectangle2D r) {

        return hex.contains(r);
    }

    @Override
    public boolean contains(double x, double y, double w, double h) {

        return hex.contains(x, y, w, h);
    }

    @Override
    public Rectangle getBounds() {

        return hex.getBounds();
    }

    @Override
    public Rectangle2D getBounds2D() {

        return hex.getBounds2D();
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at) {

        return hex.getPathIterator(at);
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at, double flatness) {

        return hex.getPathIterator(at, flatness);
    }

    @Override
    public boolean intersects(Rectangle2D r) {

        return hex.intersects(r);
    }

    @Override
    public boolean intersects(double x, double y, double w, double h) {

        return hex.intersects(x, y, w, h);
    }
}
