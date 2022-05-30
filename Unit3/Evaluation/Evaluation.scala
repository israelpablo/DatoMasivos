//1. Import a simple Spark session.
val dataset = spark.read.option("header", "true").option("inferSchema","true")csv("data.csv")

//2. Use lines of code to minimize errors

//3. Create an instance of the Spark session

//4. Import the Kmeans library for the clustering algorithm.
import org.apache.spark.ml.clustering.KMeans
import org.apache.spark.ml.evaluation.ClusteringEvaluator
import org.apache.spark.ml.feature.{VectorIndexer, VectorAssembler}

//5. Load the Wholesale Customer Dataset
var data=featureIndexer.transform(feature_data)

//6. Select the following columns: Fresh, Milk, Grocery, Frozen, 
//Detergents Paper, Delicassen and call this set feature data
val featureIndexer = new VectorAssembler().setInputCols(Array("Fresh", "Milk", "Grocery", "Frozen", "Detergents_Paper",
"Delicassen")).setOutputCol("features")