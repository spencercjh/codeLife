# 上海嬴彻科技有限公司 后端开发(Java)实习岗面经

## 一面

### 说说几种join的区别,join on和where的区别,关系

### 算法题1

二叉搜索树的左子节点要比根节点小，右节点要比根节点大。


如果给你 n 个数 （从 1 到 n），如果把他们组成二叉搜索树，有多少种不同的组成方法。

```java
public class Main {
   public int fun(int n){
       int[] g=new int[n+1];
       g[0]=1;
       g[1]=1;
       for(int i=2;i<n+1;i++){
           for(int j=1;j<=i;j++){
               g[i]+=g[j-1]*g[i-j];
           }
       }
       return g[n];
   }

    public static void main(String... args){
        Main main=new Main();
        System.out.println(main.fun(0));
    }
}
```

### 算法题2

一款游戏棋，从起点出发，走到终点结束。这个游戏棋的规则如下：

每个格子上都写了一个数字，每次前进的步数可以自由选择，但不能大于这个数

求：
如果你是一个玩家，你最少可以用多少次走完。

示例：
输入数组：
| 3 | 2 | 4 | 1 | 3 | 2 | 2 | 1 | 5 | 🚩|
 😊     🚶‍               🚶‍     🚶‍🚶‍
 3   _    4   _   _   _   2   _  5    #

输出：4  （需要 4 次走完）

```java
public class Main {
    public static void main(String[] args) {
        System.out.println(fun(new int[]{3,2,4,1,3,2,2,1,5}));
    }

    public static int fun(int[] steps){
        if(steps==null||steps.length==0){
            return 0;
        }
        int n=steps.length;
        int skipNums=0;
        int answer=0;
        for(int i=0;i<n;){
            int maxLocaltion=i+steps[i];
            int maxIndex=i;
             System.out.println("i:"+i);
             for(int j=i+1+skipNums;j<=i+steps[i]&&j<n;j++){
                int innerLocation=j+steps[j];
                 System.out.println("innerStep:"+innerLocation+" j:"+j);
                if(innerLocation>=maxLocaltion){
                    maxLocaltion=innerLocation;
                    maxIndex=j;
                    skipNums=steps[i]-maxIndex;
                }
            }
             System.out.println("maxIndex:"+maxIndex);
            answer++;
            i+=steps[i];
        }
        return answer;
    }
}
```

## 二面

### 算法题1

Given two arrays arr1 and arr2, the elements of arr2 are distinct, and all elements in arr2 are also in arr1.

Sort the elements of arr1 such that the relative ordering of items in arr1 are the same as in arr2.  Elements that don't appear in arr2 should be placed at the end of arr1 in ascending order.

Example 1:

Input: arr1 = [2,3,1,3,2,4,6,7,9,2,19],
arr2 = [2,1,4,3,9,6] Output: [2,2,2,1,4,3,3,9,6,7,19]

bonus:结合实际业务场景优化代码

```java
public class SortServiceImpl implements SortService  {
    @Value("${your.array.logic}")
    private Integer[] logic;

    private Map<Integer,Integer> indexMap=null;

    @Override
    public void sort(int[] array){
        if(array==null){
            throw new IllegalArgumentException();
        }
        if(logic==null||logic.length==0){
            return;
        }
        mySortImpl(array,logic);
    }


    public void mySortImpl(int[] array1,int[] array2){
        if(indexMap==null){
            indexMap=new HashMap<>();
            for(int i=0;i<array2.length;i++){
                indexMap.put(array2[i],i);
            }
        }
        Arrays.sort(array1,(o1,o2)->{
            if(o1==o2){
                return 0;
            }
            int indexA=indexMap.getOrDefault(o1,array2.length);
            int indexB=indexMap.getOrDefault(o2,array2.length);
            if(indexA!=indexB){
                return indexA-indexB;
            }else{
                return o1-o2;
            }
        });
    }
}
```