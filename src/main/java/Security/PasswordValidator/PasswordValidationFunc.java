package Security.PasswordValidator;

import java.util.function.Function;

public class PasswordValidationFunc implements PasswordValidation {
    private String failText;
    Function<String, Boolean> func;

    public PasswordValidationFunc(String failText, Function<String, Boolean> func) {
        this.failText = failText;
        this.func = func;
    }

    public Boolean passwordMatches(final String password) {
        return this.func.apply(password);
    }

    public String getFailText() {
        return failText;
    }
}
