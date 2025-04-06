/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.rlm.graficos;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author rober
 */
public class MiRectangulo extends MiShapeRellenable{
    
    private Rectangle2D geomRectangulo = new Rectangle2D.Double();
    
    public MiRectangulo() {}
    
    public MiRectangulo(Color color, Integer grosor, Boolean transparente, 
                   Boolean alisada, Boolean rellena, Point2D p1, Point2D p2) {
        super(color, grosor, transparente, alisada, rellena);
        this.pressedPoint = p1;
        this.geomRectangulo.setFrameFromDiagonal(p1, p2);
    }

    @Override
    public void fillShape(Graphics2D g2d) {
        g2d.fill(geomRectangulo); 
    }

    @Override
    public void drawShape(Graphics2D g2d) {
        g2d.draw(geomRectangulo); 
    }

    @Override
    public Point2D getLocation() {
        return new Point2D.Double(this.geomRectangulo.getX(), this.geomRectangulo.getY()); 
    }

    @Override
    public void setLocation(Point2D pos) {
        this.geomRectangulo.setFrame(pos.getX(), pos.getY(), 
                this.geomRectangulo.getWidth(), this.geomRectangulo.getHeight()); 
    }

    @Override
    public boolean contains(Point2D p) {
        return this.geomRectangulo.contains(p); 
    }
    
    @Override
    public void updateShape(Point2D pEnd) {
        this.geomRectangulo.setFrameFromDiagonal(this.pressedPoint, pEnd);
    }
}
