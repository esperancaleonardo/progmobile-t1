package com.example.manager;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.navigation.NavigationView;

import java.security.PublicKey;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navView;
    private int lastId = 0;
    private Toolbar toolbar;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer);

        //configurando a sidebar oculta com menu sanduíche e slider
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);

        //carregando o fragmento da página inicial para o contexto do framelayout
        CtxInit initFragment = new CtxInit();
        seleciona_fragmento(lastId, initFragment, "frag_init");

        navView = findViewById(R.id.nav_sidebar);

        navView.setNavigationItemSelectedListener((MenuItem item) -> {
            int id = item.getItemId();
            if (id != lastId){
                if (id == R.id.menuCtxInicial){ //usuario seleciona contexto inicial para o viewpager
                    CtxInit initFragment1 = new CtxInit();
                    seleciona_fragmento(id, initFragment1, "frag_inicial");
                }
                else if (id == R.id.menuCtxCurso){ //usuario seleciona contexto cursos para o viewpager
                    CtxCursoFragment cursoFragment = new CtxCursoFragment();
                    seleciona_fragmento(id, cursoFragment, "frag_curso");
                }
                else if (id == R.id.menuCtxAluno){ //usuario seleciona contexto alunos para o viewpager
                    CtxAlunoFragment alunoFragment = new CtxAlunoFragment();
                    seleciona_fragmento(id, alunoFragment, "frag_aluno");
                }
                else if (id == R.id.menuCtxLista){ //usuario seleciona contexto listar para o viewpager
                    CtxListagemFragment listagemFragment = new CtxListagemFragment();
                    seleciona_fragmento(id, listagemFragment, "frag_lista_cursos_cadastro");
                }
                else if (id == R.id.menuCtxRelAlunoCurso){ //usuario seleciona contexto listar qtde alunos por curso
                    CtxListagemQtdeFragment listagemQtdeFragment = new CtxListagemQtdeFragment();
                    seleciona_fragmento(id, listagemQtdeFragment, "frag_lista_alunos_curso");
                }
                else if (id == R.id.menuCtxAbout){ //usuario seleciona contexto página sobre para o viewpager
                    CtxAbout aboutFragment = new CtxAbout();
                    seleciona_fragmento(id, aboutFragment, "frag_about");
                }
                else if (id == R.id.menuCtxSair){ //usuario seleciona contexto sair do app para o viewpager
                    lastId = id;
                    finish();
                }
            }

            //inicia com o menu lateral oculto
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    public void seleciona_fragmento(Integer id, Fragment fragment, String tag){
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.pagerContext, fragment, tag);
        transaction.addToBackStack("pilha");
        transaction.commit();
        lastId = id;
    }
}