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