/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.rlm.image;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import sm.image.BufferedImageOpAdapter;

/**
 * Clase que implementa una operación de resaltado del color rojo.
 *
 * Esta operación analiza cada píxel de la imagen. Si la diferencia entre el
 * canal rojo y la suma de los canales verde y azul supera un umbral dado, el
 * píxel se mantiene tal cual. En caso contrario, se convierte a escala de
 * grises utilizando el promedio de sus componentes.
 *
 * Esta clase extiende {@code BufferedImageOpAdapter} para integrarse fácilmente
 * con operaciones de procesamiento de imagen.
 *
 * @author rober
 */
public class RojoOp extends BufferedImageOpAdapter {

    /**
     * Umbral de activación para decidir si un píxel se mantiene en color rojo o
     * se vuelve gris.
     */
    private int umbral;

    /**
     * Crea una nueva instancia de la operación de resaltado rojo.
     *
     * @param umbral Valor umbral (recomendado: 20) que determina si un píxel se
     * considera suficientemente rojo.
     */
    public RojoOp(int umbral) {
        this.umbral = umbral;
    }

    /**
     * Aplica la operación de resaltado de rojo sobre una imagen.
     *
     * Recorre cada píxel de la imagen fuente. Si la diferencia entre el valor
     * rojo y la suma de verde y azul supera el umbral, se deja el color
     * original. Si no, se convierte a escala de grises usando la media
     * aritmética.
     *
     * @param src Imagen fuente sobre la que se aplica el filtro.
     * @param dest Imagen de destino donde se escribe el resultado. Si es
     * {@code null}, se crea una nueva.
     * @return Imagen con el filtro aplicado.
     * @throws NullPointerException Si la imagen fuente es {@code null}.
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
        
        int[] pixelComp = new int[srcRaster.getNumBands()];
        int[] pixelCompDest = new int[srcRaster.getNumBands()];

        for (int x = 0; x < src.getWidth(); x++) {
            for (int y = 0; y < src.getHeight(); y++) {
                srcRaster.getPixel(x, y, pixelComp);
                int r = pixelComp[0];
                int g = pixelComp[1];
                int b = pixelComp[2];
                
                if ((r - g - b) > this.umbral) {
                    pixelCompDest[0] = r;
                    pixelCompDest[1] = g;
                    pixelCompDest[2] = b;
                } else {
                    int media = (r + g + b)/3;
                    pixelCompDest[0] = media;
                    pixelCompDest[1] = media;
                    pixelCompDest[2] = media;
                }
                
                destRaster.setPixel(x, y, pixelCompDest);
            }
        }
        
        return dest;
    }
}
