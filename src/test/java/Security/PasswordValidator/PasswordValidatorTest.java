package Security.PasswordValidator;

import org.junit.Test;
import static org.junit.Assert.*;

public class PasswordValidatorTest {

    @Test
    public void testValidPassword(){
        PasswordResult passwordResult = new PasswordValidator().getSecurityPercentage("&UTNdiseno2020");
        assertEquals(100,passwordResult.getSecurityPercentage());
    }

    @Test
    public void testMinimunLength(){
        PasswordResult passwordResult = new PasswordValidator().getSecurityPercentage("&Utn12");
        assertTrue(passwordResult.getSecurityFails().contains("La contraseña debe ser mínimo de 8 caracteres."));
    }

    @Test
    public void testMaximumLength(){
        String str = new String(new char[130]);
        PasswordResult passwordResult = new PasswordValidator().getSecurityPercentage(str);
        assertTrue(passwordResult.getSecurityFails().contains("La contraseña debe ser máximo de 128 caracteres."));
    }

    @Test
    public void testSymbols(){
        PasswordResult passwordResult = new PasswordValidator().getSecurityPercentage("Utn12");
        assertTrue(passwordResult.getSecurityFails().contains("La contraseña debe contener símbolos."));
    }

    @Test
    public void testCapitals(){
        PasswordResult passwordResult = new PasswordValidator().getSecurityPercentage("utn12");
        assertTrue(passwordResult.getSecurityFails().contains("La contraseña debe contener caracteres en mayúscula."));
    }

    @Test
    public void testLowerCase(){
        PasswordResult passwordResult = new PasswordValidator().getSecurityPercentage("UTN12");
        assertTrue(passwordResult.getSecurityFails().contains("La contraseña debe contener caracteres en minúscula."));
    }

    @Test
    public void testNumbers(){
        PasswordResult passwordResult = new PasswordValidator().getSecurityPercentage("UTN");
        assertTrue(passwordResult.getSecurityFails().contains("La contraseña debe contener números."));
    }

    @Test
    public void testLetters(){
        PasswordResult passwordResult = new PasswordValidator().getSecurityPercentage("168437");
        assertTrue(passwordResult.getSecurityFails().contains("La contraseña debe contener caracteres alfabéticos."));
    }

    @Test
    public void testCommonPasswords(){
        PasswordResult passwordResult = new PasswordValidator().getSecurityPercentage("contraseña");
        assertTrue(passwordResult.getSecurityFails().contains("La contraseña es muy común, por favor, elija otra."));
    }

}
