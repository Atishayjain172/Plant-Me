package com.example.plantme;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Shop extends AppCompatActivity {
    TextView t1,t2;
    Button b1,b2,b3;
    EditText name,mobile,add;
    DatabaseReference reference2;
    String item_name="";
    String cust_name="null",cust_address="null",cust_mob="null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        t1=(TextView) findViewById(R.id.textView2);
        t2=(TextView) findViewById(R.id.textView3);
        b1=(Button) findViewById(R.id.button2);
        b2=(Button) findViewById(R.id.button3);
        b3=(Button) findViewById(R.id.button5);
        name=(EditText) findViewById(R.id.name);
        add=(EditText) findViewById(R.id.adress);
        mobile=(EditText) findViewById(R.id.mobile);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Shop.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void button1(View view)
    {
        item_name=t1.getText().toString();
        t1.setVisibility(View.GONE);
        t2.setVisibility(View.GONE);
        b1.setVisibility(View.GONE);
        b2.setVisibility(View.GONE);
        name.setVisibility(View.VISIBLE);
        add.setVisibility(View.VISIBLE);
        mobile.setVisibility(View.VISIBLE);
        b3.setVisibility(View.VISIBLE);


    }

    public void button2(View view)
    {
        item_name=t2.getText().toString();
        t1.setVisibility(View.GONE);
        t2.setVisibility(View.GONE);
        b1.setVisibility(View.GONE);
        b2.setVisibility(View.GONE);
        name.setVisibility(View.VISIBLE);
        add.setVisibility(View.VISIBLE);
        mobile.setVisibility(View.VISIBLE);
        b3.setVisibility(View.VISIBLE);

    }

    public void purchasesubmit(View view)
    {
        if(name.getText().toString()!=null)
        {
            cust_name=name.getText().toString().trim();
        }
        else
        {
            Toast toast=Toast.makeText(Shop.this,"Please enter your name",Toast.LENGTH_LONG);
            toast.show();
        }
        if(add.getText().toString()!=null)
        {
            cust_address=add.getText().toString().trim();
        }
        else
        {
            Toast toast=Toast.makeText(Shop.this,"Please enter your address",Toast.LENGTH_LONG);
            toast.show();
        }
        if(mobile.getText().toString()!=null)
        {
            cust_mob=mobile.getText().toString().trim();
        }
        else
        {
            Toast toast=Toast.makeText(Shop.this,"Please enter your mobiile no.",Toast.LENGTH_LONG);
            toast.show();
        }
        if(cust_name.equalsIgnoreCase("null")&&cust_mob.equalsIgnoreCase("null")&&cust_address.equalsIgnoreCase("null"))
        {
            Toast toast=Toast.makeText(Shop.this,"Please enter your your details",Toast.LENGTH_LONG);
            toast.show();
        }
        else
        {
            reference2 = FirebaseDatabase.getInstance().getReference().child("Purchase").child(cust_mob.toLowerCase());
            submitdata selection = new submitdata(cust_name, cust_address, cust_mob, "");
            reference2.setValue(selection);
            Toast toast=Toast.makeText(Shop.this,"Details submitted successfully",Toast.LENGTH_LONG);
            toast.show();
            Intent intent=new Intent(Shop.this,MainActivity.class);
            startActivity(intent);
        }

    }

}
