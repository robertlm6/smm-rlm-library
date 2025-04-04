/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.rlm.graficos;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;

/**
 *
 * @author rober
 */
public class MiCurva extends MiShape{

    private QuadCurve2D geomCurva;
    
    public MiCurva(){}
    
    public MiCurva(Color color, Integer grosor, Boolean transparente, 
                   Boolean alisada, Point2D p1, Point2D p2, Point2D pc) {
        super(color, grosor, transparente, alisada);
        this.geomCurva = new QuadCurve2D.Double(p1.getX(), p1.getY(), 
                pc.getX(), pc.getY(), p2.getX(), p2.getY());
    }
    
    @Override
    public void drawShape(Graphics2D g2d) {
        g2d.draw(geomCurva); 
    }

    @Override
    public Point2D getLocation() {
        return this.geomCurva.getP1(); 
    }

    @Override
    public void setLocation(Point2D pos) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public boolean contains(Point2D p) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
}
