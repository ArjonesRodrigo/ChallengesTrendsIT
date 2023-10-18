// Importa a classe Scanner do pacote java.util para permitir a entrada de dados pelo usuario.
import java.util.Scanner;

// Classe base para decriptar mensagens encriptadas.
class Decriptador {
    
	// Metodo para decriptar uma mensagem encriptada
    public static String decriptar(String palavraConhecida, String mensagemEncriptada) {
        int deslocamento = calcularDeslocamento(mensagemEncriptada, palavraConhecida);
        return aplicarDeslocamento(mensagemEncriptada, -deslocamento);
    }

    // Metodo para quebrar o codigo da mensagem encriptada
    public static String quebrarCodigo(String palavraConhecida, String mensagemEncriptada) {
        int deslocamento = calcularDeslocamento(mensagemEncriptada, palavraConhecida);
        return aplicarDeslocamento(mensagemEncriptada, -deslocamento);
    }

    // Metodo privado para calcular o deslocamento necessario para decriptar a mensagem
    private static int calcularDeslocamento(String mensagemEncriptada, String palavraConhecida) {
        for (int deslocamento = 0; deslocamento < 26; deslocamento++) {
            String decriptada = aplicarDeslocamento(mensagemEncriptada, -deslocamento);
            if (decriptada.contains(palavraConhecida)) {
                return deslocamento;
            }
        }
        return 0; // Deslocamento padrao se a palavra nao for encontrada.
    }

    // Metodo privado para aplicar o deslocamento e decriptar a mensagem
    private static String aplicarDeslocamento(String mensagem, int deslocamento) {
        StringBuilder decriptada = new StringBuilder();

        for (char c : mensagem.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                int offset = (c - base + deslocamento + 26) % 26;
                decriptada.append((char) (base + offset));
            } else {
                decriptada.append(c);
            }
        }

        return decriptada.toString();
    }
}

// Classe principal que interage com o usuario.
public class RevelarDecriptacao {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String mensagemEncriptada;
		
		// Solicita a entrada da mensagem encriptada pelo usuario, validando-a.
        do {
            System.out.print("Digite a mensagem encriptada: ");
            mensagemEncriptada = scanner.nextLine();

            if (!mensagemEncriptada.matches("^[a-zA-Z ]*$")) {
                System.out.println("A mensagem encriptada deve conter apenas letras e espaços.");
            }
        } while (!mensagemEncriptada.matches("^[a-zA-Z ]*$"));

        String palavraConhecida;
		
		// Solicita a entrada da palavra conhecida pelo usuario, validando-a.
        do {
            System.out.print("Digite a palavra conhecida (única palavra, sem espaços): ");
            palavraConhecida = scanner.nextLine();

            if (!palavraConhecida.matches("^[a-zA-Z]*$") || palavraConhecida.contains(" ")) {
                System.out.println("A palavra conhecida deve conter apenas letras e não deve conter espaços.");
            }
        } while (!palavraConhecida.matches("^[a-zA-Z]*$") || palavraConhecida.contains(" "));
		
		// Chama o metodo quebrarCodigo da classe Decriptador para decriptar a mensagem encriptada usando a palavra conhecida fornecida.
        String mensagemDecriptada = Decriptador.quebrarCodigo(palavraConhecida, mensagemEncriptada);
		
		// Exibe a mensagem decriptada na saida.
        System.out.println("Mensagem decriptada: " + mensagemDecriptada);
    }
}


