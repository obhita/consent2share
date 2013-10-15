function phoneNumberParser (number){
	if (number.length==10){
		number="("+number.substr(0,3)+") "+number.substr(3,3)+"-"+number.substr(6,4);
	}
	
	return number;
		
}

function zipCodeParser(zipcode){
	if (zipcode.length==9){
		zipcode=zipcode.substr(0,5)+"-"+zipcode.substr(5,4);
	}
	return zipcode;
}