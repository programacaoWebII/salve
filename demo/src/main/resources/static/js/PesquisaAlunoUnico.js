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
        
    }).catch((error)=>{
        console.log("erro",error)
    })
}
function printResult(data){
    let devolvidos = []
    let alugados = []
    for(aluguel of data){
        if(aluguel.status.substring(0,2) =="OK"){
            devolvidos.push(aluguel)
        }else{
            alugados.push(aluguel)
        }
    }
    y = alugados.length;
    tabela.innerHTML="";
    for(i=0;i<y;i++){
        let ultimo = alugados.pop();
        stts = ultimo.status;
        stts = stts.split(" / ")
        stts = stts[0];
        tabela.innerHTML+="<div class=\"pure-u-6-24 tb\">"+ultimo.livro+"</div><div class=\"pure-u-6-24 tb\">"+converteData(ultimo.dataAluguel)+"</div><div class=\"pure-u-6-24 tb\">"+converteData(ultimo.diaDevolucao)+"</div><div class=\"pure-u-6-24 tb\">"+stts+"<button onclick=\"renovaLivro(\""+ultimo.id+"\") class=\"pure-button\"></button></div>";
    }
    y = devolvidos.length;
    for(i=o;i<y;i++){
        stts = ultimo.status;
        stts = stts.split(" / ")
        stts = stts[0];
        tabela.innerHTML+="<div class=\"pure-u-6-24 tb\">"+ultimo.livro+"</div><div class=\"pure-u-6-24 tb\">"+converteData(ultimo.dataAluguel)+"</div><div class=\"pure-u-6-24 tb\">"+converteData(ultimo.diaDevolucao)+"</div><div class=\"pure-u-6-24 tb\">"+stts+"</div>";
    }
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


function renovaLivro(idAluguel){
    fetch("http://localhost:8081/livroDevolucao/"+idAluguel).then(res => res.json())
    .then(function(data){
        if(data){
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