let open = document.querySelectorAll(".pics");
let close = document.querySelector("#background");

open.forEach((e) => {
  e.addEventListener("click", () => {
    
    let imgSrc = e.querySelector('#pics img').src;
    
    let indexSrc = imgSrc.indexOf("Images");
    
    let resultSrc = imgSrc.substr(indexSrc);
    
    document.querySelector('#pop img').src = "./" + resultSrc;
    
    document.querySelector("#background").classList.add("active");
   
    document.querySelector("#pop").classList.add("active");
  });
});

close.addEventListener("click", () => {
 	document.querySelector("#background").classList.remove("active");
 	
	document.querySelector("#pop").classList.remove("active");
});