module.exports = app => {

  const comprenden = app.db.models.comprenden;

  app.route('/comprenden')
    .get((req, res) => {
      comprenden.findAll({})
        .then(result => res.json(result))
        .catch(error => {
          res.status(412).json({
            msg: error.message
          });
        });
    })
    .post((req, res) => {
      comprenden.create(req.body)
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
  app.route('/comprenden/getById/:id')
    .get((req, res) => {
      comprenden.findOne({
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
      comprenden.update(req.body, {
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
      comprenden.destroy({
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
  app.route('/comprenden/getByIdContrato/:contratoId')
    .get((req, res) => {
      comprenden.findAll({
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
      comprenden.update(req.body, {
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
      comprenden.destroy({
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
  app.route('/comprenden/getByIdServicio/:servicioId')
    .get((req, res) => {
      comprenden.findAll({
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
      comprenden.update(req.body, {
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
      comprenden.destroy({
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