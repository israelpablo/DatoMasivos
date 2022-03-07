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
