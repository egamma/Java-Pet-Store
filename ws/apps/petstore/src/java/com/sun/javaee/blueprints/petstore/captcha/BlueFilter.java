/* Copyright 2006 Sun Microsystems, Inc. All rights reserved. You may not modify, use, reproduce, or distribute this software except in compliance with the terms of the License at: http://developer.sun.com/berkeley_license.html
$Id: BlueFilter.java,v 1.3 2006-05-03 21:48:56 inder Exp $ */

package com.sun.javaee.blueprints.petstore.captcha;

import java.awt.image.*;

/**
 *
 * @author yuta
 */
public class BlueFilter extends RGBImageFilter {
    
    /** Creates a new instance of BlueFilter */
    public BlueFilter() {
        canFilterIndexColorModel = true;
    }
    public int filterRGB(int x, int y, int rgb) {
        return (rgb | 0x000000FF);
    }
}
