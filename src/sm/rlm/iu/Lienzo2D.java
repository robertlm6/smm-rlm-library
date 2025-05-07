/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package sm.rlm.iu;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import sm.rlm.enums.HerramientaDibujo;
import sm.rlm.eventos.LienzoEvent;
import sm.rlm.eventos.LienzoListener;
import sm.rlm.graficos.MiCurva;
import sm.rlm.graficos.MiElipse;
import sm.rlm.graficos.MiLinea;
import sm.rlm.graficos.MiRectangulo;
import sm.rlm.graficos.MiShape;
import sm.rlm.graficos.MiShapeRellenable;

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

    /**
     * Lista de figuras dibujadas en el lienzo.
     */
    private List<MiShape> vShape = new ArrayList();
    
    /**
     * Figura en construcción o seleccionada para manipulación.
     */
    private MiShape forma = new MiLinea();
    
    /**
     * Herramienta de dibujo activa.
     */
    private HerramientaDibujo herramienta = HerramientaDibujo.LINE;

    /**
     * Color de trazo actual.
     */
    private Color color = Color.BLACK;

    /**
     * Grosor de la línea de dibujo.
     */
    private Integer grosor = 5;

    /**
     * Indica si las figuras deben dibujarse rellenas.
     */
    private Boolean relleno = false;

    /**
     * Indica si la herramienta de movimiento está activa.
     */
    private Boolean mover = false;

    /**
     * Indica si la opción de fijar figura está activa.
     */
    private Boolean fijar = false;

    /**
     * Indica si la opción de borrar figura está activa.
     */
    private Boolean borrar = false;

    /**
     * Indica si se aplica alisado a las figuras.
     */
    private Boolean alisado = false;

    /**
     * Indica si se aplica transparencia al dibujo.
     */
    private Boolean transparente = false;

    /**
     * Número de pasos para representar una curva (si aplica).
     */
    private Integer pasosCurva = 0;

    /**
     * Punto de referencia para mover figuras.
     */
    private Point2D puntoAncla = null;

    /**
     * Imagen donde se realiza el dibujo final.
     */
    private BufferedImage img;

    /**
     * Figura actualmente seleccionada en el lienzo.
     */
    private MiShape seleccionada = null;

    /**
     * Archivo de sonido reproducido al fijar una figura.
     */
    private File sonidoFijar;

    /**
     * Archivo de sonido reproducido al eliminar una figura.
     */
    private File sonidoEliminar;
    
    /**
     * Coordenada X actual del ratón sobre el lienzo.
     */
    private int x = 0;

    /**
     * Coordenada Y actual del ratón sobre el lienzo.
     */
    private int y = 0;
    
    /**
     * Valor del componente rojo del color (0-255).
     */
    private int r = 0;

    /**
     * Valor del componente verde del color (0-255).
     */
    private int g = 0;

    /**
     * Valor del componente azul del color (0-255).
     */
    private int b = 0;
    
    /**
     * Lista de listeners registrados para recibir eventos del lienzo. Se utiliza
     * para notificar eventos como agregar figuras, seleccionar figuras o salir
     * del modo de edición.
     */
    ArrayList<LienzoListener> lienzoEventListeners = new ArrayList();
    
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
        
        if (this.img != null) {
            g2d.drawImage(img, 0, 0, this);
            
            dibujarMarco(g2d);
            aplicarClip(g2d);
        }
        
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
            if (s.contains(p)) {
                return s;
            }
        }
        
        return null;
    }
    
    /**
     * Marca como seleccionada la figura actual, deseleccionando cualquier otra
     * previamente seleccionada.
     */
    private void selectShape() {
        if (this.seleccionada != null) {
            this.seleccionada.setSelected(false);
        }
        
        this.seleccionada = this.forma;
        if (this.seleccionada != null){
            this.seleccionada.setSelected(true);
        }
        repaint();
    }
    
    /**
     * Deselecciona la figura actualmente seleccionada.
     */
    private void unselectShape() {
        if (this.seleccionada != null) {
            this.seleccionada.setSelected(false);
            this.seleccionada = null;
            repaint();
        }
    }
    
    /**
     * Agrega un {@link LienzoListener} a la lista de escucha para eventos del
     * lienzo.
     *
     * @param listener el oyente a agregar (no debe ser null)
     */
    public void addLienzoListener(LienzoListener listener) {
        if (listener != null) {
            this.lienzoEventListeners.add(listener);
        }
    }

    /**
     * Notifica a todos los oyentes que se ha agregado una figura al lienzo.
     *
     * @param evt el evento que representa la acción
     */
    private void notifyShapeAddedEvent(LienzoEvent evt) {
        if (!lienzoEventListeners.isEmpty()) {
            for (LienzoListener listener : lienzoEventListeners) {
                listener.shapeAdded(evt);
            }
        }
    }

    /**
     * Notifica a todos los oyentes que se ha seleccionado una figura en el
     * lienzo.
     *
     * @param evt el evento que representa la acción
     */
    private void notifyShapeSelectedEvent(LienzoEvent evt) {
        if (!lienzoEventListeners.isEmpty()) {
            for (LienzoListener listener : lienzoEventListeners) {
                listener.shapeSelected(evt);
            }
        }
    }

    /**
     * Notifica a todos los oyentes que se ha salido del modo de edición del
     * lienzo.
     *
     * @param evt el evento que representa la acción
     */
    private void notifyEditingModeExited(LienzoEvent evt) {
        if (!lienzoEventListeners.isEmpty()) {
            for (LienzoListener listener : lienzoEventListeners) {
                listener.editingModeExited(evt);
            }
        }
    }
    
    /**
     * Dibuja una figura sobre el buffer de imagen y la elimina de la lista de
     * figuras activas. Reproduce un sonido de confirmación si está disponible.
     *
     * @param figura La figura a volcar en la imagen final.
     */
    private void volcarFigura(MiShape figura) {
        Graphics2D g2d = (Graphics2D) this.getImg().getGraphics();

        if (g2d != null) {
            figura.draw(g2d);
            this.play(sonidoFijar);
            this.vShape.remove(figura);
            repaint();
        }
    }

    /**
     * Elimina una figura de la lista y reproduce un sonido de eliminación si
     * está disponible.
     *
     * @param figura La figura a eliminar del lienzo.
     */
    private void borrarFigura(MiShape figura) {
        this.vShape.remove(figura);
        this.play(sonidoEliminar);
        repaint();
    }
    
    /**
     * Establece el área de recorte del contexto gráfico al tamaño de la imagen.
     *
     * @param g2d El contexto gráfico donde se aplicará el recorte.
     */
    private void aplicarClip(Graphics2D g2d) {
        Shape clipArea = new Rectangle(0, 0, img.getWidth(), img.getHeight());
        g2d.clip(clipArea);
    }

    /**
     * Dibuja un marco punteado alrededor de la imagen.
     *
     * @param g2d El contexto gráfico donde se dibujará el marco.
     */
    private void dibujarMarco(Graphics2D g2d) {
        float[] patronDiscontinuidad = {15.0f, 15.0f};
        g2d.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, patronDiscontinuidad, 0.0f));
        g2d.drawRect(0, 0, img.getWidth(), img.getHeight());
    }
    
    /**
     * Reproduce un archivo de sonido utilizando el sistema de audio.
     *
     * @param f Archivo de sonido a reproducir.
     */
    private void play(File f) {
        try {
            Clip sound = AudioSystem.getClip();
            sound.open(AudioSystem.getAudioInputStream(f));
            sound.start();
        } catch (Exception ex) {
            System.err.println(ex);
        }
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
     * Devuelve una imagen del contenido actual del lienzo, incluyendo la imagen
     * base y todas las figuras dibujadas en orden.
     *
     * @return una nueva imagen con todo el contenido renderizado del lienzo.
     */
    public BufferedImage getPaintedImage() {
        BufferedImage imgout = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

        Graphics2D g2dImagen = imgout.createGraphics();

        if (img != null) {
            g2dImagen.drawImage(img, 0, 0, this);
        }
        for (MiShape s : vShape) {
            s.draw(g2dImagen);
        }

        return imgout;
    }

    /**
     * Obtiene el archivo de sonido utilizado al fijar una figura al lienzo.
     *
     * @return el archivo de sonido para la acción de fijado.
     */
    public File getSonidoFijar() {
        return sonidoFijar;
    }

    /**
     * Establece el archivo de sonido que se reproducirá al fijar una figura.
     *
     * @param sonidoFijar archivo de sonido para la acción de fijado.
     */
    public void setSonidoFijar(File sonidoFijar) {
        this.sonidoFijar = sonidoFijar;
    }

    /**
     * Obtiene el archivo de sonido utilizado al eliminar una figura del lienzo.
     *
     * @return el archivo de sonido para la acción de eliminación.
     */
    public File getSonidoEliminar() {
        return sonidoEliminar;
    }

    /**
     * Establece el archivo de sonido que se reproducirá al eliminar una figura.
     *
     * @param sonidoEliminar archivo de sonido para la acción de eliminación.
     */
    public void setSonidoEliminar(File sonidoEliminar) {
        this.sonidoEliminar = sonidoEliminar;
    }

    /**
     * Obtiene la imagen base sobre la que se dibujan las figuras.
     *
     * @return la imagen actualmente asociada al lienzo.
     */
    public BufferedImage getImg() {
        return img;
    }

    /**
     * Establece la imagen base del lienzo. También ajusta el tamaño preferido
     * del componente.
     *
     * @param img la imagen a establecer como fondo del lienzo.
     */
    public void setImg(BufferedImage img) {
        this.img = img;
        if (img != null) {
            setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
        }
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
        this.notifyEditingModeExited(new LienzoEvent(this, null));
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
        
        if (this.mover && this.seleccionada != null) {
            this.seleccionada.setColor(color);
            repaint();
        }
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
        
        if (this.mover && this.seleccionada != null) {
            ((MiShapeRellenable) this.seleccionada).setRellena(relleno);
            repaint();
        }
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
        if (!mover) {
            this.unselectShape();
        }
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
        
        if (this.mover && this.seleccionada != null) {
            this.seleccionada.setRender(alisado);
            repaint();
        }
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
        
        if (this.mover && this.seleccionada != null) {
            this.seleccionada.setComp(transparente);
            repaint();
        }
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
        
        if (this.mover && this.seleccionada != null) {
            this.seleccionada.setStroke(grosor);
            repaint();
        }
    }

    /**
     * Obtiene el valor actual de la opción de fijar figuras.
     *
     * @return true si la opción de fijar está activada, false en caso
     * contrario.
     */
    public Boolean getFijar() {
        return fijar;
    }

    /**
     * Establece si las figuras deben fijarse al lienzo.
     *
     * @param fijar true para activar fijado, false para desactivarlo.
     */
    public void setFijar(Boolean fijar) {
        this.fijar = fijar;
        this.notifyEditingModeExited(new LienzoEvent(this, null));
    }

    /**
     * Obtiene el estado de la opción de borrar figuras.
     *
     * @return true si el modo borrar está activado, false en caso contrario.
     */
    public Boolean getBorrar() {
        return borrar;
    }

    /**
     * Establece si se activa el modo de borrado de figuras.
     *
     * @param borrar true para activar borrado, false para desactivarlo.
     */
    public void setBorrar(Boolean borrar) {
        this.borrar = borrar;
        this.notifyEditingModeExited(new LienzoEvent(this, null));
    }

    /**
     * Devuelve la coordenada X actual del ratón sobre el lienzo.
     *
     * @return la posición horizontal del ratón.
     */
    public int getXCoord() {
        return x;
    }

    /**
     * Establece la coordenada X del ratón sobre el lienzo.
     *
     * @param x la nueva posición horizontal del ratón.
     */
    public void setXCoord(int x) {
        this.x = x;
    }

    /**
     * Devuelve la coordenada Y actual del ratón sobre el lienzo.
     *
     * @return la posición vertical del ratón.
     */
    public int getYCoord() {
        return y;
    }

    /**
     * Establece la coordenada Y del ratón sobre el lienzo.
     *
     * @param y la nueva posición vertical del ratón.
     */
    public void setYCoord(int y) {
        this.y = y;
    }

    /**
     * Obtiene el valor del componente rojo.
     *
     * @return valor del rojo (0-255)
     */
    public int getR() {
        return r;
    }

    /**
     * Establece el valor del componente rojo.
     *
     * @param r valor del rojo (0-255)
     */
    public void setR(int r) {
        this.r = r;
    }

    /**
     * Obtiene el valor del componente verde.
     *
     * @return valor del verde (0-255)
     */
    public int getG() {
        return g;
    }

    /**
     * Establece el valor del componente verde.
     *
     * @param g valor del verde (0-255)
     */
    public void setG(int g) {
        this.g = g;
    }

    /**
     * Obtiene el valor del componente azul.
     *
     * @return valor del azul (0-255)
     */
    public int getB() {
        return b;
    }

    /**
     * Establece el valor del componente azul.
     *
     * @param b valor del azul (0-255)
     */
    public void setB(int b) {
        this.b = b;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
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
                this.selectShape();
                this.puntoAncla = evt.getPoint();
                this.notifyShapeSelectedEvent(new LienzoEvent(this, this.forma));
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
                this.notifyShapeAddedEvent(new LienzoEvent(this, forma));
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

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        MiShape figura = this.getSelectedShape(evt.getPoint());

        if (fijar && figura != null) {
            this.volcarFigura(figura);
        }

        if (borrar && figura != null) {
            this.borrarFigura(figura);
        }

        if (this.img != null) {
            this.x = evt.getPoint().x;
            this.y = evt.getPoint().y;

            if (this.x >= 0 && this.x < this.img.getWidth() 
                && this.y > 0 && this.y < this.img.getHeight()) {

                int rgb = this.img.getRGB(this.x, this.y);
                Color color = new Color(rgb);
                this.r = color.getRed();
                this.g = color.getGreen();
                this.b = color.getBlue();
            }
        }
    }//GEN-LAST:event_formMouseMoved


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
