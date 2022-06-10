
## Decision Tree 

We add the necessary libraries.
```
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
```
we add error level code.
```
import org.apache.log4j._
Logger.getLogger("org").setLevel(Level.ERROR)
```
we start spark session.
```
val spark = SparkSession.builder.appName("DecisionTree").getOrCreate()
```
we load the csv file.
```
val bank  = spark.read.option("header","true").option("inferSchema", "true").option("delimiter",";").format("csv").load`("bank-full.csv")
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
Renaming the column y as label.
```
val featuresLabel = features.withColumnRenamed("y", "label")
```
now is necessary union of label and features as dataIndexed.
```
val dataIndexed = featuresLabel.select("label","features")
```
now is necessary created of labelIndexer and featureIndexer for the pipeline, Where features with distinct values > 4, are treated as continuous.
```
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
we creat the Decision Tree object.
```
val decisionTree = new DecisionTreeClassifier().setLabelCol("indexedLabel").setFeaturesCol("indexedFeatures")
```
we creat the Index to String object.
```
val labelConverter = new IndexToString().setInputCol("prediction").setOutputCol("predictedLabel").setLabels(labelIndexer.labels)
```
we creating the pipeline with the objects created before.
```
val pipeline = new Pipeline().setStages(Array(labelIndexer, featureIndexer, decisionTree, labelConverter))
```
we need fitting the model with training data.
```
val model = pipeline.fit(training)
```
we making the predictions transforming the testData.
```
val predictions = model.transform(test)
```
we need Creati the evaluator.
```
val evaluator = new MulticlassClassificationEvaluator().setLabelCol("indexedLabel").setPredictionCol("prediction").setMetricName("accuracy")
```
now is necessary add the Accuracy.
```
val accuracy = evaluator.evaluate(predictions)
```
we need end the time for the model
```
val endTimeMillis = System.currentTimeMillis()
val durationSeconds = (endTimeMillis - startTimeMillis) / 1000
```
We are printing the all results.
```
println("|"+i + "|"+accuracy +"|"+ (1.0 - accuracy) +"|" +durationSeconds + "|")
}
```





### Result
|Cycle|Accuracy |Test Error| Duration cycle by seconds |
|-----|---------|----------|----------------|
|1|0.8915608663181479|0.1084391336818521|1|
|2|0.8937088514996342|0.1062911485003658|1|
|3|0.893221708158919|0.10677829184108101|1|
|4|0.8884950524294787|0.11150494757052132|1|
|5|0.8944752996199942|0.10552470038000583|1|
|6|0.8941528578728374|0.10584714212716262|1|
|7|0.8932526206998376|0.10674737930016243|1|
|8|0.8885192577807348|0.11148074221926518|1|
|9|0.8938144329896908|0.10618556701030923|1|
|10|0.8944021297049471|0.10559787029505285|1|
|11|0.8958627349030677|0.10413726509693233|1|
|12|0.8902546739290446|0.10974532607095544|1|
|13|0.8928729526339088|0.10712704736609124|1|
|14|0.8924009051755603|0.10759909482443974|1|
|15|0.8909802184824328|0.1090197815175672|1|
|16|0.8973810402065658|0.10261895979343416|1|
|17|0.8932179981100531|0.10678200188994691|1|
|18|0.8894332723948811|0.11056672760511888|1|
|19|0.8934569884621002|0.1065430115378998|1|
|20|0.893167701863354|0.10683229813664596|1|
|21|0.8893874803679606|0.11061251963203944|1|
|22|0.8932980599647267|0.10670194003527333|1|
|23|0.8895444361463779|0.1104555638536221|1|
|24|0.8910751410751411|0.10892485892485892|1|
|25|0.8948717948717949|0.10512820512820509|1|
|26|0.893535842688385|0.10646415731161496|1|
|27|0.893015685113939|0.10698431488606097|1|
|28|0.8956344289774798|0.1043655710225202|1|
|29|0.8936716286573293|0.1063283713426707|1|
|30|0.8879615952732643|0.11203840472673565|1|