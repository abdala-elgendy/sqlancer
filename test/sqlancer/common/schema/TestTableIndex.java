package sqlancer.common.schema;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TestTableIndex {

    @Test
    void testCreateAndGetIndexName() {
        String indexName = "test_index";
        TableIndex index = TableIndex.create(indexName);
        assertEquals(indexName, index.getIndexName());
    }

    @Test
    void testToString() {
        String indexName = "test_index";
        TableIndex index = TableIndex.create(indexName);
        assertEquals(indexName, index.toString());
    }

    @Test
    void testNotEquals() {
        TableIndex index1 = TableIndex.create("test_index1");
        TableIndex index2 = TableIndex.create("test_index2");
        assertNotEquals(index1, index2);
    }
}
