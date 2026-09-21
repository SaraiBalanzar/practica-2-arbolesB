import java.util.Arrays;
public class Nodo{
    private int[] claves;
    private Nodo[] hijos;
    private boolean esHoja;
    private int contador = 0;
    private Nodo padre;

    /* Arbol del orden m = 4 */
    public Nodo(){
        this.claves = new int[4];
        //el -1 es para q actuen como null, -1 para poder usar el 0. este cambio se hizo para poder actualizar el contador (cuenta 1 por cada elemento q no sea -1)
        Arrays.fill(claves, -1);
        this.hijos = new Nodo[5];
        this.esHoja = true;
        this.padre = null;
    }

    public int[] getClaves(){
        return this.claves;
    }

    public Nodo[] getHijos(){
        return this.hijos;
    }

    public boolean getEsHoja(){
        return esHoja;
    }

    public void setEsHoja(boolean b){
        this.esHoja = b;
    }

    public int getContador(){
        return this.contador;
    }

    public void insertarClave(int indice, int clave){
        claves[indice] = clave;
    }

    public void insertarHijo(int indice, Nodo hij){
       for (int i = this.hijos.length - 1; i > indice; i--) {
        this.hijos[i] = this.hijos[i - 1];
        }
    
        this.hijos[indice] = hij;
        if (hij != null) {
            hij.setPadre(this);
        }
    }

    public void incrementarContador(){
        this.contador++;
    }

    public void actualizarContador(){
        int i= 0;
        while(i < claves.length && claves[i] != -1 ){
            i++;
        }

        this.contador = i;
    }

    public void ordenarClaves(){
        Arrays.sort(claves, 0, contador);
    }

    public Nodo getPadre(){
        return padre;
    }

    public void setPadre(Nodo padre){
        this.padre = padre;
    }

    public String toString(){
        String cadena = "[";
            for(int i = 0; i < contador; i++){
                if (claves[i] != -1) {
                cadena += claves[i];
                if (i < contador -1 ){
                    cadena+= ",";
                }
                }
            }
            cadena += "]";
            return cadena;  
        }





}