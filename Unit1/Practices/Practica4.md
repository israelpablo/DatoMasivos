## Practice #4

### Algoritmo 1
In this algorithm, a recursive method is presented that iterates until the added number is 2.
``` 
def algo1(num1:Int): Int = {

if (num1<2)
{
    return num1
}
else
{
    return algo1(num1-1) + algo1(num1-2)
}
}
algo1(10)

``` 
#### Result
![practica 4-1](https://user-images.githubusercontent.com/77422159/157471233-74355d2c-3d5c-4c4e-9853-b9285349ca9a.PNG)

### Algoritmo 2
For this algorithm, it returns the entered value if it is less than 2, otherwise it performs some mathematical operations which is to take the square root and exponentiate, in the same way this is a recursion method
``` 
def algo2(num1:Int): Int = {

if (num1<2)
{
    return num1
}
else
{
 var p=((1+  Math.sqrt(5))/2)
var j=((Math.pow(p,num1 )  - Math.pow((1-p), num1) )/( Math.sqrt(5)))
    return algo1(num1-1) + algo1(num1-2)
}
}
algo2(10)

``` 
#### Result
![practica 4-2](https://user-images.githubusercontent.com/77422159/157471264-2c3e2ff1-90c7-411e-a762-011dbcc08726.PNG)

### Algoritmo 3
For this algorithm, the only thing that must be done is to iterate from 0 to the number entered, within the iteration the sequence of sums is performed to return the result
``` 
def algo3(num1:Int): Int = {
var a=0
var b=1
var c=0
for(n <- Range(0, num1)){
       c=b+a
       a=b
       b=c
    }
return a

}
algo3(10)

``` 
#### Result
![practica 4-3](https://user-images.githubusercontent.com/77422159/157471305-fcfa3cbf-f75f-4af0-9307-f4eba0b2ca44.PNG)

### Algoritmo 4
Similar to the previous one but it is started from 1 to the number entered, it performs the same operations as the other one, only with fewer variables
``` 
def algo4(num1:Int): Int = {
var a=0
var b=1

for(n <- Range(1, num1)){
      b=b+a
      a=b-a
    }
return b

}
algo4(10)

```
#### Result
![practica 4-4](https://user-images.githubusercontent.com/77422159/157471367-dfbafa2e-5a91-466f-9591-2db5acf0ba0f.PNG)

### Algoritmo 5
For this algorithm it is necessary to create arrays in which it will be iterated and from there the result will be obtained, this, unlike the others, will start at 2 and 1 will be added to the number entered
``` 
def algo5(num1:Int): Int ={

if (num1<2){
return num1
}
else
{
var vector=Array.range(0, (num1 + 1) )
vector(0)=0
vector(1)=1
for(n <-Range(2, (num1 + 1)) ){
vector(n)=vector(n-1)+vector(n-2)
}
return vector(num1)
}
}
algo5(10)

```
#### Result
![practica 4-5](https://user-images.githubusercontent.com/77422159/157471385-cdc64fdf-7071-40f6-b6e7-3a91844fbb67.PNG)
