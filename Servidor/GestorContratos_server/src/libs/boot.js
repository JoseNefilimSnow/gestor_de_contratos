module.exports = app => {
  app.db.sequelize.sync().then(() => {
    app.listen(app.get('port'), () => {
      console.log('Infiltrado, escuchando y recolectando desde el puerto:', app.get('port'));
    });
  });
}