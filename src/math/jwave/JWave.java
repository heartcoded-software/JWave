/**
 * JWave is distributed under the MIT License (MIT); this file is part of.
 *
 * Copyright (c) 2008-2015 Christian Scheiblich (cscheiblich@gmail.com)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package math.jwave;

import math.jwave.exceptions.JWaveException;
import math.jwave.transforms.BasicTransform;
import math.jwave.transforms.DiscreteFourierTransform;
import math.jwave.transforms.FastWaveletTransform;
import math.jwave.transforms.TransformBuilder;
import math.jwave.transforms.WaveletPacketTransform;
import math.jwave.transforms.wavelets.Haar1;
import math.jwave.transforms.wavelets.Wavelet;
import math.jwave.transforms.wavelets.WaveletBuilder;
import math.jwave.transforms.wavelets.coiflet.Coiflet1;
import math.jwave.transforms.wavelets.daubechies.Daubechies2;
import math.jwave.transforms.wavelets.daubechies.Daubechies3;
import math.jwave.transforms.wavelets.daubechies.Daubechies4;
import math.jwave.transforms.wavelets.legendre.Legendre1;
import math.jwave.transforms.wavelets.legendre.Legendre2;
import math.jwave.transforms.wavelets.legendre.Legendre3;

/**
 * Main class for doing little test runs for different transform types and
 * different wavelets without JUnit.
 * 
 * @date 23.02.2010 14:26:47
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 */
public class JWave {

  /**
   * Constructor.
   * 
   * @date 23.02.2010 14:26:47
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   */
  public JWave( ) {
  } // JWave

  /**
   * Main method for doing little test runs for different transform types and
   * different wavelets without JUnit. Requesting the transform type and the
   * type of wavelet to be used else usage is printed.
   * 
   * @date 23.02.2010 14:26:47
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @param args
   *          [transformType] [waveletType]
   */
  public static void main( String[ ] args ) {

    try {

      if( args.length < 3 || args.length > 5 ) {
        System.err.println( "usage: JWave [transformType] {waveletType}" );
        System.err.println( "" );
        System.err.println( "Transform names: "
            + "'Discrete Fourier Transform'" + " " + "'Fast Wavelet Transform'"
            + " " + "'Wavelet Packet Transform'" );
        System.err.println( "Wavelet names: " + "'Haar'," + " "
            + "'Haar orthogonal'," + " " + "'Daubechies 2'" + " " + ".." + " "
            + "'Daubechies 20'," + " " + "'Symlet 2'" + " " + ".." + " "
            + "'Symlet 20'," + " " + "'Coiflet 1'" + " " + ".." + " "
            + "'Coiflet 5'," + " " + "'Legendre 1'" + " " + ".." + " "
            + "'Legendre 3'," + " " + " ... 'BiOrthogonal 1/1'"
            + " have a look for more in the 'transform.wavelets' package!" );
        return;
      } // if args

      String wType = args[ 3 ] + " " + args[ 4 ]; // raw n dirty but working
      Wavelet wavelet = null;
      wavelet = WaveletBuilder.create( wType );

      String tType = args[ 0 ] + " " + args[ 1 ] + " " + args[ 2 ]; // raw n dirty but working
      BasicTransform bWave = null;
      bWave = TransformBuilder.create( tType, wavelet );

      // instance of transform
      Transform t;

      t = new Transform( bWave ); // perform all steps

      double[ ] arrTime = { 1., 1., 1., 1., 1., 1., 1., 1. };

      if( bWave instanceof DiscreteFourierTransform )
        System.out.print( TransformBuilder.identify( bWave ) );
      else
        System.out.print( TransformBuilder.identify( bWave ) + " using "
            + WaveletBuilder.identify( wavelet ) );
      System.out.println( "" );
      System.out.println( "time domain:" );
      for( int p = 0; p < arrTime.length; p++ )
        System.out.printf( "%9.6f", arrTime[ p ] );
      System.out.println( "" );

      double[ ] arrFreqOrHilb = null;
      arrFreqOrHilb = t.forward( arrTime );

      if( bWave instanceof DiscreteFourierTransform )
        System.out.println( "frequency domain:" );
      else
        System.out.println( "Hilbert domain:" );
      for( int p = 0; p < arrTime.length; p++ )
        System.out.printf( "%9.6f", arrFreqOrHilb[ p ] );
      System.out.println( "" );

      double[ ] arrReco = null;

      arrReco = t.reverse( arrFreqOrHilb );

      System.out.println( "reconstruction:" );
      for( int p = 0; p < arrTime.length; p++ )
        System.out.printf( "%9.6f", arrReco[ p ] );
      System.out.println( "" );

    } catch( JWaveException e ) {

      e.showMessage( );

    } // try

  } // main

} // class
