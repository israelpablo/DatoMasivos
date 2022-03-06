Practice #2

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