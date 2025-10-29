import java.util.*;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

public class GeradorDeSenhas {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Gerador De Senhas Seguras");
        System.out.println("-------------------------");

        System.out.println("Digite o tamanho da senha que deseja (ex: 12): ");
        int tamanho = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Incluir letras maiúsculas? (s/n): ");
        boolean incluirMaiusculas = scanner.nextLine().trim().equalsIgnoreCase("s");

        System.out.println("Incluir letras minúsculas? (s/n): ");
        boolean incluirMinusculas = scanner.nextLine().trim().equalsIgnoreCase("s");

        System.out.println("Incluir números? (s/n): ");
        boolean incluirNumeros = scanner.nextLine().trim().equalsIgnoreCase("s");

        System.out.println("Incluir símbolos? (s/n): ");
        boolean incluirSimbolos = scanner.nextLine().trim().equalsIgnoreCase("s");
        String senhaGerada = gerarSenha(tamanho, incluirMaiusculas, incluirMinusculas, incluirNumeros, incluirSimbolos);

        System.out.println("\n Sua senha gerada é: " + senhaGerada);

        System.out.println("Deseja copiar a senha para a área de transferência? (s/n): ");
        String resposta = scanner.nextLine();

        if (resposta.equalsIgnoreCase("s")) {
            copiarParaAreaDeTransferencia(senhaGerada);
            System.out.println("Senha copiada para a área de transferência");
        }
        System.out.println("Obrigado por usar o Gerador de Senhas Seguras!");
        scanner.close();        
    }

    public static String gerarSenha(int tamanho, boolean maiusculas, boolean minusculas, boolean numeros, boolean simbolos) {
        String letrasMaiusculas = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String letrasMinusculas = "abcdefghijklmnopqrstuvwxyz";
        String digitos = "0123456789";
        String simbolosEspeciais = "!@#$%^&*()-_=+<>?";

        String conjunto = "";

        if (maiusculas) conjunto += letrasMaiusculas;
        if (minusculas) conjunto += letrasMinusculas;
        if (numeros) conjunto += digitos;
        if (simbolos) conjunto += simbolosEspeciais;

        if (conjunto.isEmpty()){
            System.out.println("Erro: Nenhum tipo de caractere selecionado");
            return "";
        }

        Random random = new Random();

        StringBuilder senha = new StringBuilder();

        for (int i = 0; i < tamanho; i++) {
            int index = random.nextInt(conjunto.length());
            senha.append(conjunto.charAt(index));
        }

        return senha.toString();        
    }

    public static void copiarParaAreaDeTransferencia(String texto) {
        StringSelection selecao = new StringSelection(texto);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selecao, null);
    }
}