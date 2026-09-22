
public class ArbolB{
    private Nodo raiz;

    public ArbolB(){
        this.raiz = new Nodo();
    }

    public boolean buscar(int clave){
        return buscarAux(this.raiz, clave);
    }

    public boolean buscarAux(Nodo nodo, int clave){
        if(nodo == null) return false;

        int[] clavesActuales = nodo.getClaves();

        int indice = 0;
        while(indice < nodo.getContador() && clave > clavesActuales[indice]){
            indice++;
        }
        /* cuando el while termina tenemos dos casos:
        1.recorrimos las claves actuales y la clave buscada no fue encontrada;
        2. la clave buscada es menor o igual que la última clave actual comparada. */

        /* si la clave es igual a la última clave actual comparada */
        if(indice < nodo.getContador() && clave == clavesActuales[indice]) return true;
        /* si no era igual quiere decir que era menor, pero si es hoja ya no hay hijos donde buscar */
        if(nodo.getEsHoja()) return false;
        /* si no es hoja, continuamos buscando en el intervalo correspondiente */
        return buscarAux(nodo.getHijos()[indice], clave);
    }

    public void insertar(int clave){
        if(this.buscar(clave)){
            return;
        }
        insertarAux(this.raiz, clave);
    }

    public void insertarAux(Nodo nodo, int clave){

        if(this.buscar(clave)){
            return;
        }

        if(nodo.getEsHoja()){
            if(nodo.getContador() < 3){
                int indice = nodo.getContador();
                nodo.insertarClave(indice, clave);
                nodo.incrementarContador();
                nodo.ordenarClaves();
                return;
            } 

            if(nodo.getContador() == 3){
                nodo.insertarClave(3, clave);
                nodo.incrementarContador();
                nodo.ordenarClaves();
                split(nodo);
                return;
            }
        }

        int indiceHijo = buscarIntervalo(nodo, clave);
        Nodo hijo = nodo.getHijos()[indiceHijo]; 

        if(hijo != null){
            insertarAux(hijo, clave);
        }
    }

    public void split(Nodo nodo){
        //agarramos nuestro "pivote" y lo juntamos ocn el resto del arbol
        int i = nodo.getClaves()[2];
        Nodo padre = nodo.getPadre();

        //si es raiz indicamos q el padre (el pivote) va a pasar a ser la raiz
        if (this.raiz == nodo) {
            padre = new Nodo();
            this.raiz = padre;
            padre.setEsHoja(false);
        }

        //Creamos los dos hijos que salen del split y colocamos las claves
        Nodo hijo1 = new Nodo();
        hijo1.setEsHoja(nodo.getEsHoja());
        hijo1.insertarClave(0, nodo.getClaves()[0]);
        hijo1.insertarClave(1, nodo.getClaves()[1]);

        hijo1.actualizarContador();
        hijo1.setPadre(padre);

        Nodo hijo2 = new Nodo();
        hijo2.setEsHoja(nodo.getEsHoja());
        hijo2.insertarClave(0, nodo.getClaves()[3]);

        hijo2.actualizarContador();
        hijo2.setPadre(padre);

        //Si el nodo al cual se le aplico split tenia más hijos se agregan a los nuevos hijos creado previamente
        if (!nodo.getEsHoja()) {
            hijo1.getHijos()[0] = nodo.getHijos()[0];
            if (hijo1.getHijos()[0] != null){
                    hijo1.getHijos()[0].setPadre(hijo1);
            }

            hijo1.getHijos()[1] = nodo.getHijos()[1];
            if (hijo1.getHijos()[1] != null){
                hijo1.getHijos()[1].setPadre(hijo1);
            }

            hijo1.getHijos()[2] = nodo.getHijos()[2];
            if (hijo1.getHijos()[2] != null){
                hijo1.getHijos()[2].setPadre(hijo1);
            }
            
            hijo2.getHijos()[0] = nodo.getHijos()[3];
            if (hijo2.getHijos()[0] != null){
                hijo2.getHijos()[0].setPadre(hijo2);
            }

            hijo2.getHijos()[1] = nodo.getHijos()[4];
            if (hijo2.getHijos()[1] != null){
            hijo2.getHijos()[1].setPadre(hijo2);
            }
        }

        //sube al pivote y hace espacio para este si es necesario (ex: el pivote es 12 y sube a [2,17], s ehace su espacio y s emueve sus hijos)
        int p = padre.getContador() - 1;
        while (p >= 0 && padre.getClaves()[p] > i) {
            padre.getClaves()[p + 1] = padre.getClaves()[p];
            padre.getHijos()[p + 2] = padre.getHijos()[p + 1];
            p--;
        }
        padre.getClaves()[p + 1] = i;
        padre.actualizarContador();

        //terminamos de conectar a hijo y padre
        padre.getHijos()[p + 1] = hijo1;
        padre.getHijos()[p + 2] = hijo2;
        hijo1.setPadre(padre);
        hijo2.setPadre(padre);

        //si al subir el padre las claves sobrebordan se hace split nuevamente
        if (padre.getContador() == 4) {
            split(padre);
        }
    }


    public int buscarIntervalo(Nodo nodo, int clave){
        int[] clavesActuales = nodo.getClaves();
        int indice = 0;
        while(indice < nodo.getContador() && clave > clavesActuales[indice]){
            indice++;
        }

        return indice;
    }

    public void eliminar(int clave) {
        eliminarAux(raiz, clave);
    }

    public boolean eliminarAux(Nodo nodo, int clave) {
        if (nodo == null) return false;

        int[] clavesNodo = nodo.getClaves();
        int indice = 0;

        // Buscamos la posición de la clave
        while (indice < nodo.getContador() && clave > clavesNodo[indice]) {
            indice++;
        }

        if (indice < nodo.getContador() && clave == clavesNodo[indice]) {

            // el nodo es hoja
            if (nodo.getEsHoja()) {
                for (int i = indice; i < nodo.getContador() - 1; i++) {
                    clavesNodo[i] = clavesNodo[i + 1];
                }
                // Limpiamos la última posición
                clavesNodo[nodo.getContador() - 1] = -1;

                nodo.decrementarContador();
                // Si quedó vacío, reparamos el underflow
                if (nodo.getContador() == 0) {
                    repararUnderflow(nodo);
                }

                return true;
            }

            //el nodo NO es hoja
            // Intentamos usar el predecesor si el hijo izquierdo tiene suficientes claves
            Nodo hijoIzq = nodo.getHijos()[indice];

            if (hijoIzq.getContador() >= 2) {
                int predecesor = getPredecesor(nodo, indice);
                clavesNodo[indice] = predecesor;
                return eliminarAux(hijoIzq, predecesor);

            } else {
                // sino, usamos el sucesor
                Nodo hijoDer = nodo.getHijos()[indice + 1];
                int sucesor = getSucesor(nodo, indice);
                clavesNodo[indice] = sucesor;

                return eliminarAux(hijoDer, sucesor);
            }
        }

        // La clave no estaba en este nodo.
        //seguimos buscando en el hijo correspondiente.
        if (!nodo.getEsHoja()) {
            return eliminarAux(nodo.getHijos()[indice], clave);
        }

        return false;
    }

    public int getPredecesor(Nodo nodo, int indice) {
        Nodo actual = nodo.getHijos()[indice];
        while (!actual.getEsHoja()) {
            actual = actual.getHijos()[actual.getContador()];
        }
        return actual.getClaves()[actual.getContador() - 1];
    }

    public int getSucesor(Nodo nodo, int indice) {
        Nodo actual = nodo.getHijos()[indice + 1];
        while (!actual.getEsHoja()) {
            actual = actual.getHijos()[0];
        }
        return actual.getClaves()[0];
    }

    public void repararUnderflow(Nodo nodo) {
        // el underflow llego a la raiz
        if (nodo == raiz) {
            if (nodo.getContador() == 0) {
                if (nodo.getEsHoja()) {
                    // La raíz era una hoja y quedó vacía
                    raiz = null;
                } else {
                    // La raíz tenía un solo hijo válido
                    raiz = nodo.getHijos()[0];
                    raiz.setPadre(null);
                }
            }
            return;
        }

        Nodo padre = nodo.getPadre();
        int indice = -1;
        //posición en la que esta el nodo dentro del padre
        for (int i = 0; i <= padre.getContador(); i++) {
            if (padre.getHijos()[i] == nodo) {
                indice = i;
                break;
            }
        }
        //el hermano izquiero presta
        if (indice > 0 &&
            padre.getHijos()[indice - 1].getContador() >= 2) {
            Nodo hermanoIzq = padre.getHijos()[indice - 1];
            //clave del padre baja al nodo vacío
            nodo.getClaves()[0] = padre.getClaves()[indice - 1];

            // clave mayor del hermano izquierdo sube al padre
            padre.getClaves()[indice - 1] =
                hermanoIzq.getClaves()[hermanoIzq.getContador() - 1];

            //no son hojas, movemos también el hijo correspondiente
            if (!nodo.getEsHoja()) {
                nodo.getHijos()[1] = nodo.getHijos()[0];
                nodo.getHijos()[0] =hermanoIzq.getHijos()[hermanoIzq.getContador()];

                if (nodo.getHijos()[0] != null) {
                    nodo.getHijos()[0].setPadre(nodo);
                }

                if (nodo.getHijos()[1] != null) {
                    nodo.getHijos()[1].setPadre(nodo);
                }
                hermanoIzq.getHijos()[hermanoIzq.getContador()] = null;
            }
            hermanoIzq.getClaves()[hermanoIzq.getContador() - 1] = -1;

            nodo.incrementarContador();
            hermanoIzq.decrementarContador();

            return;
        }

        //el hermano derecho presta
        if (indice < padre.getContador() &&
            padre.getHijos()[indice + 1].getContador() >= 2) {
            Nodo hermanoDer = padre.getHijos()[indice + 1];

            nodo.getClaves()[0] = padre.getClaves()[indice];

            padre.getClaves()[indice] = hermanoDer.getClaves()[0];

            //no son hojas, movemos el primer hijo del hermano derecho
            if (!nodo.getEsHoja()) {
                // El primer hijo del hermano derecho pasa al nodo
                nodo.getHijos()[1] = hermanoDer.getHijos()[0];

                if (nodo.getHijos()[1] != null) {
                    nodo.getHijos()[1].setPadre(nodo);
                }
                for (int i = 0; i < hermanoDer.getContador(); i++) {
                    hermanoDer.getHijos()[i] = hermanoDer.getHijos()[i + 1];
                }

                hermanoDer.getHijos()[hermanoDer.getContador()] = null;
            }
            for (int i = 0; i < hermanoDer.getContador() - 1; i++) {
                hermanoDer.getClaves()[i] =hermanoDer.getClaves()[i + 1];
            }

            hermanoDer.getClaves()[hermanoDer.getContador() - 1] = -1;

            nodo.incrementarContador();
            hermanoDer.decrementarContador();

            return;
        }

        //fusion con el hermano izq
        if (indice > 0) {
            Nodo hermanoIzq = padre.getHijos()[indice - 1];
            int contadorHermano = hermanoIzq.getContador();

            hermanoIzq.getClaves()[contadorHermano] = padre.getClaves()[indice - 1];
            hermanoIzq.incrementarContador();

            if (!nodo.getEsHoja()) {
                hermanoIzq.getHijos()[contadorHermano + 1] =
                    nodo.getHijos()[0];

                if (hermanoIzq.getHijos()[contadorHermano + 1] != null) {
                    hermanoIzq.getHijos()[contadorHermano + 1]
                        .setPadre(hermanoIzq);
                }
            }

            for (int i = indice - 1; i < padre.getContador() - 1; i++) {
                padre.getClaves()[i] = padre.getClaves()[i + 1];
            }
            padre.getClaves()[padre.getContador() - 1] = -1;

            for (int i = indice; i < padre.getContador(); i++) {
                padre.getHijos()[i] = padre.getHijos()[i + 1];
            }

            padre.getHijos()[padre.getContador()] = null;
            padre.decrementarContador();

            if (padre.getContador() == 0 && padre.getPadre() != null) {
                repararUnderflow(padre);
            }

            //el padre era la raíz y quedó vacía,
            if (padre == raiz && padre.getContador() == 0) {
                raiz = hermanoIzq;
                raiz.setPadre(null);
            }

            return;
        }

        //fusion con el hijo derecho
        else{
            Nodo hermanoDer = padre.getHijos()[indice + 1];
            int clavePadre = padre.getClaves()[indice];

            for (int i = hermanoDer.getContador(); i > 0; i--) {
                hermanoDer.getClaves()[i] = hermanoDer.getClaves()[i - 1];
            }

            hermanoDer.getClaves()[0] = clavePadre;
            hermanoDer.incrementarContador();

            if (!nodo.getEsHoja()) {
                for (int i = hermanoDer.getContador(); i > 0; i--) {
                    hermanoDer.getHijos()[i] = hermanoDer.getHijos()[i - 1];
                }
                hermanoDer.getHijos()[0] = nodo.getHijos()[0];

                if (hermanoDer.getHijos()[0] != null) {
                    hermanoDer.getHijos()[0].setPadre(hermanoDer);
                }
            }
            for (int i = indice; i < padre.getContador() - 1; i++) {
                padre.getClaves()[i] = padre.getClaves()[i + 1];
            }

            padre.getClaves()[padre.getContador() - 1] = -1;
            for (int i = indice; i < padre.getContador(); i++) {
                padre.getHijos()[i] = padre.getHijos()[i + 1];
            }

            padre.getHijos()[padre.getContador()] = null;
            padre.decrementarContador();
            if (padre.getContador() == 0 && padre.getPadre() != null) {
                repararUnderflow(padre);
            }

            // Si el padre era la raíz y quedó vacía,
            if (padre == raiz && padre.getContador() == 0) {
                raiz = hermanoDer;
                raiz.setPadre(null);
            }
            return;
        }
    }

    public String toString(){
        return toStringAux(this.raiz);
    }


    public String toStringAux(Nodo nodo){
        if(nodo == null){
            return "El arbol esta vacio";
        }
        String cadena = "Nodo = " + nodo.toString();
        Nodo [] hijos = nodo.getHijos();
        if(hijos != null){
            for(int i = 0; i < hijos.length; i++){
                if(hijos[i] != null){
                    cadena += "\n -> " + toStringAux(hijos[i]);
                }
            }
        }

        return cadena;
    
    }

}