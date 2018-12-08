# Ejecutar el programa
1. Desde la línea de comandos, colocarse en el directorio `Parser/out/production/Parser`.
2. Correr el comando `java Lexer factorial.mio`.

# Estilo de codificación recomendado
## 1. Convención de nombrado de variables y métodos 
Utilizar camelCase.

### Ejemplos
`miVariable`, `miMetodo()`

No deseado: `mi_variable`, etc. 

## 2. Llaves 
Se abren en la misma línea del paréntesis de cierre de la lista de parámetros y se cierra en su propia línea.

### Ejemplo
```
public void miMetodo() {
  // Contenido
}
```
No deseado:
```
public void miMetodo() 
{
  // Contenido
}
```
## 3. Comentarios
Para comentarios de una sola línea, no añadir símbolos extra en adición al comentario como tal.

### Ejemplo
`// Un comentario`

No deseado: `//------Un comentario------`, etc. 

Para comentarios de bloques, proporcionar su propia línea a los marcadores de inicio y fin de comentario.

### Ejemplo
```
/*
Un comentario
de varias líneas
*/
```

No deseado:

```
/* Un comentario
de varias líneas
*/
```
