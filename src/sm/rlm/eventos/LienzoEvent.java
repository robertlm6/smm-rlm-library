/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.rlm.eventos;

import java.util.EventObject;
import sm.rlm.graficos.MiShape;

/**
 * Representa un evento personalizado asociado al lienzo.
 *
 * Esta clase extiende {@link EventObject} y encapsula información relacionada
 * con una forma ({@link MiShape}) involucrada en el evento. Se utiliza para
 * notificar acciones como la adición, selección o edición de una forma en el
 * lienzo.
 *
 * @author rober
 */
public class LienzoEvent extends EventObject {

    /**
     * La forma asociada con el evento.
     */
    private MiShape forma;

    /**
     * Crea un nuevo evento de lienzo.
     *
     * @param source el objeto que originó el evento.
     * @param forma la forma implicada en el evento.
     */
    public LienzoEvent(Object source, MiShape forma) {
        super(source);
        this.forma = forma;
    }

    /**
     * Obtiene la forma asociada con este evento.
     *
     * @return la instancia de {@link MiShape} involucrada en el evento.
     */
    public MiShape getForma() {
        return forma;
    }

}
