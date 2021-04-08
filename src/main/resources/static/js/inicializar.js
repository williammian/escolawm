$(function() {
	
	$( document ).ready(function() {
		$('.datepicker').pickadate({
		    selectMonths: true, 
		    selectYears: 20,
		    
		    format: 'dd/mm/yyyy',
		    
		    monthsFull: ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'],
			monthsShort: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
			weekdaysFull: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sáb'],
			weekdaysShort: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sáb'],
			showMonthsShort: false,
			showWeekdaysFull: true,
			
			today: 'Hoje',
			clear: 'Limpar',
			close: 'Fechar',
			
		  });
		$('select').material_select();
	});
})