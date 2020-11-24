package io.github.andyalvarezdev.sugar.util;

/**
 * Thrown to indicate that the application has attempted to specify
 * a non supported encoding
 */
public class EncodingException extends IllegalArgumentException {

    /**
     * Constructs a <code>EncodingException</code> with no detail message.
     */
    public EncodingException () {
      super();
    }

    /**
     * Constructs a <code>EncodingException</code> with the
     * specified detail message.
     *
     * @param   s   the detail message.
     */
    public EncodingException (String s) {
      super (s);
    }

}
