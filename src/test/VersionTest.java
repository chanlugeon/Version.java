package test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import io.github.chanlugeon.version.IllegalFormatVersionException;
import io.github.chanlugeon.version.Version;

final class VersionTest {

    @Test
    void testFirstDigit() {
        Version a = Version.parse("2.0");
        Version b = Version.parse("1.9.9");
        assertTrue(a.compareTo(b) > 0);
    }

    @Test
    void testThirdDigit() {
        Version a = Version.parse("1.1");
        Version b = Version.parse("1.1.1");
        assertTrue(a.compareTo(b) < 0);
    }

    @Test
    void testEquals() {
        Version a = Version.parse("1.01");
        Version b = Version.parse("1.010.0");
        assertTrue(a.equals(b));
    }

    @TestFactory
    Collection<DynamicTest> testComparableImpl() {
        List<Version> vers = new LinkedList<>();
        Version min = Version.parse("1.00.1");
        Version max = Version.parse("2");
        vers.add(Version.parse("1.0.2"));
        vers.add(max);
        vers.add(Version.parse("1.01.0"));
        vers.add(min);
        Collections.sort(vers);
        return Arrays.asList(
                dynamicTest("Minimum", () -> assertTrue(Collections.min(vers).equals(min))),
                dynamicTest("Maximum", () -> assertTrue(Collections.max(vers).equals(max)))
                );
    }

    @TestFactory
    Collection<DynamicTest> testIllegalFormatVersionException() {
        return Arrays.asList(
                dynamicTestForFormatVersion("1."),
                dynamicTestForFormatVersion(".1"),
                dynamicTestForFormatVersion("01"),
                dynamicTestForFormatVersion("1..2"),
                dynamicTestForFormatVersion("1.3d"),
                dynamicTestForFormatVersion("")
                );
    }

    private static DynamicTest dynamicTestForFormatVersion(String version) {
        return dynamicTest("Illegal format: " + version, () -> assertThrows(
                IllegalFormatVersionException.class, () -> Version.parse(version)));
    }
}
