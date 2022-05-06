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
printf("Estoy escribiendo un ${bird}tweet")
``` 
### 4. Given the variable var message = "Hello Luke I am your father!" uses slice to extract the "Luke" sequence
```
var mensaje="Hola Luke yo soy tu padre!"
mensaje slice(5,9)
```
#### Result
![practica 2-4](https://user-images.githubusercontent.com/77422159/157133222-cf00b9ed-b4e1-44d3-92df-501182852d3b.PNG)

### 5. What is the difference between value (val) and a variable (var) in scala?
val is a value that can not modificated is a value static
var is a value taht can modificated and we can change for other values.

### 6. Given the tuple (2,4,5,1,2,3,3.1416,23) it returns the number 3.1416
```
val tupla= (2,4,5,1,2,3,3.1416,23)
tupla._7
```
#### Result
![practica 2-6](https://user-images.githubusercontent.com/77422159/157133233-eba4d723-56e5-4aa0-9d29-a5f1639f7d86.PNG)

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
![practica 3-4](https://user-images.githubusercontent.com/77422159/157061965-7349ee7a-8e92-4228-9955-0a6b5671c134.PNG)

### 5. What are the unique elements of the list List(1,3,3,4,6,7,3,7) use conversion to sets
in this code we need to get only one value that are not repeated
```
val mylist = List(1,3,3,4,6,7,3,7)
mylist.toSet
```
#### Result
![practica 3-5](https://user-images.githubusercontent.com/77422159/157061988-531af8ee-5bdd-4949-ade8-68160512a3b0.PNG)

### 6. Crea una mapa mutable llamado nombres que contenga los siguiente "Jose", 20, "Luis", 24, "Ana", 23, "Susana", "27"
We create the map with the indicated values 
```
val mutmap =    collection.mutable.Map  (("Jose", 20),( "Luis", 24),( "Ana", 23),( "Susana", "27"))

```
![practica 3-6](https://user-images.githubusercontent.com/77422159/157062016-c7aa9645-7f90-46b8-8767-d365610922d7.PNG)

#### 6 a . Print all the keys on the map
executing the following statement shows the keys of each data set
```
mutmap.keys
```
##### Result
![practica 3-6 1](https://user-images.githubusercontent.com/77422159/157062068-7d1a3fc4-fbf0-4ceb-a63d-e2d97316d39a.PNG)


#### 6 b . Agrega el siguiente valor al mapa("Miguel", 23)
with the following code the new values ​​are added to map
```
mutmap += ("Miguel" -> 23)
```
##### Result
![practica 3-6 2](https://user-images.githubusercontent.com/77422159/157062103-9dea0773-b8f2-4304-bd8d-0316ca7739d4.PNG)
