/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.rlm.image;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import sm.image.BufferedImageOpAdapter;

/**
 * Filtro de estilo Pop Art para imágenes.
 *
 * Esta operación convierte cada píxel de una imagen en uno de dos colores
 * planos (claro u oscuro), en función de su intensidad luminosa comparada con
 * un umbral.
 *
 * Hereda de {@code BufferedImageOpAdapter} para facilitar la integración como
 * filtro gráfico.
 *
 * @author rober
 */
public class PopArtOp extends BufferedImageOpAdapter {

    /**
     * Umbral de intensidad a partir del cual se aplica el color claro.
     */
    private int umbral;

    /**
     * Color que se aplica a los píxeles con intensidad mayor o igual al umbral.
     */
    private Color claro;

    /**
     * Color que se aplica a los píxeles con intensidad menor al umbral.
     */
    private Color oscuro;

    /**
     * Constructor de la operación Pop Art.
     *
     * @param umbral Umbral de intensidad [0–255] para dividir los píxeles entre
     * claro y oscuro.
     * @param claro Color a asignar a los píxeles claros (intensidad ≥ umbral).
     * @param oscuro Color a asignar a los píxeles oscuros (intensidad).
     */
    public PopArtOp(int umbral, Color claro, Color oscuro) {
        this.umbral = umbral;
        this.claro = claro;
        this.oscuro = oscuro;
    }

    /**
     * Aplica el filtro Pop Art sobre una imagen de entrada.
     *
     * Calcula la intensidad media de cada píxel (promedio de R, G y B) y aplica
     * uno de los dos colores definidos según si la intensidad supera el umbral.
     *
     * @param src Imagen fuente sobre la que se aplica el filtro.
     * @param dest Imagen de destino donde se guarda el resultado. Si es
     * {@code null}, se crea una nueva.
     * @return Imagen resultante con el efecto Pop Art aplicado.
     * @throws NullPointerException si la imagen fuente es {@code null}.
     */
    @Override
    public BufferedImage filter(BufferedImage src, BufferedImage dest) {
        if (src == null) {
            throw new NullPointerException("src image is null");
        }
        
        if (dest == null) {
            dest = createCompatibleDestImage(src, null);
        }

        WritableRaster srcRaster = src.getRaster();
        WritableRaster destRaster = dest.getRaster();

        int[] pixel = new int[3];

        for (int x = 0; x < src.getWidth(); x++) {
            for (int y = 0; y < src.getHeight(); y++) {
                srcRaster.getPixel(x, y, pixel);
                int r = pixel[0];
                int g = pixel[1];
                int b = pixel[2];

                int intensidad = (r + g + b) / 3;

                Color nuevoColor = (intensidad >= this.umbral) ? this.claro : this.oscuro;
                destRaster.setPixel(x, y, new int[] {
                    nuevoColor.getRed(), nuevoColor.getGreen(), nuevoColor.getBlue()
                });
            }
        }

        return dest;
    }
    
}
