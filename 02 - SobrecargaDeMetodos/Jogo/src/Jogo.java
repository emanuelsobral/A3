import java.util.Scanner;

public class Jogo {
    public static void main(String[] args) throws InterruptedException {
        Personagem cacador = new Personagem(10, 0, 0);
        Personagem soneca = new Personagem(2, 6, 4);
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o nome do novo personagem: ");
        String nomeNovoPersonagem = scanner.nextLine();
        Personagem novoPersonagem = new Personagem(5, 5, 5);
        novoPersonagem.nome = nomeNovoPersonagem;
        cacador.nome = "John";
        soneca.nome = "Soneca";
        scanner.close();
        while (true) {
            cacador.cacar();
            soneca.dormir();
            cacador.comer();
            soneca.dormir();
            cacador.dormir();
            soneca.dormir();
            cacador.cacar();
            soneca.comer();
            cacador.cacar();
            soneca.cacar();
            novoPersonagem.dormir();
            novoPersonagem.cacar();
            novoPersonagem.comer();
            System.out.println("====================");
            Thread.sleep(3000);
        }
    }
}