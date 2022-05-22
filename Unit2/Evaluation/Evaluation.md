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
```
// Split the data into train and test  Divide los datos
val splits = data.randomSplit(Array(0.6, 0.4), seed = 1234L)
val train = splits(0)
val test = splits(1)

// specify layers for the neural network:  especificar capas para la red neuronal:
// input layer of size 4 (features), two intermediate of size 5 and 4  capa de entrada de tamano 4 (features), dos intermedias de tamano 5 y 4
// and output of size 3 (classes)  y salida de tamano 3 (classes) 
val layers = Array[Int](4, 5, 4, 3)
```
### Result

7. Build the classification model and explain its architecture.

```
// create the trainer and set its parameters  Crea el trainer y establece sus parametros.
val trainer = new MultilayerPerceptronClassifier()
  .setLayers(layers)
  .setBlockSize(128)
  .setSeed(1234L)
  .setMaxIter(100)

// train the model  entrena el model
val model = trainer.fit(train)

// compute accuracy on the test set  precision de calculo en el conjunto de prueba
val result = model.transform(test)
val predictionAndLabels = result.select("prediction", "label")
val evaluator = new MulticlassClassificationEvaluator()
  .setMetricName("accuracy")


```

8. Print the model results.
```
println(s"Test set accuracy = ${evaluator.evaluate(predictionAndLabels)}")
```