package com.sneakerate.web.crawler.service;

import com.ECS.client.jax.ItemLookupResponse;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

public class AmazonFeedServiceIT {
    private AmazonFeedService amazonFeedService;

    @Before
    public void setUp() {
        amazonFeedService = new AmazonFeedService();
    }

    @Test
    public void testLookup() {
        ItemLookupResponse result = amazonFeedService.findParentIds(Collections.singletonList("B01J5F21SE"));

        String parentId = result.getItems().get(0).getItem().get(0).getParentASIN();

        result = amazonFeedService.findVariants(Collections.singletonList(parentId));

        Assertions.assertThat(result.getItems().get(0).getItem().get(0).getVariations()).isNotNull();
    }
}