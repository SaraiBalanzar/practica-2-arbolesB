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
}