public class Pruebas {

    public static void main(String[] args) {
        pruebaSecuenciaOficial();
        System.out.println();
        pruebasIndividuales();
    }

    // Reproduce la secuencia de la seccion 5.3 del PDF (sin imprimirPorNiveles,
    // asi que aqui solo comparamos con buscar() y con el toString() que ya tienen).
    static void pruebaSecuenciaOficial() {
        System.out.println("=== Secuencia oficial (seccion 5.3) ===");
        ArbolB arbol = new ArbolB();

        int[] insertar = {20,40,10,30,50,60,70,5,15,25,35,45};
        for (int x : insertar) arbol.insertar(x);

        System.out.println("Arbol tras insertar todo (toString):");
        System.out.println(arbol);

        check("buscar(35) tras insertar", arbol.buscar(35), true);
        check("buscar(99) tras insertar", arbol.buscar(99), false);

        arbol.eliminar(25);
        arbol.eliminar(10);
        arbol.eliminar(70);
        arbol.eliminar(5);

        System.out.println();
        System.out.println("Arbol tras eliminar 25,10,70,5 (toString):");
        System.out.println(arbol);

        check("buscar(25) tras eliminar", arbol.buscar(25), false);
        check("buscar(35) tras eliminar", arbol.buscar(35), true);
    }

    // Pruebas 1 a 9 del PDF, una por una, para poder senalar exactamente
    // cual falla si algo sale mal.
    static void pruebasIndividuales() {
        System.out.println("=== Pruebas 1-9 (seccion 5.1) ===");

        ArbolB a1 = new ArbolB();
        check("Prueba 1: buscar(10) en arbol vacio", a1.buscar(10), false);

        ArbolB a2 = new ArbolB();
        a2.insertar(20); a2.insertar(40); a2.insertar(10);
        System.out.println("Prueba 2 (esperado raiz [10,20,40]): " + a2);

        a2.insertar(30);
        System.out.println("Prueba 3 (esperado raiz [30], hijos [10,20] y [40]): " + a2);

        check("Prueba 4: buscar(20)", a2.buscar(20), true);
        check("Prueba 4: buscar(30)", a2.buscar(30), true);
        check("Prueba 4: buscar(99)", a2.buscar(99), false);

        ArbolB a5 = new ArbolB();
        int[] seq5 = {20,40,10,30,50,60,70,5,15,25,35,45};
        for (int x : seq5) a5.insertar(x);
        System.out.println("Prueba 5: " + a5);

        a5.insertar(35); // Prueba 6: no debe duplicar
        System.out.println("Prueba 6 (no debe cambiar): " + a5);

        a5.eliminar(25); // Prueba 7
        System.out.println("Prueba 7 (hoja [20,25] -> [20]): " + a5);

        a5.eliminar(10);
        a5.eliminar(70); // Prueba 8
        System.out.println("Prueba 8: " + a5);

        a5.eliminar(5); // Prueba 9
        System.out.println("Prueba 9 (reduccion de altura): " + a5);
    }

    static void check(String nombre, boolean obtenido, boolean esperado) {
        String resultado = (obtenido == esperado) ? "OK" : "FALLO";
        System.out.println("[" + resultado + "] " + nombre + " -> " + obtenido + " (esperado " + esperado + ")");
    }
}
