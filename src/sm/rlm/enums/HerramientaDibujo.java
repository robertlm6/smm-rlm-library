/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package sm.rlm.enums;

/**
 * Enumeración que representa las diferentes herramientas de dibujo disponibles
 * en el lienzo 2D.
 * 
 * Cada valor corresponde a un tipo de forma que el usuario puede crear.
 * 
 * - {@code LINE}: Herramienta para dibujar líneas.
 * - {@code RECTANGLE}: Herramienta para dibujar rectángulos.
 * - {@code ELLIPSE}: Herramienta para dibujar elipses.
 * - {@code QUADCURVE}: Herramienta para dibujar curvas cuadráticas.
 * 
 * Esta enumeración suele utilizarse para determinar qué tipo de forma
 * crear durante los eventos de interacción del usuario.
 * 
 * @author rober
 */
public enum HerramientaDibujo {
    LINE,
    RECTANGLE,
    ELLIPSE,
    QUADCURVE
}
