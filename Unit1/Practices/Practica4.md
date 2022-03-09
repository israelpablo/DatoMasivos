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

