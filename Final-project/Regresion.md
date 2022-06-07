
## Logistic Regression

Start timer
```
val startTimeMillis = System.currentTimeMillis()
```

Importing this libraries is required in order to get the example done.
```
import org.apache.spark.sql.SparkSession
import org.apache.spark.mllib.evaluation.MulticlassMetrics
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.feature.StringIndexer
import org.apache.spark.ml.feature.VectorIndexer
```
Error level code.
```
import org.apache.log4j._
Logger.getLogger("org").setLevel(Level.ERROR)
```
Spark session.
```
val spark = SparkSession.builder.appName("LogisticRegression").getOrCreate()

//Reading the csv file.
val bank  = spark.read.option("header","true").option("inferSchema", "true").option("delimiter",";").format("csv").load("bank-additional-full.csv")

//Indexing.
val labelIndexer = new StringIndexer().setInputCol("y").setOutputCol("indexedLabel").fit(bank)
val indexed = labelIndexer.transform(bank).drop("y").withColumnRenamed("indexedLabel", "label")

//Vector of the numeric category columns.
val vectorFeatures = (new VectorAssembler().setInputCols(Array("balance","day","duration","pdays","previous")).setOutputCol("features"))

//Transforming the indexed value.
val features = vectorFeatures.transform(indexed)

//Renaming the column y as label.
val featuresLabel = features.withColumnRenamed("y", "label")

//Union of label and features as dataIndexed.
val dataIndexed = featuresLabel.select("label","features")

//Training data as 70% and test data as 30%.
val Array(trainingData, testData) = dataIndexed.randomSplit(Array(0.7, 0.3))

//Logistic regression object.
val logisticReg = new LogisticRegression().setMaxIter(10).setRegParam(0.3).setElasticNetParam(0.8).setFamily("multinomial")

//Fitting the model with the training data.
val model = logisticReg.fit(trainingData)

//Making the predictions transforming the testData.
val predictions = model.transform(testData)

//Obtaining the metrics.
val predictionAndLabels = predictions.select($"prediction",$"label").as[(Double, Double)].rdd
val metrics = new MulticlassMetrics(predictionAndLabels)

//Confusion matrix.
println("Confusion matrix:")
println(metrics.confusionMatrix)

//Accuracy and Test Error.
println("Accuracy: " + metrics.accuracy) 
println(s"Test Error: ${(1.0 - metrics.accuracy)}")

val endTimeMillis = System.currentTimeMillis()
val durationSeconds = (endTimeMillis - startTimeMillis) / 1000

//Print the time in seconds that took the whole algorithm to compile
println(durationSeconds)
```
### Result 
|Cycle|Accuracy |Test Error| Duration cycle by seconds |
|-----|---------|----------|----------------|
|1|0.8807743070831501|0.11922569291684992|1|
|2|0.8855346831371975|0.11446531686280248|1|
|3|0.8809576590556417|0.11904234094435828|1|
|4|0.8836392891099246|0.11636071089007538|1|
|5|0.8822389505850786|0.11776104941492138|1|
|6|0.883510054844607|0.11648994515539302|1|
|7|0.8843807423741271|0.11561925762587288|1|
|8|0.8827889908256881|0.11721100917431193|1|
|9|0.880214848061217|0.11978515193878303|1|
|10|0.8784362991427727|0.12156370085722734|1|
|11|0.8835383019706623|0.1164616980293377|1|
|12|0.880204474736998|0.11979552526300197|1|
|13|0.8816238681905892|0.11837613180941076|1|
|14|0.8817442719881744|0.1182557280118256|1|
|15|0.8854804217978025|0.11451957820219749|1|
|16|0.8815700934579439|0.1184299065420561|1|
|17|0.8855599940819648|0.11444000591803516|1|
|18|0.8812047664302947|0.11879523356970534|1|
|19|0.8836577304443628|0.1163422695556372|1|
|20|0.8855688357680389|0.11443116423196109|1|
|21|0.8885282183696053|0.11147178163039473|1|
|22|0.8828541790160047|0.11714582098399529|1|
|23|0.8804050558060462|0.11959494419395378|1|
|24|0.8787678282605499|0.12123217173945011|1|
|25|0.8808570581257414|0.11914294187425856|1|
|26|0.8838885212147952|0.11611147878520478|1|
|27|0.8815981335666375|0.11840186643336248|1|
|28|0.8829958920187794|0.11700410798122063|1|
|29|0.8867225146415598|0.11327748535844018|1|
|30|0.8838155849922297|0.11618441500777033|1|