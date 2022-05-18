/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// scalastyle:off println
package org.apache.spark.examples.ml

// $example on$
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.{RandomForestClassificationModel, RandomForestClassifier}
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorIndexer}
// $example off$
import org.apache.spark.sql.SparkSession

object RandomForestClassifierExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .appName("RandomForestClassifierExample")
      .getOrCreate()

    // $example on$
    // Load and parse the data file, converting it to a DataFrame.
    val data = spark.read.format("libsvm").load("c:/Spark/data/mllib/sample_libsvm_data.txt")

    // Index labels, adding metadata to the label column.
    // Fit on whole dataset to include all labels in index.
    val labelIndexer = new StringIndexer()
      labelIndexer.setInputCol("label")
      labelIndexer.setOutputCol("indexedLabel")
     labelIndexer.setHandleInvalid("skip")
      labelIndexer.fit(data)


      val labelindexed = labelIndexer.fit(data).transform(data)

    // Automatically identify categorical features, and index them.
    // Set maxCategories so features with > 4 distinct values are treated as continuous.
    val featureIndexer = new VectorIndexer()
      featureIndexer.setInputCol("features")
      featureIndexer.setOutputCol("indexedFeatures")
      featureIndexer.setMaxCategories(4)
     featureIndexer.setHandleInvalid("skip")
      featureIndexer.fit(data)

   // Split the data into training and test sets (30% held out for testing).
    val Array(trainingData, testData) = data.randomSplit(Array(0.7, 0.3))

    // Train a RandomForest model.
    val rf = new RandomForestClassifier()
      rf.setLabelCol("indexedLabel")
      rf.setFeaturesCol("indexedFeatures")
      rf.setNumTrees(10)

    // Convert indexed labels back to original labels.
    val labelConverter = new IndexToString()
      labelConverter.setInputCol("prediction")
      labelConverter.setOutputCol("predictedLabel")
      labelConverter.setLabels(labelindexed.schema("indexedLabel").metadata.getMetadata("ml_attr").getStringArray("vals"))



    // Chain indexers and forest in a Pipeline.
    val pipeline = new Pipeline()
      pipeline.setStages(Array(labelIndexer, featureIndexer, rf, labelConverter))

    // Train model. This also runs the indexers.
    val model = pipeline.fit(trainingData)

    // Make predictions.
    val predictions = model.transform(testData)

    // Select example rows to display.
    predictions.select("predictedLabel", "label", "features").show(5)

    // Select (prediction, true label) and compute test error.
    val evaluator = new MulticlassClassificationEvaluator()
      evaluator.setLabelCol("indexedLabel")
      evaluator.setPredictionCol("prediction")
      evaluator.setMetricName("accuracy")
    val accuracy = evaluator.evaluate(predictions)
    println(s"Test Error = ${(1.0 - accuracy)}")

    val rfModel = model.stages(2).asInstanceOf[RandomForestClassificationModel]
    println(s"Learned classification forest model:\n ${rfModel.toDebugString}")
    // $example off$

    spark.stop()
  }
}
// scalastyle:on println
