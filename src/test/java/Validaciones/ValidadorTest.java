package Validaciones;

import Domain.Entities.Validaciones.ResultadoValidacion;
import Domain.Entities.Validaciones.ValidacionCantPresupuestos;
import Domain.Entities.Validaciones.Validador;
import Domain.Repositories.Repositorio;
import Domain.Repositories.daos.DAOMemoria;
import Domain.Entities.Operaciones.Operacion.CriteriosDeSeleccion.CriterioMenorValor;
import Domain.Entities.Operaciones.Operacion.CriteriosDeSeleccion.ICriterioDeSeleccion;
import Domain.Entities.Operaciones.Operacion.DataObject.DOOperacionEgreso;
import Domain.Entities.Operaciones.Operacion.OperacionEgreso;
import Domain.Entities.Presupuestos.Presupuesto;
import Domain.Entities.Usuarios.Usuario;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ValidadorTest {

    private Validador validador;
    private OperacionEgreso operacionEgreso;

    private Repositorio<Usuario> repoUsuarios = null;
    private Repositorio<ResultadoValidacion> repoResultadoValidacion = null;

    @Before
    public void before() {
        validador = new Validador();

        operacionEgreso = this.getOperacionEgresoTest();

        this.repoUsuarios = new Repositorio<>(new DAOMemoria<>());
        this.repoResultadoValidacion = new Repositorio<>(new DAOMemoria<>());

        repoUsuarios.agregar(new Usuario( "Jose"));
    }

    @Test
    public void validar_operacion_egreso_deja_validada_la_operacion() {
        operacionEgreso.setNecesitaPresupuesto(true);

        validador.validarOperacionEgreso(operacionEgreso, this.repoResultadoValidacion, this.repoUsuarios);

        Assert.assertTrue(operacionEgreso.isFueValidada());
    }

    @Test
    public void validar_operacion_egreso_no_deja_validada_la_operacion() {
        operacionEgreso.setNecesitaPresupuesto(false);

        validador.validarOperacionEgreso(operacionEgreso, this.repoResultadoValidacion, this.repoUsuarios);

        Assert.assertTrue(operacionEgreso.isFueValidada());
    }

    @Test
    public void al_validar_los_usuarios_tienen_mensajes() throws Exception {
        operacionEgreso.setNecesitaPresupuesto(true);

        List<Usuario> usuarios = this.repoUsuarios.buscarTodos();

        operacionEgreso.setRevisores(usuarios);

        validador.addValidacionesARealizar(new ValidacionCantPresupuestos("Mensaje Ok", "Mensaje Fail", "Validacion Cantidad de Presupuestos"));

        validador.validarOperacionEgreso(operacionEgreso, this.repoResultadoValidacion, this.repoUsuarios);

        Assert.assertTrue(usuarios.get(0).getMensajes().size() > 0);
    }

    @Test
    public void al_no_necesitar_validacion_presupuesto_los_usuarios_no_tienen_mensajes() throws Exception {
        operacionEgreso.setNecesitaPresupuesto(false);

        List<Usuario> usuarios = this.repoUsuarios.buscarTodos();

        operacionEgreso.setRevisores(usuarios);

        validador.validarOperacionEgreso(operacionEgreso, this.repoResultadoValidacion, this.repoUsuarios);

        Assert.assertFalse(usuarios.get(0).getMensajes().size() > 0);
    }

    private OperacionEgreso getOperacionEgresoTest() {
        DOOperacionEgreso doOperacionEgreso = new DOOperacionEgreso(null, null, 0.0f, null, null,null, false);
        OperacionEgreso operacionEgreso = new OperacionEgreso(doOperacionEgreso);

        ICriterioDeSeleccion criterioDeSeleccion = new CriterioMenorValor();

        operacionEgreso.setCriterioSeleccionPresupuesto(criterioDeSeleccion);

        Presupuesto presupuesto1 = new Presupuesto(null, null, 500, operacionEgreso, null, null,null);
        Presupuesto presupuesto2 = new Presupuesto(null, null, 1000, operacionEgreso, null, null,null);
        Presupuesto presupuesto3 = new Presupuesto(null, null, 2500, operacionEgreso, null, null,null);

        operacionEgreso.cargarPresupuesto(presupuesto1);
        operacionEgreso.cargarPresupuesto(presupuesto2);
        operacionEgreso.cargarPresupuesto(presupuesto3);

        operacionEgreso.setPresupuestoElegido(presupuesto1);

        operacionEgreso.setNecesitaPresupuesto(true);

        return operacionEgreso;
    }
}
