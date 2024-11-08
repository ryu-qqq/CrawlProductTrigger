package com.ryuqq.setof.lamda;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MustItMapperTest {

    private MustItMapper mustItMapper;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mustItMapper = new MustItMapper();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testParseData() throws Exception {
        // Given: JSON data representing MustItCrawlRawData
        String jsonData = """
                [
                    {
                        "items": {
                            "itemNo": 12345,
                            "name": "Test Product 1"
                        }
                    },
                    {
                        "items": {
                            "itemNo": 67890,
                            "name": "Test Product 2"
                        }
                    }
                ]
                """;

        JsonNode dataNode = objectMapper.readTree(jsonData);
        long siteId = 1L;

        // When: parsing data with MustItMapper
        List<CrawlProductInsertRequestDto> result = mustItMapper.parseData(siteId, dataNode);

        // Then: verifying that the result matches expected data
        assertNotNull(result);
        assertEquals(2, result.size());

        CrawlProductInsertRequestDto firstProduct = result.get(0);
        assertEquals(siteId, firstProduct.getSiteId());
        assertEquals(12345, firstProduct.getProductId());
        assertEquals("Test Product 1", firstProduct.getProductName());

        CrawlProductInsertRequestDto secondProduct = result.get(1);
        assertEquals(siteId, secondProduct.getSiteId());
        assertEquals(67890, secondProduct.getProductId());
        assertEquals("Test Product 2", secondProduct.getProductName());
    }
}
