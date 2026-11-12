package Practica;
import java.util.Random;
import java.util.function.LongSupplier;
import java.util.function.Predicate;

/**
 * Laboratorio 02: Benchmarking de algoritmos (caso guía: buscar duplicados).
 * Genera datasets, ejecuta cada solución, cuenta operaciones, mide tiempo,
 * repite pruebas y registra la tabla n / tiempo / operaciones.
 */
public class Main {

    static final int REPETICIONES = 5;             // paso 06: repetir pruebas
    static final int LIMITE_CUADRATICO = 100_000;  // A no se ejecuta con n mayor (tardaría demasiado)
    static final Random RND = new Random(42);      // semilla fija: siempre los mismos datos

    public static void main(String[] args) {
        int[] tamanos = {1_000, 5_000, 10_000, 50_000, 1_000_000};

        SolucionA a = new SolucionA(false);
        SolucionA aParImpar = new SolucionA(true);
        SolucionB b = new SolucionB();

        // Calentamiento de la JVM para que el primer resultado no salga inflado
        int[] calentar = sinDuplicados(2_000);
        a.hayDuplicados(calentar);
        aParImpar.hayDuplicados(calentar);
        b.hayDuplicados(calentar);

        for (String tipo : new String[]{"SIN duplicados (peor caso)", "CON duplicados"}) {
            System.out.println("\n=== Dataset: " + tipo + " ===");
            System.out.printf("%-18s %10s %14s %16s%n",
                    "Algoritmo", "n", "Tiempo (ms)", "Operaciones");
            System.out.println("-".repeat(61));

            for (int n : tamanos) {
                // paso 01: generar dataset (los mismos datos para todos los algoritmos)
                int[] datos = tipo.startsWith("SIN") ? sinDuplicados(n) : conDuplicados(n);

                if (n <= LIMITE_CUADRATICO) {
                    medir(a.nombre(), datos, a::hayDuplicados, a::getOperaciones);
                    medir(aParImpar.nombre(), datos, aParImpar::hayDuplicados, aParImpar::getOperaciones);
                } else {
                    System.out.printf("%-18s %10d %14s %16s%n", a.nombre(), n, "omitido", "~n^2/2");
                    System.out.printf("%-18s %10d %14s %16s%n", aParImpar.nombre(), n, "omitido", "~n^2/4");
                }
                medir(b.nombre(), datos, b::hayDuplicados, b::getOperaciones);
                System.out.println();
            }
        }
    }

    // pasos 02-07: ejecuta, cuenta operaciones, mide tiempo, repite y registra una fila de la tabla
    static void medir(String nombre, int[] datos, Predicate<int[]> algoritmo, LongSupplier operaciones) {
        double sumaMs = 0;
        for (int r = 0; r < REPETICIONES; r++) {
            long t0 = System.nanoTime();                    // paso 05: medir tiempo
            algoritmo.test(datos);
            sumaMs += (System.nanoTime() - t0) / 1_000_000.0;
        }
        System.out.printf("%-18s %10d %14.3f %16d%n",
                nombre, datos.length, sumaMs / REPETICIONES, operaciones.getAsLong());
    }

    // Peor caso: n valores distintos mezclados (no hay salida temprana)
    static int[] sinDuplicados(int n) {
        int[] d = new int[n];
        for (int i = 0; i < n; i++) d[i] = i;
        for (int i = n - 1; i > 0; i--) {                   // mezcla Fisher-Yates
            int j = RND.nextInt(i + 1);
            int t = d[i]; d[i] = d[j]; d[j] = t;
        }
        return d;
    }

    // Caso con repetidos: valores al azar en un rango menor que n
    static int[] conDuplicados(int n) {
        int[] d = new int[n];
        for (int i = 0; i < n; i++) d[i] = RND.nextInt(Math.max(1, n / 2));
        return d;
    }
}