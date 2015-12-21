package com.pos.puc.trabalhofinal;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Cadastro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        Button btnCadastrarContato = (Button) findViewById(R.id.btnCadastrarContato);
        btnCadastrarContato.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TextView nomeContato = (TextView) findViewById(R.id.nomeContato);
                TextView telefoneContato = (TextView) findViewById(R.id.telefoneContato);
                TextView emailContato = (TextView) findViewById(R.id.emailContato);

                try {
                    DBAdapter db = new DBAdapter(getApplicationContext());

                    db.open();
                    db.insertContact(nomeContato.getText().toString(), telefoneContato.getText().toString(), emailContato.getText().toString());
                    db.close();

                    Toast.makeText(getApplicationContext(), "O Contato " + nomeContato.getText().toString() + " foi adicionado com sucesso!", Toast.LENGTH_SHORT).show();

                    Intent it = new Intent(getApplicationContext(), Cadastro.class);
                    startActivity(it);

                } catch (Exception ex) {
                    String mes = ex.getMessage();
                }
            }
        });

        Button btnListarContatos = (Button) findViewById(R.id.listarContatos);
        btnListarContatos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), Listagem.class);
                startActivity(it);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cadastro, menu);
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
