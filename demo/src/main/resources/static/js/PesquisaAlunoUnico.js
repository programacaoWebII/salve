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
        printResult(data);
    }).catch((error)=>{
        console.log("Erro",error);
    });
}
async function printResult(data){
    let devolvidos = []
    let alugados = []
    for(aluguel of data){
        if(aluguel.status.substring(0,2) =="OK"){
            devolvidos.push(aluguel);
        }else{
            alugados.push(aluguel);
        }
    }
    y = alugados.length;
    tabela.innerHTML="";
    for(i=0;i<y;i++){
        let ultimo = alugados.pop();
        stts = ultimo.status;
        stts = stts.split(" / ")
        stts = stts[0];
        tabela.innerHTML+="<div class=\"pure-u-6-24 tb\">"+ultimo.livro+"</div><div class=\"pure-u-6-24 tb\">"+converteData(ultimo.dataAluguel)+"</div><div class=\"pure-u-6-24 tb\">"+ await testaseTaVOkPraRenovar(ultimo)+"</div><div class=\"pure-u-6-24 tb\">"+stts+"</div>";
    }
    tamDevolv = devolvidos.length;
    for(i=0;i<tamDevolv;i++){
        let ultimo = devolvidos.pop();
        stts = ultimo.status;
        stts = stts.split(" / ");
        stts = stts[0];
        tabela.innerHTML+="<div class=\"pure-u-6-24 tb\">"+ultimo.livro+"</div><div class=\"pure-u-6-24 tb\">"+converteData(ultimo.dataAluguel)+"</div><div class=\"pure-u-6-24 tb\">"+converteData(ultimo.diaDevolucao)+"</div><div class=\"pure-u-6-24 tb\">"+stts+"</div>";
    }
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




function renovaLivro(idLivro){
    url = "http://localhost:8081/livroRenovacao/"+idLivro;
    console.log(url);
    fetch(url).then(res => res.json())
    .then(function(data){
        if(data){
            pesquisaViaSubcategorias();
        }else{
            alert("algo deu errado");
        }
    }).catch((error)=>{
        console.log("Erro",error);
    });
}
pesquisaViaSubcategorias();

async function testaseTaVOkPraRenovar(aluguel){
    dataAluguel = aluguel.dataAluguel;
    dataAluguel = new Date(dataAluguel);
    dataAtual = new Date();
    var diferencaEmMs = dataAtual.getTime() - dataAluguel.getTime();
    var diferencaEmDias = diferencaEmMs / (1000 * 60 * 60 * 24);
    
    console.log(diferencaEmDias);
    return fetch("http://localhost:8081/livros/unidade/"+aluguel.livro).then(res =>res.json())
    .then(function(data){
        livro = data;
        if(livro.status=="alugado" && diferencaEmDias>=6 && diferencaEmDias<8){
            return "<button onclick=\"renovaLivro('"+aluguel.id+"')\" class=\"pure-button\">Renovar</button>";
        }else if(livro.status=="renovado 1" && diferencaEmDias>=9 && diferencaEmDias<10){
            return "<button onclick=\"renovaLivro('"+aluguel.id+"')\" class=\"pure-button\">Renovar</button>";
        }else if(livro.status=="renovado 2" && diferencaEmDias>=12 && diferencaEmDias<13){
            return "<button onclick=\"renovaLivro('"+aluguel.id+"')\" class=\"pure-button\">Renovar</button>";
        }else if(livro.status =="renovado 3"){
            return "Não é possível renovar mais.";
        }else if(diferencaEmDias>=8){
            return "Atrasado";
        }else{
            return "";
        }
    }).catch((error)=>{
        console.log("Erro",error);
    });
}

/*

    
    if(diferencaEmDias> 5 && diferencaEmDias<8){
        return "<button onclick=\"renovaLivro('"+aluguel.id+"')\" class='pure-button' >Renovar</button>";
    }else if(diferencaEmDias>7){
        fetch("http://localhost:8081/livros/unidade/"+aluguel.livro).then(res =>res.json())
        .then(function(data){
            if(data.status.includes("renovado")){
                vezes = data.status.split(" ");
                switch(vezes){
                    case 1:
                        if(diferencaEmDias>10){
                            return "Atrasado.";
                        }else if(diferencaEmDias>8){
                            return "<button onclick=\"renovaLivro(\""+aluguel.livro+"\") class=\"pure-button\">Renovar</button>";
                        }else{
                            return "";
                        }
                        break;
                    case 2:
                        if(diferencaEmDias>13){
                            return "Atrasado.";
                        }else if(diferencaEmDias>11){
                            return "<button onclick=\"renovaLivro(\""+aluguel.livro+"\") class=\"pure-button\">Renovar</button>";
                        }else{
                            return "";
                        }
                        break;
                    default:
                        return "";
                }
            }else{
                return "Atrasado.";
            }
        }).catch((error)=>{
            console.log("Erro",error);
        });
    }else{
        return "";
    }

*/