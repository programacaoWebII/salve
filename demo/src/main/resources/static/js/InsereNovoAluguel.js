var idLivro= document.getElementById("livroID");
idLivro.addEventListener("keydown",insereNovoAluguel);
var cpf= document.getElementById("cpf");
cpf.addEventListener("keydown",insereNovoAluguel);
var tabela = document.getElementById("tableBody");
var buscaIDlivro = document.getElementById("buscaIdLivro");
var buscaCPF = document.getElementById("buscaCpf");
var ultimaPesquisa=undefined;
function insereNovoAluguel(e){
    if (e.key === 'Enter') {
        console.log(idLivro.value);
        console.log(cpf.value);
        fetch("http://localhost:8081/livroUnidade?cpf="+cpf.value+"&"+"idLivro="+idLivro.value).then(res => res.json())
        .then(function(data){
            let opcao = confirm("quer por como aluguel do livro "+data[1].grupo+ " para o usuáro "+data[0].givenName+" "+data[0].familyName);
            if(opcao){
                console.log("é um milagre")
                let url = "http://localhost:8081/aluguelPosting/"+data[0].cpf+"/"+data[1].id;
                console.log(url)
                fetch(url).then(res => res.json())
                .then(function(data){
                    if(data == true){
                        alert("Aluguel Registrado.")
                        buscaAlugueisEMostra();
                    }else{
                        alert("Ocorreu algum problema...")
                    }
                }).catch((error) => {
                    console.error('Erro:', error);
                });
            }else{
                console.log("negou foi?")
            }
        }).catch((error) => {
            console.error('Erro:', error);
        });
    }
}
function buscaAlugueisEMostra(){
    ultimaPesquisa = "buscaAlugueisEMostra";
    tabela.innerHTML=""
    fetch("http://localhost:8081/aluguel").then(res => res.json())
    .then(function(data){
       printResult(data);
        
    }).catch((error)=>{
        console.log("Erro",error);
    });
}
buscaAlugueisEMostra();
function devolveLivro(idAluguel){

}

function converteData(dataInput){
    if(dataInput==null) return "";
    var data = new Date(dataInput);
    var opcoesData = { day: '2-digit', month: '2-digit', year: 'numeric' };
    var dataFormatada = data.toLocaleDateString('pt-BR', opcoesData);
    var opcoesHora = { hour: '2-digit', minute: '2-digit' };
    var horaFormatada = data.toLocaleTimeString('pt-BR', opcoesHora);
    return dataFormatada + ' - ' + horaFormatada;
}

function printResult(data){
    tabela.innerHTML=""
    var alugados =[]
    var devolvidos = []

    for(aluguel of data){
        if(aluguel.status.substring(0,2) =="OK"){
            devolvidos.push(aluguel)
        }else{
            alugados.push(aluguel)
        }
    }
    let alugadosleng = alugados.length;
    for(i=0;i< alugadosleng;i++){
        let ultimo = alugados.pop();
        if(ultimo.status.substring(0, 7)=="Perdido"){
            tabela.innerHTML+="<div class=\"pure-u-4-24 tb\">"+ultimo.id+"</div><div class=\"pure-u-3-24 tb\">"+ultimo.livro+"</div><div class=\"pure-u-4-24 tb\">"+converteData(ultimo.dataAluguel)+"</div><div class=\"pure-u-4-24 tb\">"+converteData(ultimo.diaDevolucao)+"</div><div class=\"pure-u-5-24 tb\">"+ultimo.status+"</div><div class=\"pure-u-4-24 tb displayflex\"><button onclick=\"devolveLivro("+ultimo.id+")\" class=\"pure-u-24-24 pure-button xm-bt\">devolvido</button></div>"
        }else{
            tabela.innerHTML+="<div class=\"pure-u-4-24 tb\">"+ultimo.id+"</div><div class=\"pure-u-3-24 tb\">"+ultimo.livro+"</div><div class=\"pure-u-4-24 tb\">"+converteData(ultimo.dataAluguel)+"</div><div class=\"pure-u-4-24 tb\">"+converteData(ultimo.diaDevolucao)+"</div><div class=\"pure-u-5-24 tb\">"+ultimo.status+"</div><div class=\"pure-u-4-24 tb displayflex\"><button onclick=\"livroFoiPerdido("+ultimo.id+")\" class=\"pure-u-12-24 pure-button xm-bt\">perdido</button> <button onclick=\"devolveLivro("+ultimo.id+")\" class=\"pure-u-12-24 pure-button xm-bt\">devolvido</button></div>";
        }
        
    }
    let devolvidoslen = devolvidos.length
    for(i=0;i<devolvidoslen;i++){
        let ultimo = devolvidos.pop();
        
        tabela.innerHTML+="<div class=\"pure-u-4-24 tb\">"+ultimo.id+"</div><div class=\"pure-u-3-24 tb\">"+ultimo.livro+"</div><div class=\"pure-u-4-24 tb\">"+converteData(ultimo.dataAluguel)+"</div><div class=\"pure-u-4-24 tb\">"+converteData(ultimo.diaDevolucao)+"</div><div class=\"pure-u-5-24 tb\">"+ultimo.status+"</div><div class=\"pure-u-4-24 tb displayflex\"></div>";
    }
}
function buscaPorIdDoLivro(){
    ultimaPesquisa = "buscaPorIdDoLivro";
    let id = buscaIDlivro.value;
    fetch("http://localhost:8081/aluguelPorLivro/"+id).then(res => res.json())
    .then(function(data){
        printResult(data);
    }).catch((error)=>{
        console.log("Erro",error);
    });
}
function buscaPorCpf(){
    ultimaPesquisa = "buscaPorCpf";
    let cpf = buscaCPF.value
    fetch("http://localhost:8081/aluguelPorPessoa/"+cpf).then(res => res.json())
    .then(function(data){
        printResult(data)
    }).catch((error)=>{
        console.log("Erro",error);
    });
}
var bt1 = document.getElementById("buttonBuscaidlivro");
bt1.addEventListener("click",buscaPorIdDoLivro);
var bt2 = document.getElementById("buttonBuscacpf");
bt2.addEventListener("click",buscaPorCpf);

function devolveLivro(idAluguel){
    fetch("http://localhost:8081/livroDevolucao/"+idAluguel).then(res => res.json())
    .then(function(data){
        if(data){
            if(ultimaPesquisa == "buscaPorCpf"){
                buscaPorCpf();
            }
            if(ultimaPesquisa =="buscaAlugueisEMostra"){
                buscaAlugueisEMostra();
            }
            if(ultimaPesquisa == "buscaPorIdDoLivro"){
                buscaPorIdDoLivro();
            }
            if(ultimaPesquisa =="pesquisaViaSubcategorias"){
                pesquisaViaSubcategorias();
            }
        }else{
            alert("algo deu errado")
        }
    }).catch((error)=>{
        console.log("Erro",error);
    });
}
function livroFoiPerdido(idAluguel){
    fetch("http://localhost:8081/livro/foi/perdido/"+idAluguel).then(res => res.json())
    .then(function(data){
        if(data){
            if(ultimaPesquisa == "buscaPorCpf"){
                buscaPorCpf();
            }
            if(ultimaPesquisa =="buscaAlugueisEMostra"){
                buscaAlugueisEMostra();
            }
            if(ultimaPesquisa == "buscaPorIdDoLivro"){
                buscaPorIdDoLivro();
            }
            if(ultimaPesquisa =="pesquisaViaSubcategorias"){
                pesquisaViaSubcategorias();
            }
        }else{
            alert("algo deu errado")
        }
    }).catch((error)=>{
        console.log("Erro",error);
    });
}

function pesquisaViaSubcategorias(){
    var checkboxes = document.querySelectorAll('#meuId input[type="checkbox"]');
    let ids =""
    for(checkbox of checkboxes){
        if(checkbox.checked){
            ids+=(checkbox.name)+"-";
        }
    }
    ids = ids.trim();
    fetch("http://localhost:8081/encontraAlugueisPorSubcatDeLivros?subcategorias="+ids).then(res =>res.json())
    .then(function(data){
        ultimaPesquisa = "pesquisaViaSubcategorias";
        printResult(data)
    })
    .catch((error)=>{
        console.log("erro",error)
    })
}