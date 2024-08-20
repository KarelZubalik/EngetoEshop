
let apiUrl = '';

function loadConfigAndInitialize() {
    fetch('/api/config')
        .then(response => response.json())
        .then(config => {
            const host = config.host;
            const port = config.port;
            apiUrl = `http://${host}:${port}/eshop`;
            load();
            console.log(`Host: ${host}, Port: ${port}`);
        })
        .catch(error => console.log('Error fetching config:', error));
}



function loadItem() {

        var requestOptions = {
            method: 'GET',
            redirect: 'follow'
        };

        var id = document.getElementById("id").value;

        fetch(apiUrl+"/"+id, requestOptions)
            .then(response => response.json())
            .then(result => {
                console.log(result);
                console.log("result.id: " + result.id);
                console.log("result.partNo: " + result.partNo);
                console.log("result.name: " + result.name);
                console.log("result.description: " + result.description);
                console.log("result.isForSale: " + result.isForSale);
                console.log("result.price: " + result.price);
                document.getElementById("text").value = "ID:"+result.id+"\n"+"Název produktu:"+result.name+"\n"+"Popis produktu:"+result.description+"\n"+"Číslo produktu:"+result.partNo+"\n"+"Cena produktu:"+result.price;
                document.getElementById("done").checked = result.isForSale;

            })
            .catch(error => console.log('error', error));

    }

    function load() {
            var requestOptions = {
                method: 'GET',
                redirect: 'follow'
            };

            fetch(apiUrl, requestOptions)
                .then(response => response.json())
                .then(result => {
                    console.log(result);

//                    document.getElementById("todoList").innerHTML = "";
                    document.getElementById("products1").innerHTML = "";

                    result.forEach((e) => {
                          newItem = document.createElement("div");
                          newItem.setAttribute('class', 'pro');
                          img = document.createElement("img");
                          img.setAttribute('src', e?.fileInBase64);
                          img.setAttribute('alt', '');
                          newItem.appendChild(img);
                          description = document.createElement("div");
                          description.setAttribute('class', 'des');
                          newItem.appendChild(description);
                          itemName = document.createElement("h3");
                          itemName.innerText = e?.name;
                          description.appendChild(itemName);
                          idProduktu = document.createElement("span");
                          idProduktu.innerText = "idProduktu:"+e?.id+"\n";
                          description.appendChild(idProduktu);
                          itemPartNo = document.createElement("span");
                          itemPartNo.innerText = "partNo:"+e?.partNo+"\n";
                          description.appendChild(itemPartNo);
                          itemDescription = document.createElement("span");
                          itemDescription.innerText = "Popis produktu:"+e?.description;
                          description.appendChild(itemDescription);
                          itemPrice = document.createElement("h4");
                          itemPrice.innerText = "Cena:"+e?.price;
                          description.appendChild(itemPrice);
                          document.getElementById("products1").appendChild(newItem);



//                        newItem = document.createElement("div");
//                        newItem.innerText = (e?.isForSale? "Ano" : "Ne") + "\n"+e?.id + "\n"+ e?.partNo + "\n"+ e?.name + "\n"+ e?.description + "\n"+ e?.price;
//                        changePriceInput = document.createElement("input");
//                        changePriceInput.setAttribute('id', 'priceToChange');
//                        changePriceButton = document.createElement("button");
//                        changePriceButton.innerText = "Změn cenu";
//                        changePriceButton.setAttribute('onclick', 'javascript : setItemNewPrice(' + e?.id + ');');
//                        deleteButton = document.createElement("button");
//                        deleteButton.innerText = "Delete";
//                        deleteButton.setAttribute('onclick', 'javascript : deleteItem(' + e?.id + ');');
//                        newItem.appendChild(deleteButton);
//                        newItem.appendChild(changePriceInput);
//                        newItem.appendChild(changePriceButton);
//                        document.getElementById("todoList").appendChild(newItem);
                    })




                })
                .catch(error => console.log('error', error));
        }
        loadConfigAndInitialize();
        load();

        function addValue(){
        var file = document.getElementById("attachementName").files[0];

//        getBase64(file).then(
//          data =>  document.getElementById("base64textarea").value = data
//        );
//        var doprdkynacpat= console.get(data);
            var myHeaders = new Headers();
            myHeaders.append("Content-Type", "application/json");
//            const inputKey = fileInput.getAttribute('name')
//            myFiles[inputKey] = "data:${file.type};base64,${document.getElementById("base64textarea").value}"

            var raw = JSON.stringify({
                "partNo": document.getElementById("itemPartNo").value,
                "name": document.getElementById("itemName").value,
                "description": document.getElementById("itemDescription").value,
                "price": document.getElementById("itemPrice").value,
                "isForSale": document.getElementById("done").checked,
                "fileInBase64": document.getElementById("base64textarea").value? document.getElementById("base64textarea").value : "null"
            });
            document.getElementById("base64textarea").value ="";


            var requestOptions = {
                method: 'POST',
                headers: myHeaders,
                body: raw,
                redirect: 'follow'
            };

            fetch(apiUrl, requestOptions)
                .then(response => response.text())
                .then(result => {console.log(result); load();})
                .catch(error => console.log('error', error));
        }

        function deleteItemFromInput() {
            var requestOptions = {
                method: 'DELETE',
                redirect: 'follow'
            };
            var id=document.getElementById("deleteSpecificItemInput").value;
            console.log(id);

            fetch(apiUrl+"/"+id, requestOptions)
                .then(response => response.text())
                .then(result => {console.log(result); load();})
                .catch(error => console.log('error', error));
        }
        function deleteAllNotSaleItems() {
            var requestOptions = {
                method: 'DELETE',
                redirect: 'follow'
            };

            fetch(apiUrl+"/"+"deleteAllNotSaleItems", requestOptions)
                .then(response => response.text())
                .then(result => {console.log(result); load();})
                .catch(error => console.log('error', error));
        }

        function setItemNewPrice(id) {
        var myHeaders = new Headers();
            myHeaders.append("Content-Type", "application/json");

        var raw = JSON.stringify({
                "price": document.getElementById("priceToChange").value,
            });
            var requestOptions = {
                method: 'PUT',
                headers: myHeaders,
                body: raw,
                redirect: 'follow'
            };

            fetch(apiUrl+"/"+id, requestOptions)
                .then(response => response.text())
                .then(result => {console.log(result); load();})
                .catch(error => console.log('error', error));
        }

function getBase64(file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result);
    reader.onerror = error => reject(error);
  });
}
var handleFileSelect = function(evt) {
    alert("file selected");
    var files = evt.target.files;
    var file = files[0];
    if (files && file) {
        var reader = new FileReader();

        reader.onload = function(readerEvt) {
            var binaryString = readerEvt.target.result;
            konvertierteDatei = btoa(binaryString);
            document.getElementById("base64textarea").value = konvertierteDatei;
            getBase64(file).then(
                      data =>  document.getElementById("base64textarea").value = data
                    );
            console.log(konvertierteDatei);
        };

        reader.readAsBinaryString(file);
    }
};



document.addEventListener("DOMContentLoaded", function() {
    if (window.File && window.FileReader && window.FileList && window.Blob) {
       document.getElementById('attachementName').addEventListener('change', handleFileSelect, false);
    } else {
        alert('Die Datei APIs werden von diesem Browser nicht vollständig unterstützt.');
    }
});