//1. Import a simple Spark session.
//Para iniciar sesión en Spark, primero abrimos una nueva terminal  
//y abrimos el spark-shell e inmediatamente importamos una biblioteca para poder iniciar sesión:
import org.apache.spark.sql.SparkSession

//2. Use lines of code to minimize errors
import org.apache.log4j.Logger
import org.apache.log4j.Level

Logger.getLogger("org").setLevel(Level.OFF)
Logger.getLogger("akka").setLevel(Level.OFF)

//3. Create an instance of the Spark session
//Declaramos la variable session y usamos la librería para crear una nueva sesión
val session = SparkSession.builder().getOrCreate()

//4. Import the Kmeans library for the clustering algorithm.
import org.apache.spark.ml.clustering.Kmeans

//5. Load the Wholesale Customer Dataset
//Cargamos el archivo de Wholesale_customers_data
val Wholesaledata=spark.read.option("header","true").option("inferSchema","true")csv("C:/Users/valer/Documents/9no Semestre/Datos Masivos/Unit 3/Wholesale_customers_data.csv")

//6. Select the following columns: Fresh, Milk, Grocery, Frozen, 
//Detergents Paper, Delicassen and call this set feature data
val feature_data = (Wholesaledata.select($"Fresh",$"Milk",$"Grocery",$"Frozen",$"Detergents_Paper",$"Delicassen"))