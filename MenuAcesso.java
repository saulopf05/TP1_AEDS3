import java.util.Scanner;

public class MenuAcesso {
    private static Scanner console = new Scanner(System.in);
    ArquivoUsuario arqUsuarios;

    public MenuAcesso() throws Exception {
        arqUsuarios = new ArquivoUsuario();
    }

    public void menu() {
        char opcao;

        do {
            System.out.println("\n\nAJUDA AÍ 1.0");
            System.out.println("------------");
            System.out.println("A - Login");
            System.out.println("B - Novo usuário");
            System.out.println("S - Sair");
            System.out.print("\nOpção: ");

            try {
                opcao = console.nextLine().toUpperCase().charAt(0);
            } catch(Exception e) {
                opcao = ' ';
            }

            switch(opcao) {
                case 'A':
                    login();
                    break;
                case 'B':
                    novoUsuario();
                    break;
                case 'S':
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        } while(opcao != 'S');
    }

    private void login() {
        System.out.println("\nLogin");
        System.out.println("-----");

        try {
            System.out.print("E-mail (vazio para cancelar): ");
            String email = console.nextLine();

            if(email.isEmpty())
                return;

            Usuario usuario = arqUsuarios.read(email);

            if(usuario == null) {
                System.out.println("Nome/e-mail ou senha incorretos.");
                return;
            }

            System.out.print("Senha: ");
            String senha = console.nextLine();
            String hashSenha = Usuario.gerarHash(senha);

            if(hashSenha.equals(usuario.hashSenha)) {
                System.out.println("\nLogin realizado com sucesso!");
                System.out.println("Bem-vindo(a), " + usuario.nome + "!");

                try {
                    MenuUsuario menuUsuario = new MenuUsuario(usuario, arqUsuarios);
                    menuUsuario.menu();

                } catch(Exception e) {
                    System.out.println("Erro ao acessar o menu do usuário!");
                    e.printStackTrace();
                }

            } else {
                System.out.println("Nome/e-mail ou senha incorretos.");
            }

        } catch(Exception e) {
            System.out.println("Erro do sistema. Não foi possível realizar o login!");
            e.printStackTrace();
        }
    }

    private void novoUsuario() {
        System.out.println("\nNovo usuário");

        try {
            System.out.print("E-mail (vazio para cancelar): ");
            String email = console.nextLine();

            if(email.isEmpty())
                return;

            Usuario usuarioExistente = arqUsuarios.read(email);

            if(usuarioExistente != null) {
                System.out.println("Este e-mail já está cadastrado.");
                return;
            }

            System.out.print("Nome completo: ");
            String nome = console.nextLine();

            if(nome.isEmpty()) {
                System.out.println("Nome inválido.");
                return;
            }

            System.out.print("Senha: ");
            String senha = console.nextLine();

            if(senha.isEmpty()) {
                System.out.println("Senha inválida.");
                return;
            }

            System.out.print("Pergunta secreta: ");
            String perguntaSecreta = console.nextLine();

            if(perguntaSecreta.isEmpty()) {
                System.out.println("Pergunta secreta inválida.");
                return;
            }

            System.out.print("Resposta secreta: ");
            String respostaSecreta = console.nextLine();

            if(respostaSecreta.isEmpty()) {
                System.out.println("Resposta secreta inválida.");
                return;
            }

            Usuario u = new Usuario(
                nome,
                email,
                senha,
                perguntaSecreta,
                respostaSecreta
            );

            int id = arqUsuarios.create(u);

            System.out.println("\nUsuário cadastrado com sucesso!");
            System.out.println("ID do usuário: " + id);

        } catch(Exception e) {
            System.out.println(
                "Erro do sistema. Não foi possível cadastrar o usuário!"
            );

            e.printStackTrace();
        }
    }
}