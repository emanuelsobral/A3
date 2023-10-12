import java.util.Scanner;
import java.util.Locale;

public class TesteCalculadora {

    public static void main(String[] args) { 
        Locale.setDefault(Locale.US);
        CalculadoraCientifica calculadora = new CalculadoraCientifica();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Escolha a operação (1 para potência, 2 para raiz, Q para sair):");
        String operacao = scanner.nextLine();

        switch (operacao) {
            case "1":
                System.out.println("Escolha os tipos de input da potencia \n (1 para byte, 2 para inteiros, 3 para doubles):");
                String potenciaEscolha = scanner.nextLine();

                switch (potenciaEscolha) {
                    case "1":
                        System.out.println("Digite a base:");
                        byte baseByte = scanner.nextByte();
                        System.out.println("Digite o expoente:");
                        byte expoenteByte = scanner.nextByte();
                        System.out.println("Resultado: " + calculadora.potencia(baseByte, expoenteByte));
                        break;
                    case "2":
                        System.out.println("Digite um número inteiro:");
                        int baseInt = scanner.nextInt();
                        System.out.println("Digite um número decimal:");
                        double expoenteDouble = scanner.nextDouble();
                        System.out.println("Resultado: " + calculadora.potencia(baseInt, expoenteDouble));
                        break;
                    case "3":
                        System.out.println("Digite um número decimal:");
                        String baseStr = scanner.next();
                        System.out.println("Digite um número decimal:");
                        String expoenteStr = scanner.next();
                        System.out.println("Resultado: " + calculadora.potencia(baseStr, expoenteStr));
                        break;
                    default:
                        System.out.println("Operação inválida.");
                    }
                    break;
            case "2":
                System.out.println("Escolha os tipos de input da raiz \n (1 para inteiro, 2 para double, 3 para inteiroSTR):");
                String raizEscolha = scanner.nextLine();
                
                switch (raizEscolha) {
                    case "1":
                        System.out.println("Digite um número inteiro:");
                        int numeroInt = scanner.nextInt();
                        System.out.println("Resultado: " + calculadora.raiz(numeroInt));
                        break;
                    case "2":
                        System.out.println("Digite um número decimal:");
                        double numeroDouble = scanner.nextDouble();
                        System.out.println("Resultado: " + calculadora.raiz(numeroDouble));
                        break;
                    case "3":
                        System.out.println("Digite um número decimal:");
                        String numeroStr = scanner.next();
                        System.out.println("Resultado: " + calculadora.raiz(numeroStr));
                        break;
                    default:
                        System.out.println("Operação inválida.");
                    break;
                }
            default:
                System.out.println("Operação inválida.");
        }

        scanner.close();
    }
}


