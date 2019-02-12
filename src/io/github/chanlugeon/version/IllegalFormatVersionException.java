package io.github.chanlugeon.version;

/**
 * An exception that will be thrown if the version format is wrong.
 */
public class IllegalFormatVersionException extends IllegalArgumentException {
    private static final long serialVersionUID = 1L;

    public IllegalFormatVersionException(String specifier) {
        super("Invalid version format specifier \'" + specifier + "\'");
    }
}
