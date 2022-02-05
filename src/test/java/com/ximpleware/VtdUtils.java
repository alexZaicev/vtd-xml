package com.ximpleware;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VtdUtils {

  private static final String URN_MARKER = "urn:";
  private static final String HTTP_MARKER = "http://";
  private static final String DEFAULT_NS_PREFIX = "xmlns:";
  // threshold of index position for hasRootNsDeclarations
  private static final int TH_NS_DETECTION_ROOT = 10;

  static final String TEST_FILE_2 = "src/test/resources/xml_2.xml";
  static final String TEST_FILE_3 = "src/test/resources/xml_3.xml";

  @Test
  public void testExtractUrnNameSpaces() throws Exception {
    byte[] bytes = FileUtils.readFileToByteArray(new File(TEST_FILE_2));
    VTDNav vtdNav = getVtdNavigator(bytes, true);
    System.out.println(extractUrnNameSpaces(vtdNav));

    bytes = FileUtils.readFileToByteArray(new File(TEST_FILE_3));
    vtdNav = getVtdNavigator(bytes, true);
    System.out.println(extractUrnNameSpaces(vtdNav));
  }

  @Test
  public void testRetrieve() throws IOException {
    final byte[] bytes = FileUtils.readFileToByteArray(new File(TEST_FILE_2));


    final ImmutableList.Builder<String> builder = ImmutableList.builder();
    final List<String> pathElements = new ArrayList<>();
    try {
      final VTDGen vtdGen = new VTDGen();
      vtdGen.setDoc(bytes);
      vtdGen.parse(true);
      final VTDNav vNav = vtdGen.getNav();
      this.stepInto(vNav, builder, pathElements);
      System.out.println(builder.build());
    } catch (final com.ximpleware.ParseException | NavException ex) {
      System.out.println(builder.build());
    }
  }

  public static VTDNav getVtdNavigator(final byte[] xml, final boolean isNsAware) throws Exception {
    try {
      final VTDGen vtdGen = new VTDGen();
      vtdGen.setDoc(xml);
      vtdGen.parse(isNsAware);
      return vtdGen.getNav();
    } catch (final ParseException e) {
      throw new Exception("Couldn't parse document: ParseException");
    }
  }

  public static Map<String, String> extractUrnNameSpaces(final VTDNav vNav) throws Exception {
    final Map<String, String> urns = new HashMap<String, String>();
    try {
      final String[] rootElementFragments = vNav.toString(vNav.getCurrentIndex()).split(":");
      int k = 1;
      for (int i = 0; i < vNav.getTokenCount(); i++) {
        final int type = vNav.getTokenType(i);
        if (type == VTDNav.TOKEN_ATTR_NS) {
          final String ns = vNav.toString(i).replace(DEFAULT_NS_PREFIX, "");
          final String uri = vNav.toString(i + 1);
          if (uri.startsWith(URN_MARKER) || uri.startsWith(HTTP_MARKER)) {
            if (urns.get(ns) != null) {
              if (!urns.get(ns).equals(uri)) {
                urns.put(ns + '~' + k++, uri);
              }
            } else {
              urns.put(ns, uri);
            }
          }
        }
      }
      if (rootElementFragments.length > 1
          && !urns.containsKey(rootElementFragments[0])
          && urns.containsKey("xmlns")) {
        urns.put(rootElementFragments[0], urns.get("xmlns"));
      }
    } catch (final VTDException e) {
      throw new Exception("Couldn't extract urn name spaces from document");
    }
    return urns;
  }

  private void stepInto(
          final VTDNav vNav,
          final ImmutableList.Builder<String> builder,
          final List<String> pathElements)
          throws NavException {
    pathElements.add(vNav.toString(vNav.getCurrentIndex()));
    builder.add(xPathOf(pathElements));
    if (vNav.toElement(VTDNav.FC)) {
      do {
        stepInto(vNav, builder, pathElements);
        pathElements.remove(pathElements.size() - 1);
      } while (vNav.toElement(VTDNav.NS));
      vNav.toElement(VTDNav.P);
    }
  }

  private String xPathOf(final List<String> pathElements) {
    return "/" + Joiner.on("/").join(pathElements);
  }
}
