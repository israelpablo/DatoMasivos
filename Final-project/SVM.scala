
//We add the necessary libraries.
import org.apache.spark.sql.SparkSession
import org.apache.spark.mllib.evaluation.MulticlassMetrics
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.ml.classification.LinearSVC
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.feature.StringIndexer
import org.apache.spark.ml.feature.VectorIndexer
import org.apache.spark.ml.feature.VectorAssembler

// Now we need import also the librarie for errors
import org.apache.log4j._
Logger.getLogger("org").setLevel(Level.ERROR)

// Creating a spark session named SVM
val spark = SparkSession.builder.appName("SVM").getOrCreate()

// Importing the dataframe bank
val bank  = spark.read.option("header","true").option("inferSchema", "true").option("delimiter",";").format("csv").load("bank-full.csv")
//now we need check the information for that we need add the next code
bank.show
//show the name of columms for verify 
bank.columns

//we created the indexing.
val labelIndexer = new StringIndexer().setInputCol("y").setOutputCol("indexedLabel").fit(bank)
val indexed = labelIndexer.transform(bank).drop("y").withColumnRenamed("indexedLabel", "label")

//we add the vector of the numeric category columns.
val vectorFeatures = (new VectorAssembler().setInputCols(Array("duration","pdays","previous")).setOutputCol("features"))

//Now we nedd transforming the indexed value.
val features = vectorFeatures.transform(indexed)

//we renaming the column "y" as label.
val featuresLabel = features.withColumnRenamed("y", "label")

//now we union of label and features as dataIndexed.
val dataIndexed = featuresLabel.select("label","features")

//we create of labelIndexer and featureIndexer for the pipeline, Where features with distinct values > 4, are treated as continuous.
val labelIndexer = new StringIndexer().setInputCol("label").setOutputCol("indexedLabel").fit(dataIndexed)
val featureIndexer = new VectorIndexer().setInputCol("features").setOutputCol("indexedFeatures").setMaxCategories(4).fit(dataIndexed)
//Now is necessary run 30 times for the model
for(i <- Range(1,31))
{
    //We start timer
val startTimeMillis = System.currentTimeMillis()
//now is necessary separeted the values and we splitting the data in 70% and 30%.

val Array(training, test) = dataIndexed.randomSplit(Array(0.7, 0.3))

//now is necessay create the object Linear Vector Machine.
val supportVM = new LinearSVC().setMaxIter(10).setRegParam(0.1)
    
//now is necessary fitting trainingData into the model.
val model = supportVM.fit(training)

//Transforming testData for the predictions model.
val predictions = model.transform(test)

//now we obtain the metrics.
val predictionAndLabels = predictions.select($"prediction",$"label").as[(Double, Double)].rdd
val metrics = new MulticlassMetrics(predictionAndLabels)

//this code is for confusion matrix.
println("Confusion matrix:")
println(metrics.confusionMatrix)
//we need end the time for the model
val endTimeMillis = System.currentTimeMillis()
val durationSeconds = (endTimeMillis - startTimeMillis) / 1000


//We are printing the all results.
println("|"+i + "|"+ metrics.accuracy +"|"+ (1.0 - metrics.accuracy)+"|" +durationSeconds + "|")

}