package Security.PasswordValidator;

import java.util.Scanner;

public class CreatePassword {

    public static void main(String[] args) {
        PasswordValidator validador = new PasswordValidator();
        System.out.print("Ingrese su contrasena: ");
        Scanner scanner = new Scanner(System.in);
        String enteredPassword = scanner.nextLine();

        PasswordResult passwordResult= validador.getSecurityPercentage(enteredPassword);
        System.out.println("El nivel de seguridad de su password es del: " + (passwordResult.getSecurityPercentage() + "%" ));
        passwordResult.getSecurityFails().stream().forEach(fail -> System.out.println(fail));
    }

}
