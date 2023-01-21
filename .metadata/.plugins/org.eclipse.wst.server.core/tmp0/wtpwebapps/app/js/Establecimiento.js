const $d = document;

$d.addEventListener("DOMContentLoaded", () => {
	handleOnClick();
});

const handleOnClick = () => { 
	const $btnUpdate = $d.getElementById("btn-update-establishment");
	
	$d.addEventListener("click", (e) => {
		if ($btnUpdate == e.target) {
			let $txtSobreNosostros = $d.querySelector("textarea[name='txtSobreNosotros']");
			
			if(!$txtSobreNosostros.value.match("^(?=.{1,500}$)[A-ZÑÁÉÍÓÚ1-9\\S][a-zñáéíóú1-9\\S]+(?: [A-Za-zñáéíóú1-9\\S]+)*$")){
				if(!$txtSobreNosostros.classList.contains("is-invalid")) $txtSobreNosostros.classList.add("is-invalid");
				e.preventDefault();
			}else{
				if($txtSobreNosostros.classList.contains("is-invalid")) $txtSobreNosostros.classList.remove("is-invalid");
			}
		}	
	});	
}