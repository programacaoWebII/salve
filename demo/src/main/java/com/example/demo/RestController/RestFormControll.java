package com.example.demo.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Aluguel;
import com.example.demo.entities.Grupo;
import com.example.demo.entities.Livro;
import com.example.demo.entities.NormalUser;
import com.example.demo.entities.auxEntity.GrupoAux;
import com.example.demo.repositories.AluguelRep;
import com.example.demo.repositories.GrupoRep;
import com.example.demo.repositories.LivroRep;
import com.example.demo.repositories.NormalUserRep;
import com.example.demo.repositories.SubcategoriaRep;



@RestController
public class RestFormControll {
    @Autowired
    private GrupoRep grp;
    @Autowired
    private SubcategoriaRep srp;
    @Autowired
    private AluguelRep arp;
    @Autowired
    private NormalUserRep nurep;
    @Autowired
    private LivroRep lrep;
    @GetMapping("/livros")
    public List<Grupo> getLivros(){
        return grp.findAll();
    }
    @GetMapping("/livro/{id}")
    public Grupo getLivros(@PathVariable long id) {
        return grp.findById(id);
    }
    @GetMapping("/livroUnidade")
    public List<Object> pegaLivroUsario(@RequestParam String cpf,@RequestParam long idLivro){
        ArrayList<Object> resultado = new ArrayList<>();
        resultado.add(nurep.findByCpf(cpf));
        Livro livro = lrep.findById(idLivro);
        resultado.add(livro);
        return resultado;
    }
    @GetMapping("/livrosViaSubcategorias")
    public List<Grupo> getGruposViaSubcategorias(@RequestParam String subcategorias) {
        if(subcategorias.equals("")){
            return grp.findAll();
        }
        List<Long> ids = Arrays.asList(subcategorias.split("-")).stream().map(Long::valueOf).collect(Collectors.toList());
        return grp.findAllBySubcategorias(ids,Long.parseLong(ids.size()+""));
    }
    @GetMapping("/encontraAlugueisPorSubcatDeLivros")
    public List<Aluguel> encontraViaSubcategoria(@RequestParam String subcategorias){
        return arp.findAllByLivroGrupoIn(getGruposViaSubcategorias(subcategorias));
    }
    @GetMapping("/aluguel")
    public List<Aluguel> getAlugueis() {
        return arp.findAll();
    }
    @GetMapping("/aluguelPorPessoa/{cpf}")
    public List<Aluguel> getAluguelPorCpf(@PathVariable String cpf){
        return arp.findByUsuarioIdCpf(cpf);
    }
    @GetMapping("/aluguelPorLivro/{idlivro}")
    public List<Aluguel> getAlugueisPorLivro(@PathVariable long idlivro){
        return arp.findByLivroId(idlivro);
    }
    @GetMapping("/aluguelPosting/{cpf}/{idlivro}")
    public boolean registraAluguel(@PathVariable String cpf,@PathVariable long idlivro){
        Aluguel aluguel = new Aluguel();
        aluguel.setLivro(lrep.findById(idlivro));
        if(aluguel.getLivro().getStatus().equals("alugado")||aluguel.getLivro().getStatus().equals("perdido")||aluguel.getLivro().getStatus().contains("renovado")){
            return false;
        }
        aluguel.setUsuarioId(nurep.findByCpf(cpf));
        aluguel.setStatus("Emprestado / "+cpf);
        Livro livro = aluguel.getLivro();
        if(!livro.isEmprestavel()){
            return false;
        }
        livro.setStatus("alugado");
        lrep.save(livro);
        aluguel = arp.save(aluguel);
        if(aluguel.getId()!=0){
            try {
                Grupo grupo = grp.findByLivrosId(idlivro);
                int livrosDisponiveis = grupo.getQuantidadeDisponivel() - 1;
                grupo.setQuantidadeDisponivel(livrosDisponiveis);
                grp.save(grupo);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }else{
            return false;
        }
    }
    @GetMapping("/livro/foi/perdido/{idAluguel}")
    public boolean definePerdido(@PathVariable long idAluguel){
        try {
            Aluguel aluguel= arp.findById(idAluguel);
            Livro livro = lrep.findById(aluguel.getLivro().getId());
            aluguel.setStatus("Perdido / "+aluguel.getUsuarioId().getCpf());
            livro.setStatus("perdido");
            lrep.save(livro);
            arp.save(aluguel);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @GetMapping("/livroRenovacao/{idAluguel}")
    public boolean renovaLivro(@PathVariable long idAluguel) {
        Aluguel aluguel = arp.findById(idAluguel);
        Livro livro = lrep.findById(aluguel.getLivro().getId());
        LocalDate data1 = aluguel.getDataAluguel().toLocalDate();
        LocalDate data2 = LocalDate.now();
        long distancia = ChronoUnit.DAYS.between(data1, data2);
        if(livro.getStatus().contains("alugado") && distancia>=6 && distancia<8){
            livro.setStatus("renovado 1");
            lrep.save(livro);
            return true;
        }else if(livro.getStatus().contains("renovado")){
            String[] statusDividido = livro.getStatus().split(" ");
            int quant = Integer.parseInt(statusDividido[1]);
            if(quant >2){
                return false;
            }else{
                if(distancia>=9 && distancia<10 && quant==1){
                    quant+=1;
                    statusDividido[1] =""+quant;
                    livro.setStatus(statusDividido[0]+" "+statusDividido[1]);
                    lrep.save(livro);
                    return true;
                }else if(distancia>=12 && distancia<13 && quant ==2){
                    quant+=1;
                    statusDividido[1] =""+quant;
                    livro.setStatus(statusDividido[0]+" "+statusDividido[1]);
                    lrep.save(livro);
                    return true;
                }else{
                    return false;
                }
            }
        }else{
            return false;
        }
    }
    @GetMapping("/livroDevolucao/{idAluguel}")
    public boolean devolveLivro(@PathVariable long idAluguel) {
        try {
            Aluguel aluguel = arp.findById(idAluguel);
            aluguel.setStatus("OK / "+aluguel.getUsuarioId().getCpf());
            aluguel.setDiaDevolucao(LocalDateTime.now());
            Livro livro = lrep.findById(aluguel.getLivro().getId());
            livro.setStatus("OK");
            lrep.save(livro);
            aluguel.setLivro(livro);
            Grupo grupo = grp.findByLivrosId(livro.getId());
            grupo.setQuantidadeDisponivel(grupo.getQuantidadeDisponivel()+1);
            grp.save(grupo);
            arp.save(aluguel);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    @GetMapping("/aluguel/usuario/{id}")
    public List<Aluguel> getAlugueisDoUsuario(@PathVariable String id){
        return arp.findByUsuarioIdName(id);
    }
    @GetMapping("/usuario/{param}")
    public NormalUser getAlugadorByCpf(String param) {
        return nurep.findByCpf(param);
    }
    @GetMapping("/usuario/{name}")
    public boolean existeUsuario(String name){
        return nurep.existsById(name);
    }
    @GetMapping("/usuario/meuslivros/{cpf}")
    public List<Aluguel> encontraLivrosQueOUsuarioAlugouViaSubcategoias(@PathVariable String cpf,@RequestParam String subcategorias){
        ArrayList<String> cpfs = new ArrayList<>();
        cpfs.add(cpf);
        return arp.findByUsuarioIdCpfInAndLivroGrupoIn(cpfs, getGruposViaSubcategorias(subcategorias));
    }
    public NormalUser terminaCadastro(NormalUser user) {
        return nurep.save(user);
    }
    public NormalUser encontraPorId(String name){
        return nurep.findByName(name);
    }
    @GetMapping("/formulario")
    public boolean salvaTudo(@RequestParam String string) {
        try {
            System.out.println(string);
            JSONObject jSONObject = new JSONObject(string);
            GrupoAux grupoAux = GrupoAux.ConstroiUmGrupoViaJSONobject(jSONObject);
            Grupo gp = grupoAux.getGrupo();
            gp.setSubcategorias(srp.findAllById(grupoAux.getSubcategorias()));
            grp.save(gp);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
