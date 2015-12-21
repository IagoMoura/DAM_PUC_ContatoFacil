package com.pos.puc.trabalhofinal;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Principal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        Button btnEntrar = (Button) findViewById(R.id.brnEntrar);

        btnEntrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText edtLogin = (EditText) findViewById(R.id.edtLogin);
                EditText edtSenha = (EditText) findViewById(R.id.edtSenha);

                try {
                    DBAdapter db = new DBAdapter(getApplicationContext());
                    db.open();
                    Cursor c = db.getUserByNamePassword(edtLogin.getText().toString(), edtSenha.getText().toString());
                    if (c.getCount() > 0)
                    {
                        Intent it = new Intent(getApplicationContext(), Cadastro.class);
                        startActivity(it);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Usuário "+edtLogin.getText().toString()+" não existe ou a senha informada está errada!", Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception ex){
                    String mes = ex.getMessage();
                }
            }
        });

        Button btnCriarUsuario = (Button) findViewById(R.id.btnCriarUsuario);

        btnCriarUsuario.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText edtLogin = (EditText) findViewById(R.id.edtLogin);
                EditText edtSenha = (EditText) findViewById(R.id.edtSenha);

                DBAdapter db = new DBAdapter(getApplicationContext());

                db.open();
                db.insertUser(edtLogin.getText().toString(), edtSenha.getText().toString());
                db.close();

                Toast.makeText(getApplicationContext(),"Usuário "+edtLogin.getText().toString()+" adicionado com sucesso!", Toast.LENGTH_SHORT).show();

                Intent it = new Intent(getApplicationContext(), Principal.class);
                startActivity(it);
            }
            });
    }

    public void CopyDB (InputStream inputStream, OutputStream outputStream) throws IOException{

        byte[] buffer = new byte[1024];
        int length;

        while ((length = inputStream.read(buffer)) > 0){
            outputStream.write(buffer, 0, length);
        }

        inputStream.close();
        outputStream.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
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
}
