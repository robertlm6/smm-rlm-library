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
    public Boolean pControl;
    
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
        double dx = pos.getX() - this.geomCurva.getX1();
        double dy = pos.getY() - this.geomCurva.getY1();
        Point2D newp2 = new Point2D.Double(this.geomCurva.getX2() + dx, this.geomCurva.getY2() + dy);
        Point2D newcp = new Point2D.Double(this.geomCurva.getCtrlX()+ dx, this.geomCurva.getCtrlY() + dy);
        this.geomCurva.setCurve(pos, newcp, newp2);
    }

    @Override
    public boolean contains(Point2D p) {
        return this.geomCurva.contains(p); 
    }

    @Override
    public void updateShape(Point2D pEnd) {
        if(!this.pControl) {
            this.geomCurva.setCurve(this.geomCurva.getP1(), pEnd, pEnd);
        } else {
            this.geomCurva.setCurve(this.geomCurva.getP1(), pEnd, this.geomCurva.getP2());
        }
    }

    public Boolean getpControl() {
        return pControl;
    }

    public void setpControl(Boolean pControl) {
        this.pControl = pControl;
    }
}
