package com.cyberspeed;

import com.cyberspeed.utils.CmdArgsUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CmdArgsUtilsTest {

    @Test
    void testValidArgs() {
        String[] args = {"--name", "John", "--age", "30"};
        Map<String, String> result = CmdArgsUtils.parseArgs(args);

        assertEquals(2, result.size());
        assertEquals("John", result.get("--name"));
        assertEquals("30", result.get("--age"));
    }

    @Test
    void testEmptyArgs() {
        String[] args = {};
        Map<String, String> result = CmdArgsUtils.parseArgs(args);

        assertTrue(result.isEmpty());
    }

    @Test
    void testIncompleteNumberOfArgs() {
        String[] args = {"--key1", "value1", "--key2"};
        Map<String, String> result = CmdArgsUtils.parseArgs(args);

        assertEquals(1, result.size());
        assertEquals("value1", result.get("--key1"));
        assertNull(result.get("--key2"));
    }

    @Test
    void testDuplicateKeys() {
        String[] args = {"--key", "value1", "--key", "value2"};
        Map<String, String> result = CmdArgsUtils.parseArgs(args);

        assertEquals(1, result.size());
        assertEquals("value2", result.get("--key"));
    }
}
