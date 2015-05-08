package com.example.mwgshowshow;

import org.apache.http.client.RedirectException;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

/**
 * 
 * @author supermanmwg
 * 
 * @class ImageHandle
 * 
 * @brief  Handle image effect 
 *
 */

public class ImageHandle {
	
	/**
	 * @method handleImageEffect
	 * 
	 * @brief  Color Adjustment : 1.hue   2.saturation   3.luminous
	 */
	public static Bitmap handleImageEffect(Bitmap bm, float hue, float saturation, float lum) {
		Bitmap  bmp = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Bitmap.Config.ARGB_8888); //32 bit ARGB bitmap
		Canvas canvas = new Canvas(bmp);
		Paint painter = new Paint(Paint.ANTI_ALIAS_FLAG);
		
		ColorMatrix hueMatrix = new ColorMatrix();
		hueMatrix.setRotate(0, hue);  //red
		hueMatrix.setRotate(1, hue);  //green
		hueMatrix.setRotate(2, hue);  //blue
		
		ColorMatrix saturationMatrix = new ColorMatrix();
		saturationMatrix.setSaturation(saturation);
		
		ColorMatrix lumMatrix = new ColorMatrix();
		lumMatrix.setScale(lum, lum, lum, 1);
		
		ColorMatrix imageMatrix = new ColorMatrix();
		imageMatrix.postConcat(hueMatrix);
		imageMatrix.postConcat(saturationMatrix);
		imageMatrix.postConcat(lumMatrix);
		
		
		painter.setColorFilter(new ColorMatrixColorFilter(imageMatrix));
		
		canvas.drawBitmap(bm, 0, 0, painter);
		return  bmp;
	}

}
