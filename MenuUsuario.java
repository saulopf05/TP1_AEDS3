import java.util.Scanner;

public class MenuUsuario {
    private static Scanner console = new Scanner(System.in);
    private Usuario usuario;
    private ArquivoUsuario arqUsuarios;

    public MenuUsuario(Usuario usuario, ArquivoUsuario arqUsuarios) {
        this.usuario = usuario;
        this.arqUsuarios = arqUsuarios;
    }

    public void menu() {
        char opcao;

        do {
            System.out.println("\n\nAJUDA AÍ 1.0");
            System.out.println("------------");
            System.out.println("Usuário: " + usuario.nome);

            System.out.println("\nA - Minha área");
            System.out.println("B - Buscar perguntas");
            System.out.println("S - Sair");

            System.out.print("\nOpção: ");

            try {
                opcao = console.nextLine().toUpperCase().charAt(0);
            } catch(Exception e) {
                opcao = ' ';
            }

            switch(opcao) {
                case 'A':
                    minhaArea();
                    break;

                case 'B':
                    try {
                        MenuPerguntas menuPerguntas = new MenuPerguntas(usuario);
                        menuPerguntas.menu();
                    } catch(Exception e) {
                        System.out.println("\nErro ao acessar o menu de perguntas!");
                        e.printStackTrace();
                    }
                    break;

                case 'S':
                    break;

                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        } while(opcao != 'S');
    }

    private void minhaArea() {

        System.out.println("\nMinha área");
        System.out.println("----------");
        System.out.println("A - Meus dados");
        System.out.println("B - Minhas perguntas");
        System.out.println("C - Minhas respostas");
        System.out.println("D - Meus votos");
        System.out.println("R - Retornar");
        System.out.print("\nOpção: ");

        char opcao;

        try {
            opcao = console.nextLine().toUpperCase().charAt(0);
        } catch(Exception e) {
            opcao = ' ';
        }

        switch(opcao) {
            case 'A':
                meusDados();
                break;

            case 'B':
                System.out.println("\nMinhas perguntas - Em construção...");
                break;

            case 'C':
                System.out.println("\nMinhas respostas - Em construção...");
                break;

            case 'D':
                System.out.println("\nMeus votos - Em construção...");
                break;

            case 'R':
                break;

            default:
                System.out.println("Opção inválida!");
                break;
        }
    }

    private void meusDados() {
        char opcao;

        do {
            System.out.println("\nMeus dados");
            System.out.println("----------");
            System.out.println("Nome: " + usuario.nome);
            System.out.println("Email: " + usuario.email);

            System.out.println("\nA - Alterar nome");
            System.out.println("B - Alterar email");
            System.out.println("C - Alterar senha");
            System.out.println("D - Alterar pergunta e resposta de recuperação");
            System.out.println("R - Retornar");

            System.out.print("\nOpção: ");

            try {
                opcao = console.nextLine().toUpperCase().charAt(0);

            } catch(Exception e) {
                opcao = ' ';
            }

            switch(opcao) {
                case 'A':
                    alterarNome();
                    break;

                case 'B':
                    alterarEmail();
                    break;

                case 'C':
                    alterarSenha();
                    break;

                case 'D':
                    alterarRecuperacao();
                    break;

                case 'R':
                    break;

                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } while(opcao != 'R');
    }

    private void alterarNome() {
        try {
            System.out.print("\nNovo nome (vazio para cancelar): ");
            String novoNome = console.nextLine();

            if(novoNome.isEmpty()) {
                return;
            }

            usuario.nome = novoNome;

            if(arqUsuarios.update(usuario)) {
                System.out.println("\nNome alterado com sucesso!");
            } else {
                System.out.println("\nNão foi possível alterar o nome.");
            }

        } catch(Exception e) {
            System.out.println("\nErro ao alterar o nome!");
            e.printStackTrace();
        }
    }

    private void alterarEmail() {
        try {
            System.out.print("\nNovo e-mail (vazio para cancelar): ");
            String novoEmail = console.nextLine();

            if(novoEmail.isEmpty()) {
                return;
            }

            // Verifica se o novo e-mail já pertence a outro usuário
            Usuario usuarioExistente = arqUsuarios.read(novoEmail);

            if(usuarioExistente != null &&
                usuarioExistente.getId() != usuario.getId()) {
                System.out.println("\nEste e-mail já está cadastrado.");
                return;
            }

            usuario.email = novoEmail;

            if(arqUsuarios.update(usuario)) {
                System.out.println("\nE-mail alterado com sucesso!");
            } else {
                System.out.println("\nNão foi possível alterar o e-mail.");
            }

        } catch(Exception e) {
            System.out.println("\nErro ao alterar o e-mail!");
            e.printStackTrace();
        }
    }

    private void alterarSenha() {
        try {
            System.out.print("\nNova senha (vazio para cancelar): ");
            String novaSenha = console.nextLine();

            if(novaSenha.isEmpty()) {
                return;
            }

            System.out.print("Confirme a nova senha: ");
            String confirmacao = console.nextLine();

            if(!novaSenha.equals(confirmacao)) {
                System.out.println("\nAs senhas não conferem.");
                return;
            }

            usuario.hashSenha = Usuario.gerarHash(novaSenha);

            if(arqUsuarios.update(usuario)) {
                System.out.println("\nSenha alterada com sucesso!");
            } else {
                System.out.println("\nNão foi possível alterar a senha.");
            }

        } catch(Exception e) {
            System.out.println("\nErro ao alterar a senha!");
            e.printStackTrace();
        }
    }

    private void alterarRecuperacao() {
        try {
            System.out.print("\nNova pergunta secreta (vazio para cancelar): ");
            String novaPergunta = console.nextLine();

            if(novaPergunta.isEmpty()) {
                return;
            }

            System.out.print("Nova resposta secreta: ");
            String novaResposta = console.nextLine();

            if(novaResposta.isEmpty()) {
                System.out.println("\nResposta secreta inválida.");
                return;
            }

            usuario.perguntaSecreta = novaPergunta;
            usuario.hashRespostaSecreta = Usuario.gerarHashResposta(novaResposta);

            if(arqUsuarios.update(usuario)) {
                System.out.println("\nPergunta e resposta de recuperação alteradas com sucesso!");
            } else {
                System.out.println("\nNão foi possível alterar os dados de recuperação.");
            }

        } catch(Exception e) {
            System.out.println("\nErro ao alterar os dados de recuperação!");
            e.printStackTrace();
        }
    }

}