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
 * Filtro de modificación de tono en píxeles similares a un color base.
 *
 * Esta operación compara el tono (H de HSB) de cada píxel con el tono de un
 * color de referencia. Si la diferencia de tono es menor que un umbral
 * definido, se aplica un desplazamiento circular sobre el tono manteniendo la
 * saturación y el brillo originales.
 *
 * @author rober
 */
public class ModificarTonoOp extends BufferedImageOpAdapter {

    /**
     * Color base usado como referencia para comparar tonos.
     */
    private Color color;

    /**
     * Umbral de diferencia de tono para activar el desplazamiento.
     */
    private int umbral;

    /**
     * Desplazamiento circular que se aplica al tono (en grados).
     */
    private int desplazamientoTono;

    /**
     * Constructor de la operación de modificación de tono.
     *
     * @param color Color base de referencia (el tono del cual se comparará con
     * los píxeles).
     * @param umbral Umbral de similitud tonal (en grados, rango sugerido
     * 0–180).
     * @param desplazamientoTono Ángulo en grados para modificar el tono de los
     * píxeles similares.
     */
    public ModificarTonoOp(Color color, int umbral, int desplazamientoTono) {
        this.color = color;
        this.umbral = umbral;
        this.desplazamientoTono = desplazamientoTono;
    }

    /**
     * Aplica el filtro de modificación de tono a una imagen.
     *
     * Compara el tono (componente H de HSB) de cada píxel con el tono de un
     * color de referencia. Si la diferencia es menor o igual al umbral
     * especificado, se modifica el tono desplazándolo en el círculo HSB por el
     * ángulo indicado, manteniendo la saturación y el brillo originales.
     *
     * @param src Imagen fuente sobre la que se aplica el filtro.
     * @param dest Imagen de destino. Si es {@code null}, se crea una nueva.
     * @return Imagen resultante con los tonos modificados en los píxeles
     * seleccionados.
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

        float[] hsb = new float[3];
        float[] hsbSelectedColor = Color.RGBtoHSB(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), null);
        float hSelectedColor = hsbSelectedColor[0] * 360;

        for (int x = 0; x < src.getWidth(); x++) {
            for (int y = 0; y < src.getHeight(); y++) {
                int[] pixel = new int[3];
                srcRaster.getPixel(x, y, pixel);

                Color c = new Color(pixel[0], pixel[1], pixel[2]);
                Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), hsb);
                float h = hsb[0] * 360.0f;
                float s = hsb[1];
                float b = hsb[2];

                float distancia = Math.abs(h - hSelectedColor);
                if (distancia > 180) {
                    distancia = 360 - distancia;
                }

                if (distancia <= this.umbral) {
                    h = (h + this.desplazamientoTono) % 360;
                }

                int rgb = Color.HSBtoRGB(h / 360.0f, s, b);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int bVal = rgb & 0xFF;

                destRaster.setPixel(x, y, new int[]{r, g, bVal});
            }
        }

        return dest;
    }

}
