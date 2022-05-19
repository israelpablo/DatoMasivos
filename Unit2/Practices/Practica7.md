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
### Result
![img](https://github.com/israelpablo/DatoMasivos/blob/Unit2/Unit2/Practices/Result%201.PNG)


If some one need check the values you can use the next code.


```
data.show()
```
### Result

![img](https://github.com/israelpablo/DatoMasivos/blob/Unit2/Unit2/Practices/Result%202.PNG)
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

### Result

![img](https://github.com/israelpablo/DatoMasivos/blob/Unit2/Unit2/Practices/Result%203.PNG)

Now is necessary create the prediction on the model

```
val evaluator = new MulticlassClassificationEvaluator().setLabelCol("label").setPredictionCol("prediction").setMetricName("accuracy")

```

Also we need add the prediction for that is necessary add the next code.

```
val precision = evaluator.evaluate (predictions) 
```

Now we can print promedio error 

```
println ("tasa de error =" + (1-precision))
```
with this we are finish the process

### Result
![img](https://github.com/israelpablo/DatoMasivos/blob/Unit2/Unit2/Practices/Result%204.PNG)
