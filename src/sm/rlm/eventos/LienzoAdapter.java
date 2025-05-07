/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.rlm.eventos;

/**
 * Adaptador base para la interfaz {@link LienzoListener}.
 *
 * Esta clase proporciona implementaciones vacías de todos los métodos de la
 * interfaz. Puede ser extendida por clases que solo necesiten implementar
 * algunos de los métodos, sin tener que definir todos.
 *
 * @author rober
 */
public class LienzoAdapter implements LienzoListener {

    /**
     * Método invocado cuando se ha añadido una nueva forma al lienzo.
     *
     * @param evt el evento que contiene información sobre la forma añadida.
     */
    @Override
    public void shapeAdded(LienzoEvent evt) {
    }

    /**
     * Método invocado cuando una forma ha sido seleccionada en el lienzo.
     *
     * @param evt el evento que contiene información sobre la selección.
     */
    @Override
    public void shapeSelected(LienzoEvent evt) {
    }

    /**
     * Método invocado cuando se ha salido del modo de edición en el lienzo.
     *
     * @param evt el evento asociado al fin del modo de edición.
     */
    @Override
    public void editingModeExited(LienzoEvent evt) {
    }

}
