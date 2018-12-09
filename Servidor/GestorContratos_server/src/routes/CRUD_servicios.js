module.exports = app => {

  const servicios = app.db.models.servicios;

  app.route('/servicios')
    .get((req, res) => {
      servicios.findAll({})
        .then(result => res.json(result))
        .catch(error => {
          res.status(412).json({
            msg: error.message
          });
        });
    })
    .post((req, res) => {
      servicios.create(req.body)
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
  app.route('/servicios/getById/:id')
    .get((req, res) => {
      servicios.findOne({
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
      servicios.update(req.body, {
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
      servicios.destroy({
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
  app.route('/servicios/getByName/:nombre')
    .get((req, res) => {
      servicios.findOne({
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
      servicios.update(req.body, {
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
      servicios.destroy({
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
  app.route('/servicios/getByIdCategoria/:categoriaid')
    .get((req, res) => {
      servicios.findOne({
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
      servicios.update(req.body, {
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
      servicios.destroy({
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