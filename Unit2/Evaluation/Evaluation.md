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
```
### Result

7. Build the classification model and explain its architecture.

8. Print the model results.