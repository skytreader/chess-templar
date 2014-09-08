package net.skytreader.kode.chesstemplar.pieces;

/**
This exception is thrown when you try to get the moves of a piece not equivalent
to the owner of a getLegalMoves method.

@author Chad Estioco
*/
public class NotMeException extends Exception{
    
    public NotMeException(){
        super();
    }

    public NotMeException(String message){
        super(message);
    }

    public NotMeException(String message, Throwable cause){
        super(message, cause);
    }

    public NotMeException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace){
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
    public NotMeException(Throwable cause){
        super(cause);
    }
}
