package pl.akademiakodu.animation;

import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
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

    // żeby obsłużyć w Handlerze musimy nasza animacje wyciagnac jako globalna zeby moc obsluzyc w wiadomosci Handlera
    AnimationDrawable animationDrawable;

    // Tworzymy Handlera
    Handler handlerAnim = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what==1){
                animationDrawable.start();
                // musi byc main activity.this bo Handller jest globalny i trzeba wpisac layout gdyz tutaj samo this nie wystarczy
                // po prostu nie widzi tego layoutu samo this
                Toast.makeText(MainActivity.this, "Wiadomość nr 1", Toast.LENGTH_LONG).show();
            } else if(msg.what==2){
                animationDrawable.stop();
                Toast.makeText(MainActivity.this, "Wiadomość nr 2", Toast.LENGTH_SHORT).show();
            } else if (msg.what==3){// obslugujemy wiadomość
                Bundle data = msg.getData();
                // metoda getString key i coś tam zabezpiecza nas przed wyjatkiem nullPointerException jeśli wiadomość byłaby pusta
                // wtedy wyświetli nam Brak (error)
                // interfejs użytkownika możemy obsługiwać tylko z wątku głównego - ŻELAZNA ZASADA !!!!!!!!!!!
                Toast.makeText(MainActivity.this, data.getString("msg", "Brak (error)"), Toast.LENGTH_LONG).show();
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // najbardziej wydajny sposób przypisania do deklaracji globanej :D
        logo = (ImageView) findViewById(R.id.logo);
        layout = (RelativeLayout) findViewById(R.id.activity_main);

        // musimy zrzutowac na animacje ponieważ sama metoda getBackground() zwraca wartosc typu tylko Drawable
        animationDrawable = (AnimationDrawable) layout.getBackground();

        // opóźniamy start animacji o 4s i po 4s ja zamykamy
        handlerAnim.sendEmptyMessageDelayed(1, 4000);
        handlerAnim.sendEmptyMessageDelayed(2, 8000);

        // tworzymy pusty obiekt wiadomości
        Message message = new Message();
        message.what=3;

        // klasa Bundle przechowuje nasze zdefiniwane informacje jako różne typy danych
        Bundle data = new Bundle();
        data.putString("msg", "Informacja przesłana wiadomością");

        // ustawiamy do wiadomosci klasę Bundle
        message.setData(data);

        // wysyłamy wiadomość - nie pustą!
        handlerAnim.sendMessageDelayed(message, 5000);

        // szybsze stworzenie zadania do wykonania z opóźnieniem
        //new Handler().postDelayed(new Runnable() {
           // @Override
            //public void run() {
                // jakieś coś wykona się po 3 sekundach
            //}
       // })

        // Załadowanie animacji - przypisanie
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fadein);
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
        //logo.startAnimation(animation);
    }
}
