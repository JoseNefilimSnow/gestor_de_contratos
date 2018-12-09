module.exports = (sequelize, DataType) => {
  const comprenden = sequelize.define("comprenden", {
    id: {
      type: DataType.INTEGER,
      primaryKey: true,
      autoIncrement: true
    }
  }, {
    timestamps: false,
    freezeTableName: true
  });
  comprenden.associate = (models) => {
    comprenden.belongsTo(models.contratos);
    comprenden.belongsTo(models.servicios);
  };
  return comprenden;
};