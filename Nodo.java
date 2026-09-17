public class Nodo{
    private int[] claves;
    private Nodo[] hijos;
    private boolean esHoja;
    private int contador = 0;

    /* Arbol del orden m = 4 */
    public Nodo(){
        this.claves = new int[4];
        this.hijos = new Nodo[5];
        this.esHoja = true;
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

    public String toString(){
        String cadena = "[";
            for(int i = 0; i < contador; i++){
                cadena += claves[i];
                if (i < contador -1 ){
                    cadena+= ",";
                }
            }
            cadena += "]";
            return cadena;  
        }





}