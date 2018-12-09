module.exports = app => {


  app.route('/')
    .get((req, res) => {
      res.json('Las rutas disponibles son : categorias , comprenden, contratos, empresas, servicios ');
    });
}