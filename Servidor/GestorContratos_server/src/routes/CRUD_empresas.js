module.exports = app => {

  const empresas = app.db.models.empresas;

  app.route('/empresas')
    .get((req, res) => {
      empresas.findAll({})
        .then(result => res.json(result))
        .catch(error => {
          res.status(412).json({
            msg: error.message
          });
        });
    })
    .post((req, res) => {
      empresas.create(req.body)
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
  app.route('/empresas/getById/:id')
    .get((req, res) => {
      empresas.findOne({
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
      empresas.update(req.body, {
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
      empresas.destroy({
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
  app.route('/empresas/getByName/:nombre')
    .get((req, res) => {
      empresas.findOne({
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
      empresas.update(req.body, {
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
      empresas.destroy({
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