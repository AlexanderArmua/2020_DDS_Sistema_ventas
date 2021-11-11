package Domain.Entities.Usuarios;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class UsuarioTest extends TestCase {

    @Test
    public void testGetPasswordHashed_hashea_correctamente() {
        final String contrasennaOriginal = "44MMnnpp??,-.1";
        final String contrasennaHasheada = "66847d5805ded2be3636007f50b6fb87b33f93de1ea3857c56b48fe5bd152e6cb85209f72a1727db218a247c24c973101052013787bf3fac5aceded9ae3c2480";
        String passwordHasshed = Usuario.getPasswordHashed(contrasennaOriginal);

        Assert.assertEquals(contrasennaHasheada, passwordHasshed);
    }
}