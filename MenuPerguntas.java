import java.util.Scanner;
import java.util.ArrayList;

public class MenuPerguntas {
    private static Scanner console = new Scanner(System.in);
    private Usuario usuario;
    private ArquivoPergunta arqPerguntas;

    public MenuPerguntas(Usuario usuario) throws Exception {
        this.usuario = usuario;
        this.arqPerguntas = new ArquivoPergunta();
    }

    public void menu() {
        char opcao;

        do {
            System.out.println("\n\nMinhas perguntas");
            System.out.println("----------------");
            System.out.println("A - Listar");
            System.out.println("B - Incluir");
            System.out.println("C - Alterar");
            System.out.println("D - Arquivar");
            System.out.println("R - Retornar");

            System.out.print("\nOpcao: ");

            try {
                opcao = console.nextLine().toUpperCase().charAt(0);
            } catch(Exception e) {
                opcao = ' ';
            }

            switch(opcao) {
                case 'A':
                    listar();
                    break;

                case 'B':
                    incluir();
                    break;

                case 'C':
                    alterar();
                    break;

                case 'D':
                    arquivar();
                    break;

                case 'R':
                    break;

                default:
                    System.out.println("Opcao invalida!");
                    break;
            }
        } while(opcao != 'R');
    }

    private void listar() {
        try {
            System.out.println("\nMinhas perguntas");
            System.out.println("----------------");

            ArrayList<ParUsuarioPergunta> lista =
                arqPerguntas.getIndiceUsuarioPergunta().read(null);

            int numero = 1;

            for(ParUsuarioPergunta par : lista) {
                if(par.getIdUsuario() == usuario.getId()) {

                    Pergunta p = arqPerguntas.read(par.getIdPergunta());

                    if(p != null) {
                        System.out.println("\n" + numero + " - " + p.pergunta);
                        System.out.println("Palavras-chave: " + p.palavrasChave);

                        if(!p.ativa) {
                            System.out.println("ARQUIVADA");
                        }

                        numero++;
                    }
                }
            }
            if(numero == 1) {
                System.out.println("\nVocê ainda não possui perguntas.");
            }

        } catch(Exception e) {
            System.out.println("\nErro ao listar perguntas!");
            e.printStackTrace();
        }
    }

    private void incluir() {
        try {
            System.out.println("\nIncluir pergunta");
            System.out.println("----------------");
            System.out.print("Pergunta (vazio para cancelar): ");
            String textoPergunta = console.nextLine();

            if(textoPergunta.isEmpty()) {
                return;
            }

            System.out.print("Palavras-chave (separadas por ;): ");
            String palavrasChave = console.nextLine();

            if(palavrasChave.isEmpty()) {
                System.out.println("\nPalavras-chave inválidas.");
                return;
            }

            Pergunta p = new Pergunta(
                usuario.getId(),
                textoPergunta,
                palavrasChave
            );

            int id = arqPerguntas.create(p);
            System.out.println("\nPergunta cadastrada com sucesso!");

        } catch(Exception e) {
            System.out.println("\nErro ao cadastrar pergunta!");
            e.printStackTrace();
        }
    }

    private void alterar() {
        System.out.println("\nAlterar pergunta - Em construcao...");
    }

    private void arquivar() {
        System.out.println("\nArquivar pergunta - Em construcao...");
    }
}