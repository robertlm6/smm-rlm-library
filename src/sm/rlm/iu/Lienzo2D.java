/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package sm.rlm.iu;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import sm.rlm.enums.HerramientaDibujo;
import sm.rlm.graficos.MiCurva;
import sm.rlm.graficos.MiElipse;
import sm.rlm.graficos.MiLinea;
import sm.rlm.graficos.MiRectangulo;
import sm.rlm.graficos.MiShape;

/**
 * La clase Lienzo2D representa un componente de dibujo interactivo que permite
 * al usuario crear, mover y manipular formas personalizadas en un lienzo.
 * Soporta características como color, relleno, grosor de línea, transparencia y
 * alisado.
 *
 * Las figuras dibujadas se almacenan en una lista y se renderizan en orden. Se
 * puede seleccionar una figura y moverla usando un punto de ancla.
 *
 * @author rober
 */
public class Lienzo2D extends javax.swing.JPanel {
    
    private List<MiShape> vShape = new ArrayList();
    private MiShape forma = new MiLinea();
    private HerramientaDibujo herramienta = HerramientaDibujo.LINE;
    private Color color = Color.BLACK;
    private Integer grosor = 0;
    private Boolean relleno = false;
    private Boolean mover = false;
    private Boolean alisado = false;
    private Boolean transparente = false;
    
    private Integer pasosCurva = 0;
    private Point2D puntoAncla = null;
    /**
     * Creates new form Lienzo2D
     */
    public Lienzo2D() {
        initComponents();
    }
    
    /**
     * Pinta todos los elementos de la lista de figuras en el lienzo.
     *
     * @param g el contexto gráfico proporcionado por el sistema para dibujar.
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        
        for(MiShape s: vShape) {
            s.draw(g2d);
        }
    }
    
    /**
     * Retorna la figura que contiene el punto dado, buscando desde la figura
     * superior.
     *
     * @param p el punto a verificar dentro de las figuras.
     * @return la figura que contiene el punto, o null si ninguna lo contiene.
     */
    private MiShape getSelectedShape(Point2D p) {
        List<MiShape> reversedList = this.vShape.reversed();
        for (MiShape s: reversedList) {
            if (s.contains(p)) return s;
        }
        
        return null;
    }
    
    /**
     * Mueve la figura actualmente seleccionada de acuerdo al desplazamiento
     * calculado entre el nuevo punto y el punto ancla.
     *
     * @param pEvt el nuevo punto del evento de movimiento.
     */
    private void moverFiguraConAncla(Point2D pEvt) {
        double dx = pEvt.getX() - this.puntoAncla.getX();
        double dy = pEvt.getY() - this.puntoAncla.getY();

        Point2D posActual = this.forma.getLocation();

        Point2D nuevaPos = new Point2D.Double(posActual.getX() + dx, posActual.getY() + dy);

        this.forma.setLocation(nuevaPos);

        this.puntoAncla = pEvt;
    }

    /**
     * Obtiene la herramienta de dibujo actual.
     *
     * @return la herramienta de dibujo seleccionada.
     */
    public HerramientaDibujo getHerramienta() {
        return herramienta;
    }

    /**
     * Establece la herramienta de dibujo.
     *
     * @param herramienta la herramienta de dibujo a establecer.
     */
    public void setHerramienta(HerramientaDibujo herramienta) {
        this.herramienta = herramienta;
    }

    /**
     * Obtiene el color actual de dibujo.
     *
     * @return el color seleccionado.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Establece el color de dibujo.
     *
     * @param color el nuevo color a utilizar.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Indica si las figuras se deben rellenar.
     *
     * @return true si se deben rellenar, false en caso contrario.
     */
    public Boolean getRelleno() {
        return relleno;
    }

    /**
     * Establece si las figuras deben ser rellenadas.
     *
     * @param relleno true para rellenar, false para contorno solamente.
     */
    public void setRelleno(Boolean relleno) {
        this.relleno = relleno;
    }

    /**
     * Indica si la funcionalidad de mover figuras está activada.
     *
     * @return true si se puede mover, false en caso contrario.
     */
    public Boolean getMover() {
        return mover;
    }

    /**
     * Establece si las figuras pueden ser movidas.
     *
     * @param mover true para permitir movimiento, false para desactivarlo.
     */
    public void setMover(Boolean mover) {
        this.mover = mover;
    }

    /**
     * Indica si se debe aplicar alisado al dibujar las figuras.
     *
     * @return true si se debe alisar, false en caso contrario.
     */
    public Boolean getAlisado() {
        return alisado;
    }

    /**
     * Establece si se debe aplicar alisado en el renderizado de figuras.
     *
     * @param alisado true para activar alisado, false para desactivarlo.
     */
    public void setAlisado(Boolean alisado) {
        this.alisado = alisado;
    }

    /**
     * Indica si el dibujo es transparente.
     *
     * @return true si es transparente, false si es opaco.
     */
    public Boolean getTransparente() {
        return transparente;
    }

    /**
     * Establece la transparencia del dibujo.
     *
     * @param transparente true para hacer transparente, false para opaco.
     */
    public void setTransparente(Boolean transparente) {
        this.transparente = transparente;
    }

    /**
     * Obtiene el grosor de trazo actual.
     *
     * @return el grosor del trazo.
     */
    public Integer getGrosor() {
        return grosor;
    }

    /**
     * Establece el grosor del trazo para el dibujo.
     *
     * @param grosor el valor de grosor a establecer.
     */
    public void setGrosor(Integer grosor) {
        this.grosor = grosor;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        if (this.mover) {
            this.forma = this.getSelectedShape(evt.getPoint());
            if(this.forma != null) {
                this.puntoAncla = evt.getPoint();
            }
            this.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        } else {
            switch(herramienta){
            case HerramientaDibujo.LINE:
                this.forma = new MiLinea(this.color, this.grosor, 
                                         this.transparente, this.alisado, 
                                         evt.getPoint(), evt.getPoint());
                this.pasosCurva = 0;
                break;
            case HerramientaDibujo.RECTANGLE:
                this.forma = new MiRectangulo(this.color, this.grosor,
                                         this.transparente, this.alisado, 
                                         this.relleno, evt.getPoint(), 
                                         evt.getPoint());
                this.pasosCurva = 0;
                break;
            case HerramientaDibujo.ELLIPSE:
                this.forma = new MiElipse(this.color, this.grosor,
                                         this.transparente, this.alisado, 
                                         this.relleno, evt.getPoint(), 
                                         evt.getPoint());
                this.pasosCurva = 0;
                break;
            case HerramientaDibujo.QUADCURVE:
                if (this.pasosCurva%2 == 0) {
                    this.forma = new MiCurva(this.color, this.grosor, 
                                         this.transparente, this.alisado, 
                                         evt.getPoint(), evt.getPoint(), 
                                         evt.getPoint());
                    ((MiCurva) forma).setpControl(false);
                } else {
                    this.forma = this.vShape.getLast();
                    ((MiCurva) forma).setpControl(true);
                }
                this.pasosCurva++;
                break;
            }
            if (!((this.forma instanceof MiCurva) && (this.pasosCurva%2 == 0))) {   
                this.vShape.add(forma);
            }
        }
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        if (this.mover) {
            if (this.forma != null && this.puntoAncla != null) {
                this.moverFiguraConAncla(evt.getPoint());
            }
        } else {
            this.forma.updateShape(evt.getPoint());
        }
        this.repaint();
    }//GEN-LAST:event_formMouseDragged

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        if (this.mover) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
    }//GEN-LAST:event_formMouseReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
