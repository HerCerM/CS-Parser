# Acerca de
El repositorio contiene un analizador léxico y un analizador sintáctico para el lenguaje MIO. Este lenguaje está definido por la gramática libre de contexto presentada en la siguiente subsección. Para leer más acerca de la implementación de los analizadores puede referirse al manual de usario.

## Gramática para el lenguaje MIO
```
Símbolo inicial: <PROG>
Reglas:
<PROG>    -> PROGRAMA[id]<SENTS>FINPROG
<SENTS>   -> <SENT><SENTS>
<SENTS>   -> <SENT>
<SENT>    -> [id]=<ELEM>[op_ar]<ELEM>
<SENT>    -> [id]=<ELEM>
<SENT>    -> SI<COMPARA>ENTONCES<SENTS>SINO<SENTS>FINSI
<SENT>    -> SI<COMPARA>ENTONCES<SENTS>FINSI
<SENT>    -> REPITE<ELEM>VECES<SENTS>FINREP
<SENT>    -> IMPRIME<ELEM>
<SENT>    -> IMPRIME[txt]
<SENT>    -> LEE[id]
<SENT>    -> #[comentario]
<ELEM>    -> [id]
<ELEM>    -> [val]
<COMPARA> -> [id][op_rel]<ELEM>
```

## Ejemplo de cadena válida
Este ejemplo puede encontrarse en [aquí](Analizadores/Ejemplos de archivos MIO/factorial.mio). La cadena representa un programa escrito en el lenguaje MIO para calcular el factorial de un número.
```
# Programa que calcula el factorial de un número
PROGRAMA factorial
# VarX acumula los productos por iteración
VarX = 0x1
# VarY contiene el iterador del factor
VarY = 0x0
LEE Num
# Aplica Num! = 1 * 2 * 3 * ... * Num
REPITE Num VECES
VarY = VarY + 0x1
VarX = VarX * VarY
FINREP
IMPRIME "Factorial de "
IMPRIME Num
IMPRIME " es "
IMPRIME VarX
FINPROG 
```

# Ejecutar los programas
En la carpeta [Analizadores](Analizadores) se encuentran dos archivos JAR: _analex_ es el analizador léxico y _anasin_, el sintáctico. _analex_ requiere de un archivo MIO, por lo que debe colocarse un archivo con esta extensión en el mismo directorio que los JAR. Para ejecutar _analex_ se escribe en la consola lo siguiente:

`java -jar analex.jar nombreDelArchivo.mio`

Si el programa se ejecuta correctamente, los archivos LEX y SIM deben crearse. En caso contrario, se reporta la línea del primer error hallado. 

_anasin_ requiere un archivo LEX generado por el _analex_. Para ejecutar _anasin_ se escribe en la consola lo siguiente: 

`java -jar anasin.jar nombreDelArchivo.lex`

Si el programa se ejecuta correctamente, un mensaje de aprobación aparece en la consola. En caso contrario, un mensaje de no aprobado aparece en la consola.
