
//We add the necessary libraries.
import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.classification.DecisionTreeClassifier
import org.apache.spark.ml.classification.DecisionTreeClassificationModel
import org.apache.spark.ml.feature.IndexToString
import org.apache.spark.ml.feature.StringIndexer
import org.apache.spark.ml.feature.VectorIndexer
import org.apache.spark.ml.feature.VectorAssembler

//we add error level code.
import org.apache.log4j._
Logger.getLogger("org").setLevel(Level.ERROR)

//we start spark session.
val spark = SparkSession.builder.appName("DecisionTree").getOrCreate()

//we load the csv file.
val bank  = spark.read.option("header","true").option("inferSchema", "true").option("delimiter",";").format("csv").load("bank-full.csv")
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

//now is necessary union of label and features as dataIndexed.
val dataIndexed = featuresLabel.select("label","features")

//now is necessary created of labelIndexer and featureIndexer for the pipeline, Where features with distinct values > 4, are treated as continuous.
val labelIndexer = new StringIndexer().setInputCol("label").setOutputCol("indexedLabel").fit(dataIndexed)
val featureIndexer = new VectorIndexer().setInputCol("features").setOutputCol("indexedFeatures").setMaxCategories(4).fit(dataIndexed)

//Now is necessary run 30 times for the model
for(i <- Range(1,31))
{
    //We start timer
val startTimeMillis = System.currentTimeMillis()


//now is necessary separeted the values and we splitting the data in 70% and 30%.
val Array(training, test) = dataIndexed.randomSplit(Array(0.7, 0.3))

//we creat the Decision Tree object.
val decisionTree = new DecisionTreeClassifier().setLabelCol("indexedLabel").setFeaturesCol("indexedFeatures")

//we creat the Index to String object.
val labelConverter = new IndexToString().setInputCol("prediction").setOutputCol("predictedLabel").setLabels(labelIndexer.labels)

//we creating the pipeline with the objects created before.
val pipeline = new Pipeline().setStages(Array(labelIndexer, featureIndexer, decisionTree, labelConverter))

//we need fitting the model with training data.
val model = pipeline.fit(training)

//we making the predictions transforming the testData.
val predictions = model.transform(test)

//we need Creati the evaluator.
val evaluator = new MulticlassClassificationEvaluator().setLabelCol("indexedLabel").setPredictionCol("prediction").setMetricName("accuracy")

//now is necessary add the Accuracy.
val accuracy = evaluator.evaluate(predictions)

//we need end the time for the model
val endTimeMillis = System.currentTimeMillis()
val durationSeconds = (endTimeMillis - startTimeMillis) / 1000

//We are printing the all results.
println("|"+i + "|"+accuracy +"|"+ (1.0 - accuracy) +"|" +durationSeconds + "|")
}
