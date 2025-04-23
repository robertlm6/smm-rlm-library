/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.rlm.graficos;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;

/**
 * Representa una curva cuadrática de Bézier como una implementación concreta de {@link MiShape}.
 * 
 * Utiliza {@link QuadCurve2D} para definir la geometría de la curva, que se compone de un punto de inicio,
 * un punto de control (que define la curvatura) y un punto final.
 * 
 * La clase permite operaciones como dibujo, traslado, edición del punto de control o final,
 * y verificación de contención.
 * 
 * @author rober
 */
public class MiCurva extends MiShape{

    /**
     * La curva cuadrática representada como una {@link QuadCurve2D}.
     */
    private QuadCurve2D geomCurva;
    
    /**
     * Indica si se está manipulando el punto de control (true) o el punto final (false).
     * Utilizado en el método {@link #updateShape(Point2D)}.
     */
    public Boolean pControl;
    
    /**
     * Constructor por defecto.
     */
    public MiCurva(){}
    
    /**
     * Constructor que inicializa la curva con estilo gráfico y tres puntos: inicio, control y final.
     *
     * @param color el color del trazo.
     * @param grosor el grosor del trazo.
     * @param transparente true si debe aplicarse transparencia.
     * @param alisada true si debe aplicarse antialiasing.
     * @param p1 el punto inicial de la curva.
     * @param p2 el punto final de la curva.
     * @param pc el punto de control que define la curvatura.
     */
    public MiCurva(Color color, Integer grosor, Boolean transparente, 
                   Boolean alisada, Point2D p1, Point2D p2, Point2D pc) {
        super(color, grosor, transparente, alisada);
        this.geomCurva = new QuadCurve2D.Double(p1.getX(), p1.getY(), 
                pc.getX(), pc.getY(), p2.getX(), p2.getY());
    }
    
    /**
     * Dibuja la curva sobre el contexto gráfico especificado.
     *
     * @param g2d el contexto gráfico 2D.
     */
    @Override
    public void drawShape(Graphics2D g2d) {
        g2d.draw(geomCurva); 
    }

    /**
     * Retorna el punto inicial de la curva.
     *
     * @return el punto de inicio de la curva.
     */
    @Override
    public Point2D getLocation() {
        return this.geomCurva.getP1(); 
    }

    /**
     * Traslada la curva a una nueva posición, manteniendo la forma y distancia relativa
     * entre los puntos de control y final.
     *
     * @param pos el nuevo punto inicial.
     */
    @Override
    public void setLocation(Point2D pos) {
        double dx = pos.getX() - this.geomCurva.getX1();
        double dy = pos.getY() - this.geomCurva.getY1();
        Point2D newp2 = new Point2D.Double(this.geomCurva.getX2() + dx, this.geomCurva.getY2() + dy);
        Point2D newcp = new Point2D.Double(this.geomCurva.getCtrlX()+ dx, this.geomCurva.getCtrlY() + dy);
        this.geomCurva.setCurve(pos, newcp, newp2);
    }

    /**
     * Verifica si un punto se encuentra dentro del área de la curva.
     * 
     * @param p el punto a verificar.
     * @return true si el punto está dentro de los límites visuales de la curva.
     */
    @Override
    public boolean contains(Point2D p) {
        return this.geomCurva.contains(p); 
    }

    /**
     * Actualiza la forma de la curva según el modo de edición:
     * <ul>
     *   <li>Si {@code pControl} es false: se modifica tanto el punto de control como el punto final.</li>
     *   <li>Si {@code pControl} es true: se modifica únicamente el punto de control.</li>
     * </ul>
     *
     * @param pEnd el nuevo punto final o de control, según el modo.
     */
    @Override
    public void updateShape(Point2D pEnd) {
        if(!this.pControl) {
            this.geomCurva.setCurve(this.geomCurva.getP1(), pEnd, pEnd);
        } else {
            this.geomCurva.setCurve(this.geomCurva.getP1(), pEnd, this.geomCurva.getP2());
        }
    }
    
    /**
     * Obtiene el rectángulo delimitador que encierra completamente la curva.
     *
     * @return un {@link Rectangle2D} que representa los límites de la curva.
     */
    @Override
    protected Rectangle2D getBounds() {
        return this.geomCurva.getBounds();
    }

    /**
     * Obtiene el valor del flag {@code pControl}, que indica si se edita el punto de control.
     *
     * @return true si se edita el punto de control, false si se edita el punto final.
     */
    public Boolean getpControl() {
        return pControl;
    }

    /**
     * Establece si se debe editar el punto de control (true) o el punto final (false).
     *
     * @param pControl modo de edición de la curva.
     */
    public void setpControl(Boolean pControl) {
        this.pControl = pControl;
    }
}
