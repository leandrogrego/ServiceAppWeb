//VARIÁVEIS DE AMBIENTE
var user, servicos, servico = {id:'', name:''}, prestadores, prestador, loop=false, position = {};

//FUNÇÕES DE USUÁRIO
    /*$.get("/error", function(data) {
        if (data) {
            $(".error").html(data);
        } else {
            $(".error").html('');
        }
    });*/
    
    var logout = function() {
        $.post("/logout", function() {
            $("#user").html('');
            $(".unauthenticated").show();
            $(".authenticated").hide();
        })
        return true;
    }

//FUNÇÕES DA TELA DE SERVIÇOS
function geraBotaoServico(servico){
	return'<button onclick="loadPrestadores('+servico.id+', \''+servico.name+'\')" class="btn btn-secondary btn-outline-warning text-light">'+
		servico.name + '</button>';
}

function addServico(servico){
	var botao = geraBotaoServico(servico);
	$("#listaServicos").append(botao);
}

function loadServicos(){							
	$.get("/api/servicos", function(serv) {
		servicos = serv;
		$("#listaServicos").html('');
		servicos.forEach(addServico);
		show("Servicos");
	});
}

function filterServicos(text){
	var find = false;
	$("#listaServicos").html('');
	servicos.forEach(function(s){
		if(s.name.toLowerCase().indexOf(text.toLowerCase()) > -1){
			addServico(s); 								
		};
	});
}

//FUNÇÕES DA TELA DE PRESTADORES
function geraBotaoPrestador(prestador){
	var servs = '';
	prestador.servicos.forEach(function(s){
		servs += s.name+" "
	});
	return'<button onclick="loadMensagens('+prestador.id+', \''+prestador.name+'\')" class="btn-outline-warning col-xs-4 col-md-6 col-sm-12 p-2" style="border-radius: 50px">'+
	'<div class="btn btn-secondary text-light col-12" style="border-radius: 50px"><img class="col-4 m-1 float-left" style="border-radius: 50%" src="'+prestador.avatar_url+'"><br/><big>'+prestador.name + '</big><br/><small>'+servs+'</small></div></button>';
}

function addPrestador(prestador){
	var botao = geraBotaoPrestador(prestador);
	$("#listaPrestadores").append(botao);
}

async function loadPrestadores(servicoId, servicoName){
	await gps();
	servico.id = servicoId;
	servico.name = servicoName;
	var distancia=$("#distancia").val();
	console.log(distancia);
	$.get("/api/servico/"+servicoId+"/prestadores?latitude="+position.latitude+"&longitude="+position.longitude+"&distancia="+distancia, function(contatos) {
		prestadores = contatos;
		$("#listaPrestadores").html('<h4>'+servicoName+'</h4>');
		contatos.forEach(addPrestador);
		show("Prestadores");
	});
}	

function success(pos) {
	var crd = pos.coords;			
	$.ajax({
        url: 'api/u/l',
        type: 'post',
        dataType: 'json',
        contentType: 'application/json',
        success: function (loc) {
        	console.log('Sua posição atual é:');
			console.log('Latitude : ' + loc.latitude);
			console.log('Longitude: ' + loc.longitude);
			console.log('Altitude: ' + loc.altitude);
			console.log('Atualizada em: ' + loc.time);
			position = loc;
		},
        data: JSON.stringify({ 
			time: new Date().getTime(),
			bearing : null,
			speed: crd.speed,
			altitude: crd.altitude,
			longitude: crd.longitude,
			latitude: crd.latitude,
			provider: ""
		})
    });
};

function error(err) {
	console.warn('ERROR(' + err.code + '): ' + err.message);
};

function gps(){
	var options = {
		enableHighAccuracy: true,
		timeout: 5000,
		maximumAge: 0
	};
	navigator.geolocation.getCurrentPosition(success, error, options);
}

function filterPrestadores(text){
	var find = false;
	$("#listaPrestadores").html('');
	prestadores.forEach(function(p){
		if(p.name.toLowerCase().indexOf(text.toLowerCase()) > -1){
			addPrestador(p); 								
		};
	});
}

function loadContatos(){
	$.get("/api/u/c", function(contatos) {
		prestadores = contatos;
		$("#listaPrestadores").html('<h4>Meus Contatos</h4>');
		contatos.forEach(addPrestador);
		show("Prestadores");
	});
}			

//FUNÇÕES DA TELA DE MENSAGENS
function geraMensagen(mensagem){
	var color;
	if(mensagem.ready){
		color = 'primary';
	} else if(mensagem.delivery){
		color = 'success';
	} else {
		color = 'light';
	}
	var divdate = '<div class="text-1 text-'+color+'">'+mensagem.created.substring(11,16)+'<div>'
	var avatar = '<img src="'+mensagem.from.avatar_url+'" style="border-radius: 50%; height: 40px">';
	if(mensagem.from.id == $('#email').attr("alt")){
		return '<div class="d-flex justify-content-end text-dark"><div class="bg-info m-1 p-2" style="border-radius: 20px">'+mensagem.text+'</div>'+avatar+divdate+'</div>';
	} else {
		return '<div class="d-flex justify-content-start text-dark">'+avatar+'<div class="bg-light m-1 p-2" style="border-radius: 20px">'+mensagem.text+'</div>'+divdate+'</div>';	
	}
}

function criaRotuloData(date){
	var dia = date.substring(8,10);
	var mes = date.substring(5,7);
	var ano = date.substring(0,4);
	date = dia+'/'+mes+'/'+ano;
	return '<div class="mx-auto text-light m-2 col-12 bg-secondary">'+date+'</dov>'
}

function addMensagem(mensagem, data){
	var div = geraMensagen(mensagem);
	$("#listaMensagens").append(div);
}

async function loadMensagens(prestadorId, pretadorName){
	$.get("api/m/p/"+prestadorId, function(mensagens) {
		$("#titleMensagens").html(pretadorName	);
		$("#listaMensagens").html('');
		$("#buttonMensagem").val(prestadorId);
		var data;
		for(var i=0; i<mensagens.length; i++){
			var created = mensagens[i].created.substring(0,10);
			if(data != created){
				var rotuloData = criaRotuloData(created);
				$("#listaMensagens").append(rotuloData); 
				data = created;
			}
			addMensagem(mensagens[i]);
		}
		show("Mensagens")
		$("#listaMensagens").append('<a name="lastMensagem" />');
		location.href="#lastMensagem";
		//$("#inputMensagem").focus();
	})						
}

function loopMensagens(){
	var PrestadorName = $("#titleMensagens").html();
	var PrestadorId = $("#buttonMensagem").val();
	var div = $("#listaMensagem"); 
	if(loop){
		loadMensagens(PrestadorId, PrestadorName);
	}
}


function sendMensagem(){
	var mens = $('#inputMensagem').val();
	var prestadorId = $('#buttonMensagem').val();
	$.post(
		"api/m", 
		{ text : mens, id : prestadorId}, 
		function(mensagem){
			addMensagem(mensagem)
			$('#inputMensagem').val('')
		}
	)
	
}

//FUNÇÕES DA TELA MEUS SERVIÇOS
function geraBotaoAddServico(servico){
	return'<button onclick="addServicos('+servico.id+')" class="btn btn-secondary btn-outline-warning text-light">'+
		servico.name + '</button>';
}

function geraBotaoRemoveServico(servico){
	return'<button onclick="remServicos('+servico.id+')" class="btn btn-secondary btn-outline-warning text-light">'+
		servico.name + '</button>';
}

function added(servico){
	var botao = geraBotaoRemoveServico(servico);
	$("#myservicos").append(botao);
}

function notAdded(servico){
	var botao = geraBotaoAddServico(servico);
	$("#noservicos").append(botao);
}

function addServicos(id){
	$.ajax({
	    url: "/api/u/s/"+id,
	    type: 'PUT',
	    success: function(myservicos) {
	    	$("#myservicos").html('');
			$("#noservicos").html('');
			myservicos[0].forEach(added);
			myservicos[1].forEach(notAdded);
	    }
	});
}

function remServicos(id){
	$.ajax({
	    url: "/api/u/s/"+id,
	    type: 'DELETE',
	    success: function(myservicos) {
	    	$("#myservicos").html('');
			$("#noservicos").html('');
			myservicos[0].forEach(added);
			myservicos[1].forEach(notAdded);
	    }
	});
}

function loadMyServicos(){
	$.get("/api/u/s", function(myservicos) {
		$("#myservicos").html('');
		$("#noservicos").html('');
		myservicos[0].forEach(added);
		myservicos[1].forEach(notAdded);
	});
	show("listaMeusServicos");
}

function getUser(){
	$.get("api/u", function(u) {
		user = u;
	});
	return(user)
}

function show(div){
	$("#Servicos").hide();
	$("#Prestadores").hide();
	$("#listaMeusServicos").hide();
	$("#Mensagens").hide();
	$('#'+div).show();
	if(div == 'Mensagens') {loop = true} else {loop = false};
}


gps();
//getUser();					
loadServicos();
setInterval(loopMensagens, 10000);

//FUNÇÕES WEBPUSHR
(function(w,d, s, id) {
	if(typeof(w.webpushr)!=='undefined') return;
	w.webpushr=w.webpushr|| function(){
		(w.webpushr.q=w.webpushr.q||[]).push(arguments)
	};
	var js, fjs = d.getElementsByTagName(s)[0];
	js = d.createElement(s); 
	js.id = id;js.async=1;
	js.src = "https://cdn.webpushr.com/app.min.js";
	fjs.parentNode.appendChild(js);
}
(window,document, 'script', 'webpushr-jssdk'));
webpushr(
	'setup',
	{'key':'BDNhX2aQcjWvQ-PCMhsr94F3OazoCRc-8pMcwGl3A1JebG92i9uEzK3o5Oc7vL3szIkOKC_v64yTft93gBXeH4o' }
	);
function updatePushId(){
	webpushr('fetch_id',function (sid) { 
		$.ajax({
		    url: "/api/u/push/"+sid,
		    type: 'PUT',
		    success: function(pushId) {
		    	console.log('push id: ' + pushId)
		    }
		});
    });
}
updatePushId(); 