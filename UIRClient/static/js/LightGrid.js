/* LightGrid.js */
// Скрипт изменения ширины колонок - начало
	rsdhLightGrid_colToRes = ''; // ID колонки, ширина которой изменяется в данный момент
	rsdhLightGrid_startX = 0; // Начальная позиция курсора
	
	function rsdhLightGrid_startResizing(element, event){
		rsdhLightGrid_colToRes = element;
		rsdhLightGrid_startX = event.clientX - parseInt(document.getElementById(rsdhLightGrid_colToRes).width);
	}
	
	function rsdhLightGrid_resizeColl(element, event){
		if (rsdhLightGrid_colToRes != '') {
			var addWidth = event.clientX - rsdhLightGrid_startX;
			if (addWidth < 14) addWidth = 14; // Длинна 2 символов
			document.getElementById(rsdhLightGrid_colToRes).width = addWidth;
			document.getElementById(rsdhLightGrid_colToRes + '_b').width = addWidth;
			document.getElementById(rsdhLightGrid_colToRes + '_f').width = addWidth;
		}
	}
	
	function rsdhLightGrid_stopResizing(){
		rsdhLightGrid_colToRes = '';
		rsdhLightGrid_startX = 0;
	}
// Скрипт изменения ширины колонок - конец