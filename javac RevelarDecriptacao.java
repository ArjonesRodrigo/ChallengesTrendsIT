// Importa a classe Scanner do pacote java.util para permitir a entrada de dados pelo usuario.
import java.util.Scanner;

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
        Decriptador.setPalavraConhecida(palavraConhecida);

        // Decripta a mensagem encriptada usando a palavra conhecida.
        String mensagemDecriptada = Decriptador.quebrarCodigo(mensagemEncriptada);
       
        // Exibe a mensagem decriptada na saida.
        System.out.println("Mensagem decriptada: " + mensagemDecriptada);
    }
}
