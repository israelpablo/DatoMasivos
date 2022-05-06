###Pratice 3

the firt step is necesary add the necesary librerys
```
package org.apache.spark.examples.ml


import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.{RandomForestClassificationModel, RandomForestClassifier}
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorIndexer}

import org.apache.spark.sql.SparkSession
```
we start the sesion spark
```
    val spark = SparkSession
      .builder
      .appName("RandomForestClassifierExample")
      .getOrCreate()
```
   
```
    val data = spark.read.format("libsvm").load("c:/Spark/data/mllib/sample_libsvm_data.txt")
```

```
val labelIndexer = new StringIndexer()
      labelIndexer.setInputCol("label")
      labelIndexer.setOutputCol("indexedLabel")
     labelIndexer.setHandleInvalid("skip")
      labelIndexer.fit(data)
```
```
      val labelindexed = labelIndexer.fit(data).transform(data)
```

    ```
    val featureIndexer = new VectorIndexer()
      featureIndexer.setInputCol("features")
      featureIndexer.setOutputCol("indexedFeatures")
      featureIndexer.setMaxCategories(4)
     featureIndexer.setHandleInvalid("skip")
      featureIndexer.fit(data)
```
  
  
```  
    val Array(trainingData, testData) = data.randomSplit(Array(0.7, 0.3))
```

```
    val rf = new RandomForestClassifier()
      rf.setLabelCol("indexedLabel")
      rf.setFeaturesCol("indexedFeatures")
      rf.setNumTrees(10)
```
```
    val labelConverter = new IndexToString()
      labelConverter.setInputCol("prediction")
      labelConverter.setOutputCol("predictedLabel")
      labelConverter.setLabels(labelindexed.schema("indexedLabel").metadata.getMetadata("ml_attr").getStringArray("vals"))
```

```
    val pipeline = new Pipeline()
      pipeline.setStages(Array(labelIndexer, featureIndexer, rf, labelConverter))
```
```
    val model = pipeline.fit(trainingData)
```
```
    val predictions = model.transform(testData)
```
```
    predictions.select("predictedLabel", "label", "features").show(5)
```
### Result:
![image](https://user-images.githubusercontent.com/77422159/167225891-295d1c0b-b91a-4799-b973-a92e9d84f3eb.png)


```
    val evaluator = new MulticlassClassificationEvaluator()
      evaluator.setLabelCol("indexedLabel")
      evaluator.setPredictionCol("prediction")
      evaluator.setMetricName("accuracy")
    val accuracy = evaluator.evaluate(predictions)
    println(s"Test Error = ${(1.0 - accuracy)}")
```
###result:
![image](https://user-images.githubusercontent.com/77422159/167225987-12ba5b94-b3ab-444e-82fb-5d47849dd9b5.png)


```

val rfModel = model.stages(2).asInstanceOf[RandomForestClassificationModel]
    println(s"Learned classification forest model:\n ${rfModel.toDebugString}")
  ```
  
  ###result
  ![image](https://user-images.githubusercontent.com/77422159/167226002-39244bbd-63cb-485b-9889-0bf354e5bd54.png)
