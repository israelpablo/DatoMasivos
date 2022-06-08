<p align="center">
 <img src="https://user-images.githubusercontent.com/77422159/157056166-aa1ef8bd-fa1d-42c0-8846-860d0e81f54f.png">
  </p>

<h1 align="center"> Instituto Tecnológico de Tijuana </h1>
<h3 align="center"> Subdirección académica departamento de sistemas y computación.</h3>
<h4 align="center"> Datos Masivos</h4>

<h4 align="center"> JOSE CHRISTIAN ROMERO HERNANDEZ.</h4>


<h4 align="center"> Unidad 4</h4>

<h4 align="center"> Proyecto final</h4>

<h4 align="center"> Perez Ortega Victoria Valeria 18210718</h4>
<h4 align="center"> Lopez Pablo Israel 17210585</h4>



##

## Multilayer Perceptron



We add the necessary libraries.
```
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.IntegerType
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.feature.StringIndexer 
import org.apache.spark.ml.feature.VectorAssembler
```
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

[Codigo completo](https://github.com/israelpablo/DatoMasivos/blob/Final-project/Final-project/Multilayer.scala)
##

## Logistic Regression
We add the necessary libraries.
```
import org.apache.spark.sql.SparkSession
import org.apache.spark.mllib.evaluation.MulticlassMetrics
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.feature.StringIndexer
import org.apache.spark.ml.feature.VectorIndexer
```
we add error level code.
```
import org.apache.log4j._
Logger.getLogger("org").setLevel(Level.ERROR)
```
we start spark session.
```
val spark = SparkSession.builder.appName("LogisticRegression").getOrCreate()
```
we load the csv file.
```
val bank  = spark.read.option("header","true").option("inferSchema", "true").option("delimiter",";").format("csv").load("bank-additional-full.csv")
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
now we union of label and features as dataIndexed.
```
val dataIndexed = featuresLabel.select("label","features")
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
val Array(trainingData, testData) = dataIndexed.randomSplit(Array(0.7, 0.3))
```
Logistic regression object.
```
val logisticReg = new LogisticRegression().setMaxIter(10).setRegParam(0.3).setElasticNetParam(0.8).setFamily("multinomial")
```
Fitting the model with the training data.
```
val model = logisticReg.fit(trainingData)
```
Making the predictions transforming the testData.
```
val predictions = model.transform(testData)
```
Obtaining the metrics.
```
val predictionAndLabels = predictions.select($"prediction",$"label").as[(Double, Double)].rdd
val metrics = new MulticlassMetrics(predictionAndLabels)
```
Confusion matrix.
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

[Codigo Completo](https://github.com/israelpablo/DatoMasivos/blob/Final-project/Final-project/Regresion.scala)
##

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

[Codigo Completo](https://github.com/israelpablo/DatoMasivos/blob/Final-project/Final-project/SVM.scala)
##
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

[Codigo Completo](https://github.com/israelpablo/DatoMasivos/blob/Final-project/Final-project/Tree.scala)


## Comparison between the models

|Model| Average Accuracy |  Average Test Error|  Average Duration cycle by seconds |
|-----|---------|----------|----------------|
|Multilayer Perceptron| 0.88333777|	0.11666223|	6.466666667|
|Logistic Regression| 15.5	0.882768719|	0.117231281|	1|
|SVM| 15.5	0.883127415|	0.116872585|	13.96666667	|
|Decision Tree|15.5	0.892554255	|0.107445745|	1|
											 		
## Conclutions

