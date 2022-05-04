## Practice 2 ##

On first step we need import the librery necesary for run the code.
``` 
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.DecisionTreeClassificationModel
import org.apache.spark.ml.classification.DecisionTreeClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorIndexer}

``` 
#### Result

On this step is necesary load the data for this step is necesary take the file "sample_libsvm_data.txt"
``` 
val data = spark.read.format("libsvm").load("C:/Spark/data/mllib/sample_libsvm_data.txt")

``` 
#### Result
we need create labelindexer and featureIndexer, this is necesary for adding metadata an fit the dataset
```
val labelIndexer = new StringIndexer()
  labelIndexer.setInputCol("label")
  labelIndexer.setOutputCol("indexedLabel")
  labelIndexer.fit(data)

val featureIndexer = new VectorIndexer()
  featureIndexer.setInputCol("features")
  featureIndexer.setOutputCol("indexedFeatures")
  featureIndexer.setMaxCategories(4) 
  ```
  #### Result
  the next step is fit data
```
  featureIndexer.fit(data)
```

#### Result

on this section we make all feactures necesary for train model
``` 
val dt = new DecisionTreeClassifier()
  dt.setLabelCol("indexedLabel")
  dt.setFeaturesCol("indexedFeatures")

``` 
#### Result
now we convert indexed labels back to original
```
val labelConverter = new IndexToString()
  labelConverter.setInputCol("prediction")
  labelConverter.setOutputCol("predictedLabel")
  labelConverter.setLabels(labelIndexer.labels)
```
#### Result
now we change indexers and tree to pipeline
```
val pipeline = new Pipeline()
  pipeline.setStages(Array(labelIndexer, featureIndexer, dt, labelConverter))

```
#### Result
Now is necesary train the model
```
val model = pipeline.fit(trainingData)
```
#### Result
we make predictions
```
val predictions = model.transform(testData)
```
#### Result
On this section we show 5 rows
```
predictions.select("predictedLabel", "label", "features").show(5)
```
#### Result

this last codes is select an compute test error
```
val evaluator = new MulticlassClassificationEvaluator()
  .setLabelCol("indexedLabel")
  .setPredictionCol("prediction")
  .setMetricName("accuracy")
val accuracy = evaluator.evaluate(predictions)
println(s"Test Error = ${(1.0 - accuracy)}")

val treeModel = model.stages(2).asInstanceOf[DecisionTreeClassificationModel]
println(s"Learned classification tree model:\n ${treeModel.toDebugString}")
```


#### Result


