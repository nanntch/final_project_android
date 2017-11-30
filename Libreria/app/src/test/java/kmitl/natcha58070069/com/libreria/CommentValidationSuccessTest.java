package kmitl.natcha58070069.com.libreria;


import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class CommentValidationSuccessTest {
    @Test
    public void NameIsNormal(){
        String comment = "This Libreria is good";
        CommentValidation validation = new CommentValidation();
        boolean result = validation.isWrongLength(comment);
        assertTrue("ต้องผ่านนะเพราะคอมเม้นต์เป็นปกติแล้ว", result);
        assertEquals("Comment validation is success", validation.validate(comment));
    }
}
