/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sm.rlm.eventos;

import java.util.EventListener;

/**
 *
 * @author rober
 */
public interface LienzoListener extends EventListener {
    public void shapeAdded(LienzoEvent evt);
    public void shapeSelected(LienzoEvent evt);
    public void editingModeExited(LienzoEvent evt);
}
