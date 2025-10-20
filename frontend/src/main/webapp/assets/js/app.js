// --- Navbar hide on scroll (solo móviles) ---
(function () {
  var lastScrollTop = 0;
  var navbar = document.querySelector('.navbar');

  window.addEventListener('scroll', function () {
    if (window.innerWidth < 992 && navbar) {
      var currentScroll = window.scrollY || window.pageYOffset || 0;

      if (currentScroll > lastScrollTop && currentScroll > 50) {
        if (!navbar.classList.contains('hide')) navbar.classList.add('hide');
      } else {
        navbar.classList.remove('hide');
      }
      lastScrollTop = currentScroll <= 0 ? 0 : currentScroll;
    }
  });
})();

// --- Searchbar: clear button + datalist a partir de 2 char ---
(function () {
  var form  = document.getElementById('searchForm');
  var input = document.getElementById('searchInput');

  // si no hay buscador en esta página, salimos
  if (!input) return;

  // id del datalist (por defecto: actSuggestions)
  var listId = input.getAttribute('data-list-id') || 'actSuggestions';


  function updateDatalistBinding() {
    var hasMinChars = (input.value || '').length >= 0; // en caso de tener demasiadas actividades conviene subirlo a 2
    if (hasMinChars) {
      if (input.getAttribute('list') !== listId) input.setAttribute('list', listId);
    } else {
      input.removeAttribute('list'); // evita mostrar todas las opciones al enfocar
    }
  }

  // eventos
  input.addEventListener('input', function () {
    updateDatalistBinding();
  });
  input.addEventListener('focus', updateDatalistBinding);

  // estado inicial
  updateDatalistBinding();
})();
