package net.skytreader.kode.chesstemplar.exceptions;

/**
This is exception is thrown whenever, for some reason, it is detected that a
chess game has reached an invalid state. This could be caused by any number of
reasons like user input, faulty AI/rules arbiter, or notational typos.

Possible invalid states include but are not limited to:
<ul>
    <li>The number of Kings in the board is not equal to 2.</li>
    <li>There are more than eight pawns for a particular color.</li>
</ul>

@author Chad Estioco
*/
public class InvalidStateException extends Exception{
    public InvalidStateException(){
        super();
    }
    public InvalidStateException(String message){
        super(message);
    }
    public InvalidStateException(String message, Throwable cause){
        super(message, cause);
    }
    public InvalidStateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
        super(message, cause, enableSuppression, writableStackTrace);
    }
    public InvalidStateException(Throwable cause){
        super(cause);
    }
}
