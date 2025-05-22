/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.rlm.image;

import java.awt.image.ByteLookupTable;
import java.awt.image.LookupTable;

/**
 * Clase auxiliar para generar distintas tablas de transformación de color.
 *
 * Esta clase proporciona métodos estáticos para crear {@code LookupTable}s
 * personalizadas que se pueden aplicar sobre imágenes mediante operaciones de
 * tipo Lookup.
 *
 * Contiene funciones como corrección ABC (curva compuesta) y transformación
 * sepia.
 *
 * @author rober
 */
public class MiLookupTableProducer {

    private MiLookupTableProducer() {
    }

    /**
     * Genera una tabla de transformación en forma de curva compuesta (ABC).
     *
     * La transformación tiene dos tramos: - De 0 a 127: interpola linealmente
     * de A a B. - De 128 a 255: interpola linealmente de B a C.
     *
     * @param a Valor inicial del primer tramo.
     * @param b Punto medio común.
     * @param c Valor final del segundo tramo.
     * @return Tabla de transformación de tipo {@code ByteLookupTable}.
     */
    public static LookupTable crearTablaABC(int a, int b, int c) {
        byte[] funcionT = new byte[256];

        for (int x = 0; x < 256; x++) {
            int y;
            if (x < 128) {
                y = (int) ((b - a) * (x / 128.0) + a);
            } else {
                y = (int) ((c - b) * ((x - 128) / 127.0) + b);
            }
            funcionT[x] = (byte) Math.max(0, Math.min(255, y));
        }

        return new ByteLookupTable(0, funcionT);
    }

    /**
     * Genera una tabla de transformación para simular el efecto sepia.
     *
     * Basado en una fórmula estándar que aplica diferentes coeficientes a los
     * canales RGB para obtener un tono cálido característico de fotografías
     * antiguas.
     *
     * @return Tabla {@code ByteLookupTable} con 3 bandas (R, G, B) modificadas.
     */
    public static LookupTable createSepiaTable() {
        byte[] r = new byte[256];
        byte[] g = new byte[256];
        byte[] b = new byte[256];

        for (int i = 0; i < 256; i++) {
            // Valores obtenidos en https://www.geeksforgeeks.org/image-processing-in-java-colored-image-to-sepia-image-conversion/
            r[i] = (byte) Math.min(255, (int) (i * 0.393 + i * 0.769 + i * 0.189));
            g[i] = (byte) Math.min(255, (int) (i * 0.349 + i * 0.686 + i * 0.168));
            b[i] = (byte) Math.min(255, (int) (i * 0.272 + i * 0.534 + i * 0.131));
        }

        return new ByteLookupTable(0, new byte[][]{r, g, b});
    }
}
