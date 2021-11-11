package Domain.Entities.Usuarios;

public class NoTienePermisoException extends Exception {
    public NoTienePermisoException() {
        super();
    }

    public NoTienePermisoException(String message) {
        super(message);
    }
}
