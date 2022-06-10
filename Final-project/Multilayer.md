

## Multilayer Perceptron



We add the necessary libraries.
```
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.IntegerType
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.feature.StringIndexer 
import org.apache.spark.ml.feature.VectorAssembler

we add error level code.
```
import org.apache.log4j._
Logger.getLogger("org").setLevel(Level.ERROR)
```
we start spark session.
```
val spark = SparkSession.builder.appName("MultilayerPerceptron").getOrCreate()
```
we load the csv file.
```
val bank  = spark.read.option("header","true").option("inferSchema", "true").option("delimiter",";").format("csv").load("bank-full.csv")
```
now we need check the information for that we need add the next code
```
bank.show
```
show the name of columms for verify 
```
bank.columns
```

we created the indexing.
```
val labelIndexer = new StringIndexer().setInputCol("y").setOutputCol("indexedLabel").fit(bank)
val indexed = labelIndexer.transform(bank).drop("y").withColumnRenamed("indexedLabel", "label")
```
we add the vector of the numeric category columns.
```
val vectorFeatures = (new VectorAssembler().setInputCols(Array("balance","day","duration","pdays","previous")).setOutputCol("features"))
```
Now we nedd transforming the indexed value.
```
val features = vectorFeatures.transform(indexed)
```

now is necessary fitting indexed and finding labels 0 and 1.
```
val labelIndexer = new StringIndexer().setInputCol("label").setOutputCol("indexedLabel").fit(indexed)
```

Now is necessary run 30 times for the model
```
for(i <- Range(1,30))
{
    ```
We start timer
```
val startTimeMillis = System.currentTimeMillis()
```
now is necessary separeted the values and we splitting the data in 70% and 30%.
```
val splits = features.randomSplit(Array(0.7, 0.3))
val trainingData = splits(0)
val testData = splits(1)
```

we creating the layers array.
```
val layers = Array[Int](5, 4, 1, 2)
```

now is necessay create the Multilayer Perceptron object of the Multilayer Perceptron Classifier.
```
val multilayerP = new MultilayerPerceptronClassifier().setLayers(layers).setBlockSize(128).setSeed(1234L).setMaxIter(100)  
```

now is necessary fitting trainingData into the model.
```
val model = multilayerP.fit(trainingData)
```
Transforming the testData for the predictions.
```
val prediction = model.transform(testData)
```

Now is necessary select the prediction and label columns.
```
val predictionAndLabels = prediction.select("prediction", "label")
```

we created a Multiclass Classification Evaluator object.
```
val evaluator = new MulticlassClassificationEvaluator().setMetricName("accuracy")
```
we need end the time for the model
```
val endTimeMillis = System.currentTimeMillis()
val durationSeconds = (endTimeMillis - startTimeMillis) / 1000
```

We are printing the all results.
``
println("|"+i + "|"+evaluator.evaluate(predictionAndLabels) +"|"+ (1.0 - evaluator.evaluate(predictionAndLabels))+"|" +durationSeconds + "|")

}
```

### Result
We have the resul for MultilayerPerceptronClassifier

|Cycle|Accuracy |Test Error| Duration cycle by seconds |
|-----|---------|----------|----------------|
|1|0.8861233480176212|0.11387665198237884|6|
|2|0.882365988909427|0.11763401109057303|6|
|3|0.8824778501867174|0.11752214981328257|6|
|4|0.8844454264250994|0.11555457357490062|8|
|5|0.8826898757717772|0.11731012422822285|6|
|6|0.8809766763848397|0.11902332361516033|6|
|7|0.8796384745389081|0.12036152546109191|6|
|8|0.883880486905201|0.11611951309479895|7|
|9|0.8820588235294118|0.11794117647058822|6|
|10|0.8798737058521183|0.12012629414788167|6|
|11|0.8825152916604505|0.11748470833954949|6|
|12|0.8813872661391703|0.11861273386082971|8|
|13|0.8826662721017977|0.11733372789820229|7|
|14|0.8818791946308725|0.1181208053691275|6|
|15|0.8844751915495053|0.11552480845049473|7|
|16|0.8880047594258943|0.1119952405741057|6|
|17|0.8836883614653203|0.11631163853467974|6|
|18|0.8847984530715455|0.11520154692845452|6|
|19|0.8860326894502228|0.11396731054977716|7|
|20|0.882918338000454|0.11708166199954595|7|
|21|0.885659907766635|0.11434009223336505|7|
|22|0.8800469828219057|0.11995301717809426|6|
|23|0.8837001784651993|0.11629982153480067|6|
|24|0.8819604063171943|0.1180395936828057|6|
|25|0.8842975206611571|0.11570247933884292|7|
|26|0.8859090235590458|0.11409097644095423|7|
|27|0.8838704962362055|0.1161295037637945|6|
|28|0.8835855646100116|0.11641443538998841|7|
|29|0.8825466520307355|0.11745334796926454|6|
|30|0.885659907766635|0.11434009223336505|7|
