package org.crow.android.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
/**
 * 图像处理工具类
 * @author Crow
 *
 */
public class BitmapUtils {
	
	private static final String TAG = "Crow_BitmapUtils";
	private static final CompressFormat BITMAP_JPEG = Bitmap.CompressFormat.JPEG;
	private static final CompressFormat BITMAP_PNG = Bitmap.CompressFormat.PNG;
//	private static final CompressFormat BITMAP_WEBP = Bitmap.CompressFormat.WEBP;
	public static final int MINI_KIND = MediaStore.Images.Thumbnails.MINI_KIND;
	public static final int MICRO_KIND = MediaStore.Images.Thumbnails.MICRO_KIND;
	public static final int FULL_SCREEN_KIND = MediaStore.Images.Thumbnails.FULL_SCREEN_KIND;
	
	/**
	 * 将Bitmap对象输出到指定存储位置，输出的图片质量为80
	 * @author Crow
	 * @date 2015-4-8下午4:07:28
	 * @param bitmap
	 * @param file
	 */
	public static void exportBitmap(Bitmap bitmap, File file){
		exportBitmap(bitmap, file, 80);
	}
	/**
	 * 将Bitmap对象输出到指定存储位置
	 * @author Crow
	 * @date 2015-4-8下午4:05:00
	 * @param bitmap 目标Bitmap
	 * @param file 要输出成为的File对象
	 */
	public static void exportBitmap(Bitmap bitmap, File file, int quality){
		// 根据File对象判断要输出的图片文件类型，如果无后缀，输出JPEG格式
		String fileName = file.getName();
		String suffix = StringUtils.getSuffix(fileName).toLowerCase(Locale.getDefault());
		CompressFormat cf;
		switch(suffix){
		case ".png":
			cf = BITMAP_PNG;
			break;
		// new case here...
		case ".jpg":
		default:
			cf = BITMAP_JPEG;
		}
		
		BufferedOutputStream bos = null;
		try {
			 bos = new BufferedOutputStream(new FileOutputStream(file));
			 bitmap.compress(cf, quality, bos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				bos.flush();
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 根据指定的图像路径和大小来获取缩略图
	 * 此方法有两点好处：
	 *     1. 使用较小的内存空间，第一次获取的bitmap实际上为null，只是为了读取宽度和高度，
	 *        第二次读取的bitmap是根据比例压缩过的图像，第三次读取的bitmap是所要的缩略图对象。
	 *     2. 缩略图对于原图像来讲没有拉伸，这里使用了2.2版本开始提供的新工具ThumbnailUtils，
	 *        使用这个工具生成的图像不会被拉伸。
	 * @param imagePath 目标图像的路径
	 * @param width 指定输出图像的宽度
	 * @param height 指定输出图像的高度
	 * @return 生成的缩略图的Bitmap对象
	 */
	public static Bitmap getImageThumbnail(String imagePath, int width, int height) {
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		// 直译：如果设置为true，解码器将之返回null（不返回图片），但依然会安置图片的场景，允许在不在内容中定位的情况下搜寻图片
		options.inJustDecodeBounds = true;
		// 获取这个图片的宽和高，注意此处的bitmap为null
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		options.inJustDecodeBounds = false; // 设为 false
		// 计算缩放比
		int h = options.outHeight;
		int w = options.outWidth;
		int beWidth = w / width;
		int beHeight = h / height;
		int be = 1;
		if (beWidth < beHeight) {
			be = beWidth;
		} else {
			be = beHeight;
		}
		if (be <= 0) {
			be = 1;
		}
		options.inSampleSize = be;//举例：如果value是4，返回原始图片的1/4大小
		// 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds设为false
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		// 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
		bitmap = ThumbnailUtils.extractThumbnail(
				bitmap, 
				width, 
				height,
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		
		return bitmap;
	}

	/**
	 * 获取视频的缩略图
	 * 先通过ThumbnailUtils来创建一个视频的缩略图，然后再利用ThumbnailUtils来生成指定大小的缩略图。
	 * 如果想要的缩略图的宽和高都小于MICRO_KIND，则类型要使用MICRO_KIND作为kind的值，这样会节省内存。
	 * @param videoPath 目标视频的路径
	 * @param width 指定输出视频缩略图的宽度
	 * @param height 指定输出视频缩略图的高度度
	 * @param kind 参照MediaStore.Images.Thumbnails类中的常量MINI_KIND和MICRO_KIND。
	 *            其中，MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96
	 * @return 指定大小的视频缩略图Bitmap对象
	 */
	public static Bitmap getVideoThumbnail(String videoPath, int width, int height,
			int kind) {
		Bitmap bitmap = null;
		// 获取视频的缩略图
		bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
		System.out.println("w" + bitmap.getWidth());
		System.out.println("h" + bitmap.getHeight());
		bitmap = ThumbnailUtils.extractThumbnail(
				bitmap, 
				width, 
				height,
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		
		return bitmap;
	}
}
