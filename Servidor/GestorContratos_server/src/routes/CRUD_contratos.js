module.exports = app => {

  const contratos = app.db.models.contratos;

  app.route('/contratos')
    .get((req, res) => {
      contratos.findAll({})
        .then(result => res.json(result))
        .catch(error => {
          res.status(412).json({
            msg: error.message
          });
        });
    })
    .post((req, res) => {
      contratos.create(req.body)
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
  app.route('/contratos/getById/:id')
    .get((req, res) => {
      contratos.findOne({
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
      contratos.update(req.body, {
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
      contratos.destroy({
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
  app.route('/contratos/getByIdEmpresa/:empresaId')
    .get((req, res) => {
      contratos.findAll({
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
      contratos.update(req.body, {
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
      contratos.destroy({
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