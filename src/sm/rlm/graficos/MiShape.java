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
 * Clase abstracta que representa una forma básica dentro del lienzo.
 * Proporciona atributos y comportamiento común para el dibujo de formas
 * como color, grosor, transparencia y alisado.
 * 
 * Las subclases deben implementar los métodos abstractos para definir
 * el comportamiento específico de cada forma (ubicación, contención de punto, etc.).
 * 
 * @author rober
 */
public abstract class MiShape {
    protected Color color;
    protected Stroke stroke;
    protected Composite comp;
    protected RenderingHints render;
    
    /**
     * Constructor por defecto.
     */
    public MiShape(){}
    
    /**
     * Constructor que inicializa los atributos visuales de la forma.
     *
     * @param color el color de la forma.
     * @param grosor el grosor del trazo.
     * @param transparente true si la forma debe ser transparente.
     * @param alisada true si debe aplicarse alisado en el dibujo.
     */
    public MiShape(Color color, Integer grosor, Boolean transparente, Boolean alisada) {
        this.color = color;
        this.stroke = new BasicStroke(grosor);
        
        this.comp = transparente 
                ? AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)
                : AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
        
        this.render = new RenderingHints(RenderingHints.KEY_ANTIALIASING, 
                alisada ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
    }
    
    /**
     * Dibuja la forma en el contexto gráfico especificado.
     * Establece las propiedades de renderizado antes de llamar a drawShape().
     *
     * @param g2d el contexto gráfico 2D.
     */
    public void draw(Graphics2D g2d) {
        g2d.setPaint(color);
        g2d.setStroke(stroke);
        g2d.setComposite(comp);
        g2d.setRenderingHints(render);
        drawShape(g2d);
    }
    
    /**
     * Método abstracto que debe implementar el dibujo específico de la forma.
     *
     * @param g2d el contexto gráfico 2D.
     */
    public abstract void drawShape(Graphics2D g2d);
    
    /**
     * Obtiene la ubicación de la forma.
     *
     * @return un objeto Point2D representando la posición.
     */
    public abstract Point2D getLocation();
    
    /**
     * Establece una nueva ubicación para la forma.
     *
     * @param pos la nueva posición como Point2D.
     */
    public abstract void setLocation(Point2D pos);
    
    /**
     * Determina si un punto está contenido dentro de la forma.
     *
     * @param p el punto a verificar.
     * @return true si el punto está dentro, false en caso contrario.
     */
    public abstract boolean contains(Point2D p);
    
    /**
     * Actualiza la forma basándose en un nuevo punto (p. ej. al arrastrar).
     *
     * @param pEnd el punto final del evento de modificación.
     */
    public abstract void updateShape(Point2D pEnd);

    /**
     * Obtiene el color de la forma.
     *
     * @return el color actual.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Establece el color de la forma.
     *
     * @param color el nuevo color.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Obtiene el trazo (grosor de línea) de la forma.
     *
     * @return el objeto Stroke actual.
     */
    public Stroke getStroke() {
        return stroke;
    }

    /**
     * Establece el trazo de la forma.
     *
     * @param stroke el nuevo objeto Stroke.
     */
    public void setStroke(Stroke stroke) {
        this.stroke = stroke;
    }

    /**
     * Obtiene la configuración de transparencia (composite) de la forma.
     *
     * @return el objeto Composite actual.
     */
    public Composite getComp() {
        return comp;
    }

    /**
     * Establece la configuración de transparencia de la forma.
     *
     * @param comp el nuevo objeto Composite.
     */
    public void setComp(Composite comp) {
        this.comp = comp;
    }

    /**
     * Obtiene las pistas de renderizado (e.g., alisado).
     *
     * @return el objeto RenderingHints actual.
     */
    public RenderingHints getRender() {
        return render;
    }

    /**
     * Establece las pistas de renderizado.
     *
     * @param render el nuevo objeto RenderingHints.
     */
    public void setRender(RenderingHints render) {
        this.render = render;
    }
}
