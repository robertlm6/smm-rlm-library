/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.rlm.graficos;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

/**
 *
 * @author rober
 */
public abstract class MiShapeRellenable extends MiShape{
    protected Boolean rellena;
    protected Point2D pressedPoint;
    
    public MiShapeRellenable(){}
    
    public MiShapeRellenable(Color color, Integer grosor, Boolean transparente, 
                             Boolean alisada, Boolean rellena) {
        super(color, grosor, transparente, alisada);
        this.rellena = rellena;
    }
    
    @Override
    public void draw(Graphics2D g2d) {
        g2d.setPaint(color);
        g2d.setStroke(stroke);
        g2d.setComposite(comp);
        g2d.setRenderingHints(render);
        if (rellena) {
            this.fillShape(g2d);
        }
        this.drawShape(g2d);
    }
    
    public abstract void fillShape(Graphics2D g2d);

    public Boolean getRellena() {
        return rellena;
    }

    public void setRellena(Boolean rellena) {
        this.rellena = rellena;
    }
}
