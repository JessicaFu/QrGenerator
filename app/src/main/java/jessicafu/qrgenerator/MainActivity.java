package jessicafu.qrgenerator;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import android.os.Build;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }*/
    }

    public void buttonOnClick(View v)throws IOException {
        EditText txtBox = (EditText) findViewById(R.id.editText);
        final String imgUrl = txtBox.getText().toString();
        final Bitmap[] bmp = new Bitmap[1];
        ImageView image = (ImageView) findViewById(R.id.imageView);


        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    bmp[0]=getCode("https://api.qrserver.com/v1/create-qr-code/?size=150x150&data="+imgUrl+"!&size=100x100");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();
        while (bmp[0]==null) {

        }
        image.setImageBitmap(bmp[0]);
        System.out.println("Done");

    }

    private Bitmap getCode(String qrUrl) throws IOException{
        URL url = new URL(qrUrl);
        InputStream is = url.openStream();

        Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        is.close();
        return bmp;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
}
