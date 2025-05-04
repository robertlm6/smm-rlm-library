/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.rlm.eventos;

import java.util.EventObject;
import sm.rlm.graficos.MiShape;

/**
 *
 * @author rober
 */
public class LienzoEvent extends EventObject {
    
    private MiShape forma;
    
    public LienzoEvent(Object source, MiShape forma) {
        super(source);
        this.forma = forma;
    }

    public MiShape getForma() {
        return forma;
    }
    
}
