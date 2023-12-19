var panelLivros = document.getElementById("panelLivros")
function pegaTodosOsCheckbox(){
    var checkboxes = document.querySelectorAll('#meuId input[type="checkbox"]');
    let ids =""
    //console.log(checkboxes);
    for(checkbox of checkboxes){
        if(checkbox.checked){
            ids+=(checkbox.name)+"-";
        }
    }
    ids = ids.trim();
    let url = "http://localhost:8081/livrosViaSubcategorias?subcategorias="+encodeURIComponent(ids)
    var panelLivros = document.getElementById("panelLivros")
    console.log(url)
    fetch(url)
    .then(response => response.json())
    .then(function(data){
        panelLivros.innerHTML = "";
        
        for(livro of data){
            panelLivros.innerHTML += "<div class=\"pure-u-sm-1-24\"></div><div class=\"pure-u-1-1 pure-u-sm-10-24 pure-u-md-6-24 pure-u-lg-4-24 pure-g\"><h1 class =\"seletor\" title="+livro.nome+">"+livro.nome+"</h1><img class=\"pure-u-1-1\" src="+livro.imagemLink+" alt="+livro.descricao+"><p>"+livro.quantidadeDisponivel+" livros disponíveis!</p></div><div class=\"pure-u-sm-1-24\"></div>";
            //panelLivros.innerHTML +=" <div class=\"col-md-9 principal\"><div class=\"card\" style=\"width: 12rem;\"><img src=\""+livro.imagemLink+"\" class=z\"card-img-top\" alt=\"Imagem do Livro\"><div class=\"card-body\"><h5 class=\"card-title\">"+livro.nome+"</h5><p class=\"card-text\">"+livro.descricao+"</p><hr><p>"+livro.quantidadeDisponivel+" livros disponíveis!</p></div></div></div>";
            console.log("passou aqui");
        }
    }).catch((error) => {
        console.error('Erro:', error);
    });
}