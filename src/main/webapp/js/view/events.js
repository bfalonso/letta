var dao;
var eventos = [];

var EventsView = (function() {
	
	// Referencia a this que permite acceder a las funciones públicas desde las funciones de jQuery.
	var self;
	var contRow = 0;
	var rowId = 'events-row-' + contRow;
	var rowQuery = '#' + rowId;
	
	function EventsView(eventsDao, listContainerId, paginationContainerId) {
		dao = eventsDao;
		self = this;
		
		this.init = function() {
			contRow = 0;
			$('.' + listContainerId).html("");
			$('#carouselExampleIndicators').show();
			dao.listRecentEvents(function(events) {
				$.each(events, function(key, event) {
					if(contRow % 3 == 0){
						rowId = 'events-row-' + contRow;
						rowQuery = '#' + rowId;
						insertEventsRow($('.' + listContainerId));
					}
					eventos[contRow] = event.id;
					appendToRow(event, contRow);
					contRow++;
				});
			},
			function() {
			    	alert('No ha sido posible acceder al listado de eventos recientes.');
			});
			

		};
		
		this.doSearch = function(query, page) {
			contRow = 0;
			$('.' + listContainerId).html("");
			$('.' + paginationContainerId).html("");
			$('#carouselExampleIndicators').hide();
			dao.searchEvents(query, page, function(response) {
				/**
				Response is an object array (Object[])
				First element of the array refers to the pagianted events
				Second element refers to total pages for that query
				*/
				events = response[0]
				totalPages = response[1]	
				if(events.length == 0){
					var mensaje = "No hay resultados para tu búsqueda:"
				}else{
					var mensaje = "Los resultados de tu búsqueda:"
				}
				$('.' + listContainerId).append(
						'<div class="section mt-3">\
							<div class="col-md-12">\
							<h4>' + mensaje + '</h4>\
							<p>"' + query + '"</p>\
							</div>\
						</div>'
				);
				if(events.length > 0){
					$.each(events, function(key, event) {
						if(contRow % 3 == 0){
							rowId = 'events-row-' + contRow;
							rowQuery = '#' + rowId;
							insertEventsRow($('.' + listContainerId));
						}
						contRow++;
						appendToRow(event);
					});
					
					// Ads pagination
					if(totalPages > 0) {
						
						var htmlPages = "";
						var queryString = "'" + new String(query) + "'";
						for(var i = 0; i < totalPages; i++){
							
							htmlPages += '<li class="page-item"><a class="page-link" onclick="return searchCall(' + queryString + ', ' + i + ');" href="#">' + (i + 1) + '</a></li>';
						}
						
						$('.' + paginationContainerId).append(
							'<nav aria-label="Page navigation example">\
								<ul class="pagination">' +
								htmlPages +
								'</ul></nav>'
						);
					}
					
				}
				
			},
			function() {
			    	alert('No hay ningún evento asociado a esta búsqueda.');
			});
			

		};
	};
	
	var insertEventsRow = function(parent) {
		parent.append(
			'<div id="' + rowId + '" class="row">\
			</div>'
		);
	};
	
	

	var createEventRow = function(event, contRow) {
		var description;
		var image = '<i class="fa fa-calendar-o" aria-hidden="true"></i>';
		
		if(event.description.length > 200){
			description = event.description.substr(0, 200) + "...";
		}else{
			description = event.description;
		}
		
		switch(event.category){
		case 'sports':
			image = '<i class="fa fa-futbol-o" aria-hidden="true"></i>';
			break;
		case 'cinema':
			image = '<i class="fa fa-film" aria-hidden="true"></i>';
			break;
		case 'theater':
			image = '<i class="fa fa-ticket" aria-hidden="true"></i>';
			break;
		case 'music':
			image = '<i class="fa fa-music" aria-hidden="true"></i>';
			break;
		case 'literature':
			image = '<i class="fa fa-book" aria-hidden="true"></i>';
			break;
		case 'videogames':
			image = '<i class="fa fa-gamepad" aria-hidden="true"></i>';
			break;
		case 'series':
			image = '<i class="fa fa-television" aria-hidden="true"></i>';
			break;		
		}
		
		var text = "";
		
		if(event.num_participants == 0){
			text = "No hay participantes";
		}else if(event.num_participants == 1){
			text = "1 persona asistirá";
		}else{
			text = event.num_participants + " personas asistirán";
		}
		
		var fecha = event.event_date.split("-");
		
		return '<div class="col-lg-4" style="margin-top: 32px">\
					<div class="row">\
					<div class="col-1" style="font-size: xx-large">\
						' + image + '\
					</div>\
					<label>  </label>\
					<div class="col-10 ml-2">\
						<h3 id="event-title">'+ event.title + '</h3>\
					</div>\
					</div>\
					<p id="event-description">' + description + '.</p>\
					<div class="row-lg-1">\
						<small class="text-muted" id="event-location"> ' + event.location + '</small>\
					</div>\
					<div class="row">\
						<div class="col-lg-5">\
							<small class="text-muted" id="event-hour">' + fecha[2]+"/"+fecha[1]+"/"+fecha[0] + '</small>\
						</div>\
						<div class="col-lg-7 mr-0">\
							<small class="text-muted float-right" id="event-participant">' +  text + '</small>\
						</div>\
					</div>\
					<p>\
						<button class="btn btn-secondary mt-2" role="button"  onclick="eventDetailView('+ contRow +');" >Ver\
							detalles</button>\
					</p>\
		</div>';
	};

	var showErrorMessage = function(jqxhr, textStatus, error) {
		alert(textStatus + ": " + error);
	};

	var appendToRow = function(event, contRow) {
		$(rowQuery)
			.append(createEventRow(event, contRow));
	};
	
	return EventsView;
	
})();


var eventDetailView = function (index) {
	
	dao.get(eventos[index],
			function (event){
				$(".modal-title").text(event.title);
				$("#eventDetailDescription").text(event.description);
				$("#eventDetailLocation").text(event.location);
				$("#eventDetailNum_participants").text(event.num_participants);
				$("#eventDetailCapacity").text(event.capacity);
				$("#eventDetailDuration").text(event.duration);
				var fecha = event.event_date.split("-");
				$("#eventDetailEvent_date").text(fecha[2]+"/"+fecha[1]+"/"+fecha[0]);
			},
			function() {
		    	alert('No ha sido posible acceder al evento.');
			}		
	);
	
	$("#previous").unbind();
	$("#next").unbind();
	$("#previous").hide();
	$("#next").hide();
	
	if (index == 0){
		$("#next").click(function(){eventDetailView(index + 1);});
		$("#next").show();
		
	}
	else if (index == eventos.length - 1){
		$("#previous").click(function(){eventDetailView(index - 1);});
		$("#previous").show();
		
	}		
	else{
		$("#next").click(function(){eventDetailView(index + 1);});
		$("#previous").click(function(){eventDetailView(index - 1);});
		$("#previous").show();
		$("#next").show();
		
	}

	$(".eventDetailModal").show();
};






