
let open = document.querySelectorAll(".data");
let close = document.querySelector("#close");

open.forEach((e) => {
  e.addEventListener("click", () => {
    
  	let a = e.getAttribute('id');
  	 
  	
  	let table = document.getElementsByTagName('table')[0];
	let rowIndex = document.getElementById(a).rowIndex;
	
	
	let id = table.rows[rowIndex].cells[0].innerText;
	let nickname = table.rows[rowIndex].cells[1].innerText;
	let user_role = table.rows[rowIndex].cells[2].innerText;
	console.log(id);
	console.log(nickname);
	console.log(user_role);
  	
  	
 	document.querySelector("#id_person").value = id;
 	document.querySelector("#nickname").value = nickname;
 	
 	if(user_role == "user"){
		document.getElementById('user_role').getElementsByTagName('option')[0].selected = 'selected';
	} else if (user_role == "moderator"){
		document.getElementById('user_role').getElementsByTagName('option')[1].selected = 'selected';
	} else{
				document.getElementById('user_role').getElementsByTagName('option')[2].selected = 'selected';
	}
 	
	document.querySelector("#window_modify").classList.add("active");

  });
});


close.addEventListener("click", () => {
  document.querySelector("#window_modify").classList.remove("active");

});





