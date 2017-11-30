package kmitl.natcha58070069.com.libreria;

import org.junit.Test;

import kmitl.natcha58070069.com.libreria.NameValidation;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertEquals;

public class NameValidationSuccessTest {

    @Test
    public void NameIsNormal(){
        NameValidation validation = new NameValidation();
        boolean result = validation.isNormal("Libreria");
        assertTrue("ต้องผ่านนะเพราะชื่อเป็นปกติแล้ว", result);
        assertEquals("Name validation is success", validation.validate("Libreria"));
    }
}
