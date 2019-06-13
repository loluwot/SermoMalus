package com.mygdx.game;

/**
 * @author Andy Cai
 * @version 1.0
 *
 * This class was made because arraylists of arrays are not possible
 */

public class Triplet {
   public String [] triplet = new String[3];

    /**
     * This is the constructor which will assign values to the array class
     * @param one   String
     * @param two   String
     * @param three String
     */
    public Triplet(String one, String two, String three){
        triplet[0] = one;
        triplet[1] = two;
        triplet[2] = three;
    }


}
