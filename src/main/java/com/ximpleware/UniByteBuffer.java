/*
 * Copyright (C) 2002-2012 XimpleWare, info@ximpleware.com
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */
package com.ximpleware;

/**
 * A simple wrapper around monolithic byte array implementing IByteBuffer interface.
 * Creation date: (11/25/03 5:05:57 PM)
 */
public class UniByteBuffer implements IByteBuffer {
    private byte[] ba;

    /**
     * UniByteBuffer constructor comment.
     *
     * @throws IllegalArguement when b is a null pointer
     */
    public UniByteBuffer(byte[] b) {
        super();
        if (b == null)
            throw new IllegalArgumentException();
        ba = b;
    }

    /**
     * Get the byte at the index.
     * Creation date: (11/25/03 5:07:42 PM)
     *
     * @param index int
     * @return byte
     */
    public final byte byteAt(int index) {
        return (byte) ba[index];
    }

    /**
     * Return the token in its original encoding format.
     * Creation date: (11/28/03 7:02:07 PM)
     *
     * @param offset int
     * @param len    int
     * @return byte[]
     */
    public final byte[] getBytes(int offset, int len) {
        byte[] b = new byte[len];
        System.arraycopy(ba, offset, b, 0, len);
        return b;
    }

    /**
     * Get total size in terms of # of bytes.
     * Creation date: (11/25/03 5:13:10 PM)
     *
     * @return int
     */
    public int length() {
        return ba.length;
    }

    /**
     * Return the byte array containing the original XML document
     *
     * @return byte[]
     */
    public byte[] getBytes() {
        return ba;
    }
}
