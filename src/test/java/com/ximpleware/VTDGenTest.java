package com.ximpleware;

import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class VTDGenTest extends TestCase {

  static final String TEST_FILE_1 = "src/test/resources/xml_1.xml";
  static final String TEST_FILE_2 = "src/test/resources/xml_2.xml";
  static final String TEST_FILE_3 = "src/test/resources/xml_3.xml";
  static final String TEST_FILE_4 = "src/test/resources/xml_4.xml";
  static final String TEST_FILE_5 = "src/test/resources/xml_5.xml";
  static final String TEST_FILE_6 = "src/test/resources/xml_6.xml";

  public void testParse1() throws IOException, ParseException {
    final byte[] bytes = FileUtils.readFileToByteArray(new File(TEST_FILE_1));
    final VTDGen vtdGen = new VTDGen();
    vtdGen.setDoc(bytes);
    vtdGen.parse(true);
  }

  public void testParse2() throws IOException, ParseException {
    final byte[] bytes = FileUtils.readFileToByteArray(new File(TEST_FILE_2));
    final VTDGen vtdGen = new VTDGen();
    vtdGen.setDoc(bytes);
    vtdGen.parse(true);
  }

  public void testParse3() throws IOException, ParseException {
    final byte[] bytes = FileUtils.readFileToByteArray(new File(TEST_FILE_3));
    final VTDGen vtdGen = new VTDGen();
    vtdGen.setDoc(bytes);
    vtdGen.parse(true);
  }

  public void testParse4() throws IOException, ParseException {
    final byte[] bytes = FileUtils.readFileToByteArray(new File(TEST_FILE_4));
    final VTDGen vtdGen = new VTDGen();
    vtdGen.setDoc(bytes);
    vtdGen.parse(true);
  }

  public void testParse5() throws IOException, ParseException {
    final byte[] bytes = FileUtils.readFileToByteArray(new File(TEST_FILE_5));
    final VTDGen vtdGen = new VTDGen();
    vtdGen.setDoc(bytes);
    vtdGen.parse(false);
  }

  public void testParse6() throws IOException, ParseException {
    final byte[] bytes = FileUtils.readFileToByteArray(new File(TEST_FILE_6));
    final VTDGen vtdGen = new VTDGen();
    vtdGen.setDoc(bytes);
    vtdGen.parse(false);
  }
}
