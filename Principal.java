import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner console;

        try {
            console = new Scanner(System.in);
            MenuAcesso menuAcesso = new MenuAcesso();
            menuAcesso.menu();
            console.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}