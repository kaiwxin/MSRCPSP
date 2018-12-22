import evolutionary_algorithms.CommonUtil;

public class Test {
    public static void main(String[] args){
        int[] array=new int[]{1,3,4,5,8,12};
        double target=4.6;
        int result=CommonUtil.binarySearch(array, target);
        System.out.println(result); 
    }
}