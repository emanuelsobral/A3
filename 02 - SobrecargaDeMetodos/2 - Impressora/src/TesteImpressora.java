public class TesteImpressora {

    public static void main(String[] args) {
        impressora impressora = new impressora();
        impressora.exibir(1.5f);
        impressora.exibir(2.7f, 0.6f);
        impressora.exibir(3.4f, "TESTE");
        impressora.exibir("TEXTO1", 2.50f);
        impressora.exibir("Gosto", "do", "ceu");
        impressora.exibir(1, 2, "Ta com voce");
    }
}