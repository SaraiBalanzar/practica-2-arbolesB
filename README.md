                                     Universidad Nacional Autónoma de México
                                Facultad de Ciencias  Ciencias de la Computación
                                           Modelado y Programación
                                           Práctica 02. ArbolesB
        
## Explicación de la implementación
La implementación que realizamos está basada en Java como único lenguaje.


### Explicación breve de la representación de un nodo.

En la clase nodo tiene como atributos un arreglo de enteros con las claves, un arreglo de nodos con los hijos, un booleano para saber si es hoja, un número entero contador y un Nodo padre.

En el constructor al arreglo de claves se le asigna un límite de 4 elementos con la finalidad de facilitar e identificar los casos de saturación. En el caso de los hijos se le asigna un límite de 5 para mantener la estructura y poder hacer la división de hijos en el split.

Con esta representación es más sencillo manejar los casos al insertar y eliminar y mantener el orden m = 4.

### Explicación de qué significa m = 4 y por qué cada nodo admite máximo tres llaves.

Por lo visto en clase sabemos que m representa la cantidad de hijos que puede tener, lo cual nos dice que para las claves de deben de tener a lo más m - 1. Usando los datos solicitados en esta práctica tendríamos que 4 - 1 nos da 3, es decir, admite máximo 3 claves.

### Explicación de cómo se decide qué hijo seguir durante una búsqueda.

Utilizando un índice y un arreglo con las claves del nodo se recorre las claves mientras el indice no rebase al contador y la clave sea mayor a las del arreglo en la posición del índice. Con esto podemos saber el intervalo correspondiente a la clave y revisa los casos donde es hoja y en caso de que sea menor no se encuentra, ya que las hojas no tienen hijos, en el caso de ser iguales regresa que se encuentra, y en el caso donde no es hoja hace recursión sobre los hijos para verificar si se encuentra.

### Explicación de qué ocurre cuando un nodo alcanza cuatro llaves

Al alcanzar 4 claves debe hacer split, el cual ubica el pivote para hacer la división, revisa si el nodo es raíz y en caso de serlo, promueve al pivote como padre y nueva raíz.

En otro caso, se crean los dos hijos que salen al hacer el split y le asigna las claves correspondientes en orden.En el caso donde ese nodo tenía más hijos, esos son agredados a los nuevos hijos que se crearon por el split.

Posteriormente, se promueve al pivote y para mantener el orden se le debe de hacer espacio al acomodarlo, para lo cual se recorren los valores necesarios para que quede en su lugar. Una vez terminado el split se terminan de actualizar los nuevos datos de referencias y contador.

En el caso donde se vuelva a saturar el nodo padre, se hace recursión.

### Explicación de la convención de promoción usada en la práctica.

Para mantener un orden y tener mejor definido los métodos necesarios para el split quedó más limpio. Además podemos hacer más notorios los cambios, como cuando se le hace espacio a la llave y cuando se sube al pivote. Con la recursión es más sencillo poder llamar nuevamente al método hasta tener correctas las posiciones y el orden del árbol, lo cuál hace más eficiente el código en lugar de revisar múltiples casos. En este caso, se promueve la tercera clave, dejando del lado izquierdo las dos primeras claves y del derecho la cuarta, esto cumple lo pedido en la práctica y lo visto en clase.

### Explicación breve de redistribución y fusión
        
Para redistribución, primero revisamos si el hermano izquierdo puede prestar (tiene 2 o más claves). Si puede, la clave del padre que separa a ambos nodos baja al nodo con underflow, y la clave mayor del hermano izquierdo sube a ocupar ese lugar en el padre. Si el nodo no es hoja, también se mueve el hijo correspondiente del hermano hacia el nodo receptor, para conservar los intervalos correctos. Se actualizan los contadores de ambos nodos. Si el hermano izquierdo no puede prestar, se intenta lo mismo con el hermano derecho, mediante el proceso simétrico.

Para la fusión, como ninguno puede prestar, se fusiona el nodo subocupado con un hermano (primero se intenta con el izquierdo; si no existe, con el derecho). La clave separadora del padre baja y se incorpora al nodo resultante de la fusión, junto con las claves y, en caso de no ser hoja, los hijos del nodo que quedó vacío. Después de esto, esa clave se elimina del padre y se recorren sus arreglos de claves e hijos para cerrar el espacio que dejó.

Si el nodo que queda vacío tras la fusión es la raíz, el nodo resultante de la fusión se convierte en la nueva raíz del árbol, reduciendo la altura en uno. La raíz solo se vuelve null en el caso distinto en que ella misma es una hoja y se queda sin ninguna clave.

## Ejecución y Compilación del programa
1. clone el repositorio desde la terminal: https://github.com/SaraiBalanzar/practica-2-arbolesB.git
2. Compile los archivos principales: javac src/*.java
3. Compile el archivo principal de pruebas: javac tests/Pruebas.java
4. Ejecute el archivo con el método principal: java tests.Pruebas


## Preguntas teóricas de ÁrbolesB  

#### 1. ¿Por qué al insertar una llave nueva no podemos decidir el hijo únicamente comparando con la primera llave del nodo?

Porque al comparar únicamente con la primera clave obtenemos si es mayor o menor a esta, no revisa los intervalos de las otras claves, esto haría que esa nueva clave pueda ubicarse en un intervalo incorrecto. Es por eso que se debe de comparar con las otras llaves ordenadas.

#### 2. ¿Por qué una búsqueda no debe recorrer todos los hijos de un nodo?

Al utilizar intervalos es más sencillo ubicar el intervalo al cual pertenece ,una vez ubicado ese es el único hijo que podría contener esa clave, no es necesario buscar en los demás hijos ya que no habría posibilidad de encontrar la clave si no corresponde a ese intervalo.

## Integrantes del equipo
- **Ruth Sarai Guadalupe Balanzar**
- **Alondra Campos Mendoza**
- **Edith Alejandra Mendoza Aragón**
- **Evelyn Vianey Mondragón Ceballos**
- **Oscar Adolfo Jaimez Martinez**
- **Jorge Guadalupe Mancilla Lagunacd doc**