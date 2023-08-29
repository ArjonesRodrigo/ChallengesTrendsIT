// Importa a classe Scanner do pacote java.util para permitir a entrada de dados pelo usuario.
import java.util.Scanner;

// Super classe para decriptar mensagens encriptadas. 
class Decriptador {
    private static String palavraConhecida; // Palavra conhecida para decriptacao.

    // Metodo setter para definir a palavra conhecida.
    public static void setPalavraConhecida(String palavra) {
        palavraConhecida = palavra;
    }

    // Metodo para decriptar a mensagem encriptada.
    public static String decriptar(String mensagemEncriptada) {
        int deslocamento = calculeDeslocamento(mensagemEncriptada);
        return corrijaDeslocamento(mensagemEncriptada, -deslocamento);
    }

    // Metodo para quebrar o código da mensagem encriptada.
    public static String quebrarCodigo(String mensagemEncriptada) {
        int deslocamento = calculeDeslocamento(mensagemEncriptada);
        return corrijaDeslocamento(mensagemEncriptada, -deslocamento);
    }

    // Metodo privado para calcular o deslocamento suficiente para decriptar a mensagem.
    private static int calculeDeslocamento(String mensagemEncriptada) {
        for (int deslocamento = 0; deslocamento < 26; deslocamento++) {
            String decriptada = corrijaDeslocamento(mensagemEncriptada, -deslocamento);
            if (decriptada.contains(palavraConhecida)) {
                return deslocamento;
            }
        }
        return 0; // Deslocamento padrao se a palavra nao for encontrada.
    }

    // Metodo privado para aplicar o deslocamento e decriptar a mensagem.
    private static String corrijaDeslocamento(String mensagem, int deslocamento) {
        StringBuilder decriptada = new StringBuilder();

        for (char c : mensagem.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                int offset = (c - base + deslocamento + 26) % 26;
                decriptada.append((char) (base + offset));
            } else {
                decriptada.append(c); // Caracteres que nao sejam alfabeticos permanecem os mesmos.
            }
        }

        return decriptada.toString();
    }
}