package com.pos.puc.trabalhofinal;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Listagem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem);

        ListarContatos();
    }

    public void ListarContatos()
    {
        Contato contato = new Contato();
        List<Contato> contatos = BuscarContatos();
        ListView ListaContatos = (ListView)  findViewById(R.id.listaContatos);

        ArrayList<String> cli = new ArrayList<String>();
        for (int i = 0; i < contatos.size(); i++)
        {
            String Nome = contatos.get(i).getNome();
            String Telefone = contatos.get(i).getTelefone();
            String Email = contatos.get(i).getEmail();
            cli.add(Nome + " - "+ Telefone + " - " + Email);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, android.R.id.text1, cli);

            ListaContatos.setAdapter(arrayAdapter);
        }
    }


    public List<Contato> BuscarContatos()
    {
        List<Contato> contatos = new ArrayList<Contato>();

        try {
            DBAdapter db = new DBAdapter(getApplicationContext());
            db.open();
            Cursor c = db.getAllContacts();

            if (c.getCount() < 1) {
                Toast.makeText(getApplicationContext(), "Não existem contatos cadastrados", Toast.LENGTH_SHORT).show();
            } else {
                if (c.moveToFirst()) {
                    do {
                        Contato contato = new Contato();
                        contato.setNome(c.getString(1));
                        contato.setTelefone(c.getString(2));
                        contato.setEmail(c.getString(3));

                        contatos.add(contato);
                    } while (c.moveToNext());
                }
            }
        }catch (Exception ex){
            String mes = ex.getMessage();
        }
        finally {
            return contatos;
        }
        }
    }

