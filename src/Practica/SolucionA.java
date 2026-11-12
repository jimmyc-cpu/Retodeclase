package Practica;
/**
 * Solución A (tu idea): separar pares e impares y luego comparar
 * cada elemento contra todos los demás, dentro de cada grupo.
 * Con separar = false funciona como la Estrategia A "pura" (sirve de base para comparar).
 */
public class SolucionA {

    private final boolean separar;
    private long operaciones;

    public SolucionA(boolean separar) {
        this.separar = separar;
    }

    public String nombre() {
        return separar ? "A + par/impar" : "A (doble ciclo)";
    }

    public long getOperaciones() {
        return operaciones;
    }

    public boolean hayDuplicados(int[] datos) {
        operaciones = 0;                           // reinicia el contador en cada ejecución
        if (!separar) {
            return compararTodos(datos, datos.length);
        }

        // Paso 1: separar pares e impares (n operaciones)
        int[] pares = new int[datos.length];
        int[] impares = new int[datos.length];
        int np = 0, ni = 0;
        for (int x : datos) {
            operaciones++;
            if (x % 2 == 0) pares[np++] = x;
            else            impares[ni++] = x;
        }

        // Paso 2: buscar repetidos dentro de cada grupo
        return compararTodos(pares, np) || compararTodos(impares, ni);
    }

    // Compara cada elemento contra todos los siguientes: O(n²), memoria extra O(1)
    private boolean compararTodos(int[] a, int tamano) {
        for (int i = 0; i < tamano; i++) {
            for (int j = i + 1; j < tamano; j++) {
                operaciones++;                     // una comparación
                if (a[i] == a[j]) return true;
            }
        }
        return false;
    }
}