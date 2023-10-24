// Importa a classe Scanner do pacote java.util para permitir a entrada de dados pelo usuário.
import java.util.Scanner;

// Classe base para decriptar mensagens encriptadas.
    
class Decriptador {
    
    
    // Método para decriptar uma mensagem encriptada
    public static String decriptar(String palavraConhecida, String mensagemEncriptada) {
        int deslocamento = calcularDeslocamento(mensagemEncriptada, palavraConhecida);
        return aplicarDeslocamento(mensagemEncriptada, -deslocamento);
    }
    
    // Método para quebrar o código da mensagem encriptada
    public static String quebrarCodigo(String palavraConhecida, String mensagemEncriptada) {
        int deslocamento = calcularDeslocamento(mensagemEncriptada, palavraConhecida);
        return aplicarDeslocamento(mensagemEncriptada, -deslocamento);
    }

    // Método privado para calcular o deslocamento necessário para decriptar a mensagem
    private static int calcularDeslocamento(String mensagemEncriptada, String palavraConhecida) {
        for (int deslocamento = 0; deslocamento < 26; deslocamento++) {
            String decriptada = aplicarDeslocamento(mensagemEncriptada, -deslocamento);
            if (decriptada.contains(palavraConhecida)) {
                return deslocamento;
            }
        }
        return 0; // Deslocamento padrão se a palavra não for encontrada.
    }

    // Método privado para aplicar o deslocamento e decriptar a mensagem
    private static String aplicarDeslocamento(String mensagem, int deslocamento) {
        StringBuilder decriptada = new StringBuilder();

        for (char c : mensagem.toCharArray()) {
            if (Character.isLetter(c) || Character.isWhitespace(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                if (Character.isWhitespace(c)) {
                    decriptada.append(c);
                } else {
                    int offset = (c - base + deslocamento + 26) % 26;
                    decriptada.append((char) (base + offset));
                }
            } else {
                decriptada.append(c);
            }
        }

        return decriptada.toString();
    }
}

// Classe principal que interage com o usuário.

public class InteractiveDecriptacao {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String mensagemEncriptada;
        String palavraConhecida;

        System.out.println("Bem-vindo ao Decriptador Interativo!");

        // Solicita a entrada da mensagem encriptada pelo usuário, validando-a.
        do {
            System.out.print("Digite a mensagem encriptada: ");
            mensagemEncriptada = scanner.nextLine();
        } while (mensagemEncriptada.isEmpty());

        // Solicita a entrada da palavra conhecida pelo usuário, validando-a.
        do {
            System.out.print("Digite a palavra conhecida (única palavra): ");
            palavraConhecida = scanner.nextLine();
        } while (palavraConhecida.isEmpty() || palavraConhecida.contains(" "));

        // Chama o método quebrarCodigo da classe Decriptador para decriptar a mensagem encriptada usando a palavra conhecida fornecida.
        String mensagemDecriptada = Decriptador.quebrarCodigo(palavraConhecida, mensagemEncriptada);

        // Exibe a mensagem decriptada na saída.
        System.out.println("Mensagem decriptada: " + mensagemDecriptada);

        // Fecha o Scanner após a conclusão da operação.
        scanner.close();
    }
}


