/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.rlm.image;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import sm.image.BufferedImageOpAdapter;

/**
 * Filtro de posterización que reduce la cantidad de niveles de color por canal.
 *
 * Esta operación transforma la imagen original a una versión con menos niveles
 * de color, agrupando los valores de cada canal (R, G, B) en bloques. Produce
 * un efecto visual de tipo "paleta limitada".
 *
 * @author rober
 */
public class PosterizarOp extends BufferedImageOpAdapter {

    /**
     * Número de niveles a los que se reduce cada canal de color (debe ser > 1).
     */
    private int niveles;

    /**
     * Constructor del filtro Posterizar.
     *
     * @param niveles Número de niveles de color a aplicar (por canal). Valores
     * menores a 256 provocan agrupaciones y pérdida de detalle.
     */
    public PosterizarOp(int niveles) {
        this.niveles = niveles;
    }

    /**
     * Aplica el filtro de posterización sobre una imagen.
     *
     * Reduce el número de niveles posibles para cada canal de color RGB,
     * agrupando los valores de color en bandas equidistantes.
     *
     * Por ejemplo, si el número de niveles es 4, cada componente de color
     * (0–255) se reduce a uno de los valores: 0, 64, 128, 192.
     *
     * @param src Imagen fuente sobre la que se aplica el filtro.
     * @param dest Imagen de destino. Si es {@code null}, se crea una nueva
     * compatible.
     * @return Imagen resultante con los niveles de color reducidos.
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

        int sample;

        float k = 256.0f / this.niveles;

        for (int x = 0; x < src.getWidth(); x++) {
            for (int y = 0; y < src.getHeight(); y++) {
                for (int band = 0; band < srcRaster.getNumBands(); band++) {
                    sample = srcRaster.getSample(x, y, band);

                    sample = (int) (k * (int) (sample / k));

                    destRaster.setSample(x, y, band, sample);
                }
            }
        }

        return dest;
    }
}
