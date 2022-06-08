
 


//We add the necessary libraries.
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.IntegerType
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.feature.StringIndexer 
import org.apache.spark.ml.feature.VectorAssembler

//we add error level code.
import org.apache.log4j._
Logger.getLogger("org").setLevel(Level.ERROR)

//we start spark session.
val spark = SparkSession.builder.appName("MultilayerPerceptron").getOrCreate()

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

//now is necessary fitting indexed and finding labels 0 and 1.
val labelIndexer = new StringIndexer().setInputCol("label").setOutputCol("indexedLabel").fit(indexed)


//Now is necessary run 30 times for the model
for(i <- Range(1,31))
{
    //We start timer
val startTimeMillis = System.currentTimeMillis()
//now is necessary separeted the values and we splitting the data in 70% and 30%.
val splits = features.randomSplit(Array(0.7, 0.3))
val trainingData = splits(0)
val testData = splits(1)

//we creating the layers array.
val layers = Array[Int](5, 4, 1, 2)

//now is necessay create the Multilayer Perceptron object of the Multilayer Perceptron Classifier.
val multilayerP = new MultilayerPerceptronClassifier().setLayers(layers).setBlockSize(128).setSeed(1234L).setMaxIter(100)  

//now is necessary fitting trainingData into the model.
val model = multilayerP.fit(trainingData)

//Transforming the testData for the predictions.
val prediction = model.transform(testData)

//Now is necessary select the prediction and label columns.
val predictionAndLabels = prediction.select("prediction", "label")

//we created a Multiclass Classification Evaluator object.
val evaluator = new MulticlassClassificationEvaluator().setMetricName("accuracy")

//we need end the time for the model
val endTimeMillis = System.currentTimeMillis()
val durationSeconds = (endTimeMillis - startTimeMillis) / 1000
//We are printing the all results.
println("|"+i + "|"+evaluator.evaluate(predictionAndLabels) +"|"+ (1.0 - evaluator.evaluate(predictionAndLabels))+"|" +durationSeconds + "|")
}

