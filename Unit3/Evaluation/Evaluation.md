1. Import a simple Spark session.

Para iniciar sesión en Spark, primero abrimos una nueva terminal  
y abrimos el spark-shell e inmediatamente importamos una biblioteca para poder iniciar sesión:

import org.apache.spark.sql.SparkSession

![img](https://github.com/israelpablo/DatoMasivos/blob/Unit3/Unit3/Evaluation/session.png)

2. Use lines of code to minimize errors

import org.apache.log4j.Logger
import org.apache.log4j.Level

Logger.getLogger("org").setLevel(Level.OFF)
Logger.getLogger("akka").setLevel(Level.OFF)

![img](https://github.com/israelpablo/DatoMasivos/blob/Unit3/Unit3/Evaluation/errors.png)

3. Create an instance of the Spark session

Declaramos la variable session y usamos la librería para crear una nueva sesión

val session = SparkSession.builder().getOrCreate()

![img](https://github.com/israelpablo/DatoMasivos/blob/Unit3/Unit3/Evaluation/instance%20session.png)

4. Import the Kmeans library for the clustering algorithm.

import org.apache.spark.ml.clustering.Kmeans
import org.apache.spark.ml.evaluation.ClusteringEvaluator
![img](https://github.com/israelpablo/DatoMasivos/blob/Unit3/Unit3/Evaluation/library.png)

5. Load the Wholesale Customer Dataset

Cargamos el archivo de Wholesale_customers_data
val Wholesaledata=spark.read.option("header", "true").option("inferSchema","true")csv("C:/Users/ilopez/Desktop/Data/datas/DatoMasivos/Unit3/Evaluation/Wholesale_customers_data.csv")


![img](https://github.com/israelpablo/DatoMasivos/blob/Unit3/Unit3/Evaluation/cargar%20dataset.png)

6. Select the following columns: Fresh, Milk, Grocery, Frozen, 

Detergents Paper, Delicassen and call this set feature data

val feature_data = Wholesaledata.select("Fresh", "Milk", "Grocery", "Frozen", "Detergents_Paper", "Delicassen")


![img](https://github.com/israelpablo/DatoMasivos/blob/Unit3/Unit3/Evaluation/feature_data.png)

7. Import Vector Assembler and Vector

```
import org.apache.spark.ml.feature.VectorAssembler

```

8. Create a new Vector Assembler object for the feature columns as an input set, remembering that there are no labels

```


val vectorFeatures = (new VectorAssembler().setInputCols(Array("Fresh","Milk", "Grocery","Frozen","Detergents_Paper","Delicassen")).setOutputCol("features"))

```
9. Use the assembler object to transform feature_data

```
val features = vectorFeatures.transform(feature_data)
```
10. Create a Kmeans model with K=3
```
val kmeans = new KMeans().setK(3).setSeed(1L)
val model = kmeans.fit(features)

```
11. Evaluate the clusters using Within Set Sum of Squared Errors WSSSE and print the centroids.

```
val WSSSE = model.computeCost(features)
println(s"Within set sum of Squared Errors = $WSSSE")

```
