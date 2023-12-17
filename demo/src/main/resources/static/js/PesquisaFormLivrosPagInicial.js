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
        console.log(data)
        for(livro of data){
            panelLivros.innerHTML +=" <div class=\"col-md-9 principal\"><div class=\"card\" style=\"width: 12rem;\"><img src=\""+livro.imagemLink+"\" class=z\"card-img-top\" alt=\"Imagem do Livro\"><div class=\"card-body\"><h5 class=\"card-title\">"+livro.nome+"</h5><p class=\"card-text\">"+livro.descricao+"</p><hr><p>"+livro.quantidadeDisponivel+" livros disponíveis!</p></div></div></div>";
            /*var container = document.createElement("div");
            container.className="col-md-9 principal";
            var innerContainer = document.createElement("div");
            innerContainer.className = "card";
            innerContainer.style = "width: 12rem;"
            container.appendChild(innerContainer);
            var imagem = document.createElement("img")
            imagem.src =livro.imagemLink;
            imagem.className = "card-img-top";
            innerContainer.appendChild(imagem);
            var divcardbody = document.createElement("div");
            divcardbody.className="card-body";
            var h5 = document.createElement("h5");
            h5.className = "card-title";
            h5.innerText = livro.nome;
            var p1 = document.createElement("p");
            p1.className = "card-text";
            p1.innerText = livro.descricao;
            let p2  = document.createElement("p");
            p2.innerText = livro.quantidadeDisponivel +"  livros disponíveis!";
            divcardbody.appendChild(h5);
            divcardbody.appendChild(p1);
            divcardbody.appendChild(document.createElement("hr"))
            divcardbody.appendChild(p2);*/
        }
    }).catch((error) => {
        console.error('Erro:', error);
    });
}