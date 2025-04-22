/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.rlm.graficos;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Representa un rectángulo como una forma rellenable en el lienzo.
 * 
 * Extiende de {@link MiShapeRellenable}, lo que permite que el rectángulo
 * se dibuje con o sin relleno, y con configuraciones de estilo gráfico como
 * color, grosor, transparencia y alisado.
 * 
 * Internamente usa un {@link Rectangle2D} para definir su geometría.
 * 
 * @author rober
 */
public class MiRectangulo extends MiShapeRellenable{
    
    /**
     * La forma geométrica del rectángulo.
     */
    private Rectangle2D geomRectangulo = new Rectangle2D.Double();
    
    /**
     * Constructor por defecto.
     */
    public MiRectangulo() {}
    
    /**
     * Constructor que inicializa el rectángulo con estilo gráfico, opción de relleno y dos puntos.
     * 
     * @param color el color del trazo.
     * @param grosor el grosor del trazo.
     * @param transparente true si debe aplicarse transparencia.
     * @param alisada true si debe aplicarse antialiasing.
     * @param rellena true si el rectángulo debe ser rellenado.
     * @param p1 punto inicial (esquina).
     * @param p2 punto final (esquina opuesta).
     */
    public MiRectangulo(Color color, Integer grosor, Boolean transparente, 
                   Boolean alisada, Boolean rellena, Point2D p1, Point2D p2) {
        super(color, grosor, transparente, alisada, rellena);
        this.pressedPoint = p1;
        this.geomRectangulo.setFrameFromDiagonal(p1, p2);
    }

    /**
     * Rellena el rectángulo con el color actual si la propiedad {@code rellena} es true.
     * 
     * @param g2d el contexto gráfico 2D.
     */
    @Override
    public void fillShape(Graphics2D g2d) {
        g2d.fill(geomRectangulo); 
    }

    /**
     * Dibuja el borde del rectángulo.
     * 
     * @param g2d el contexto gráfico 2D.
     */
    @Override
    public void drawShape(Graphics2D g2d) {
        g2d.draw(geomRectangulo); 
    }

    /**
     * Retorna la posición (esquina superior izquierda) del rectángulo.
     * 
     * @return la posición del rectángulo como {@link Point2D}.
     */
    @Override
    public Point2D getLocation() {
        return new Point2D.Double(this.geomRectangulo.getX(), this.geomRectangulo.getY()); 
    }

    /**
     * Establece una nueva posición para el rectángulo, manteniendo su ancho y alto.
     * 
     * @param pos nueva posición (esquina superior izquierda).
     */
    @Override
    public void setLocation(Point2D pos) {
        this.geomRectangulo.setFrame(pos.getX(), pos.getY(), 
                this.geomRectangulo.getWidth(), this.geomRectangulo.getHeight()); 
    }

    /**
     * Verifica si un punto está contenido dentro del área del rectángulo.
     * 
     * @param p punto a verificar.
     * @return true si el punto está dentro del rectángulo.
     */
    @Override
    public boolean contains(Point2D p) {
        return this.geomRectangulo.contains(p); 
    }
    
    /**
     * Actualiza el tamaño del rectángulo basándose en el punto de inicio ({@code pressedPoint})
     * y el nuevo punto final.
     * 
     * @param pEnd nueva esquina opuesta del rectángulo.
     */
    @Override
    public void updateShape(Point2D pEnd) {
        this.geomRectangulo.setFrameFromDiagonal(this.pressedPoint, pEnd);
    }
    
    @Override
    protected Rectangle2D getBounds() {
        return this.geomRectangulo.getBounds2D();
    }
}
