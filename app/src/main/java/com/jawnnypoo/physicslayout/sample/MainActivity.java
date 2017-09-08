package com.jawnnypoo.physicslayout.sample;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jawnnypoo.physicslayout.Physics;
import com.jawnnypoo.physicslayout.PhysicsFrameLayout;
import com.squareup.picasso.Picasso;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TESTING";

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.physics_layout)
    PhysicsFrameLayout physicsLayout;
    @Bind(R.id.physics_switch)
    SwitchCompat physicsSwitch;
    @Bind(R.id.fling_switch)
    SwitchCompat flingSwitch;
    @Bind(R.id.impulse_button)
    View impulseButton;
    @Bind(R.id.add_view_button)
    View addViewButton;
    @Bind(R.id.collision)
    TextView collisionView;

    public boolean isNavShow = true;
    int catIndex;
    public static int streamId;
    SoundPool sound = new SoundPool.Builder()
            .setMaxStreams(10)
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toggleNavigationBar();
        View decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener
                (new View.OnSystemUiVisibilityChangeListener() {
                    @Override
                    public void onSystemUiVisibilityChange(int visibility) {
                        // Note that system bars will only be "visible" if none of the
                        // LOW_PROFILE, HIDE_NAVIGATION, or FULLSCREEN flags are set.
                        if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                            // TODO: The system bars are visible. Make any desired
                            // adjustments to your UI, such as showing the action bar or
                            // other navigational controls.
                            isNavShow = true;
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (isNavShow){
                                        toggleNavigationBar();
                                    }
                                }
                            }, 2000);
                        } else {
                            isNavShow = false;
                            // TODO: The system bars are NOT visible. Make any desired
                            // adjustments to your UI, such as hiding the action bar or
                            // other navigational controls.
                        }

                    }
                });

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        toolbar.setTitle(R.string.app_name);
//        toolbar.inflateMenu(R.menu.menu_main);
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                if (item.getItemId() == R.id.action_contributors) {
//                    startActivity(AboutActivity.newInstance(MainActivity.this));
//                    return true;
//                }
//                return false;
//            }
//        });
        physicsSwitch.setChecked(physicsLayout.getPhysics().isPhysicsEnabled());
        physicsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    physicsLayout.getPhysics().enablePhysics();
                } else {
                    physicsLayout.getPhysics().disablePhysics();
                    for (int i=0; i<physicsLayout.getChildCount(); i++) {
                        physicsLayout.getChildAt(i)
                                .animate().translationY(0).translationX(0).rotation(0);
                    }
                }
            }
        });
        flingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    physicsLayout.getPhysics().enableFling();
                } else {
                    physicsLayout.getPhysics().disableFling();
                }
            }
        });
        impulseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                physicsLayout.getPhysics().giveRandomImpulse();
            }
        });
        final View circleView = findViewById(R.id.circle);
        addViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageView imageView = new ImageView(MainActivity.this);
                imageView.setImageResource(R.drawable.ic_logo);
                LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
                        getResources().getDimensionPixelSize(R.dimen.square_size),
                        getResources().getDimensionPixelSize(R.dimen.square_size));
                imageView.setLayoutParams(llp);
                imageView.setId(catIndex);
                physicsLayout.addView(imageView);
                Picasso.with(MainActivity.this)
                        .load("http://lorempixel.com/200/200/cats/" + ((catIndex % 10) + 1))
                        .placeholder(R.drawable.ic_logo)
                        .into(imageView);
                catIndex++;
            }
        });


        for (int i=0; i<physicsLayout.getChildCount(); i++) {
                ImageView imageView = (ImageView) physicsLayout.getChildAt(i);
                imageView.setId(i);
                Picasso.with(this)
                        .load("http://lorempixel.com/200/200/cats/" + (i + 1))
                        .placeholder(R.drawable.ic_logo)
                        .into(imageView);

                final int soundId = sound.load(MainActivity.this, R.raw.cat1, 1);
                imageView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            return ! physicsLayout.getPhysics().isFlingEnabled();
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            if (streamId > 0) {
                                sound.stop(streamId);
                                streamId = 0;
                            }
                            streamId = sound.play(soundId, 0.8F, 0.8F, 1, 0, 1.0F);
                            return false;
                        } else {
                            return false;
                        }
                    }
                });

        }
        catIndex = physicsLayout.getChildCount();

        physicsLayout.getPhysics().setOnCollisionListener(new Physics.OnCollisionListener() {
            @Override
            public void onCollisionEntered(int viewIdA, int viewIdB) {
                collisionView.setText(viewIdA + " collided with " + viewIdB);
            }

            @Override
            public void onCollisionExited(int viewIdA, int viewIdB) {

            }
        });

        physicsLayout.getPhysics().addOnPhysicsProcessedListener(new Physics.OnPhysicsProcessedListener() {
            @Override
            public void onPhysicsProcessed(Physics physics, World world) {
                Body body = physics.findBodyById(circleView.getId());
                if (body != null) {
                    body.applyAngularImpulse(0.001f);
                } else {
                    Log.e(TAG, "Cannot rotate, body was null");
                }
            }
        });

        physicsLayout.getPhysics().setOnBodyCreatedListener(new Physics.OnBodyCreatedListener() {
            @Override
            public void onBodyCreated(View view, Body body) {
                Log.d(TAG, "Body created for view " + view.getId());
            }
        });

        physicsLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (isNavShow) {
                        toggleNavigationBar();
                    }
                }
                return false;
            }
        });
    }

    private void toggleNavigationBar (){

        View decorView = getWindow().getDecorView();
        if (isNavShow) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);
        } else {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        }
    }

}
