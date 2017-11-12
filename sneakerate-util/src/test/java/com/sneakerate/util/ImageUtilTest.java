package com.sneakerate.util;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ImageUtilTest {

    @Test
    public void testPrepareImage() throws IOException {
        URL url = getClass().getClassLoader().getResource("com/sneakerate/util/test-image.jpg");
        BufferedImage image = ImageUtil.prepareImage(url, 1200);

        Assertions.assertThat(image).isNotNull();
        Assertions.assertThat(image.getWidth()).isEqualTo(1200);
        Assertions.assertThat(image.getHeight()).isGreaterThanOrEqualTo(627).isLessThanOrEqualTo(629);

        File file = ImageUtil.saveToFile(image);
        file.delete();
    }
}