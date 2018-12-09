module.exports = app => {

  const categorias = app.db.models.categorias;

  app.route('/categorias')
    .get((req, res) => {
      categorias.findAll({})
        .then(result => res.json(result))
        .catch(error => {
          res.status(412).json({
            msg: error.message
          });
        });
    })
    .post((req, res) => {
      categorias.create(req.body)
        .then(result => res.json(result))
        .catch(error => {
          res.status(412).json({
            msg: error.message
          });
        });
      res.json({
        status: 'received'
      });
    });
  app.route('/categorias/getById/:id')
    .get((req, res) => {
      categorias.findOne({
          where: req.params
        })
        .then(result => res.json(result))
        .catch(error => {
          res.status(412).json({
            msg: error.message
          });
        });
    })
    .put((req, res) => {
      categorias.update(req.body, {
          where: req.params
        })
        .then(result => res.sendStatus(204))
        .catch(error => {
          res.status(412).json({
            msg: error.message
          });
        });
      res.json({
        status: 'received'
      });
    })
    .delete((req, res) => {
      categorias.destroy({
          where: req.params
        })
        .then(result => res.sendStatus(204))
        .catch(error => {
          res.status(412).json({
            msg: error.message
          });
        });
      res.json({
        status: 'received'
      });
    });
  app.route('/categorias/getByName/:nombre')
    .get((req, res) => {
      categorias.findOne({
          where: req.params
        })
        .then(result => res.json(result))
        .catch(error => {
          res.status(412).json({
            msg: error.message
          });
        });
    })
    .put((req, res) => {
      categorias.update(req.body, {
          where: req.params
        })
        .then(result => res.sendStatus(204))
        .catch(error => {
          res.status(412).json({
            msg: error.message
          });
        });
      res.json({
        status: 'received'
      });
    })
    .delete((req, res) => {
      categorias.destroy({
          where: req.params
        })
        .then(result => res.sendStatus(204))
        .catch(error => {
          res.status(412).json({
            msg: error.message
          });
        });
      res.json({
        status: 'received'
      });
    });
}