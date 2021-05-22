package com.example.utils;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;

import com.example.myapplication.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;

import jp.wasabeef.fresco.processors.BlurPostprocessor;

public class ViewUtils {
	private static long lastClickTime;

	public static void getFrescoController(Context context, SimpleDraweeView imgIv, String uri, int width, int height) {
		if (uri != null) {
			ImageRequest request = ImageRequestBuilder
					.newBuilderWithSource(Uri.parse(uri))
					.setResizeOptions(new ResizeOptions(dip2px(context, width), dip2px(context, height)))
					.build();
			AbstractDraweeController controller = Fresco.newDraweeControllerBuilder().setOldController(imgIv.getController()).setImageRequest
					(request)
					.build();
			imgIv.setController(controller);
		}
	}

	public static void getBlurFresco(Context context, SimpleDraweeView simpleDraweeView, String uri) {
		if (uri != null) {
			ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(uri))
					.setPostprocessor(new BlurPostprocessor(context, 25))
					.build();
			PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
					.setImageRequest(request)
					.setOldController(simpleDraweeView.getController())
					.build();

			simpleDraweeView.setController(controller);
		}
	}

	public static int dip2px(Context context, double d) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (d * scale + 0.5f);
	}

	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}


	// 获取状态栏高度
	public static int getStatusBarHeight(Context mContext) {
		int result = 0;
		int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");

		if (resourceId > 0) {
			result = mContext.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	public synchronized static boolean isFastClick() {
		long time = System.currentTimeMillis();
		if (time - lastClickTime < 200) {
			return true;
		}
		lastClickTime = time;
		return false;
	}


}
