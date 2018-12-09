module.exports = (sequelize, DataType) => {
  const contratos = sequelize.define("contratos", {
    id: {
      type: DataType.INTEGER,
      primaryKey: true,
      autoIncrement: true
    },
    tipo: {
      type: DataType.STRING,
      allowNull: false,
      validate: {
        notEmpty: true
      }
    },
    fecha_inicio: {
      type: DataType.DATEONLY,
      allowNull: false
    },
    fecha_fin: {
      type: DataType.DATEONLY,
      allowNull: false
    }
  }, {
    timestamps: false,
    freezeTableName: true
  });
  contratos.associate = (models) => {
    contratos.belongsTo(models.empresas, {
      foreignkey: 'id_empresa'
    });
    contratos.hasMany(models.comprenden);
  };
  return contratos;
};