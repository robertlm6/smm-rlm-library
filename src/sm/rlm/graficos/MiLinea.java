/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.rlm.graficos;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 *
 * @author rober
 */
public class MiLinea extends MiShape{
    
    private Line2D geomLinea;
    
    public MiLinea(){}
    
    public MiLinea(Color color, Integer grosor, Boolean transparente, 
                   Boolean alisada, Point2D p1, Point2D p2) {
        super(color, grosor, transparente, alisada);
        this.geomLinea = new Line2D.Double(p1, p2);
    }

    @Override
    public void drawShape(Graphics2D g2d) {
        g2d.draw(geomLinea);
    }

    @Override
    public Point2D getLocation() {
        return this.geomLinea.getP1();
    }

    @Override
    public void setLocation(Point2D pos) {
        double dx = pos.getX() - this.geomLinea.getX1();
        double dy = pos.getY() - this.geomLinea.getY1();
        Point2D newp2 = new Point2D.Double(this.geomLinea.getX2() + dx, this.geomLinea.getY2() + dy);
        this.geomLinea.setLine(pos, newp2);
    }
    
    @Override
    public boolean contains(Point2D p) {
        return isNear(p);
    }
    
    private boolean isNear(Point2D p) {
        if (this.geomLinea.getP1().equals(this.geomLinea.getP2())) {
            return this.geomLinea.getP1().distance(p) <= 2.0;
        }
        return this.geomLinea.ptLineDist(p) <= 2.0;
    }
}
