//1. Create a list called "list" with the elements "red", "white", "black"

var lista: List[String] = List( "rojo", "blanco", "negro")

//2. Add 5 more items to "list" "green" ,"yellow", "blue", "orange", "pearl"

var añadir= lista ::: List("verde" ,"amarillo", "azul", "naranja", "perla")

//3. Fetch items from "list" "green", "yellow", "blue"

añadir.slice(3,6)