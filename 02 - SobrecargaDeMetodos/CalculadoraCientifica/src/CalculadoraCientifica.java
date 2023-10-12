public class CalculadoraCientifica {

    public double potencia(int base, int expoente) {
        return Math.pow(base, expoente);
    }

    public double potencia(double base, double expoente) {
        return Math.pow(base, expoente);
    }

    public double raiz(int numero) {
        return Math.sqrt(numero);
    }

    public double raiz(double numero) {
        return Math.sqrt(numero);
    }

    public double raiz(String numeroStr) {
        double numero = Double.parseDouble(numeroStr);
        return Math.sqrt(numero);
    }
}

