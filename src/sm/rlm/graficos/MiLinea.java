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
 * Representa una línea como una implementación concreta de {@link MiShape}.
 * 
 * Esta clase permite definir y manipular una línea entre dos puntos usando {@link Line2D}.
 * Incluye soporte para dibujo, selección cercana a la línea, actualización de posición,
 * y transformación basada en puntos.
 * 
 * Se puede utilizar como una figura dentro de un sistema de dibujo 2D interactivo.
 * 
 * @author rober
 */
public class MiLinea extends MiShape{
    
    /**
     * La representación geométrica de la línea.
     */
    private Line2D geomLinea = new Line2D.Double();
    
    /**
     * Constructor por defecto.
     */
    public MiLinea(){}
    
    /**
     * Constructor que inicializa la línea con estilo y puntos extremos.
     *
     * @param color el color de la línea.
     * @param grosor el grosor del trazo.
     * @param transparente true si la línea debe ser transparente.
     * @param alisada true si se debe aplicar alisado al dibujar.
     * @param p1 el punto inicial de la línea.
     * @param p2 el punto final de la línea.
     */
    public MiLinea(Color color, Integer grosor, Boolean transparente, 
                   Boolean alisada, Point2D p1, Point2D p2) {
        super(color, grosor, transparente, alisada);
        this.geomLinea = new Line2D.Double(p1, p2);
    }

    /**
     * Dibuja la línea sobre el contexto gráfico especificado.
     *
     * @param g2d el contexto gráfico 2D.
     */
    @Override
    public void drawShape(Graphics2D g2d) {
        g2d.draw(geomLinea);
    }

    /**
     * Retorna el punto inicial de la línea como su "ubicación".
     *
     * @return el punto inicial de la línea.
     */
    @Override
    public Point2D getLocation() {
        return this.geomLinea.getP1();
    }

    /**
     * Establece una nueva posición para la línea, trasladando ambos extremos
     * para mantener la longitud y dirección.
     *
     * @param pos el nuevo punto inicial.
     */
    @Override
    public void setLocation(Point2D pos) {
        double dx = pos.getX() - this.geomLinea.getX1();
        double dy = pos.getY() - this.geomLinea.getY1();
        Point2D newp2 = new Point2D.Double(this.geomLinea.getX2() + dx, this.geomLinea.getY2() + dy);
        this.geomLinea.setLine(pos, newp2);
    }
    
    /**
     * Verifica si un punto está "cerca" de la línea, permitiendo tolerancia visual.
     *
     * @param p el punto a verificar.
     * @return true si el punto está a una distancia menor o igual a 2 píxeles de la línea.
     */
    @Override
    public boolean contains(Point2D p) {
        return isNear(p);
    }
    
    /**
     * Actualiza el punto final de la línea, manteniendo fijo el punto inicial.
     *
     * @param pEnd el nuevo punto final de la línea.
     */
    @Override
    public void updateShape(Point2D pEnd) {
        this.geomLinea.setLine(this.geomLinea.getP1(), pEnd);
    }
    
    /**
     * Determina si un punto está suficientemente cerca de la línea para considerarse contenido.
     * 
     * Si los dos puntos son iguales (línea degenerada), compara la distancia al punto único.
     *
     * @param p el punto a verificar.
     * @return true si está dentro del umbral de 2.0 unidades de distancia.
     */
    private boolean isNear(Point2D p) {
        if (this.geomLinea.getP1().equals(this.geomLinea.getP2())) {
            return this.geomLinea.getP1().distance(p) <= 2.0;
        }
        return this.geomLinea.ptLineDist(p) <= 2.0;
    }
}
