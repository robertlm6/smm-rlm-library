/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sm.rlm.eventos;

import java.util.EventListener;

/**
 * Interfaz que define los métodos para escuchar eventos del lienzo.
 *
 * Las clases que implementen esta interfaz pueden responder a eventos como la
 * adición de formas, la selección de una forma o la salida del modo de edición.
 *
 * Es utilizada en conjunto con {@link LienzoEvent} y puede ser registrada
 * mediante métodos como {@code addLienzoListener()}.
 *
 * @author rober
 */
public interface LienzoListener extends EventListener {

    /**
     * Se invoca cuando se ha añadido una nueva forma al lienzo.
     *
     * @param evt el evento que contiene información sobre la forma añadida.
     */
    public void shapeAdded(LienzoEvent evt);

    /**
     * Se invoca cuando una forma ha sido seleccionada en el lienzo.
     *
     * @param evt el evento que contiene información sobre la forma
     * seleccionada.
     */
    public void shapeSelected(LienzoEvent evt);

    /**
     * Se invoca cuando se ha salido del modo de edición del lienzo.
     *
     * @param evt el evento asociado a la finalización del modo de edición.
     */
    public void editingModeExited(LienzoEvent evt);
}
