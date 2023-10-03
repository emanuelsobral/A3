import java.util.Locale;
import java.util.Scanner;

public class exercicio02 {
    public static void main(String[] args) throws Exception {
        double A, Multiplicacao, pi = 3.14159;

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        A = sc.nextDouble();

        Multiplicacao = pi * A * A;

        System.out.printf("A=%.4f%n",Multiplicacao);

        sc.close();
    }
}
