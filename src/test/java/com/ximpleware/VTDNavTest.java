package com.ximpleware;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;

public class VTDNavTest {

  static final String TEST_FILE_2 = "src/test/resources/xml_2.xml";

  @Test
  public void testGetTokenType() throws Exception {
    final VTDGen gen = new VTDGen();
    final byte[] bytes = FileUtils.readFileToByteArray(new File(TEST_FILE_2));
    gen.setDoc(bytes);
    gen.parse(true);
    final VTDNav nav = gen.getNav();
    for (int i = 0; i < nav.vtdBuffer.size; i++) {
      final int tt = nav.getTokenType(i);
      final int to = nav.getTokenOffset(i);
      final int tl = nav.getTokenLength(i);
      final int td = nav.getTokenDepth(i);
      final int ci = nav.getCurrentIndex();

      final String s = String.format("Token Type: %5d Token Offset: %15d Token Length %15d Token Depth %5d Current Index: %5d", tt, to, tl, td, ci);
      System.out.println(s);

    }
  }

  @Test
  public void testToString() throws Exception {
    final VTDGen gen = new VTDGen();
    final byte[] bytes = FileUtils.readFileToByteArray(new File(TEST_FILE_2));
    gen.setDoc(bytes);
    gen.parse(true);
    final VTDNav nav = gen.getNav();
    System.out.println(nav.toString(nav.getCurrentIndex()));
    System.out.println(nav.toString(0, 55));
    System.out.println(nav.toString(240, 2));
    System.out.println(nav.toString(328, 10));

    System.out.println("+++"+nav.toString(0, 300)+ "+++");
  }

  @Test
  public void testToRawString() throws Exception {
    final VTDGen gen = new VTDGen();
    final byte[] bytes = FileUtils.readFileToByteArray(new File(TEST_FILE_2));
    gen.setDoc(bytes);
    gen.parse(true);
    final VTDNav nav = gen.getNav();
    for (int i = 0; i < nav.getTokenCount(); i++) {
      try {
        System.out.printf("Index %d String %s%n", i, nav.toRawString(i));
        System.out.printf("Index %d String %s%n", i, nav.toString(i));
      } catch (final Exception ex) {
        // ignore
      }
    }


    System.out.println();
    System.out.println();

    System.out.println(nav.toString(0, 55));
    System.out.println(nav.toString(240, 2));
    System.out.println(nav.toString(328, 10));
    System.out.println(nav.toString(328, 10));

    System.out.println("+++"+nav.toString(0, 300)+ "+++");
  }

  @Test
  public void testToElement() throws Exception {
    final VTDGen gen = new VTDGen();
    final byte[] bytes = FileUtils.readFileToByteArray(new File(TEST_FILE_2));
    gen.setDoc(bytes);
    gen.parse(true);
    final VTDNav nav = gen.getNav();
    if (nav.toElement(VTDNav.FC)) {
      boolean v;
      do {
        v = nav.toElement(VTDNav.NS);
        System.out.println(v);
      } while (v);
    }
  }
}
