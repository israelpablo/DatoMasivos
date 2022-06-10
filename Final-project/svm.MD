## SVM

We add the necessary libraries.
```
import org.apache.spark.sql.SparkSession
import org.apache.spark.mllib.evaluation.MulticlassMetrics
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.ml.classification.LinearSVC
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.feature.StringIndexer
import org.apache.spark.ml.feature.VectorIndexer
import org.apache.spark.ml.feature.VectorAssembler
```
Now we need import also the librarie for errors
```
import org.apache.log4j._
Logger.getLogger("org").setLevel(Level.ERROR)
```
Creating a spark session named SVM
```
val spark = SparkSession.builder.appName("SVM").getOrCreate()
```
Importing the dataframe bank
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
val vectorFeatures = (new VectorAssembler().setInputCols(Array("duration","pdays","previous")).setOutputCol("features"))
```
Now we nedd transforming the indexed value.
```
val features = vectorFeatures.transform(indexed)
```
we renaming the column "y" as label.
```
val featuresLabel = features.withColumnRenamed("y", "label")
```
now we union of label and features as dataIndexed.
```
val dataIndexed = featuresLabel.select("label","features")
```
we create of labelIndexer and featureIndexer for the pipeline, Where 
```
features with distinct values > 4, are treated as continuous.
val labelIndexer = new StringIndexer().setInputCol("label").setOutputCol("indexedLabel").fit(dataIndexed)
val featureIndexer = new VectorIndexer().setInputCol("features").setOutputCol("indexedFeatures").setMaxCategories(4).fit(dataIndexed)
```
Now is necessary run 30 times for the model

```
for(i <- Range(1,31))
{
```
We start timer
```
val startTimeMillis = System.currentTimeMillis()
```
now is necessary separeted the values and we splitting the data in 70% and 30%.
```
val Array(training, test) = dataIndexed.randomSplit(Array(0.7, 0.3))
```
now is necessay create the object Linear Vector Machine.

```
val supportVM = new LinearSVC().setMaxIter(10).setRegParam(0.1)
```    
now is necessary fitting trainingData into the model.
```
val model = supportVM.fit(training)
```
Transforming testData for the predictions model.
```
val predictions = model.transform(test)
```
now we obtain the metrics.
```
val predictionAndLabels = predictions.select($"prediction",$"label").as[(Double, Double)].rdd
val metrics = new MulticlassMetrics(predictionAndLabels)
```
this code is for confusion matrix.
```
println("Confusion matrix:")
println(metrics.confusionMatrix)
```
we need end the time for the model
```
val endTimeMillis = System.currentTimeMillis()
val durationSeconds = (endTimeMillis - startTimeMillis) / 1000
```

We are printing the all results.
```
println("|"+i + "|"+ metrics.accuracy +"|"+ (1.0 - metrics.accuracy)+"|" +durationSeconds + "|")

}
```

### Result
|Cycle|Accuracy |Test Error| Duration cycle by seconds |
|-----|---------|----------|----------------|
|1|0.8830882352941176|0.11691176470588238|13|
|2|0.8833650607702446|0.11663493922975543|14|
|3|0.8828516661751695|0.11714833382483048|14|
|4|0.8820758189179242|0.11792418108207581|14|
|5|0.8811764705882353|0.11882352941176466|15|
|6|0.8810474922325788|0.11895250776742117|18|
|7|0.8813133180507284|0.11868668194927157|14|
|8|0.8845780795344326|0.11542192046556743|10|
|9|0.8870165540791329|0.11298344592086706|10|
|10|0.8848467067625055|0.11515329323749446|14|
|11|0.8853611604499704|0.11463883955002963|16|
|12|0.8866652004984972|0.11333479950150283|14|
|13|0.8802706923477356|0.11972930765226442|15|
|14|0.8859882005899705|0.11401179941002948|12|
|15|0.8796199311686368|0.12038006883136321|14|
|16|0.8811054459469445|0.11889455405305549|15|
|17|0.8844143414562321|0.1155856585437679|10|
|18|0.8838103690555432|0.11618963094445678|14|
|19|0.8831312017640573|0.11686879823594265|15|
|20|0.883469843633656|0.116530156366344|11|
|21|0.8819026384243775|0.11809736157562245|14|
|22|0.8841704495533753|0.11582955044662469|19|
|23|0.8811837128235556|0.11881628717644444|15|
|24|0.8807413971348398|0.11925860286516021|14|
|25|0.8836347465164542|0.11636525348354576|14|
|26|0.884080370942813|0.115919629057187|14|
|27|0.8816866413329436|0.11831335866705639|11|
|28|0.883072588418477|0.11692741158152298|14|
|29|0.882058455422219|0.11794154457778105|14|
|30|0.8860956618464961|0.11390433815350387|18|
