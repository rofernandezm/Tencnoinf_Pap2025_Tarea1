let lastScrollTop = 0;
const navbar = document.querySelector('.navbar');

window.addEventListener('scroll', () => {
	if (window.innerWidth < 992) {

		const currentScroll = window.scrollY;

		if (currentScroll > lastScrollTop && currentScroll > 50) {
			navbar.classList.add('hide');
		} else {
			navbar.classList.remove('hide');
		}

		lastScrollTop = currentScroll <= 0 ? 0 : currentScroll;
	}
});

(function() {
	const form = document.getElementById('searchForm');
	const input = document.getElementById('searchInput');
	const clear = document.getElementById('searchClear');

	function toggle() { clear.hidden = !input.value.trim(); }
	input.addEventListener('input', toggle);
	toggle(); // estado inicial

	clear.addEventListener('click', () => {
		input.value = '';
		toggle();
		form.submit(); // recarga lista “limpia”
	});
})();
