
var categorias  = document.getElementById("categorias");
var listadecats = document.getElementById("subcats");
function carregarSubcategorias() {
    // Obter o valor selecionado da categoria
    var categoriaSelecionada = document.getElementById("categorias").value;

    // Criar uma nova instância de XMLHttpRequest
    var xhr = new XMLHttpRequest();

    // Configurar uma solicitação GET para o servidor
    xhr.open("GET", "http://localhost:8081/subcategorias/" + categoriaSelecionada, true);

    // Definir o callback a ser chamado quando a resposta for recebida
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            // Parse da resposta JSON
            var subcategoriasDoServidor = JSON.parse(xhr.responseText);

            // Limpar o combo-box de subcategorias
            var subcategoriasSelect = document.getElementById("subcategorias");
            subcategoriasSelect.innerHTML = "<option value='0'>Selecione</option>";

            // Preencher o combo-box de subcategorias com os resultados obtidos
            for (var i = 0; i < subcategoriasDoServidor.length; i++) {
                var option = document.createElement("option");
                option.value = subcategoriasDoServidor[i].id;
                option.text = subcategoriasDoServidor[i].nome;
                subcategoriasSelect.appendChild(option);
            }
        }
    };

    // Enviar a solicitação
    xhr.send();
}
let subcategorias = [];
class Subcat{
    constructor(value,text){
        this.id= value;
        this.nome = text;
    }
}

let categoriasNovas = []

function testaSeEstaNaListaDeSubs(novaSubcat){
    subcategorias.some(function(subcat) {
        return subcat.value === novaSubcat.value && subcat.text === novaSubcat.text;
    });
}

function addMaisUmNaListaDeSub(){
    listadecats.innerHTML = "<div class=\"pure-control-group\"><label for=\"subcategoria\">subcategorias</label>";
    var subcatSelected = document.getElementById("subcategorias");
    var novaSubcat = new Subcat(subcatSelected.value,subcatSelected.options[subcatSelected.selectedIndex].text);
    // Verifique se a subcategoria já existe no array
    var existe = testaSeEstaNaListaDeSubs(novaSubcat);
    // Se a subcategoria não existir, adicione-a ao array
    if (!existe) {
        subcategorias.push(novaSubcat);
    }else{
        alert("já foi selecionada esta subcategoria!")
    }
    for(var i = 0; i < subcategorias.length; i++){
        adiconeMaisSubcats(subcategorias[i].id, subcategorias[i].nome);
    }
}

function deletaSpan(id){
    var oldId = id.substring(5); // Retirando o prefixo 'span_'
    for (var i=0; i < subcategorias.length; i++){
        if(oldId == subcategorias[i].id){
            subcategorias.splice(i, 1); // Removendo o elemento do array
            break;
        }
    }
    var element = document.getElementById(id);
    element.parentNode.removeChild(element); // Removendo o elemento do DOM
}

function checarSubcatExiste(){
    var subcategoria = {
        categoria_id: document.getElementById("categorias").value,
        nome : document.getElementById("subcatNova").value,
        categoriaNome : categorias.options[categorias.selectedIndex].text
    }
    var url = 'http://localhost:8081/subcategoriaExiste';

    // Converte os dados em parâmetros de consulta
    var params = Object.keys(subcategoria).map(function(key) {
        return key + '=' + encodeURIComponent(subcategoria[key]);
    }).join('&');

    // Adiciona os parâmetros à URL
    url += '?' + params;
    console.log(params)
    // Faz a requisição GET

    fetch(url)
        .then(response => response.json())
        .then(function(data){
            console.log(data);
            if(data=true){
                var resposta = confirm("Você quer Criar esta subcategoria que pertence a categoria "+ subcategoria.categoriaNome + "?");
                if (resposta) {
                    console.log('Usuário clicou em OK');
                    // Adicione um input com valor "texto" ao elemento com id "body"
                    enviaParaOServidorMaisUmaSubcat(subcategoria.nome,subcategoria.categoria_id);

                } else {
                    console.log('Usuário clicou em Cancelar');
                }

            }
        })
        .catch((error) => {
            console.error('Erro:', error);
        });
        
}
document.getElementById("checarSubcat").onclick = function(){
    checarSubcatExiste();
}
function adiconeMaisSubcats(id,texto){
    var span = document.createElement("span");
    span.id = "span_"+id;
    span.textContent =  texto;
    var icon = document.createElement("i");
    icon.className= "fa-solid fa-xmark";
    icon.onclick = function() { deletaSpan(this.parentNode.id); };
    span.appendChild(icon);
    listadecats.appendChild(span);
}

function enviaParaOServidorMaisUmaSubcat(nomedasub,categoria_id){
    var subcategoria = {
        categoria_id: categoria_id,
        nome : nomedasub
    }
    var url = 'http://localhost:8081/subcategoriasPosting';

    // Converte os dados em parâmetros de consulta
    var params = Object.keys(subcategoria).map(function(key) {
        return key + '=' + encodeURIComponent(subcategoria[key]);
    }).join('&');
    console.log(params)
    // Adiciona os parâmetros à URL
    url += '?' + params;

    // Faz a requisição GET

    fetch(url)
        .then(response => response.json())
        .then(function(data){
            console.log(data);
            let idNovaSub = data;
            var subcatParaATela = new Subcat(idNovaSub,subcategoria.nome);
            subcategorias.push(subcatParaATela);
            listadecats.innerHTML = "<div class=\"pure-control-group\"><label for=\"subcategoria\">subcategorias</label>";
            for(var i = 0; i < subcategorias.length; i++){
                adiconeMaisSubcats(subcategorias[i].id, subcategorias[i].nome);
            }
        })
        .catch((error) => {
            console.error('Erro:', error);
        });
}

function enviarForm(){
    class Form {
        constructor(nomeDoLivro,imagemDoLivro,descricaoDoLivro,quantidadeDisponivel,quantidadeEmprestavel,subcategorias){
            this.nomeDoLivro = nomeDoLivro;
            this.imagemDoLivro = imagemDoLivro;
            this.descricaoDoLivro = descricaoDoLivro;
            this.quantidadeDisponivel = quantidadeDisponivel;
            this.quantidadeEmprestavel = quantidadeEmprestavel;
            this.subcategorias = subcategorias;
        }
    }
    class Subcategorias{
        constructor(id){
            this.id = id;
        }
    }
    subcategoriasLocal = [];
    for(i = 0;i<subcategorias.length;i++){
        subcategoriasLocal.push(new Subcategorias(subcategorias[i].id));
    }
    var formulario  =new Form(document.getElementById("nomeDoLivro").value,
        document.getElementById("imagemDoLivro").value,
        document.getElementById("descricaoDoLivro").value,
        document.getElementById("quantidadeDisponivel").value,
        document.getElementById("quantidadeEmprestavel").value,
        subcategoriasLocal)
    formularioJson = JSON.stringify(formulario);
    formularioJsonEmTexto = encodeURIComponent(formularioJson);
    console.log(formularioJsonEmTexto);
    url = "http://localhost:8081/formulario?string="+formularioJsonEmTexto

    fetch(url).then(response => response.json())
    .then(function(data){
        console.log(data);
    });
}