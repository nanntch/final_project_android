package kmitl.natcha58070069.com.libreria;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

public class CommentValidationFailTest {

    @Test
    public void CommentIsNull(){
        CommentValidation validation = new CommentValidation();
        String comment = null;
        boolean result = validation.isEmptyAndNull(comment);
        assertFalse("ต้องไม่ผ่านนะเพราะค่าเป็น null", result);
        assertEquals("Comment is Empty or Null", validation.validate(comment));
    }

    @Test
    public void CommentIsEmpty(){
        CommentValidation validation = new CommentValidation();
        String comment = "";
        boolean result = validation.isEmptyAndNull(comment);
        assertFalse("ต้องไม่ผ่านนะเพราะเป็นค่าว่าง", result);
        assertEquals("Comment is Empty or Null", validation.validate(comment));
    }

    @Test
    public void CommentIsTooShort(){
        CommentValidation validation = new CommentValidation();
        String comment = "a";
        boolean result = validation.isWrongLength(comment);
        assertFalse("ต้องไม่ผ่านนะเพราะสั้นเกิน", result);
        assertEquals("Comment is wrong length", validation.validate(comment));
    }

    @Test
    public void CommentIsTooLong(){
        String comment = "reebolqtlsexnzhitcsjuyplqjqfkgqfpvqmyvzfnyiuybaakywckjqwwumwbtg" +
                "mvcoigctuivyaosrqbreoidclcuaxvmddscngkkbfebwgxzcfhrhdvtlbhsffkjgfwccwteu" +
                "xpjijtdmuiqqldasnhqfbhbredymqbjkcquzuhkqygpinavzgjgsiasuzamubitoipvsdfsd" +
                "fdfsdggsdfdfgsgfdsdfsdasdsdgtryytfhjklouuyjyhgfafyuioikujyhtgredoiuyhtrc" +
                "dxs rfeetwrtewsd";
        CommentValidation validation = new CommentValidation();
        boolean result = validation.isWrongLength(comment);
        assertFalse("ต้องไม่ผ่านนะเพราะยาวเกิน", result);
        assertEquals("Comment is wrong length", validation.validate(comment));
    }

}
