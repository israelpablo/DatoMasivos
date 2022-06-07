//We add the necessary libraries.
import org.apache.spark.sql.SparkSession
import org.apache.spark.mllib.evaluation.MulticlassMetrics
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.feature.StringIndexer
import org.apache.spark.ml.feature.VectorIndexer
//we add error level code.
import org.apache.log4j._
Logger.getLogger("org").setLevel(Level.ERROR)

//we start spark session.
val spark = SparkSession.builder.appName("LogisticRegression").getOrCreate()

//we load the csv file.
val bank  = spark.read.option("header","true").option("inferSchema", "true").option("delimiter",";").format("csv").load("C:/Users/valer/Documents/9no Semestre/Datos Masivos/Unit 4")

//now we need check the information for that we need add the next code
bank.show
//show the name of columms for verify 
bank.columns

//we created the indexing.
val labelIndexer = new StringIndexer().setInputCol("y").setOutputCol("indexedLabel").fit(bank)
val indexed = labelIndexer.transform(bank).drop("y").withColumnRenamed("indexedLabel", "label")

//we add the vector of the numeric category columns.
val vectorFeatures = (new VectorAssembler().setInputCols(Array("balance","day","duration","pdays","previous")).setOutputCol("features"))

//Now we nedd transforming the indexed value.
val features = vectorFeatures.transform(indexed)

//Renaming the column y as label.
val featuresLabel = features.withColumnRenamed("y", "label")

//now we union of label and features as dataIndexed.
val dataIndexed = featuresLabel.select("label","features")
//Now is necessary run 30 times for the model
for(i <- Range(1,31))
{
    //We start timer
val startTimeMillis = System.currentTimeMillis()
//now is necessary separeted the values and we splitting the data in 70% and 30%.
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
//we need end the time for the model
val endTimeMillis = System.currentTimeMillis()
val durationSeconds = (endTimeMillis - startTimeMillis) / 1000
//We are printing the all results.
println("|"+i + "|"+ metrics.accuracy +"|"+ (1.0 - metrics.accuracy)+"|" +durationSeconds + "|")

}