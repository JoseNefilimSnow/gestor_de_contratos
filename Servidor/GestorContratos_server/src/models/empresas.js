module.exports = (sequelize, DataType) => {
  const empresas = sequelize.define('empresas', {
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
    direccion: {
      type: DataType.STRING,
      allowNull: false,
      validate: {
        notEmpty: true
      },
    },
    telefono: {
      type: DataType.STRING,
      allowNull: false,
      validate: {
        notEmpty: true
      },
    },
    correo: {
      type: DataType.STRING,
      allowNull: false,
      validate: {
        notEmpty: true
      }
    }
  }, {
    timestamps: false,
    freezeTableName: true
  });
  empresas.associate = (models) => {
    empresas.hasMany(models.contratos);
  };
  return empresas;
};