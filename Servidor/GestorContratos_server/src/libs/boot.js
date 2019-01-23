module.exports = app => {
  app.db.sequelize.sync().then(() => {
    app.listen(app.get('port'), () => {
      console.log('Bip Bop (puerto):', app.get('port'));
    });
  });
}