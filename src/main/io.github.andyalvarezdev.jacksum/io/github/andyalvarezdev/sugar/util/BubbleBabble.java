package io.github.andyalvarezdev.sugar.util;

/**
 * encode() has been ported to Java from the OpenSSH's C source called key.c
 * by Johann N. Loefflmann
 *
 */

/**
 * Header notice from the source called key.c in OpenSSH:
 *
 * Copyright (c) 2000, 2001 Markus Friedl.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */


public class BubbleBabble {

  public static String encode(byte[] raw) {

    char[] vowels     = { 'a', 'e', 'i', 'o', 'u', 'y' };
    char[] consonants = { 'b', 'c', 'd', 'f', 'g', 'h', 'k', 'l', 'm',
                          'n', 'p', 'r', 's', 't', 'v', 'z', 'x' };

    int seed = 1;
    int rounds = (raw.length / 2) + 1;
    StringBuffer retval = new StringBuffer(rounds*6);
    retval.append('x');

    // x|abcd-e|abcd-e|fgh|x
    for (int i=0; i < rounds; i++) {
      int idx0, idx1, idx2, idx3, idx4;

      if ((i + 1 < rounds) || ((raw.length % 2) != 0)) {

        idx0 = ((( (((int)(raw[2 * i])) & 0xff) >>> 6) & 3) + seed) % 6;
        idx1 =   ( (((int)(raw[2 * i])) & 0xff) >>> 2) & 15;
        idx2 =  (( (((int)(raw[2 * i])) & 0xff) & 3) + (seed / 6)) % 6;

        retval.append(vowels[idx0]);
        retval.append(consonants[idx1]);
        retval.append(vowels[idx2]);

        if (i+1 < rounds) {
          idx3 =   ( (((int)(raw[2 * i + 1])) & 0xff) >>> 4) & 15;
          idx4 =     (((int)(raw[2 * i + 1])) & 0xff) & 15;

          retval.append(consonants[idx3]);
          retval.append('-');
          retval.append(consonants[idx4]);
          seed = ((seed * 5) +
               (((((int)(raw[2 * i])) & 0xff) * 7) +
               (((int)(raw[2 * i + 1])) & 0xff))) % 36;
        }
      } else {
        idx0 = seed % 6;
        idx1 = 16;
        idx2 = seed / 6;
        retval.append(vowels[idx0]);
        retval.append(consonants[idx1]);
        retval.append(vowels[idx2]);
      }
    }

    retval.append('x');
    return retval.toString();
  }

}