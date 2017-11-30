package kmitl.natcha58070069.com.libreria;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

public class NameValidationFailTest {

    @Test
    public void NameIsNull(){
        NameValidation validation = new NameValidation();
        String name = null;
        boolean result = validation.isEmptyAndNull(name);
        assertFalse("ต้องไม่ผ่านนะเพราะค่าเป็น null", result);
        assertEquals("Name is Empty or Null", validation.validate(name));
    }

    @Test
    public void NameIsEmpty(){
        NameValidation validation = new NameValidation();
        String name = "";
        boolean result = validation.isEmptyAndNull(name);
        assertFalse("ต้องไม่ผ่านนะ เพราะเป็นค่า Null", result);
        assertEquals("Name is Empty or Null", validation.validate(name));
    }

    @Test
    public void NameIsTooLong(){
        NameValidation validation = new NameValidation();
        String name = "abcdefghijabcdefghijabcdefghijl";
        boolean result = validation.isWrongLength(name);
        assertFalse("ต้องไม่ผ่านนะ เพราะชื่อยาวเกิน", result);
        assertEquals("Name is wrong length", validation.validate(name));
    }

    @Test
    public void NameIsTooShort(){
        NameValidation validation = new NameValidation();
        String name = "a";
        boolean result = validation.isWrongLength(name);
        assertFalse("ต้องไม่ผ่านนะ เพราะชื่อสั้นเกินไป", result);
        assertEquals("Name is wrong length", validation.validate(name));
    }

    @Test
    public void NameIsNotNormal(){
        NameValidation validation = new NameValidation();
        String name = "@=!Book";
        boolean result = validation.isNormal(name);
        assertFalse("ต้องไม่ผ่านนะ เพราะมีอักขระพิเศษ", result);
        assertEquals("Name is not normal", validation.validate(name));
    }
}
