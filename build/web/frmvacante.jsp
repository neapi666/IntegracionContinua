<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">    
    <meta name="viewport" content="width=device-width, initial-scale=1">    
    <link rel="icon" href="favicon.ico">
    <title>Departamento de Recursos Humanos - My Company</title>
    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom styles for this template -->
    <link href="css/justified-nav.css" rel="stylesheet">
    <script src='tinymce/tinymce.min.js'></script>

    <script>

      tinymce.init({
        selector: '#detalle',
        plugins: "textcolor, table",
        /* https://www.tinymce.com/docs/advanced/editor-control-identifiers/#toolbarcontrols */
        toolbar: "styleselect | undo redo | removeformat | bold italic underline | table \n\
                  aligncenter alignjustify  | bullist numlist outdent indent | link | print | \n\
                  fontselect fontsizeselect forecolor backcolor",
      });

    </script>
  </head>

  <body>
      
    <div class="container">
      <!-- The justified navigation menu is meant for single line per list item.
           Multiple lines will require custom code not provided by Bootstrap. -->
      <div class="masthead">
        <h3 class="text-muted">My Company - Administraci�n</h3>
        <nav>
          <ul class="nav nav-justified">
            <li><a href="admin?action=crear">Crear Vacante</a></li>            
            <li><a href="vacante?action=lista">Vacantes</a></li>                        
            <li><a href="admin?action=logout">Salir</a></li>            
          </ul>
        </nav>
      </div>

      <br>    
      <div class="panel panel-default">
        <div class="panel-heading">
          <h3 class="panel-title">Crear Vacante</h3>
        </div>
        <div class="panel-body">
         <form action="vacante" method="post">
            <div class="form-group">
              <label for="nombre">Nombre</label>
              <input type="text" class="form-control" name="nombre" required id="nombre" value="" placeholder="Escriba el nombre la vacante">
            </div>                   
            <div class="form-group">
              <label for="descripcion">Descripci�n</label>
              <textarea class="form-control" name="descripcion" id="descripcion" required rows="3" placeholder="Escribe una descripci�n de la vacante"></textarea>
            </div>
            <div class="form-group">
              <label for="detalle">Escriba los detalles</label>
              <textarea id="detalle" name="detalle" rows="10"></textarea>
            </div>
            <button type="submit" class="btn btn-default" >Guardar</button>
          </form>
        </div>
      </div>
            
      <!-- Site footer -->
      <footer class="footer">
        <p>&copy; 2016 My Company, Inc.</p>
      </footer>
    </div> <!-- /container -->
  </body>
</html>
