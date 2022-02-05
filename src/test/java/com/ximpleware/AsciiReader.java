package com.ximpleware;

public class AsciiReader implements IReader {

    int offset;
    int endOffset;
    byte[] XMLDoc;

    public AsciiReader() {
    }

    public AsciiReader(int offset, int endOffset, byte[] XMLDoc) {
        this.offset = offset;
        this.endOffset = endOffset;
        this.XMLDoc = XMLDoc;
    }

    final public int getChar() throws EOFException, ParseException, EncodingException {
        int a;
        if (offset >= endOffset)
            throw new EOFException();
        a = XMLDoc[offset++];
        if (a < 0)
            throw new ParseException(
                    "ASCII encoding error: invalid ASCII Char");
        return a;
    }

    final public boolean skipChar(int ch)
            throws ParseException, EOFException, EncodingException {
        if (ch == XMLDoc[offset]) {
            offset++;
            return true;
        } else {
            return false;
        }
    }

    final public long _getChar(int offset) {
        int c = XMLDoc[offset];
        if (c == '\r' && XMLDoc[offset + 1] == '\n')
            return (2L << 32) | '\n';
        return (1L << 32) | c;
    }

    final public char decode(int offset) {
        return (char) XMLDoc[offset];
    }

    public static void main(String[] args) {
        final AsciiReader reader = new AsciiReader(0, 4, "µ$&£".getBytes());
        while (true) {
            try {
                System.out.print(reader.getChar());
            } catch (EOFException ex) {
                break;
            } catch (EncodingException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

}
