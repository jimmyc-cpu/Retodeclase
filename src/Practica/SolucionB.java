package Practica;


    import java.util.Arrays;
    import java.util.Arrays;

/**
 * Solución B: ordenar en forma ascendente y recorrer buscando vecinos iguales.
 * Si hay repetidos, quedan juntos después de ordenar.
 * Tiempo O(n log n), memoria extra O(n) por la copia (O(1) si se ordenara el original).
 */
public class SolucionB {

    private long operaciones;

    public String nombre() {
        return "B (ordenar)";
    }

    public long getOperaciones() {
        return operaciones;
    }

    public boolean hayDuplicados(int[] datos) {
        operaciones = 0;                           // reinicia el contador en cada ejecución

        // Paso 1: ordenar una copia (así el arreglo original no cambia entre pruebas)
        int[] copia = datos.clone();
        Arrays.sort(copia);                        // orden ascendente
        // Estimación del costo de ordenar: n * log2(n) comparaciones
        operaciones += (long) (copia.length * (Math.log(copia.length) / Math.log(2)));

        // Paso 2: recorrer una sola vez comparando cada elemento con el anterior
        for (int i = 1; i < copia.length; i++) {
            operaciones++;                         // una comparación entre vecinos
            if (copia[i] == copia[i - 1]) return true;
        }
        return false;
    }
}
