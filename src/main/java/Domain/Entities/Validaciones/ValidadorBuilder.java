package Domain.Entities.Validaciones;

public class ValidadorBuilder {
    private Validador validador;

    public ValidadorBuilder() {
        this.validador = new Validador();
    }

    public ValidadorBuilder agregarValidacion(Validacion validacion) {
        this.validador.addValidacionesARealizar(validacion);

        return this;
    }

    public Validador build() {
        return this.validador;
    }
}
