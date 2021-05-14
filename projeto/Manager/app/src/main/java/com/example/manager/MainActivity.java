package com.example.manager;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    //TODO: Verificar de deixar campos de ttexto desabilitados no delete
    // TODO: desabilitados até apertar o botão na aba edittar

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
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.pagerContext, initFragment, "frag_init");
        transaction.addToBackStack("pilha");
        transaction.commit();

        navView = findViewById(R.id.nav_sidebar);

        navView.setNavigationItemSelectedListener((MenuItem item) -> {
            int id = item.getItemId();
            if (id != lastId){
                if (id == R.id.menuCtxInicial){ //usuario seleciona contexto inicial para o viewpager
                    CtxInit initFragment1 = new CtxInit();
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.pagerContext, initFragment1, "frag_inicial");
                    transaction.addToBackStack("pilha");
                    transaction.commit();
                    lastId = id;
                }
                else if (id == R.id.menuCtxCurso){ //usuario seleciona contexto cursos para o viewpager
                    CtxCursoFragment cursoFragment = new CtxCursoFragment();
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.pagerContext, cursoFragment, "frag_curso");
                    transaction.addToBackStack("pilha");
                    transaction.commit();
                    lastId = id;
                }
                else if (id == R.id.menuCtxAluno){ //usuario seleciona contexto alunos para o viewpager
                    CtxAlunoFragment alunoFragment = new CtxAlunoFragment();
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.pagerContext, alunoFragment, "frag_aluno");
                    transaction.addToBackStack("pilha");
                    transaction.commit();
                    lastId = id;
                }
                else if (id == R.id.menuCtxLista){ //usuario seleciona contexto listar para o viewpager
                    CtxListagemFragment listagemFragment = new CtxListagemFragment();
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.pagerContext, listagemFragment, "frag_lista");
                    transaction.addToBackStack("pilha");
                    transaction.commit();
                    lastId = id;
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
}