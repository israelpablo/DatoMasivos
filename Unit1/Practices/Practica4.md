## Practice #4

### algoritmo 1
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

### algoritmo 2
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
### algoritmo 3
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
