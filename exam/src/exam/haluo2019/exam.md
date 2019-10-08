##### 以下HTTP状态码表示重定向的是
<br>
302

##### 五个半径不同的圆的交点最多有几个
<br>
20

##### 关于二分查找，描述正确的是
<br>
二分查找运用了分治法的思想

##### 以下MySQL数据库引擎MyISAM的描述错误的是
支持行锁
<br>
支持行锁
<br>
如果表主要是用于插入新记录和读出记录，那么选择MyISAM引擎能实现处理高效率
<br>
在执行查询语句（SELECT）前，会自动给涉及的所有表加读锁，在执行更新操作（UPDATE、DELETE、INSERT等）前，会自动给涉及的表加写锁
<br>
不支持事务

##### 直接间接立即三种寻址方式指令的执行速度由快到慢的排序是
立即直接间接

##### 0.6332的数据类型是
double

##### 以下数据结构中部署于线性数据结构的是
二叉树

##### 有4副相同的牌,每副牌有4张不同的牌.先从这16张牌中,随机选4张出来.然后,在这4张牌中随机选择一张牌,然后把抽出的一张放回3张中,再随机选择一张牌.与上次选出的牌一样的概率是()
2/5

##### 已知n阶矩阵A的行列式满足|A|=1,求|A^(-1)|(A^(-1)表示A的逆矩阵）=？
1

##### 删除单链表中的节点p（不是头尾节点）的直接后继节点需要的操作是
p->next->next=p->next;

##### TCP协议与UDP协议负责端到端连接，下列那些信息只出现在TCP报文，UDP报文不包含此信息（ ）
序列号、窗口大小

##### 下列排序算法中，最坏时间复杂度是 O(n log(n)) 的是？
归并，堆排序

##### 下面说法正确的有
字符串是一种对象，字符串是一种数据类型，字符串是一种引用数据类型

##### 以下关于TCP和UDP的描述，正确的是
B C D
<br>
A TCP和UDP提供面向连接的传输，通信前要先建立连接（三次握手）
<br>
B TCP提供可靠的传输，UDP提供不可靠的传输
<br>
C TCP是面向数据报的传输，UDP是面向字节流的传输
<br>
D TCP提供拥塞控制和流量控制机制，UDP不提供

##### 简答题1
小哈老师是非常严厉的，它会要求所有学生在进入教室前都排成一列，并且他要求学生按照身高不递减的顺序排列。
有一次，n个学生在排队的时候，小哈老师正好去卫生间了。学生们终于有机会反击了，于是学生们决定来一次疯狂的队列，他们定义一个队列的疯狂值为每对相邻
学生身高差的绝对值总和。由于按照身高顺序排列的队列疯狂值是最小的，学生们当然决定按照疯狂值最大的顺序进行列队。
现在给出n个学生的身高，请计算出这些学生队列最大可能的疯狂值。
```java
package exam.haluo2019;

import java.util.Arrays;

/**
 * @author SpencerCJH
 * @date 2019/9/13 9:46
 */
public class Test1 {
    public int crazySort(int[] heights) {
        if (heights == null || heights.length <= 1) {
            return 0;
        }
        if (heights.length == 2) {
            return Math.abs(heights[1] - heights[0]);
        }
        Arrays.sort(heights);
        int answer = 0;
        for (int low = 0, high = heights.length - 1; low <= heights.length / 2 && high >= heights.length / 2; low++, high--) {
            if (low == high) {
                answer += Math.abs(heights[high + 1] - heights[low]);
            } else {
                answer += Math.abs(heights[high] - heights[low]);
            }
            if (high != heights.length - 1 && low != heights.length / 2) {
                answer += Math.abs(heights[high + 1] - heights[low]);
            }
        }
        return answer;
    }
}
```

##### 简答题2
有一个长为n的数组A，求满足```0<=a<=b<n```的```A[b]-A[a]```的最大值。给定数组A以及它的大小n，请返回最大差值。例如```[10,5],2```返回0

```java
package exam.haluo2019;

/**
 * @author SpencerCJH
 * @date 2019/9/13 10:15
 */
public class Test2 {
    public int getMin(int[] array, int n) {
        int minValue = array[0];
        int answer = 0;
        for (int i = 1; i < n; ++i) {
            if (answer < array[i] - minValue) {
                answer = array[i] - minValue;
            }
            if (minValue > array[i]) {
                minValue = array[i];
            }
        }
        return answer;
    }
}
```

##### 一个数组长度为10，随机存放0~9的数字，请用你认为最快的排序算法进行从小到大的排序
```java
package exam.haluo2019;

/**
 * @author SpencerCJH
 * @date 2019/9/13 10:22
 */
public class Test3 {
    public int[] mySort(int[] array) {
        if (array == null || array.length <= 1) {
            return array;
        }
        for (int i = 0; i < array.length; i++) {
            while (array[i] != i) {
                int temp = array[i];
                array[i] = array[temp];
                array[temp] = temp;
            }
        }
        return array;
    }
}
```