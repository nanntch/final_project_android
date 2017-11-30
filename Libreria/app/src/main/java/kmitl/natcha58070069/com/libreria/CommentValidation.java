package kmitl.natcha58070069.com.libreria;

class CommentValidation {

    public String validate(String comment) {
        String validateText = "Comment validation is success";

        if (isEmptyAndNull(comment) == false){
            validateText = "Comment is Empty or Null";
        } else if (isWrongLength(comment) == false){
            validateText = "Comment is wrong length";
        }
        return validateText;
    }

    public boolean isEmptyAndNull(String comment) {
        if (comment == null || comment == ""){
            return false;
        }
        return true;
    }

    public boolean isWrongLength(String comment) {
        if (comment.length() < 2 || comment.length() > 280){
            return false;
        }
        return true;
    }
}
