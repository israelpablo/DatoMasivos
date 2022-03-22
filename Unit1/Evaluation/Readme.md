### 1. Comienza una simple sesión Spark.
To log in to Spark, first we open the cmd as administrator, we run Spark with
spark-shell and immediately we import a library to be able to log in

```
import org.apache.spark.sql.SparkSession 
```
We declare the SparkSession variable and use the library to create a new session

```
val session = SparkSession.builder().getOrCreate()
```
### 2. Cargue el archivo Netflix Stock CSV, haga que Spark infiera los tipos de datos. 
```
val netflix = spark.read.option("header","true").option("inferSchema","true")csv("Netflix_2011_2016.csv")
```

### 3. ¿Cuáles son los nombres de las columnas? 
We send to call our netflix variable that with that name we load the Netflix Stock CSV file, 
and what we want to see are the columns
```
netflix.columns
```

### 4. ¿Cómo es el esquema? 
We send to call our netflix variable that with that name we load the Netflix Stock CSV file, 
we send to print the scheme that is the one we want to see

```
netflix.printSchema()
```

### 5. Imprime las primeras 5 columnas. 
We send to call our netflix variable that with that name we load the Netflix Stock CSV file
and select the first 5 columns

```
netflix.select($"Date",$"Open",$"High",$"Low",$"Close").show()
```

### 6. Usa describe () para aprender sobre el DataFrame. 
```
netflix.describe().show()
```

### 7. Crea un nuevo dataframe con una columna nueva llamada “HV Ratio” que es la relación que  
### existe entre el precio de la columna “High” frente a la columna “Volumen” de acciones  negociadas
### por un día. Hint - es una operación 
We declare a new varibale called netflixnew
```
var netflixnew = netflix.withColumn("HV Ratio", netflix("High")/netflix("Volume"))
```

### 8. ¿Qué día tuvo el pico más alto en la columna “Open”? 
We declare a variable called columnday and from the netflix file we take the data of the day column

```
val columnday = netflix.withColumn("Day",dayofmonth(netflix("Date")))
```

We declare a variable called netflixdaycolumn and we are going to group all the maximum values ​​of the day

```
val netflixdaycolumn = columnday.groupBy("Day").max()
```

we call the variable that we declare to show the table

```
netflixdaycolumn.show()
```

we send to call the variable that we declare and select the maximum day of the maxopen column

```
netflixdaycolumn.select($"Day",$"max(Open)").show()
```

### 9. ¿Cuál es el significado de la columna Cerrar “Close” en el contexto de información financiera,  
explíquelo que no hay que codificar nada? 
the close column means that the company ended the day or in this case that netflix ended the day

### 10. ¿Cuál es el máximo y mínimo de la columna “Volumen”?
from the netflix file we call the maximum and minimum value of the volume column

```
netflix.select(max("Volume"), min("Volume")).show()
```



### 11.With Scala/Spark Syntax $ answer the following: 
### a. ¿How many days was the “Close” column less than $600? 
For this only we need filter close with a condition and that condition is less that.
```
val result =netflix.filter($"Close" < 600 ).count()
```
#### Result:
here we can see the result 
![evaluacion 11-1](https://user-images.githubusercontent.com/77422159/159494727-0804d87e-0000-406b-8ade-d85c14040920.PNG)

### b. ¿What percentage of the time was the "High" column greater than $500? 
This is the  oppositive case  that the previous question 
```
val result = (netflix.filter( $"High" > 500).count()*1.0/netflix.count())*100
```

#### Result:
Here we can see the result 
![evaluacion 11-2](https://user-images.githubusercontent.com/77422159/159494906-178436b2-2607-4ace-9349-46b951aa3bac.PNG)

### c. ¿What is the Pearson correlation between the “High” column and the “Volume” column? 
Pearson's correlation coefficient is a test that measures the statistical relationship between two continuous variables.
so in this case specifically we are using for two columns High and Volume
```
netflix.select(corr($"High",$"Volume")).show()
```
#### Result:
This is the result for use the pearson correlation
![evaluacion 11-3](https://user-images.githubusercontent.com/77422159/159494927-ccc4afca-897a-4568-ac27-6f0ddffd900c.PNG)

### d. ¿What is the maximum of the “High” column per year? 
In this case we need get a maximun so we use a funtion max for get the result
```
netflix.select(max($"High")).show()
```
#### Result:
This is the results
![evaluacion 11-4](https://user-images.githubusercontent.com/77422159/159494943-3107763f-42a7-49b9-beaa-2c169dc09505.PNG)

