var librosURL;

$(document).ready(function(){
	$('#edit-cancel').click(function(e){
		e.preventDefault();
		hideEditForm();
	});
	loadRootAPI(function(rootAPI){
		librosURL = rootAPI.getLink('libros').href;
		loadStings(rootAPI.getLink('libros').href);
		$('#button-create-libro').click(function(e){
			e.preventDefault();
			var sting = new Object();
			sting.subject = $('#create-subject').val();
			sting.content = $('#create-content').val();
			var createStingLink = rootAPI.getLink('create-stings');
			createSting(createStingLink.href, createStingLink.type, JSON.stringify(sting), function(sting){
				showSting(sting);
			});
		});
	});
});