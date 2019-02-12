package io.github.chanlugeon.version;

import java.util.Arrays;

public class Version implements Comparable<Version> {

    private final String version;

    /**
     * Parses the specified format into a Version.
     *
     * @param versionFormat version format
     * @return Version using the specified format
     */
    public static Version parse(String versionFormat) {
        return new Version(versionFormat);
    }

    private Version(String versionFormat) {
        validateVersionFormat(versionFormat);
        version = versionFormat;
    }

    private static void validateVersionFormat(String versionFormat) {
        if (!isValidVersionFormat(versionFormat))
            throw new IllegalFormatVersionException(versionFormat);
    }

    private static boolean isValidVersionFormat(String versionFormat) {
        return versionFormat.matches("(?!0+[1-9]*)\\d+(\\.\\d+)*");
    }

    /**
     * Returns true if this version equals the version of the specified
     * object.
     *
     * @implSpec
     * This implementation returns {@code this.compareTo(o) == 0}.
     *
     * @return true if this version equals the version of the specified
     * object
     */
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if(!(o instanceof Version))
            return false;

        Version v = (Version)o;
        return compareTo(v) == 0;
    }

    /**
     * Compares this version with the version of another version.
     *
     * @param anotherVersion Version to which this Version is to be
     * compared
     * @return -1, 0 or 1 as this version numerically less than,
     * equal to, or greater than anotherVersion
     */
    @Override
    public int compareTo(Version anotherVersion) {
        int[] v = versionSequence();
        int[] av = anotherVersion.versionSequence();
        int max = Math.max(v.length, av.length);

        for (int i = 0; i < max; i++) {
            int vPart = i < v.length ? v[i] : 0;
            int avPart = i < av.length ? av[i] : 0;
            if (vPart != avPart)
                return vPart - avPart;
        }

        return 0;
    }

    private int[] versionSequence() {
        return Arrays.stream(dividedVersionByDot()).mapToInt(Version::trimVersionPart).toArray();
    }

    private String[] dividedVersionByDot() {
        return version.split("\\.");
    }

    private static int trimVersionPart(String versionPart) {
        return Integer.parseInt(versionPart.replaceAll("^(0*[1-9]+)0*$", "$1"));
    }

    /**
     * Returns the hash code of this version.
     *
     * @return the hash code of this version
     */
    @Override
    public int hashCode() {
        return version.hashCode();
    }

    /**
     * Returns this version.
     *
     * @return this version
     */
    @Override
    public String toString() {
        return version;
    }
}