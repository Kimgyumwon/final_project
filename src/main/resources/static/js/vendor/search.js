function search() {
	$.ajax({
		url:"/vendor/search",
		post:"post",
		datatype:"json",
		data:{
			vendorName 
		}
		,
		success: function(){
			
		},
		error: function(){
			
		}
	})
}
