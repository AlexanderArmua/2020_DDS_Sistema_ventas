package Security.PasswordValidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PasswordValidator {

    private static Set<String> blackListPasswords = new HashSet<>(
        Arrays.asList("/Qwerty123", "password", "contraseña", "123456789")
    );

    private static List<PasswordValidation> validationsToExecute =
            new ArrayList<>(
                    Arrays.asList(
                            new PasswordValidationRegex("La contraseña debe contener números.", ".*\\d.*"),
                            new PasswordValidationRegex("La contraseña debe contener caracteres alfabéticos.", ".*[A-z].*"),
                            new PasswordValidationRegex("La contraseña debe contener caracteres en minúscula.", ".*[a-z].*"),
                            new PasswordValidationRegex("La contraseña debe contener caracteres en mayúscula.", ".*[A-Z].*"),
                            new PasswordValidationRegex("La contraseña debe contener símbolos.", ".*[^\\w\\s].*"),
                            new PasswordValidationFunc("La contraseña debe ser mínimo de 8 caracteres.", pass -> pass.length() >= 8),
                            new PasswordValidationFunc("La contraseña debe ser máximo de 128 caracteres.", pass -> pass.length() <= 128),
                            new PasswordValidationFunc("La contraseña es muy común, por favor, elija otra.", pass -> !blackListPasswords.contains(pass))
                    )
            );

    public PasswordValidator() {
    }

    public PasswordResult getSecurityPercentage(final String password) {
        final PasswordResult result = new PasswordResult();
        int securityLvl = 0;

        for (PasswordValidation validation : validationsToExecute) {
            boolean match = validation.passwordMatches(password);

            securityLvl += getBoolAsNum(match);

            addFail(match, result, validation.getFailText());
        }

        result.setSecurityLevel(securityLvl);

        return result;
    }

    public Boolean isValidPassword(final String password) {
        return this.getSecurityPercentage(password).isValid();
    }

    private Integer getBoolAsNum(boolean value) {
        return (value) ? 1 : 0;
    }

    private void addFail(boolean match, PasswordResult result, String fail) {
        if(!match) {
            result.addFail(fail);
        }
    }

    public static List<PasswordValidation> getValidationsToExecute() {
        return validationsToExecute;
    }

    public static void setValidationsToExecute(List<PasswordValidation> validationsToExecute) {
        PasswordValidator.validationsToExecute = validationsToExecute;
    }

    public static Set<String> getBlackListPasswords() {
        return blackListPasswords;
    }

    public static void setBlackListPasswords(Set<String> blackListPasswords) {
        PasswordValidator.blackListPasswords = blackListPasswords;
    }
}
