import java.util.Scanner;

public class TesteCalculadora {

    public static void main(String[] args) {
        CalculadoraCientifica calculadora = new CalculadoraCientifica();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Escolha a operação (1 para potência, 2 para raiz, Q para sair):");
        String operacao = scanner.nextLine();

        switch (operacao) {
            case "1":
                System.out.println("Digite dois números para a potência:");
                String input1 = scanner.nextLine();
                String input2 = scanner.nextLine();

                try {
                    int num1 = Integer.parseInt(input1);
                    int num2 = Integer.parseInt(input2);
                    System.out.println("Potência: " + calculadora.potencia(num1, num2));
                } catch (NumberFormatException e) {
                    try {
                        double num1 = Double.parseDouble(input1);
                        double num2 = Double.parseDouble(input2);
                        System.out.println("Potência: " + calculadora.potencia(num1, num2));
                    } catch (NumberFormatException ex) {
                        System.out.println("Os inputs não são números válidos.");
                    }
                }
                break;
            case "2":
                System.out.println("Digite um número para a raiz:");
                String input = scanner.nextLine();

                try {
                    int num = Integer.parseInt(input);
                    System.out.println("Raiz: " + calculadora.raiz(num));
                } catch (NumberFormatException e) {
                    try {
                        double num = Double.parseDouble(input);
                        System.out.println("Raiz: " + calculadora.raiz(num));
                    } catch (NumberFormatException ex) {
                        System.out.println("O input não é um número válido.");
                    }
                }
                break;
            default:
                System.out.println("Operação inválida.");
        }

        scanner.close();
    }
}
