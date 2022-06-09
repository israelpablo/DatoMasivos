//1. Comienza una simple sesión Spark.
//To log in to Spark, first we open the cmd as administrator, we run Spark with
//spark-shell and immediately we import a library to be able to log in
import org.apache.spark.sql.SparkSession 
//We declare the SparkSession variable and use the library to create a new session
val session = SparkSession.builder().getOrCreate()

//2. Cargue el archivo Netflix Stock CSV, haga que Spark infiera los tipos de datos. 
val netflix = spark.read.option("header","true").option("inferSchema","true")csv("Netflix_2011_2016.csv")

//3. ¿Cuáles son los nombres de las columnas? 
//We send to call our netflix variable that with that name we load the Netflix Stock CSV file, 
//and what we want to see are the columns
netflix.columns

//4. ¿Cómo es el esquema? 
//We send to call our netflix variable that with that name we load the Netflix Stock CSV file, 
//we send to print the scheme that is the one we want to see
netflix.printSchema()

//5. Imprime las primeras 5 columnas. 
//We send to call our netflix variable that with that name we load the Netflix Stock CSV file
//and select the first 5 columns
netflix.select($"Date",$"Open",$"High",$"Low",$"Close").show()

//6. Usa describe () para aprender sobre el DataFrame. 
netflix.describe().show()

//7. Crea un nuevo dataframe con una columna nueva llamada “HV Ratio” que es la relación que  
//existe entre el precio de la columna “High” frente a la columna “Volumen” de acciones  negociadas
// por un día. Hint - es una operación 
//We declare a new varibale called netflixnew
var netflixnew = netflix.withColumn("HV Ratio", netflix("High")/netflix("Volume"))

//8. ¿Qué día tuvo el pico más alto en la columna “Open”? 
//We declare a variable called columnday and from the netflix file we take the data of the day column
val columnday = netflix.withColumn("Day",dayofmonth(netflix("Date")))
//We declare a variable called netflixdaycolumn and we are going to group all the maximum values ​​of the day
val netflixdaycolumn = columnday.groupBy("Day").max()
//we call the variable that we declare to show the table
netflixdaycolumn.show()
//we send to call the variable that we declare and select the maximum day of the maxopen column
netflixdaycolumn.select($"Day",$"max(Open)").show()

//9. ¿Cuál es el significado de la columna Cerrar “Close” en el contexto de información financiera,  
//explíquelo que no hay que codificar nada? 
//the close column means that the company ended the day or in this case that netflix ended the day

//10. ¿Cuál es el máximo y mínimo de la columna “Volumen”?
//from the netflix file we call the maximum and minimum value of the volume column
netflix.select(max("Volume"), min("Volume")).show()



//11. Con Sintaxis Scala/Spark $ conteste los siguiente: 
//a. ¿Cuántos días fue la columna “Close” inferior a $ 600? 
//b. ¿Qué porcentaje del tiempo fue la columna “High” mayor que $ 500? 
//c. ¿Cuál es la correlación de Pearson entre la columna “High” y la columna “Volumen”? d. ¿Cuál es el máximo de la columna “High” por año? 
//e. ¿Cuál es el promedio de columna “Close” para cada mes del calendario? 

