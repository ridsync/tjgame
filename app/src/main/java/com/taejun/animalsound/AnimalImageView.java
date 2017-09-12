package com.taejun.animalsound;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by arent on 2017. 9. 12..
 */

public class AnimalImageView extends de.hdodenhof.circleimageview.CircleImageView {

    public static boolean isInitial;
    public static int streamId;
    public static SoundPool sound = null;

    private int soundId;

    public AnimalImageView(Context context) {
        super(context);
    }

    public AnimalImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimalImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public static void initSoundPool(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_UNKNOWN)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();
            sound = new SoundPool.Builder()
                    .setMaxStreams(20)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            sound = new SoundPool(20,         // 최대 음악파일의 개수
                    AudioManager.STREAM_MUSIC,0); // 스트림 타입
        }

        sound.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener()
        {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId,int status) {
                isInitial = true;
            }
        });

    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public void setSountId(int soundId){
        this.soundId = soundId;
    }

    public void playSound(){
        if (streamId > 0) {
            sound.stop(streamId);
            streamId = 0;
        }
        if (isInitial)
            streamId = sound.play(soundId, 1.0F, 1.0F, 1, 0, 1.0F);

    }

}
