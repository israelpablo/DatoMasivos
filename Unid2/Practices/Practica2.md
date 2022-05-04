## Practice 2 ##

On first step we need import the librery necesary for run the code.
``` 
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.DecisionTreeClassificationModel
import org.apache.spark.ml.classification.DecisionTreeClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorIndexer}

``` 


On this step is necesary load the data for this step is necesary take the file "sample_libsvm_data.txt"
``` 
val data = spark.read.format("libsvm").load("C:/Spark/data/mllib/sample_libsvm_data.txt")

``` 
#### Result
![practice 2 1](https://user-images.githubusercontent.com/77422159/166828296-de20b2b1-70e4-4220-b050-15006e9a4e77.PNG)

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
   featureIndexer.fit(data)
  ```
  #### Result
  ![practice 2 2](https://user-images.githubusercontent.com/77422159/166828341-ee49524a-a267-48c0-9c20-f1c6be7f1066.PNG)

  ![practice 2 3](https://user-images.githubusercontent.com/77422159/166828358-f581b06c-10b0-47e8-ba87-9c31b00843ed.PNG)

Add the next code for separed the data for  test and train 
```
val Array(trainingData, testData) = data.randomSplit(Array(0.7, 0.3))
```
![practice 2 4](https://user-images.githubusercontent.com/77422159/166828560-65c58746-c09d-4672-a7ef-64cfd06c8771.PNG)

on this section we make all feactures necesary for train model
``` 
val dt = new DecisionTreeClassifier()
  dt.setLabelCol("indexedLabel")
  dt.setFeaturesCol("indexedFeatures")

``` 
#### Result
![practice 2 5](https://user-images.githubusercontent.com/77422159/166828590-4e78dd22-507d-413c-81e9-956d1df784ca.PNG)

now we convert indexed labels back to original
```
val labelConverter = new IndexToString()
  labelConverter.setInputCol("prediction")
  labelConverter.setOutputCol("predictedLabel")
  labelConverter.setLabels(labelIndexer.labels)
```
#### Result
![practice 2 6](https://user-images.githubusercontent.com/77422159/166828653-7897912c-46e4-4a8e-97b6-fd056fc54de4.PNG)

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


