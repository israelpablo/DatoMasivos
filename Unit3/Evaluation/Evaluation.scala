//1. Import a simple Spark session.
//To login to Spark, we first open a new terminal
//and we open the spark-shell and immediately import a library to be able to log in:
import org.apache.spark.sql.SparkSession

//2. Use lines of code to minimize errors
//With the import and execution of these lines of code we stop 
//the display of INFO messages in the spark console:
import org.apache.log4j.Logger
import org.apache.log4j.Level

Logger.getLogger("org").setLevel(Level.OFF)
Logger.getLogger("akka").setLevel(Level.OFF)

//3. Create an instance of the Spark session
//Declare the session variable and use the library to create a new session:
val session = SparkSession.builder().getOrCreate()

//4. Import the Kmeans library for the clustering algorithm.
import org.apache.spark.ml.clustering.Kmeans

//5. Load the Wholesale Customer Dataset
//We load the Wholesale_customers_data file:
val Wholesaledata=spark.read.option("header","true").option("inferSchema","true")csv("C:/Users/valer/Documents/9no Semestre/Datos Masivos/Unit 3/Wholesale_customers_data.csv")

//6. Select the following columns: Fresh, Milk, Grocery, Frozen, 
//Detergents Paper, Delicassen and call this set feature data
val feature_data = (Wholesaledata.select($"Fresh",$"Milk",$"Grocery",$"Frozen",$"Detergents_Paper",$"Delicassen"))

//7. Import Vector Assembler and Vector
//for now is necesary add vector assmble for that we need add the next code.
import org.apache.spark.ml.feature.VectorAssembler

//8. Create a new Vector Assembler object for the feature columns as an input set, remembering that there are no labels
//For this option we created a vectorassembler with the next code. 
val vectorFeatures = (new VectorAssembler().setInputCols(Array("Fresh","Milk", "Grocery","Frozen","Detergents_Paper","Delicassen")).setOutputCol("features"))

//9. Use the assembler object to transform feature_data
//Now is necesary give format and with this code 
val features = vectorFeatures.transform(feature_data)

//10. Create a Kmeans model with K=3
//Now is necesary create the model for this case we add the next code.
val kmeans = new KMeans().setK(3).setSeed(1L)
val model = kmeans.fit(features)

//11. Evaluate the clusters using Within Set Sum of Squared Errors WSSSE and print the centroids.
//Now only print the result.
val WSSSE = model.computeCost(features)
println(s"Within set sum of Squared Errors = $WSSSE")

