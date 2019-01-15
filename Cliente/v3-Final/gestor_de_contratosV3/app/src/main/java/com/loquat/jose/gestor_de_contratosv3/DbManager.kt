package com.loquat.jose.gestor_de_contratosv3

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.widget.Toast

class DbManager {

    /**
     * Aqui se define la base de datos SQLITE
     */
    val dbName = "subcontrataSQLITE"
    //Tabla empresa
    val TABLE_EMPRESAS = "empresas"
    val COL_EMP_ID = "id"
    val COL_EMP_NOMBRE = "nombre"
    val COL_EMP_DIR = "direccion"
    val COL_EMP_TEL = "telefono"
    val COL_EMP_CORREO = "correo"

    //Tabla contratos
    val TABLE_CONTRATOS = "contratos"
    val COL_CON_ID = "id"
    val COL_CON_TIPO = "tipo"
    val COL_CON_FECHA_INICIO = "fecha_inicio"
    val COL_CON_FECHA_FIN = "fecha_fin"
    val COL_CON_ID_EMPRESA = "empresaId"

    //Tabla categorias
    val TABLE_CATEGORIAS = "categorias"
    val COL_CAT_ID = "id"
    val COL_CAT_NOMBRE = "nombre"
    val COL_CAT_DES = "descripcion"

    //Tabla servicios
    val TABLE_SERVICIOS = "servicios"
    val COL_SER_ID = "id"
    val COL_SER_NOMBRE = "nombre"
    val COL_SER_PRECIO = "precio"
    val COL_SER_ID_CATEGORIAS = "categoriaId"

    //Tabla comprenden
    val TABLE_COMPRENDEN = "comprenden"
    val COL_COM_ID = "id"
    val COL_COM_ID_CONTRATO = "contratoId"
    val COL_COM_ID_SERVICIO = "servicioId"

    var dbVersion = 1

    val createTableEmpresas =
        "CREATE TABLE " + TABLE_EMPRESAS + "(" +
                COL_EMP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_EMP_NOMBRE + " VARCHAR(255) NOT NULL, " +
                COL_EMP_DIR + " VARCHAR(255) NOT NULL, " +
                COL_EMP_TEL + " VARCHAR(255) NOT NULL, " +
                COL_EMP_CORREO + " VARCHAR(255) NOT NULL);"
    val createTableContrato =
        "CREATE TABLE " + TABLE_CONTRATOS + "(" +
                COL_CON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_CON_TIPO + " VARCHAR(255) NOT NULL, " +
                COL_CON_FECHA_INICIO + " DATE NOT NULL, " +
                COL_CON_FECHA_FIN + " DATE NOT NULL, " +
                COL_CON_ID_EMPRESA + " INTEGER NOT NULL);"
    val createTableCategorias =
        "CREATE TABLE " + TABLE_CATEGORIAS + "(" +
                COL_CAT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_CAT_NOMBRE + " VARCHAR(255) NOT NULL, " +
                COL_CAT_DES + " DATE NOT NULL);"
    val createTableServicios =
        "CREATE TABLE " + TABLE_SERVICIOS + "(" +
                COL_SER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_SER_NOMBRE + " VARCHAR(255) NOT NULL, " +
                COL_SER_PRECIO + " DOUBLE NOT NULL, " +
                COL_SER_ID_CATEGORIAS + " INTEGER NOT NULL);"
    val createTableComprenden =
        "CREATE TABLE " + TABLE_COMPRENDEN + "(" +
                COL_COM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_COM_ID_CONTRATO + " INTEGER NOT NULL, " +
                COL_COM_ID_SERVICIO + " INTEGER NOT NULL);"

    var sqlDB: SQLiteDatabase? = null

    constructor(context: Context) {
        var db = DatabaseHelperNotes(context)
        sqlDB = db.writableDatabase
    }

    inner class DatabaseHelperNotes : SQLiteOpenHelper {
        var context: Context? = null

        constructor(context: Context) : super(context, dbName, null, dbVersion) {
            this.context = context
        }

        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(createTableEmpresas)
            db!!.execSQL(createTableContrato)
            db!!.execSQL(createTableCategorias)
            db!!.execSQL(createTableServicios)
            db!!.execSQL(createTableComprenden)
            Toast.makeText(this.context, "Base de datos creada...", Toast.LENGTH_SHORT).show()

        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("Drop table if Exists" + createTableEmpresas)
            db!!.execSQL("Drop table if Exists" + createTableContrato)
            db!!.execSQL("Drop table if Exists" + createTableCategorias)
            db!!.execSQL("Drop table if Exists" + createTableServicios)
            db!!.execSQL("Drop table if Exists" + createTableComprenden)
        }

    }

    //Inserts

    fun insertEmpresas(values: ContentValues): Long {
        val ID = sqlDB!!.insert(TABLE_EMPRESAS, "", values)
        return ID
    }
    fun insertContrato(values: ContentValues): Long {
        val ID = sqlDB!!.insert(TABLE_CONTRATOS, "", values)
        return ID
    }
    fun insertCategorias(values: ContentValues): Long {
        val ID = sqlDB!!.insert(TABLE_CATEGORIAS, "", values)
        return ID
    }
    fun insertServicios(values: ContentValues): Long {
        val ID = sqlDB!!.insert(TABLE_SERVICIOS, "", values)
        return ID
    }
    fun insertComprenden(values: ContentValues): Long {
        val ID = sqlDB!!.insert(TABLE_COMPRENDEN, "", values)
        return ID
    }

    //Selects

    fun QueryEmpresas(projection: Array<String>, selection: String, selectionArgs: Array<String>, sorOrder: String): Cursor? {
        val qb = SQLiteQueryBuilder()
        qb.tables = TABLE_EMPRESAS
        val cursor = qb.query(sqlDB, projection, selection, selectionArgs, null, null, sorOrder)
        return cursor
    }
    fun QueryContratos(projection: Array<String>, selection: String, selectionArgs: Array<String>, sorOrder: String): Cursor? {
        val qb = SQLiteQueryBuilder()
        qb.tables = TABLE_CONTRATOS
        val cursor = qb.query(sqlDB, projection, selection, selectionArgs, null, null, sorOrder)
        return cursor
    }
    fun QueryCategorias(projection: Array<String>, selection: String, selectionArgs: Array<String>, sorOrder: String): Cursor? {
        val qb = SQLiteQueryBuilder()
        qb.tables = TABLE_CATEGORIAS
        val cursor = qb.query(sqlDB, projection, selection, selectionArgs, null, null, sorOrder)
        return cursor
    }
    fun QueryServicios(projection: Array<String>, selection: String, selectionArgs: Array<String>, sorOrder: String): Cursor? {
        val qb = SQLiteQueryBuilder()
        qb.tables = TABLE_SERVICIOS
        val cursor = qb.query(sqlDB, projection, selection, selectionArgs, null, null, sorOrder)
        return cursor
    }
    fun QueryComprenden(projection: Array<String>, selection: String, selectionArgs: Array<String>, sorOrder: String): Cursor? {
        val qb = SQLiteQueryBuilder()
        qb.tables = TABLE_COMPRENDEN
        val cursor = qb.query(sqlDB, projection, selection, selectionArgs, null, null, sorOrder)
        return cursor
    }

    //Deletes

    fun deleteEmpresas(selection: String, selectionArgs: Array<String>): Int {
        val count = sqlDB!!.delete(TABLE_EMPRESAS, selection, selectionArgs)
        return count
    }
    fun deleteAllEmpresas(){
        sqlDB!!.execSQL("delete from "+ TABLE_EMPRESAS);
    }
    fun deleteContratos(selection: String, selectionArgs: Array<String>): Int {
        val count = sqlDB!!.delete(TABLE_CONTRATOS, selection, selectionArgs)
        return count
    }
    fun deleteCategorias(selection: String, selectionArgs: Array<String>): Int {
        val count = sqlDB!!.delete(TABLE_CATEGORIAS, selection, selectionArgs)
        return count
    }
    fun deleteServicios(selection: String, selectionArgs: Array<String>): Int {
        val count = sqlDB!!.delete(TABLE_SERVICIOS, selection, selectionArgs)
        return count
    }
    fun deleteComprenden(selection: String, selectionArgs: Array<String>): Int {
        val count = sqlDB!!.delete(TABLE_COMPRENDEN, selection, selectionArgs)
        return count
    }

    //Updates

    fun updateEmpresas(values: ContentValues, selection: String, selectionArgs: Array<String>): Int {
        val count = sqlDB!!.update(TABLE_EMPRESAS, values, selection, selectionArgs)
        return count
    }
    fun updateContratos(values: ContentValues, selection: String, selectionArgs: Array<String>): Int {
        val count = sqlDB!!.update(TABLE_CONTRATOS, values, selection, selectionArgs)
        return count
    }
    fun updateCategorias(values: ContentValues, selection: String, selectionArgs: Array<String>): Int {
        val count = sqlDB!!.update(TABLE_CATEGORIAS, values, selection, selectionArgs)
        return count
    }
    fun updateServicios(values: ContentValues, selection: String, selectionArgs: Array<String>): Int {
        val count = sqlDB!!.update(TABLE_SERVICIOS, values, selection, selectionArgs)
        return count
    }
    fun updateComprenden(values: ContentValues, selection: String, selectionArgs: Array<String>): Int {
        val count = sqlDB!!.update(TABLE_COMPRENDEN, values, selection, selectionArgs)
        return count
    }
}