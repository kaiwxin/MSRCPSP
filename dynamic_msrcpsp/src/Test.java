
public class Test {
    
    public static void main(String[] args){
        double a=0.5;
        double init=1;
        int L=4;
        double I=atanh(init/L)/a;
        System.out.println(I);
        
        double f=Math.tanh(a*(100+I))*L;
        double result=f<4.0?f:3.0;
        
        System.out.println(result);
    }
    public static double atanh(double x){
        return 0.5*Math.log((1+x)/(1-x));
    }
}