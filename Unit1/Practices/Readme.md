## Practice #2

### //1. Develop an algorithm in scale that calculates the radius of a circle
``` 
val diameter : Double = 26
val radio = diameter / 2
radio
``` 
### //2. Develop an algorithm in scala that tells me if a number is prime
``` 
val number : Int = 13
var primo : Boolean = true

for(i <- Range(2, number)) {
  if((number % i) == 0) {
    primo = false
  }
}
if(primo){
  println("Es Primo")
} else {
  println("No es Primo")
}
``` 
### //3. Given the variable bird = "tweet", use string interpolation to print "I am writing a tweet"

``` 
var bird = "tweet"
printf("Estoy escribiendo un tweet", bird)
``` 


## Practice #3

### //1. Create a list called "list" with the elements "red", "white", "black"
``` 
var lista: List[String] = List( "rojo", "blanco", "negro")
``` 
### //2. Add 5 more items to "list" "green" ,"yellow", "blue", "orange", "pearl"
``` 
var añadir= lista ::: List("verde" ,"amarillo", "azul", "naranja", "perla")
``` 
### //3. Fetch items from "list" "green", "yellow", "blue"
``` 
añadir.slice(3,6)
``` 
### 4. Create a array of range number 1-1000 to steps 5 in 5
For this we need create the next  array is autogenerate
the firt option is for start the array and the next parameter is end of the array and de last is the steps. 
```
val arreglo=Array.range(1, 1000, 5)
```
#### Result

### 5. What are the unique elements of the list List(1,3,3,4,6,7,3,7) use conversion to sets
in this code we need to get only one value that are not repeated
```
val mylist = List(1,3,3,4,6,7,3,7)
mylist.toSet
```
#### Result

### 6. Crea una mapa mutable llamado nombres que contenga los siguiente "Jose", 20, "Luis", 24, "Ana", 23, "Susana", "27"
We create the map with the indicated values 
```
val mutmap =    collection.mutable.Map  (("Jose", 20),( "Luis", 24),( "Ana", 23),( "Susana", "27"))

```
#### 6 a . Print all the keys on the map
executing the following statement shows the keys of each data set
```
mutmap.keys
```
##### Result

#### 6 b . Agrega el siguiente valor al mapa("Miguel", 23)
with the following code the new values ​​are added to map
```
mutmap += ("Miguel" -> 23)
```
##### Result