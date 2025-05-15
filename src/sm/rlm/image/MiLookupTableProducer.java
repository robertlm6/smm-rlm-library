/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.rlm.image;

import java.awt.image.BufferedImage;
import java.awt.image.ByteLookupTable;
import java.awt.image.LookupOp;
import java.awt.image.LookupTable;

/**
 *
 * @author rober
 */
public class MiLookupTableProducer {
    
    private MiLookupTableProducer() {}
    
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
    
    public static LookupTable createSepiaTable() {
        byte[] r = new byte[256];
        byte[] g = new byte[256];
        byte[] b = new byte[256];

        for (int i = 0; i < 256; i++) {
            r[i] = (byte) Math.min(255, (int) (i * 0.393 + i * 0.769 + i * 0.189));
            g[i] = (byte) Math.min(255, (int) (i * 0.349 + i * 0.686 + i * 0.168));
            b[i] = (byte) Math.min(255, (int) (i * 0.272 + i * 0.534 + i * 0.131));
        }

        return new ByteLookupTable(0, new byte[][]{r, g, b});
    }
}
