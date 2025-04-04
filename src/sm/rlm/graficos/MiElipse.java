/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.rlm.graficos;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

/**
 *
 * @author rober
 */
public class MiElipse extends MiShapeRellenable{
    
    private Ellipse2D geomElipse;
    
    public MiElipse() {}
    
    public MiElipse(Color color, Integer grosor, Boolean transparente, 
                   Boolean alisada, Boolean rellena, Point2D p1, Point2D p2) {
        super(color, grosor, transparente, alisada, rellena);
        this.geomElipse.setFrameFromDiagonal(p1, p2);
    }

    @Override
    public void fillShape(Graphics2D g2d) {
        g2d.fill(geomElipse); 
    }

    @Override
    public void drawShape(Graphics2D g2d) {
        g2d.draw(geomElipse); 
    }

    @Override
    public Point2D getLocation() {
        return new Point2D.Double(this.geomElipse.getX(), this.geomElipse.getY()); 
    }

    @Override
    public void setLocation(Point2D pos) {
        this.geomElipse.setFrameFromDiagonal(pos.getX(), pos.getY(), 
                this.geomElipse.getWidth(), this.geomElipse.getHeight()); 
    }

    @Override
    public boolean contains(Point2D p) {
        return this.geomElipse.contains(p); 
    }
    
}
