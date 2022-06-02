function updateProfile() {
    var cls = document.getElementsByClassName('readonly');

    for(var i = 0; i < cls.length; i++) {
           cls[i].removeAttribute('readonly');
    }
    
};


function validateProfile() {
    var cls = document.getElementsByClassName('readonly');
    for(var i = 0; i < cls.length; i++) {
        if (cls[i].value == ""){
            cls[i].classList.add("red");
            var a = true;        
            }  
    }

    if (a == true){
        alert("Veuillez remplir tous les champs"); 
        return false;  
    }

};


