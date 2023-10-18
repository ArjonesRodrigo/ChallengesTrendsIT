// Importa a classe Scanner do pacote java.util para permitir a entrada de dados pelo usuario.
import java.util.Scanner;

// Classe base para decriptar mensagens encriptadas.
class Decriptador {
    private String palavraConhecida;

    // Construtor da classe Decriptador que recebe a palavra conhecida como parametro.
    public Decriptador(String palavraConhecida) {
        this.palavraConhecida = palavraConhecida;
    }

    // Metodo para decriptar a mensagem encriptada.
    public String decriptar(String mensagemEncriptada) {
        int deslocamento = calculeDeslocamento(mensagemEncriptada);
        return corrijaDeslocamento(mensagemEncriptada, -deslocamento);
    }

    // Metodo para quebrar o codigo da mensagem encriptada.
    public String quebrarCodigo(String mensagemEncriptada) {
        int deslocamento = calculeDeslocamento(mensagemEncriptada);
        return corrijaDeslocamento(mensagemEncriptada, -deslocamento);
    }

    // Metodo privado para calcular o deslocamento suficiente para decriptar a mensagem.
    private int calculeDeslocamento(String mensagemEncriptada) {
        for (int deslocamento = 0; deslocamento < 26; deslocamento++) {
            String decriptada = corrijaDeslocamento(mensagemEncriptada, -deslocamento);
            if (decriptada.contains(palavraConhecida)) {
                return deslocamento;
            }
        }
        return 0; // Deslocamento padrao se nao encontrar a palavra.
    }

    // Metodo privado para aplicar o deslocamento e decriptar a mensagem.
    private String corrijaDeslocamento(String mensagem, int deslocamento) {
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

        // Solicita a entrada da mensagem encriptada pelo usuario.
        System.out.print("Digite a mensagem encriptada: ");
        String mensagemEncriptada = scanner.nextLine();

        // Solicita a entrada da palavra conhecida pelo usuario.
        System.out.print("Digite a palavra conhecida: ");
        String palavraConhecida = scanner.nextLine();

        // Verifica se a palavra conhecida contém apenas letras
        if (!palavraConhecida.matches("^[a-zA-Z]*$")) {
            System.out.println("A palavra conhecida deve conter apenas letras.");
            return;
        }

        // Cria um objeto Decriptador com a palavra conhecida fornecida.
        Decriptador decriptador = new Decriptador(palavraConhecida);

        // Chama o metodo quebrarCodigo para decriptar a mensagem encriptada.
        String mensagemDecriptada = decriptador.quebrarCodigo(mensagemEncriptada);

        // Exibe a mensagem decriptada na saida.
        System.out.println("Mensagem decriptada: " + mensagemDecriptada);
    }
}
