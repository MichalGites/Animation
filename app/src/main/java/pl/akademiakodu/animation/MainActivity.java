package pl.akademiakodu.animation;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView logo ;
    RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // najbardziej wydajny sposób przypisania do deklaracji globanej :D
        logo = (ImageView) findViewById(R.id.logo);
        layout = (RelativeLayout) findViewById(R.id.activity_main);

        // musimy zrzutowac na animacje ponieważ sama metoda getBackground() zwraca wartosc typu tylko Drawable
        AnimationDrawable animationDrawable = (AnimationDrawable) layout.getBackground();
        animationDrawable.start();

        // Załadowanie animacji - przypisanie
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        //animation.setFillAfter(true); // pozostawienie widzetu w stanie po animacji

        // ANIMACJA Z KODU !!!

        //ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 2f, 1f, 2f, Animation.RELATIVE_TO_SELF, Animation.RELATIVE_TO_SELF);
        //scaleAnimation.setDuration(4000);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animationnimation) {
                Toast.makeText(MainActivity.this, "Animacja ruszyła", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationEnd(Animation animationnimation) {
                Toast.makeText(MainActivity.this, "Animacja się zatrzymała", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // coś tam się wykona
            }
        });

        // Uruchomienie animacji
        //logo.startAnimation(scaleAnimation);
        logo.startAnimation(animation);
    }
}
