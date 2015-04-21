
(function ($) {
	$.fn.addOption = function () {
		var add = function (el, v, t, sO) {
			var option = document.createElement("option");
			option.value = v, option.text = t;
		// get options
			var o = el.options;
		// get number of options
			var oL = o.length;
			if (!el.cache) {
				el.cache = {};
			// loop through existing options, adding to cache
				for (var i = 0; i < oL; i++) {
					el.cache[o[i].value] = i;
				}
			}
		// add to cache if it isn't already
			if (typeof el.cache[v] == "undefined") {
				el.cache[v] = oL;
			}
			el.options[el.cache[v]] = option;
			if (sO) {
			//option.selected = true; defult last selected
				option.selected = false;
			}
		};
		var a = arguments;
		if (a.length == 0) {
			return this;
		}
	// select option when added? default is true
		var sO = true;
	// multiple items
		var m = false;
	// other variables
		var items, v, t;
		if (typeof (a[0]) == "object") {
			m = true;
			items = a[0];
		}
		if (a.length >= 2) {
			if (typeof (a[1]) == "boolean") {
				sO = a[1];
			} else {
				if (typeof (a[2]) == "boolean") {
					sO = a[2];
				}
			}
			if (!m) {
				v = a[0];
				t = a[1];
			}
		}
		this.each(function () {
			if (this.nodeName.toLowerCase() != "select") {
				return;
			}
			if (m) {
				for (var item in items) {
					add(this, item, items[item], sO);
				}
			} else {
				add(this, v, t, sO);
			}
		});
		return this;
	};
	$.fn.ajaxAddOption = function (url, params, select, fn, args) {
		if (typeof (url) != "string") {
			return this;
		}
		if (typeof (params) != "object") {
			params = {};
		}
		if (typeof (select) != "boolean") {
			select = true;
		}
		this.each(function () {
			var el = this;
			$.getJSON(url, params, function (r) {
				$(el).addOption(r, select);
				if (typeof fn == "function") {
					if (typeof args == "object") {
						fn.apply(el, args);
					} else {
						fn.call(el);
					}
				}
			});
		});
		return this;
	};
	$.fn.removeOption = function () {
		var a = arguments;
		if (a.length == 0) {
			return this;
		}
		var ta = typeof (a[0]);
		var v, index;
	// has to be a string or regular expression (object in IE, function in Firefox)
		if (ta == "string" || ta == "object" || ta == "function") {
			v = a[0];
		} else {
			if (ta == "number") {
				index = a[0];
			} else {
				return this;
			}
		}
		this.each(function () {
			if (this.nodeName.toLowerCase() != "select") {
				return;
			}
			// clear cache
			if (this.cache) {
				this.cache = null;
			}
			// does the option need to be removed?
			var remove = false;
			// get options
			var o = this.options;
			if (!!v) {
				// get number of options
				var oL = o.length;
				for (var i = oL - 1; i >= 0; i--) {
					if (v.constructor == RegExp) {
						if (o[i].value.match(v)) {
							remove = true;
						}
					} else {
						if (o[i].value == v) {
							remove = true;
						}
					}
					// if the option is only to be removed if selected
					if (remove && a[1] === true) {
						remove = o[i].selected;
					}
					if (remove) {
						o[i] = null;
					}
					remove = false;
				}
			} else {
				// only remove if selected?
				if (a[1] === true) {
					remove = o[index].selected;
				} else {
					remove = true;
				}
				if (remove) {
					this.remove(index);
				}
			}
		});
		return this;
	};
	$.fn.sortOptions = function (ascending) {
		var a = typeof (ascending) == "undefined" ? true : !!ascending;
		this.each(function () {
			if (this.nodeName.toLowerCase() != "select") {
				return;
			}
			// get options
			var o = this.options;
			// get number of options
			var oL = o.length;
			// create an array for sorting
			var sA = [];
			// loop through options, adding to sort array
			for (var i = 0; i < oL; i++) {
				sA[i] = {v:o[i].value, t:o[i].text};
			}
			// sort items in array
			sA.sort(function (o1, o2) {
					// option text is made lowercase for case insensitive sorting
				o1t = o1.t.toLowerCase(), o2t = o2.t.toLowerCase();
					// if options are the same, no sorting is needed
				if (o1t == o2t) {
					return 0;
				}
				if (a) {
					return o1t < o2t ? -1 : 1;
				} else {
					return o1t > o2t ? -1 : 1;
				}
			});
			// change the options to match the sort array
			for (var i = 0; i < oL; i++) {
				o[i].text = sA[i].t;
				o[i].value = sA[i].v;
			}
		});
		return this;
	};
	$.fn.selectOptions = function (value, clear) {
		var v = value;
		var vT = typeof (value);
		var c = clear || false;
	// has to be a string or regular expression (object in IE, function in Firefox)
		if (vT != "string" && vT != "function" && vT != "object") {
			return this;
		}
		this.each(function () {
			if (this.nodeName.toLowerCase() != "select") {
				return this;
			}
			// get options
			var o = this.options;
			// get number of options
			var oL = o.length;
			for (var i = 0; i < oL; i++) {
				if (v.constructor == RegExp) {
					if (o[i].value.match(v)) {
						o[i].selected = true;
					} else {
						if (c) {
							o[i].selected = false;
						}
					}
				} else {
					if (o[i].value == v) {
						o[i].selected = true;
					} else {
						if (c) {
							o[i].selected = false;
						}
					}
				}
			}
		});
		return this;
	};
	$.fn.copyOptions = function (to, which) {
		var w = which || "selected";
		if ($(to).size() == 0) {
			return this;
		}
		this.each(function () {
			if (this.nodeName.toLowerCase() != "select") {
				return this;
			}
			// get options
			var o = this.options;
			// get number of options
			var oL = o.length;
			for (var i = 0; i < oL; i++) {
				if (w == "all" || (w == "selected" && o[i].selected)) {
					$(to).addOption(o[i].value, o[i].text);
				}
			}
		});
		return this;
	};
	$.fn.containsOption = function (value, fn) {
		var found = false;
		var v = value;
		var vT = typeof (v);
		var fT = typeof (fn);
	// has to be a string or regular expression (object in IE, function in Firefox)
		if (vT != "string" && vT != "function" && vT != "object") {
			return fT == "function" ? this : found;
		}
		this.each(function () {
			if (this.nodeName.toLowerCase() != "select") {
				return this;
			}
			// option already found
			if (found && fT != "function") {
				return false;
			}
			// get options
			var o = this.options;
			// get number of options
			var oL = o.length;
			for (var i = 0; i < oL; i++) {
				if (v.constructor == RegExp) {
					if (o[i].value.match(v)) {
						found = true;
						if (fT == "function") {
							fn.call(o[i], i);
						}
					}
				} else {
					if (o[i].value == v) {
						found = true;
						if (fT == "function") {
							fn.call(o[i], i);
						}
					}
				}
			}
		});
		return fT == "function" ? this : found;
	};
	$.fn.selectedValues = function () {
		var v = [];
		this.find("option:selected").each(function () {
			v[v.length] = this.value;
		});
		return v;
	};
})(jQuery);

