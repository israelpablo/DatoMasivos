1. Se carga el dataframe iris.csv
val datairis = spark.read.option("header", "true").option("inferSchema","true")csv("C:/Users/valer/Documents/GitHub/DatoMasivos/Unit2/Evaluation")

1a. Importacion de librerias 
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorIndexer, VectorAssembler}

      


2. Se imprime el valor de las columnas
println(datairis.columns.toSeq)

3. Se imprime el esquema 
datairis.printSchema()

4. Mandamos a imprimir las primeras 5 columnas
datairis.show()

5. use the method describe() to learn more about the data in the DataFrame.
```
datairis.describe()
```
### Result

6. Make the pertinent transformation for the categorical data which will be our labels to classify.



For this csv is necessary add this libraries.
```
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.IntegerType
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.feature.StringIndexer 
import org.apache.spark.ml.feature.VectorAssembler
```
we need add Error level code
```
import org.apache.log4j._
Logger.getLogger("org").setLevel(Level.ERROR)
```
start the Spark session.
```
val spark = SparkSession.builder.appName("MultilayerPerceptron").getOrCreate()
```
now we need add the csv
```
val datairis = spark.read.option("header","true").
option("inferSchema","true").
format("csv").
load("C:/Users/ilopez/Desktop/Data/datos masivos 05042022/DatoMasivos/Unit2/Evaluation/iris.csv")
```

in order to correctly manipulate the data inside the CSV, it is necessary to create index
```
val labelIndexer = new StringIndexer().setInputCol("species").setOutputCol("indexedLabel").fit(datairis)
val indexed = labelIndexer.transform(datairis).drop("species").withColumnRenamed("indexedLabel", "label")
```
Now we need create the vector of the numeric category columns.
```
val vectorFeatures = (new VectorAssembler().setInputCols(Array("sepal_length","sepal_width","petal_length","petal_width")).setOutputCol("features"))
```

Now is necessart transform the indexed value.
```
val features = vectorFeatures.transform(indexed)
```
we need fitting indexed and finding labels 0 and 1.
```
val labelIndexer = new StringIndexer().setInputCol("label").setOutputCol("indexedLabel").fit(indexed)
```

Now we need splitting the data in 70% and 30%.

```
val splits = features.randomSplit(Array(0.7, 0.3))
val trainingData = splits(0)
val testData = splits(1)
```

we create the layers array.
```
val layers = Array[Int](4, 5, 4, 3)
```

7. Build the classification model and explain its architecture.
now we need create the trainer  with the parameters
we need add the layers is the values create before

we create the Multilayer Perceptron object of the Multilayer Perceptron Classifier.
```
val multilayerP = new MultilayerPerceptronClassifier().setLayers(layers).setBlockSize(128).setSeed(1234L).setMaxIter(100)  
```
Now fitting trainingData into the model
```
val model = multilayerP.fit(trainingData)
```

Now we Transform the testData for the predictions.
```
val prediction = model.transform(testData)
```
the next step is necessary select the prediction and label columns.
```
val predictionAndLabels = prediction.select("prediction", "label")
```

Now we Create a Multiclass Classification Evaluator object.

```
val evaluator = new MulticlassClassificationEvaluator().setMetricName("accuracy")
```

 

8. Print the model results.

The finaly step is necessary print the result 
```
println(s"Accuracy: ${evaluator.evaluate(predictionAndLabels)}")
println(s"Test Error: ${1.0 - evaluator.evaluate(predictionAndLabels)}")
```