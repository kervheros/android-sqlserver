package com.example.reivin.sql_peticion;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    EditText dni;
    Button consultar,nuevo;
    TextView nom,dir,fecha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dni =(EditText) findViewById(R.id.txtdni);
        nom = (TextView) findViewById(R.id.txtnom);
        dir = (TextView) findViewById(R.id.txtdir);
        fecha = (TextView) findViewById(R.id.txtfech);
        consultar = (Button) findViewById(R.id.buscar);



        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultarpersona();
            }
        });
    }

    private void consultarpersona() {
        try{
            Statement stm = conexionBD().createStatement();
            ResultSet rs = stm.executeQuery("select nombre,apellPate,apellMat from paciente where id_pac ='"+ dni.getText().toString()+"'");

            if (rs.next()){
                nom.setText(rs.getString(1));
                dir.setText(rs.getString(2));
                fecha.setText(rs.getString(3));
            }
            dni.setText("");

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public Connection conexionBD(){
        Connection cnn = null;
        try{
            StrictMode.ThreadPolicy politica = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(politica);

            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            cnn = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.1.10;databaseName=clinica1;user=sa;password=anuvis;");


        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return cnn;
    }
}
