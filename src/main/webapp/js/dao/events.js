var EventsDAO = (function () {
  var resourcePath = "rest/events/";
  var requestByAjax = function (data, done, fail, always) {
    done = typeof done !== "undefined" ? done : function () {};
    fail = typeof fail !== "undefined" ? fail : function () {};
    always = typeof always !== "undefined" ? always : function () {};

    /*let authToken = localStorage.getItem('authorization-token');
	if (authToken !== null) {
	    data.beforeSend = function(xhr) {
		xhr.setRequestHeader('Authorization', 'Basic ' + authToken);
	    };
	}*/

    $.ajax(data).done(done).fail(fail).always(always);
  };

  function EventsDAO() {
    this.listRecentEvents = function (done, fail, always) {
      requestByAjax(
        {
          url: resourcePath + "recent",
          type: "GET",
        },
        done,
        fail,
        always
      );
    };
    this.get = function (idEvent, done, fail, always) {
      requestByAjax(
        {
          url: resourcePath + idEvent,
          type: "GET",
        },
        done,
        fail,
        always
      );
    };
    this.searchEvents = function (query, done, fail, always) {
      requestByAjax(
        {
          url: resourcePath + "?search=" + query,
          type: "GET",
        },
        done,
        fail,
        always
      );
    };
  }

  return EventsDAO;
})();
