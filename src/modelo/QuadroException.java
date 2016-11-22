package modelo;

public class QuadroException extends Exception {

    public QuadroException(String message, int tamanho) {
        super(message + " | Tamanho: " + tamanho + " bytes | MAX: 1500 bytes");
    }
}
