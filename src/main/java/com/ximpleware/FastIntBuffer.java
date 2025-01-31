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

import java.lang.IndexOutOfBoundsException;
import java.lang.NullPointerException;
import java.lang.IllegalArgumentException;
import java.util.ArrayList;

/**
 * A fast, unsynchronized, chunk-based int buffer
 */

public class FastIntBuffer implements IIntBuffer {
    /* bufferArrayList is a resizable array list of int buffers
     *
     */
    public final static int ASCENDING = 0;
    public final static int DESCENDING = 1;
    private arrayList bufferArrayList;

    /**
     * Total capacity of the IntBuffer
     */
    private int capacity;

    /**
     * Page size of the incremental growth of the Int Buffer
     */
    private int pageSize;

    /**
     * Total number of integers in the IntBuffer
     */
    protected int size;
    private int exp;
    private int r;
    /**
     * FastIntBuffer constructor comment.
     */
//    public FastIntBuffer() {
//        size = 0;
//        capacity = 0;
//        pageSize = 1024;
//        exp = 10;
//        r = 1023;
//        bufferArrayList = new arrayList();
//    }

    /**
     * Constructor with adjustable buffer page size of the value bfz
     * The actually page size is 1<<e
     *
     * @param e int  is the size of the internal buffer
     */
    public FastIntBuffer(int e) {
        if (e < 0) {
            throw new IllegalArgumentException();
        }
        capacity = size = 0;
        pageSize = 1 << e;
        exp = e;
        r = pageSize - 1;
        bufferArrayList = new arrayList();
    }

    /**
     * Append an int array to the end of this buffer instance
     *
     * @param int_array int[]
     */
    public final void append(int[] int_array) {
    /*if (int_array == null) {
        throw new NullPointerException();
    }*/
        // no additional buffer space needed
        int lastBufferIndex;
        int[] lastBuffer;

        if (bufferArrayList.size == 0) {
            lastBuffer = new int[pageSize];
            bufferArrayList.add(lastBuffer);
            lastBufferIndex = 0;
            capacity = pageSize;
        } else {
            lastBufferIndex = Math.min((size >> exp),//+(((size&r)==0)? 0:1),
                    bufferArrayList.size - 1);
            lastBuffer = (int[]) bufferArrayList.get(lastBufferIndex);
        }

        if ((this.size + int_array.length) < this.capacity) {
            //get the last buffer from the bufferListArray
            //obtain the starting offset in that buffer to which the data is to be copied
            //update length

            //System.arraycopy(input, 0, lastBuffer, size % pageSize, input.length);
            if (this.size + int_array.length < ((lastBufferIndex + 1) << exp)) {
                System.arraycopy(int_array, 0, lastBuffer, size & r, int_array.length);
            } else {
                int offset = pageSize - (size & r);
                // copy the first part
                System.arraycopy(int_array, 0, lastBuffer, size & r, offset);
                // copy the middle part

                int l = int_array.length - offset;
                int k = (l) >> exp;
                int z;
                for (z = 1; z <= k; z++) {
                    System.arraycopy(int_array, offset,
                            (int[]) bufferArrayList.get(lastBufferIndex + z), 0, pageSize);
                    offset += pageSize;
                }
                // copy the last part
                System.arraycopy(int_array, offset,
                        (int[]) bufferArrayList.get(lastBufferIndex + z), 0, l & r);
            }
            size += int_array.length;
            return;
        } else // new buffers needed
        {

            // compute the number of additional buffers needed
//        int n =
//            ((int) ((int_array.length + size) / pageSize))
//                + (((int_array.length + size) % pageSize) > 0 ? 1 : 0)
//                - (int) (capacity / pageSize);
            int n =
                    ((int_array.length + size) >> exp)
                            + (((int_array.length + size) & r) > 0 ? 1 : 0)
                            - (capacity >> exp);
            // create these buffers
            // add to bufferArrayList

            //System.arraycopy(int_array, 0, lastBuffer, size % pageSize, capacity - size);
            System.arraycopy(int_array, 0, lastBuffer, size & r, capacity - size);

            for (int i = 0; i < n; i++) {
                int[] newBuffer = new int[pageSize];
                if (i < n - 1) {
                    // full copy
                    System.arraycopy(
                            int_array,
                            pageSize * i + capacity - size,
                            newBuffer,
                            0,
                            pageSize);
                } else {
                    // last page
                    System.arraycopy(
                            int_array,
                            pageSize * i + capacity - size,
                            newBuffer,
                            0,
                            int_array.length + this.size - capacity - pageSize * i);
                }
                bufferArrayList.add(newBuffer);
            }
            // update length
            size += int_array.length;
            // update capacity
            capacity += n * pageSize;
            // update
        }
    }

    /**
     * Append a single int to the end of this buffer Instance
     *
     * @param i int
     */
    public final void append(int i) {

        //int[] lastBuffer;
        //int lastBufferIndex;
    /*if (bufferArrayList.size == 0) {
        lastBuffer = new int[pageSize];
        bufferArrayList.add(lastBuffer);
        capacity = pageSize;
    } else {
        lastBufferIndex = Math.min((size>>exp),//+(((size&r)==0)? 0:1), 
                bufferArrayList.size - 1);
        lastBuffer = (int[]) bufferArrayList.oa[lastBufferIndex];
        //lastBuffer = (int[]) bufferArrayList.get(bufferArrayList.size() - 1);
    }*/
        if (this.size < this.capacity) {
            //get the last buffer from the bufferListArray
            //obtain the starting offset in that buffer to which the data is to be copied
            //update length
            //System.arraycopy(long_array, 0, lastBuffer, size % pageSize, long_array.length);
            ((int[]) bufferArrayList.oa[size >> exp])[size & r] = i;
            //((int[])bufferArrayList.oa[bufferArrayList.size-1])[size & r] = i;
//        lastBuffer[size % pageSize] = i;
            size += 1;
        } else // new buffers needed
        {
            int[] newBuffer = new int[pageSize];
            size++;
            capacity += pageSize;
            bufferArrayList.add(newBuffer);
            newBuffer[0] = i;
        }
    }

    /**
     * Get the int at the location specified by index.
     *
     * @param index int
     * @return int
     */
    public final int intAt(int index) {
    /*if (index > size-1) {
        throw new IndexOutOfBoundsException();
    }*/
//    int pageNum = (int) index / pageSize;
        int pageNum = index >> exp;
        //System.out.println("page Number is "+pageNum);
//    int offset = index % pageSize;
        int offset = index & r;
        return ((int[]) bufferArrayList.get(pageNum))[offset];
    }

    /**
     * Assigns a new int value to location index of the buffer instance.
     *
     * @param index    int
     * @param newValue int
     */
    public final void modifyEntry(int index, int newValue) {
	
        /*if (index > size - 1) {
            throw new IndexOutOfBoundsException(" index out of range");
        }*/

//        ((int[]) bufferArrayList.get((int) (index / pageSize)))[index % pageSize] =
        ((int[]) bufferArrayList.oa[index >> exp])[index & r] =
                newValue;

    }

    /**
     * Returns the total number of int values in the buffer instance
     *
     * @return int
     */
    public final int size() {
        return size;
    }

    /**
     * Returns the int array corresponding to all int values in this buffer instance
     *
     * @return int[] (null if the buffer is empty)
     */
    public int[] toIntArray() {
        if (size > 0) {
            int s = size;
            int[] resultArray = new int[size];
            //copy all the content int into the resultArray
            int array_offset = 0;
            for (int i = 0; s > 0; i++) {
                System.arraycopy(
                        (int[]) bufferArrayList.get(i),
                        0,
                        resultArray,
                        array_offset,
                        (s < pageSize) ? s : pageSize);
//            (i == (bufferArrayList.size() - 1)) ? size() % pageSize : pageSize);
                s = s - pageSize;
                array_offset += pageSize;
            }
            return resultArray;
        }
        return null;
    }


    /**
     * set the size of int buffer to zero, capacity
     * untouched so int buffer can be reused without
     * any unnecessary and additional allocation
     */
    public final void clear() {
        size = 0;
    }

    /**
     * Set the size of FastIntBuffer to newSz if newSz is less than the
     * capacity, otherwise return false
     *
     * @param newSz
     * @return status of resize
     */
    public final boolean resize(int newSz) {
        if (newSz <= capacity && newSz >= 0) {
            size = newSz;
            return true;
        } else
            return false;
    }
}
