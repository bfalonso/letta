var dao;

var EventsView = (function() {
	
	// Referencia a this que permite acceder a las funciones públicas desde las funciones de jQuery.
	var self;

	var contRow = 0;
	var rowId = 'events-row-' + contRow;
	var rowQuery = '#' + rowId;
	
	function EventsView(eventsDao, listContainerId) {
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
					contRow++;
					appendToRow(event);
				});
			},
			function() {
			    	alert('No ha sido posible acceder al listado de eventos recientes.');
			});
			

		};
		
		this.doSearch = function(query) {
			contRow = 0;
			$('.' + listContainerId).html("");
			$('#carouselExampleIndicators').hide();
			dao.searchEvents(query, function(events) {
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
				
				$.each(events, function(key, event) {
					if(contRow % 3 == 0){
						rowId = 'events-row-' + contRow;
						rowQuery = '#' + rowId;
						insertEventsRow($('.' + listContainerId));
					}
					contRow++;
					appendToRow(event);
				});
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
	
	

	var createEventRow = function(event) {
		var description;
		var image = "img/internet.svg";
		
		if(event.description.length > 5){
			description = event.description.substr(0, 30) + "...";
		}else{
			description = event.description + ".";
		}
		
		switch(event.category){
		case 'sports':
			image = "img/sports.svg";
			break;
		case 'cinema':
			image = "img/movie.svg";
			break;
		case 'theater':
			image = "img/theater.svg";
			break;
		case 'music':
			image = "img/music.svg";
			break;
		case 'literature':
			image = "img/book.svg";
			break;
		case 'videogames':
			image = "img/videogames.svg"
			break;
		case 'series':
			image = "img/tv.svg";
			break;		
		}
		
		return '<div class="col-lg-4">\
					<img class="rounded-circle" src=' + image + '\
						alt="Generic placeholder image" width="140" height="140"\
						id="event-category">\
					<h2 id="event-title">'+ event.title + '</h2>\
					<p id="event-description">' + description + '.</p>\
					<div class="row-lg-1">\
						<small class="text-muted" id="event-location"> ' + event.location + '</small>\
					</div>\
					<div class="row">\
						<div class="col-lg-5">\
							<small class="text-muted" id="event-hour">' + event.event_date + '</small>\
						</div>\
						<div class="col-lg-7 mr-0">\
							<small class="text-muted float-right" id="event-participant">' + event.num_participants + ' \
								persona/s asistirán</small>\
						</div>\
					</div>\
					<p>\
						<button class="btn btn-secondary mt-2" role="button"  onclick="eventDetailView(' + event.id + ');" >Ver\
							detalles</button>\
					</p>\
		</div>';
	};

	var showErrorMessage = function(jqxhr, textStatus, error) {
		alert(textStatus + ": " + error);
	};

	var appendToRow = function(event) {
		$(rowQuery)
			.append(createEventRow(event));
	};
	
	return EventsView;
	
})();


var eventDetailView = function (idEvent) {
	
	dao.get(idEvent,
			function (event){
				
				$(".modal-title").text(event.title);
				$("#eventDetailDescription").text(event.description);
				$("#eventDetailLocation").text(event.location);
				$("#eventDetailNum_participants").text(event.num_participants);
				$("#eventDetailCapacity").text(event.capacity);
				$("#eventDetailDuration").text(event.duration);

				$("#eventDetailCreation_date").text(event.creation_date);
				$("#eventDetailEvent_date").text(event.event_date);
				
		
				$(".eventDetailModal").show();
		
			},
			function() {
		    	alert('No ha sido posible acceder al evento.');
			}		
	);
	
	
};







