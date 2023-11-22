import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfaceGrafica extends JFrame {
 
    private JLabel intensidadeLabel;

    public InterfaceGrafica() {
        setTitle("Minha Interface Gráfica");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(6, 2));

        JLabel nomeLabel = new JLabel("Nome:");
        JLabel alturaLabel = new JLabel("Altura:");
        JLabel idadeLabel = new JLabel("Idade:");
        JLabel pesoLabel = new JLabel("Peso:");
        JLabel frequenciaLabel = new JLabel("Frequência:");
        JLabel generoLabel = new JLabel("Gênero:");

        JButton escolherExerciciosButton = new JButton("Escolher Exercícios");
        escolherExerciciosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                abrirSegundaInterface();
            }
        });

        panel.add(nomeLabel);
        panel.add(new JLabel());
        panel.add(alturaLabel);
        panel.add(new JLabel());
        panel.add(idadeLabel);
        panel.add(new JLabel());
        panel.add(pesoLabel);
        panel.add(new JLabel());
        panel.add(frequenciaLabel);
        panel.add(new JLabel());
        panel.add(generoLabel);
        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(escolherExerciciosButton);

        add(panel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void abrirSegundaInterface() {
        JFrame segundaJanela = new JFrame("Segunda Interface Gráfica");
        segundaJanela.setSize(400, 300);
        segundaJanela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(7, 1));

        JButton caminhadaButton = criarBotao("Caminhada", "Fácil");
        JButton corridaButton = criarBotao("Corrida", "Moderado");
        JButton natacaoButton = criarBotao("Natação", "Difícil");
        JButton iogaButton = criarBotao("Ioga", "Fácil");
        JButton musculacaoButton = criarBotao("Musculação", "Moderado");
        JButton boxeButton = criarBotao("Boxe", "Difícil");

        intensidadeLabel = new JLabel();

        JButton voltarButton = new JButton("Voltar");
        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                segundaJanela.dispose();
                new InterfaceGrafica();
            }
        });

        panel.add(caminhadaButton);
        panel.add(corridaButton);
        panel.add(natacaoButton);
        panel.add(iogaButton);
        panel.add(musculacaoButton);
        panel.add(boxeButton);
        panel.add(intensidadeLabel);
        panel.add(voltarButton);

        segundaJanela.add(panel, BorderLayout.CENTER);

        segundaJanela.setLocationRelativeTo(null);
        segundaJanela.setVisible(true);
    }

    private JButton criarBotao(String texto, String intensidade) {
        JButton botao = new JButton(texto);
        botao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                intensidadeLabel.setText("Intensidade: " + intensidade);
            }
        });
        return botao;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InterfaceGrafica();
            }
        });
    }
}
