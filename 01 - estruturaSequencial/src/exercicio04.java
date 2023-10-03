import java.util.Locale;
import java.util.Scanner;

public class exercicio04 {
    public static void main(String[] args) throws Exception {

        int numeroFuncionario, horasTrabalhadas ;
        double valorPorHora , calculoSalario;

        Locale.setDefault(Locale.US);

        Scanner sc = new Scanner(System.in);

        numeroFuncionario = sc.nextInt();
        horasTrabalhadas = sc.nextInt();
        valorPorHora = sc.nextDouble();
        calculoSalario = horasTrabalhadas * valorPorHora;

        System.out.println("NUMBER = " + numeroFuncionario + "\nSALARIO = R$ " + String.format("%.2f", calculoSalario));

        sc.close();
    }
}
