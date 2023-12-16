package com.example.demo.entities.auxEntity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.demo.entities.Grupo;
import com.example.demo.entities.Livro;




public class GrupoAux {

    private String nomeDoLivro;
    private String imagemDoLivro;
    private String descricaoDoLivro;
    private int quantidadeDisponivel;
    private int quantidadeEmprestavel;
    private List<Long> subcategorias;
    public GrupoAux(  String nomeDoLivro, String imagemDoLivro, String descricaoDoLivro, int quantidadeDisponivel, int quantidadeEmprestavel, JSONArray subcategorias){
        this.nomeDoLivro = nomeDoLivro;
        this.imagemDoLivro = imagemDoLivro;
        this.descricaoDoLivro = descricaoDoLivro;
        this.quantidadeDisponivel = quantidadeDisponivel;
        this.quantidadeEmprestavel = quantidadeEmprestavel;
        ArrayList<Long> arrayList = new ArrayList<>();
        for (Object iteravel : subcategorias) {
            JSONObject inter = (JSONObject) iteravel;
            arrayList.add(Long.parseLong(inter.getString("id")));
        }
        this.setSubcategorias(arrayList);
    }
    public String getDescricaoDoLivro() {
        return descricaoDoLivro;
    }
    public String getImagemDoLivro() {
        return imagemDoLivro;
    }
    public String getNomeDoLivro() {
        return nomeDoLivro;
    }
    public int getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }
    public int getQuantidadeEmprestavel() {
        return quantidadeEmprestavel;
    }
   
    public void setDescricaoDoLivro(String descricaoDoLivro) {
        this.descricaoDoLivro = descricaoDoLivro;
    }
    public void setImagemDoLivro(String imagemDoLivro) {
        this.imagemDoLivro = imagemDoLivro;
    }
    public void setNomeDoLivro(String nomeDoLivro) {
        this.nomeDoLivro = nomeDoLivro;
    }
    public void setQuantidadeDisponivel(int quantidadeDisponivel) {
        this.quantidadeDisponivel = quantidadeDisponivel;
    }
    public void setQuantidadeEmprestavel(int quantidadeEmprestavel) {
        this.quantidadeEmprestavel = quantidadeEmprestavel;
    }
    public List<Long> getSubcategorias() {
        return subcategorias;
    }
    public void setSubcategorias(List<Long> subcategorias) {
        this.subcategorias = subcategorias;
    }
    public Grupo getGrupo(){
        Grupo ret = new Grupo();
        ret.setDescricao(this.getDescricaoDoLivro());
        ret.setImagemLink(this.getImagemDoLivro());
        ret.setNome(this.getNomeDoLivro());
        ret.setQuantidadeDisponivel(this.getQuantidadeDisponivel());
        //ret.setSubcategorias(this.getSubcategorias());
        ArrayList<Livro> livros = new ArrayList<Livro>();
        for(int i=0;i<this.getQuantidadeEmprestavel();i++){
            livros.add(new Livro(true,"OK",ret));
        }
        int subtrai = this.getQuantidadeDisponivel() - this.getQuantidadeEmprestavel();
        for(int i = 0; i<subtrai;i++){
            livros.add(new Livro(false, "OK",ret));
        }
        ret.setLivros(livros);
        return ret;
    }
    public static GrupoAux ConstroiUmGrupoViaJSONobject(JSONObject jsonObject){
        return new GrupoAux(jsonObject.getString("nomeDoLivro"),
            jsonObject.getString("imagemDoLivro"),
            jsonObject.getString("descricaoDoLivro"),
            jsonObject.getInt("quantidadeDisponivel"),
            jsonObject.getInt("quantidadeEmprestavel"),
            jsonObject.getJSONArray("subcategorias"));
    }
}
