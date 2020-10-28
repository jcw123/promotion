package com.im.sky.mq;

import java.nio.charset.StandardCharsets;

/**
 * @author jiangchangwei
 * @date 2020-10-12 下午 5:26
 **/
public class Constants {

    public static final String DELIMITER = "#&#";

    public static final byte[] DELIMITER_BYTES = DELIMITER.getBytes(StandardCharsets.UTF_8);

    public static final byte[] MESSAGE_MARK = new byte[] {0xF, 0xF, 0xE, 0xE};
}
