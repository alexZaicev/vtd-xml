package com.ximpleware;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;

public class XMLModifierTest {

  static final String TEST_FILE_2 = "src/test/resources/xml_2.xml";

  @Test
  public void testRemove() throws Exception {
    final VTDGen gen = new VTDGen();
    final byte[] bytes = FileUtils.readFileToByteArray(new File(TEST_FILE_2));
    gen.setDoc(bytes);
    gen.parse(true);
    final VTDNav vNav = gen.getNav();

    final XMLModifier modifier = new XMLModifier();
    modifier.bind(vNav);
    final AutoPilot ap = new AutoPilot();
    ap.selectXPath("//*[string(.)]");
    ap.bind(vNav);
    while (ap.evalXPath() != -1) {
      modifier.remove();
    }
    final XMLByteOutputStream xbos = new XMLByteOutputStream(modifier.getUpdatedDocumentSize());
    modifier.output(xbos);

    System.out.println(new String(xbos.getXML()));
  }
}
