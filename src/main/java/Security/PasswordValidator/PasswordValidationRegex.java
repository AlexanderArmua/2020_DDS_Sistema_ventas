package Security.PasswordValidator;

public class PasswordValidationRegex implements PasswordValidation {
    private String regexp;
    private String failText;

    public PasswordValidationRegex(String failTexT, String regexp) {
        this.regexp = regexp;
        this.failText = failTexT;
    }

    public Boolean passwordMatches(String password) {
        return password.matches(this.regexp);
    }

    public String getFailText() {
        return failText;
    }
}
