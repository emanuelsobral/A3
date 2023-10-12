public class CalculadoraCientifica {

    public double potencia(byte a, byte b) {
        return Math.pow(a, b);
    }

    public double potencia(String s1, String s2) {
        double num1 = Double.parseDouble(s1);
        double num2 = Double.parseDouble(s2);
        return Math.pow(num1, num2);
    }

    public double potencia(int a, double b) {
        return Math.pow(a, b);
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
