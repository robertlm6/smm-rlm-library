/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.rlm.graficos;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

/**
 * Clase abstracta que extiende {@link MiShape} y añade la capacidad de relleno.
 * 
 * Las subclases deben implementar el método {@code fillShape()} para definir cómo se rellena la forma.
 * También mantiene un punto de referencia {@code pressedPoint} útil para operaciones como el redimensionamiento o dibujo.
 * 
 * Esta clase permite controlar si una forma debe ser dibujada solo con contorno o también con relleno.
 * 
 * @author rober
 */
public abstract class MiShapeRellenable extends MiShape{
    
    /**
     * Indica si la forma debe rellenarse además de dibujar el contorno.
     */
    protected Boolean rellena;

    /**
     * Punto de referencia utilizado, por ejemplo, en operaciones de
     * redimensionado o dibujo.
     */
    protected Point2D pressedPoint;
    
    /**
     * Constructor por defecto.
     */
    public MiShapeRellenable(){}
    
    /**
     * Constructor que inicializa los atributos visuales y la propiedad de relleno de la forma.
     *
     * @param color el color de la forma.
     * @param grosor el grosor del trazo.
     * @param transparente true si la forma debe ser transparente.
     * @param alisada true si debe aplicarse alisado en el dibujo.
     * @param rellena true si la forma debe rellenarse, false si solo se dibuja el contorno.
     */
    public MiShapeRellenable(Color color, Integer grosor, Boolean transparente, 
                             Boolean alisada, Boolean rellena) {
        super(color, grosor, transparente, alisada);
        this.rellena = rellena;
    }
    
    /**
     * Dibuja la forma aplicando las propiedades de estilo y, si corresponde, la rellena.
     *
     * @param g2d el contexto gráfico 2D.
     */
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
        if (this.selected) {
            this.drawBounds(g2d);
        }
    }
    
    /**
     * Método abstracto que debe implementar cómo se rellena la forma.
     *
     * @param g2d el contexto gráfico 2D.
     */
    public abstract void fillShape(Graphics2D g2d);

    /**
     * Indica si la forma se debe rellenar al dibujarla.
     *
     * @return true si se debe rellenar, false si solo se dibuja el contorno.
     */
    public Boolean getRellena() {
        return rellena;
    }

    /**
     * Establece si la forma debe ser rellenada al dibujarla.
     *
     * @param rellena true para rellenar la forma, false para solo contorno.
     */
    public void setRellena(Boolean rellena) {
        this.rellena = rellena;
    }
}
