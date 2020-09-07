package com.homeadvisor.kafdrop.util;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class ByteUtils {

   public static String readString(byte[] bytes)
   {
      return new String(bytes, StandardCharsets.UTF_8);
   }

   public static String readString(ByteBuffer buffer)
   {
      return new String(readBytes(buffer), StandardCharsets.UTF_8);
   }

   private static byte[] readBytes(ByteBuffer buffer)
   {
      return readBytes(buffer, 0, buffer.limit());
   }

   private static byte[] readBytes(ByteBuffer buffer, int offset, int size)
   {
      byte[] dest = new byte[size];
      if (buffer.hasArray())
      {
         System.arraycopy(buffer.array(), buffer.arrayOffset() + offset, dest, 0, size);
      }
      else
      {
         buffer.mark();
         buffer.get(dest);
         buffer.reset();
      }
      return dest;
   }

   public static byte[] convertToByteArray(ByteBuffer buffer)
   {
     byte[] bytes = new byte[buffer.remaining()];
     buffer.get(bytes, 0, bytes.length);
     return bytes;
   }

}
