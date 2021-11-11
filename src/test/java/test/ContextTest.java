package test;

import static org.junit.Assert.*;

import db.EntityManagerHelper;
import org.junit.Test;


public class ContextTest extends EntityManagerHelper {

    @Test
    public void contextUp() {
        assertNotNull(entityManager());
    }

    @Test
    public void contextUpWithTransaction() throws Exception {
        withTransaction(() -> {});
    }
}