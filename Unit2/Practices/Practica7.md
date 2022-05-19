## Practice 7

this a practice we need import the necesary complements on this case we need add the libraey necessary.

```

import org.apache.spark.ml.classification.NaiveBayes
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.sql.SparkSession 

```

Now we need add the data for create the function of naive bayes

```
val data = spark.read.format("libsvm").load("c:/Spark/data/mllib/sample_libsvm_data.txt")

```

We can show the rows count and we can access for that information use the next code
```
println ("Numero de lineas en el archivo de datos:" + data.count())
```

Now is necessary create our arrays one is for training and the other one is for testdata for that is necessary add this code.

```
val Array (trainingData, testData) = data.randomSplit (Array (0.7, 0.3), 100L)
```

Now is necessary training the model for this we need add the values for training in the model the code is necessary.

```
val naiveBayesModel = new NaiveBayes().fit(trainingData)
```

now we can show the prediction on the model

```
predictions.show()
```
