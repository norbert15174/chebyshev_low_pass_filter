import java.lang.Math;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Chebyshev {

    public static void main(String[] args) {

        //init default data
        double fc = 1000;
        double ripple = 1;
        int orderN = 2;
        double Z = 100;
        int g0 = 1;
        final double scale = 17.37;

        //Get data from user
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the fc in MHz: ");
        fc = scanner.nextDouble();
        System.out.println("Enter the ripple in dB: ");
        ripple = scanner.nextDouble();
        System.out.println("Enter the order: ");
        orderN = scanner.nextInt();
        System.out.println("Enter the Z0: ");
        Z = scanner.nextDouble();

        //Hz -> MHz
        fc *= Math.pow(10,6);


        //calculation of beta,gamma
        double beta = Math.log(Math.cosh(ripple/scale)/Math.sinh(ripple/scale));
        double gamma = Math.sinh(beta/(2*orderN));

        //init a,b
        List<Double> a = new ArrayList<>();
        List<Double> b = new ArrayList<>();


        //calculation of a,b
        for(int i=1; i<=orderN; i++){
            a.add(Math.sin((2*i-1)*Math.PI/(2*orderN)));
            b.add(Math.pow(gamma,2)+Math.pow(Math.sin((i*Math.PI)/orderN),2));
        }

        //init g
        List<Double> g = new ArrayList<>();

        //calculation of g[0]
        g.add(2*a.get(0)/gamma);



        //calculation of others g params
        for(int i=1;i<orderN;i++) g.add(((4*a.get(i-1)*a.get(i))/(b.get(i-1)*g.get(i-1))));


        double gama_L = Z/g0;
        double gama_C = g0 * Z;

        //Init C and L
        List<Double> C = new ArrayList<>();
        List<Double> L = new ArrayList<>();

        //calculation of C,L values
        for(int i=0;i<orderN;i++) {
            if ((i + 1) % 2 == 0) {
                L.add((1 / (2 * Math.PI * fc)) * gama_L * g.get(i));
            } else {
                C.add((1 / (2 * Math.PI * fc)) * (g.get(i)/gama_C));
            }
        }

        //print results
        System.out.println("C[F]: " + C);
        System.out.println("L[H]: " + L);



    }



}
