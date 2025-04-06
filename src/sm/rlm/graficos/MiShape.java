/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.rlm.graficos;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Point2D;

/**
 *
 * @author rober
 */
public abstract class MiShape {
    protected Color color;
    protected Stroke stroke;
    protected Composite comp;
    protected RenderingHints render;
    
    public MiShape(){}
    
    public MiShape(Color color, Integer grosor, Boolean transparente, Boolean alisada) {
        this.color = color;
        this.stroke = new BasicStroke(grosor);
        
        this.comp = transparente 
                ? AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)
                : AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
        
        this.render = new RenderingHints(RenderingHints.KEY_ANTIALIASING, 
                alisada ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
    }
    
    public void draw(Graphics2D g2d) {
        g2d.setPaint(color);
        g2d.setStroke(stroke);
        g2d.setComposite(comp);
        g2d.setRenderingHints(render);
        drawShape(g2d);
    }
    
    public abstract void drawShape(Graphics2D g2d);
    
    public abstract Point2D getLocation();
    
    public abstract void setLocation(Point2D pos);
    
    public abstract boolean contains(Point2D p);
    
    public abstract void updateShape(Point2D pEnd);

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Stroke getStroke() {
        return stroke;
    }

    public void setStroke(Stroke stroke) {
        this.stroke = stroke;
    }

    public Composite getComp() {
        return comp;
    }

    public void setComp(Composite comp) {
        this.comp = comp;
    }

    public RenderingHints getRender() {
        return render;
    }

    public void setRender(RenderingHints render) {
        this.render = render;
    }
}
