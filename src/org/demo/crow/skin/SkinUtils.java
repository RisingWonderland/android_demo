package org.demo.crow.skin;

import org.demo.crow.R;

import android.app.Activity;
import android.content.Intent;

public class SkinUtils {
	
	private static int activeSkin = 0;
	
	public static void changeSkin(Activity activity, int skinCode){
		activeSkin = skinCode;
		activity.finish();
		activity.startActivity(new Intent(activity, activity.getClass()));
	}
	
	public static void setSkinOnActivityCreated(Activity activity){
		
		switch(activeSkin){
		case 0:
			activity.setTheme(R.style.ResourcesStyle);
			break;
		case 1:
			activity.setTheme(R.style.ResourcesStyle_r1);
			break;
		case 2:
			activity.setTheme(R.style.ResourcesStyle_r2);
			break;
		}
		
	}
	
}
