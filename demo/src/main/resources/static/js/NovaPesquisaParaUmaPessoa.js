var cpf = document.getElementById("userCpf");
var tabela = document.getElementById("tableBody");

function pesquisaViaSubcategorias(){
    var checkboxes = document.querySelectorAll('#meuId input[type="checkbox"]');
    let ids =""
    for(checkbox of checkboxes){
        if(checkbox.checked){
            ids+=(checkbox.name)+"-";
        }
    }
    ids = ids.trim();
    fetch("/usuario/meuslivros/"+cpf.value+"?subcategorias="+ids).then(res => res.json())
    .then(function(data){
        console.log(data);
    }).catch((error)=>{
        console.log("Erro",error);
    });
}
function converteData(dataInput){
    if(dataInput==null) return"";/**/
    var data = new Date(dataInput);
    var opcoesData = { day: '2-digit', month: '2-digit', year: 'numeric' };
    var dataFormatada = data.toLocaleDateString('pt-BR', opcoesData);
    var opcoesHora = { hour: '2-digit', minute: '2-digit' };
    var horaFormatada = data.toLocaleTimeString('pt-BR', opcoesHora);
    return dataFormatada + ' - ' + horaFormatada;
}
pesquisaViaSubcategorias();
function printResult(data){
    let devolvidos = []
    let alugados = []
    for(aluguel of data){
        if(aluguel.status.substring(0,2) =="OK"){
            devolvidos.push(aluguel);
        }else{
            alugados.push(aluguel);
        }
    }
    tabela.innerHTML="";
    while (alugados.length!=0){
        let ultimo = alugados.pop();
        stts = ultimo.status;
        stts = stts.split(" / ")
        stts = stts[0];
        tabela.innerHTML+="<div class=\"pure-u-6-24 tb\">"+ultimo.livro+"</div><div class=\"pure-u-6-24 tb\">"+converteData(ultimo.dataAluguel)+"</div><div class=\"pure-u-6-24 tb\"> <button onclick=\"tentaRenovar("+ultimo.livro+")\" class=\"pure-button\">Renovar</button></div>        <div class=\"pure-u-6-24 tb\">"+stts+"</div>";
    }
    while(devolvidos.length!=0){
        let ultimo = devolvidos.pop()
        stts = ultimo.status;
        stts = stts.split(" / ")
        stts = stts[0];
        tabela.innerHTML+="<div class=\"pure-u-6-24 tb\">"+ultimo.livro+"</div><div class=\"pure-u-6-24 tb\">"+converteData(ultimo.dataAluguel)+"</div><div class=\"pure-u-6-24 tb\"> "+ultimo.diaDevolucao+"</div>        <div class=\"pure-u-6-24 tb\">"+stts+"</div>";
    }
}

function tentaRenovar(idLivro){
    fetch("http://localhost:8081/livros/unidade/"+idLivro).then(res =>res.json())
        .then(function(data){
            if(data){
                alert("Renovado com sucesso");
            }else{
                alert("não foi possivel renovar");
            }
        }).catch((error)=>{
            console.log("ERRO:"+error);
        })
}