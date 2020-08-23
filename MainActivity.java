package com.example.python_on_android;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterViewFlipper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kristijandraca.backgroundmaillibrary.BackgroundMail;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {
    AdapterViewFlipper flipper;
    Button call;
    Button send;
    EditText name;
    EditText phone;
    EditText brand;
    EditText model;
    EditText year;
    EditText num;
    EditText goods;
    EditText address;
    static int hintColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        flip();
        name = findViewById(R.id.name_field);
        phone = findViewById(R.id.phone_field);
        brand = findViewById(R.id.brand_field);
        model = findViewById(R.id.model_field);
        year = findViewById(R.id.year_field);
        num = findViewById(R.id.num_field);
        goods = findViewById(R.id.goods);
        address = findViewById(R.id.address);
        name.setOnFocusChangeListener(this);phone.setOnFocusChangeListener(this);brand.setOnFocusChangeListener(this);
        model.setOnFocusChangeListener(this);year.setOnFocusChangeListener(this);goods.setOnFocusChangeListener(this);
        hintColor = name.getCurrentHintTextColor();

        //call button
        call = findViewById(R.id.call_button);
        call.setOnClickListener(this);
        send = findViewById(R.id.submit_button);
        send.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.call_button) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:+982128424627"));
            startActivity(intent);
        }else{
            String name = this.name.getText().toString();
            String phone = this.phone.getText().toString();
            String brand = this.brand.getText().toString();
            String model = this.model.getText().toString();
            String year = this.year.getText().toString();
            String num = this.num.getText().toString();
            String goods = this.goods.getText().toString();
            String address = this.address.getText().toString();
            if (name.equals("")||phone.equals("")||brand.equals("")||model.equals("")||year.equals("")||goods.equals("")) {
                Toast toast = Toast.makeText(this,"لطفا همه فیلدهای ستاره دار را پر نمایید",Toast.LENGTH_SHORT);
                toast.getView().setBackgroundColor(0xfff44336);
                toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 200);
                toast.show();
                return;
            }
            if (!checkPhone(phone) || phone.length()!=11){
                Toast toast = Toast.makeText(this,"شماره تلفن وارد شده صحیح نمی باشد",Toast.LENGTH_SHORT);
                toast.getView().setBackgroundColor(0xfff44336);
                toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 200);
                toast.show();
                return;
            }

            sendMessage(name,phone,brand,model,year,num,goods,address);
        }
    }
    boolean checkPhone(String phone){
        for (int i = 0; i < phone.length() ; i++) {
            if (!Character.isDigit(phone.charAt(i)))
                return false;
        }
        return true;
    }

    private void sendMessage(String name, String phone, String brand, String model, String year, String num,String goods,String address) {
        String json = String.format("{\"نام\":\"%s\",\"شماره\":\"%s\",\"برند\":\"%s\",\"مدل\":\"%s\",\"سال تولید\":\"%s\",\"شماره شاسی\":\"%s\",\"کالاهای درخواستی\":\"%s\",\"آدرس یا کد نمایندگی\":\"%s\"}",name,phone,brand,model,year,(num.equals("")) ? "None" : num,goods,(address.equals("")) ? "None" : address);
        ExecutorService ex = Executors.newCachedThreadPool();
        BackgroundMail bm = new BackgroundMail(this);
        bm.setGmailUserName("mrahimian559@gmail.com");
        bm.setGmailPassword("mrahimian1380");
        bm.setMailTo("Yadakcallcenter@gmail.com");
        bm.setFormSubject("Info");
        bm.setFormBody(json);
        bm.send();
        /*String html = String.format("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "\n" +
                "<h2>Basic HTML Table</h2>\n" +
                "\n" +
                "<table style=\"width:100%\">\n" +
                "  <tr>\n" +
                "    <th>نام</th>\n" +
                "    <th>شماره</th> \n" +
                "    <th>برند</th>\n" +
                "    <th>مدل</th>\n" +
                "    <th>سال تولید</th>\n" +
                "    <th>شماره شاسی</th>\n" +
                "    <th>کالاهای درخواستی</th>\n" +
                "    <th>آدرس یا کد نمایندگی</th>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>%s</td>\n" +
                "    <td>%s</td>\n" +
                "    <td>%s</td>\n" +
                "    <td>%s</td>\n" +
                "    <td>%s</td>\n" +
                "    <td>%s</td>\n" +
                "    <td>%s</td>\n" +
                "    <td>%s</td>\n" +
                "  </tr>\n" +
                "</table>\n" +
                "\n" +
                "</body>\n" +
                "</html>",name,phone,brand,model,year,num,goods,address);*/


    }

    void flip(){
        flipper = findViewById(R.id.flip);
        Integer[] images = {R.drawable.photo1,R.drawable.photo2,R.drawable.photo3};
        flipper.setAdapter(new Flipper(this,images));
        flipper.setFlipInterval(3000);
        flipper.setAutoStart(true);
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (flipper != null && !flipper.isFlipping())
            flipper.startFlipping();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (flipper != null && flipper.isFlipping())
            flipper.stopFlipping();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        EditText view = (EditText) v;
        if (!hasFocus && view.getText().toString().trim().equals("")){
            view.setHintTextColor(Color.RED);
            view.setHint("پر کردن این فیلد الزامی ست");
        }
        else if(hasFocus){
            view.setHintTextColor(hintColor);
            view.setHint(view.getTag().toString());
        }
    }

    class Send implements Runnable{
        Context context;
        String json;
        public Send(Context context, String json) {
            this.context = context;
            this.json = json;
        }

        @Override
        public void run() {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            try {
                SoapClient soapClient = new SoapClient("mahdi.jafari98", "@25506339Aa");
                soapClient.SendSimpleSMS2("09107646383","50004000393533",json,false);
            }catch (Exception e){
                Toast.makeText(context,"Network Problem",Toast.LENGTH_LONG).show();
            }
        }
    }
}


