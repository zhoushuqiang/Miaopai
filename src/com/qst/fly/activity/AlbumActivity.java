package com.qst.fly.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.qst.fly.R;
import com.qst.fly.application.MiaoPaiApplication;
import com.qst.fly.entity.PhotoUpImageBucket;
import com.qst.fly.entity.PhotoUpImageItem;
import com.qst.fly.fragment.AlbumDetailFragment;
import com.qst.fly.fragment.AlbumFragment;
import com.qst.fly.fragment.CropFragment;

public class AlbumActivity extends BaseActivity {
	public static final String CROPED_PHOTO = "CROPED_PHOTO";
	Fragment mAlbumFragment;
	Fragment mAlbumDetailFragment;
	Fragment mCropFragment;

	/**相册列表页面*/
	public static final int ALBUM_FRAGMENT_POSITION  = 0;
	/**相片列表页面*/
	public static final int ALBUM_DETAIL_FRAGMENT_POSITION = 1;
	/**裁剪页面*/
	public static final int CROP_FRAGMENT_POSITION = 2;
	/**当前页面*/
	private int currentPosition = ALBUM_FRAGMENT_POSITION;
	
	private ImageView mImageBack;
	private TextView mTextTitle;
	/**相册*/
	private PhotoUpImageBucket mPhotoUpImageBucket;
	/**相片*/
	private PhotoUpImageItem mPhotoUpImageItem;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_album);

		initView();
		initFragment();
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		mTextTitle = (TextView) findViewById(R.id.text_album_title);
		mImageBack = (ImageView) findViewById(R.id.img_album_back);
		mImageBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				back();
			}
		});
		mTextTitle.setText(R.string.choosealbum);
	}

	/**
	 * 初始化Fragment
	 */
	private void initFragment() {
		mAlbumFragment = new AlbumFragment();
		mAlbumDetailFragment = new AlbumDetailFragment();
		mCropFragment = new CropFragment();
		jump2Fragment(ALBUM_FRAGMENT_POSITION);
	}

	/**
	 * Fragment֮之间的转换
	 * @param position
	 */
	public void jump2Fragment(int position){
		switch (position) {
		case ALBUM_FRAGMENT_POSITION:
			mTextTitle.setText(R.string.choosealbum);
			mTextTitle.setTextSize(14f);
			getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, mAlbumFragment).commit();
			currentPosition = 0;
			break;
		case ALBUM_DETAIL_FRAGMENT_POSITION:  
			mTextTitle.setText(R.string.choosephoto);

			mTextTitle.setTextSize(13f);
			getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, mAlbumDetailFragment).commit();
			currentPosition = 1;
			break;
		case CROP_FRAGMENT_POSITION:
			mTextTitle.setText("");
			getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, mCropFragment).commit();
			currentPosition = 2;
			break;
		}
	}

	/**
	 * 完成选择
	 * @param bitmap
	 */
	public void finishSelect(Bitmap bitmap) {
		Intent intent = new Intent(AlbumActivity.this, CameraPreviewActivity.class);
		MiaoPaiApplication.getApplication().isNeedResetData = false;
		intent.putExtra(CROPED_PHOTO, bitmap);
		setResult(RESULT_OK, intent);
		finish();
	}

	/**
	 * 返回上一页面
	 */
	public void back() {
		if(currentPosition > 0){
			currentPosition -= 1;
			jump2Fragment(currentPosition);
		}else if(currentPosition == 0){
			finish();
			return;
		}
	}

	public void setPhotoUpImageBucket(PhotoUpImageBucket photoUpImageBucket) {
		this.mPhotoUpImageBucket = photoUpImageBucket;
	}

	public PhotoUpImageBucket getPhotoUpImageBucket() {
		return this.mPhotoUpImageBucket;
	}

	public void setPhotoUpImageItem(PhotoUpImageItem photoUpImageItem) {
		this.mPhotoUpImageItem = photoUpImageItem;
	}

	public PhotoUpImageItem getPhotoUpImageItem() {
		return this.mPhotoUpImageItem;
	}
}