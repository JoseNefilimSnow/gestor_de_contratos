module.exports = (sequelize, DataType) => {
  const servicios = sequelize.define("servicios", {
    id: {
      type: DataType.INTEGER,
      primaryKey: true,
      autoIncrement: true
    },
    nombre: {
      type: DataType.STRING,
      allowNull: false,
      validate: {
        notEmpty: true
      }
    },
    precio: {
      type: DataType.DOUBLE,
      allowNull: false
    }
  }, {
    timestamps: false,
    freezeTableName: true
  });
  servicios.associate = (models) => {
    servicios.belongsTo(models.categorias);
    servicios.belongsToMany(models.contratos, {
      through: 'comprenden'
    });
  };
  return servicios;
};