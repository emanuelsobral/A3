package ConversorDeTemperatura;

import static ConversorDeTemperatura.ConversorDeTemperatura.criarTela;

import javax.swing.SwingUtilities;

public class converte {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            criarTela();
        });
    }
}
