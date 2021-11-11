package Security.PasswordValidator;

import java.util.List;

public class PasswordInvalidaException extends Exception {
    public PasswordInvalidaException() {
        super();
    }

    private List<String> mensajesError;

    public PasswordInvalidaException(List<String> mensajesError) {
        super(mensajesError.get(0));
        this.mensajesError = mensajesError;
    }

    public List<String> getMensajesError() {
        return this.mensajesError;
    }
}
