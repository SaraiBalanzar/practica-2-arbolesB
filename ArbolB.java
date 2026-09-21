import java.util.Arrays;
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
        } else {
            Nodo nuevo = new Nodo();
            nuevo.insertarClave(0, clave);
            nodo.getHijos()[indiceHijo] = nuevo;
            nuevo.setPadre(nodo);
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

        //Si el nodo al cual se le aplico split tenia más hijos se agergan a los nuevos hijos creado previamente
        if (!nodo.getEsHoja()) {
            hijo1.getHijos()[0] = nodo.getHijos()[0];
            if (hijo1.getHijos()[0] != null){
                    hijo1.getHijos()[0].setPadre(hijo1);
            }

            hijo1.getHijos()[1] = nodo.getHijos()[1];
            if (hijo1.getHijos()[1] != null){
                hijo1.getHijos()[1].setPadre(hijo1);
            }
            
            hijo2.getHijos()[0] = nodo.getHijos()[2];
            if (hijo2.getHijos()[0] != null){
                hijo2.getHijos()[0].setPadre(hijo2);
            }

            hijo2.getHijos()[1] = nodo.getHijos()[3];
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

        //si al subir el padre las claves sobrebordan s ehacce split nuevamente
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