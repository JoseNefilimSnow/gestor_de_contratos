module.exports = (sequelize, DataType) => {
  const categorias = sequelize.define('categorias', {
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
    descripcion: {
      type: DataType.STRING,
      allowNull: true
    },
  }, {
    timestamps: false,
    freezeTableName: true
  });
  categorias.associate = (models) => {
    categorias.hasMany(models.servicios);
  };
  return categorias;
};