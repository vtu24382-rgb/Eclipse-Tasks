function validateName(event){

let char = event.key;

if(!/[a-zA-Z ]/.test(char)){
event.preventDefault();
alert("Only letters allowed in name");
}

}

function validateEmail(event){

let char = event.key;

if(!/[a-zA-Z0-9@.]/.test(char)){
event.preventDefault();
alert("Invalid email character");
}

}

function highlight(field){

field.style.backgroundColor = "lightyellow";

}

function removeHighlight(field){

field.style.backgroundColor = "white";

}

function submitForm(){

alert("Feedback Submitted Successfully!");

}