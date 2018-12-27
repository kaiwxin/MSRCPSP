import java.util.Arrays;


public class Test {
    public static void main(String[] args){
        double[] array=new double[]{1.0,3.0,12,Double.POSITIVE_INFINITY,8};
        Arrays.sort(array);
        for(double b:array){
            System.out.print(b+"\n"); 
        }
       
    }
}