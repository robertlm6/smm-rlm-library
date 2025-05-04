/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.rlm.image;

import java.awt.image.Kernel;

/**
 * Clase utilitaria para generar máscaras de convolución (Kernel) personalizadas,
 * tanto predefinidas como generadas dinámicamente, principalmente para filtros
 * de tipo media.
 * 
 * Esta clase está pensada como complemento a KernelProducer de sm.image, para 
 * incluir máscaras personalizadas no contempladas en dicho paquete.
 * 
 * @author rober
 */
public class MiKernelProducer {
    
    /** Máscara predefinida de emborronamiento tipo media 5x5 */
    public static final int TYPE_MEDIA_5x5 = 0;
    
    /** Máscara predefinida de emborronamiento tipo media 7x7 */
    public static final int TYPE_MEDIA_7x7 = 1;
    
    /**
     * Crea un Kernel basado en el tipo solicitado.
     * @param type Tipo de kernel, definido como constante (por ejemplo TYPE_MEDIA_5x5).
     * @return Objeto Kernel correspondiente al tipo solicitado.
     * @throws IllegalArgumentException si el tipo no está soportado.
     */
    public static Kernel createKernel(int type) {
        switch (type) {
            case TYPE_MEDIA_5x5:
                return createKernelMedia(5);
            case TYPE_MEDIA_7x7:
                return createKernelMedia(7);
            default:
                throw new IllegalArgumentException("Kernel desconocido");
        }
    }
    
    /**
     * Crea un Kernel para un filtro media de tamaño personalizado.
     * El kernel es una matriz cuadrada con todos sus valores iguales, de forma
     * que suma 1.0 (promedio).
     * 
     * @param size Tamaño del kernel.
     * @return Objeto Kernel para un filtro media del tamaño solicitado.
     * @throws IllegalArgumentException si el tamaño no es válido.
     */
    public static Kernel createKernelMedia(int size) {
        int n = size * size;
        float valor = 1.0f / n;
        float filtroMedia[] = new float[n];
        
        for (int i = 0; i < n; i++) {
            filtroMedia[i] = valor;
        }
        
        return new Kernel(size, size, filtroMedia);
    }
    
    /**
     * Crea un Kernel para perfilado (realce) según un parámetro a.
     *
     * @param a Parámetro de perfilado.
     * @return Kernel 3x3 con la máscara de realce calculada.
     */
    public static Kernel createKernelPerfilado(float a) {
        float filtroPerfilado[] = {0.0f, -a, 0.0f, -a, 4.0f*a + 1.0f, -a, 0.0f, -a, 0.0f};

        return new Kernel(3, 3, filtroPerfilado);
    }
}
