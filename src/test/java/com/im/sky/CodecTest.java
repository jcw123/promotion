package com.im.sky;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @author jiangchangwei
 * @date 2020-7-14 下午 4:49
 **/
public class CodecTest {

    @Test
   public void test1() {
       System.out.println(Charset.availableCharsets());
       Charset charset = Charset.forName("UTF-8");
       Charset gbkCharSet = Charset.forName("GBK");
       ByteBuffer buffer = charset.encode("你好吗");
        System.out.println(Arrays.toString(buffer.array()));
        byte[] bytes = buffer.array();
        System.out.println(charset.decode(ByteBuffer.wrap(bytes)));
        System.out.println(gbkCharSet.decode(ByteBuffer.wrap(bytes)));
   }

   @Test
   public void test2() {
        String s = "龙";
        char[] cArray = s.toCharArray();
        for(char c : cArray) {
            System.out.println(Integer.toHexString(c));
        }
        int data = Integer.parseInt("9F99",16);
        StringBuilder sb = new StringBuilder();
        sb.append((char)data);
        System.out.println(sb.toString());
   }

   @Test
   public void test3() throws Exception {
       String s = "小红";
       Charset utf8CharSet = Charset.forName("UTF-8");
       Charset gbkCharSet = Charset.forName("GBK");
       byte[] utf8Bytes = utf8CharSet.encode(s).array();
       byte[] gbkBytes = gbkCharSet.encode(s).array();

       // utf8编码的字节串用utf8格式解码
       CharBuffer charBuffer1 = utf8CharSet.decode(ByteBuffer.wrap(utf8Bytes));
       System.out.println("utf8编码的字节串用utf8格式解码=>" + new String(charBuffer1.array()));

       // utf8编码的字节串用gbk格式解码
       CharBuffer charBuffer2 = gbkCharSet.decode(ByteBuffer.wrap(utf8Bytes));
       System.out.println("utf8编码的字节串用gbk格式解码=>" + new String(charBuffer2.array()));

       // gbk编码的自节串用gbk格式解码
       CharBuffer charBuffer3 = gbkCharSet.decode(ByteBuffer.wrap(gbkBytes));
       System.out.println("gbk编码的自节串用gbk格式解码=>" + new String(charBuffer3.array()));

       // gbk编码的字节串用utf8格式解码
       CharBuffer charBuffer4 = utf8CharSet.decode(ByteBuffer.wrap(gbkBytes));
       System.out.println("gbk编码的字节串用utf8格式解码=>" + new String(charBuffer4.array()));
   }
}
