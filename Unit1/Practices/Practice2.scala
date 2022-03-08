//1. Develop an algorithm in scale that calculates the radius of a circle

val diameter : Double = 26
val radio = diameter / 2
radio

//2. Develop an algorithm in scala that tells me if a number is prime

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

//3. Given the variable bird = "tweet", use string interpolation to print "I am writing a tweet"

var bird = "tweet"
printf("Estoy escribiendo un tweet", bird)

//4. Given the variable var message = "Hello Luke I am your father!" uses slice to extract the "Luke" sequence

var mensaje="Hola Luke yo soy tu padre!"
mensaje slice(5,9)


//5. What is the difference between value (val) and a variable (var) in scala?

val is a value that can not modificated is a value static
var is a value taht can modificated and we can change for other values.

//6. Given the tuple (2,4,5,1,2,3,3.1416,23) it returns the number 3.1416

val tupla= (2,4,5,1,2,3,3.1416,23)
tupla._7
