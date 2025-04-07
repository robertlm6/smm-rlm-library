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
 * Representa una elipse como una forma rellenable en el lienzo.
 * 
 * Extiende de {@link MiShapeRellenable}, lo que permite dibujarla con o sin relleno
 * y aplicar estilos como color, grosor, transparencia y antialiasing.
 * Internamente utiliza un {@link Ellipse2D} para definir su geometría.
 * 
 * @author rober
 */
public class MiElipse extends MiShapeRellenable{
    
    /**
     * La forma geométrica de la elipse.
     */
    private Ellipse2D geomElipse = new Ellipse2D.Double();
    
    /**
     * Constructor por defecto.
     */
    public MiElipse() {}
    
    /**
     * Constructor que inicializa la elipse con propiedades gráficas y dos puntos que definen su marco.
     * 
     * @param color el color del trazo.
     * @param grosor el grosor del trazo.
     * @param transparente true si debe aplicarse transparencia.
     * @param alisada true si debe aplicarse antialiasing.
     * @param rellena true si la elipse debe rellenarse.
     * @param p1 punto inicial (uno de los vértices del marco que contiene la elipse).
     * @param p2 punto final (vértice opuesto del marco).
     */
    public MiElipse(Color color, Integer grosor, Boolean transparente, 
                   Boolean alisada, Boolean rellena, Point2D p1, Point2D p2) {
        super(color, grosor, transparente, alisada, rellena);
        this.pressedPoint = p1;
        this.geomElipse.setFrameFromDiagonal(p1, p2);
    }

    /**
     * Rellena la elipse si la propiedad {@code rellena} está activada.
     * 
     * @param g2d el contexto gráfico 2D.
     */
    @Override
    public void fillShape(Graphics2D g2d) {
        g2d.fill(geomElipse); 
    }

    /**
     * Dibuja el contorno de la elipse.
     * 
     * @param g2d el contexto gráfico 2D.
     */
    @Override
    public void drawShape(Graphics2D g2d) {
        g2d.draw(geomElipse); 
    }

    /**
     * Obtiene la posición de la esquina superior izquierda del marco de la elipse.
     * 
     * @return posición como {@link Point2D}.
     */
    @Override
    public Point2D getLocation() {
        return new Point2D.Double(this.geomElipse.getX(), this.geomElipse.getY()); 
    }

    /**
     * Establece una nueva posición para la elipse, conservando su ancho y alto actuales.
     * 
     * @param pos nueva posición como {@link Point2D}.
     */
    @Override
    public void setLocation(Point2D pos) {
        this.geomElipse.setFrame(pos.getX(), pos.getY(), 
                this.geomElipse.getWidth(), this.geomElipse.getHeight()); 
    }

    /**
     * Verifica si un punto se encuentra dentro del área de la elipse.
     * 
     * @param p punto a comprobar.
     * @return true si el punto está contenido dentro de la elipse.
     */
    @Override
    public boolean contains(Point2D p) {
        return this.geomElipse.contains(p); 
    }
    
    /**
     * Actualiza la forma de la elipse según un nuevo punto que define
     * el marco diagonal junto con el punto inicial {@code pressedPoint}.
     * 
     * @param pEnd nuevo punto final para el marco de la elipse.
     */
    @Override
    public void updateShape(Point2D pEnd) {
        this.geomElipse.setFrameFromDiagonal(this.pressedPoint, pEnd);
    }
}
