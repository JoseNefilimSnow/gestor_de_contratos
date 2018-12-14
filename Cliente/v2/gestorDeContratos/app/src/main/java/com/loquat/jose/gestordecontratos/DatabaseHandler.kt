package com.loquat.jose.gestordecontratos

import android.app.DownloadManager
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.widget.Toast





/**
 * Aqui se define la base de datos SQLITE
 */
val DATABASE_NAME = "subcontrataSQLITE"
var version = 0
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

class DatabaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        //CREAR TABLA EMPRESA
        val createTableEmpresas =
            "CREATE TABLE" + TABLE_EMPRESAS + "(" +
                    COL_EMP_ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COL_EMP_NOMBRE + "VARCHAR(255) NOT NULL," +
                    COL_EMP_DIR + "VARCHAR(255) NOT NULL," +
                    COL_EMP_TEL + "VARCHAR(255) NOT NULL," +
                    COL_EMP_CORREO + "VARCHAR(255) NOT NULL"
        db!!.execSQL(createTableEmpresas)
        //CREAR TABLA CONTRATO
        val createTableContrato =
            "CREATE TABLE" + TABLE_CONTRATOS + "(" +
                    COL_CON_ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COL_CON_TIPO + "VARCHAR(255) NOT NULL," +
                    COL_CON_FECHA_INICIO + "DATE NOT NULL," +
                    COL_CON_FECHA_FIN + "DATE NOT NULL," +
                    COL_CON_ID_EMPRESA + "INTEGER NOT NULL"
        db!!.execSQL(createTableContrato)
        //CREAR TABLA CATEGORIAS
        val createTableCategorias =
            "CREATE TABLE" + TABLE_CATEGORIAS + "(" +
                    COL_CAT_ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COL_CAT_NOMBRE + "VARCHAR(255) NOT NULL," +
                    COL_CAT_DES + "DATE NOT NULL,"
        db!!.execSQL(createTableCategorias)
        //CREAR TABLA SERVICIOS
        val createTableServicios =
            "CREATE TABLE" + TABLE_SERVICIOS + "(" +
                    COL_SER_ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COL_SER_NOMBRE + "VARCHAR(255) NOT NULL," +
                    COL_SER_PRECIO + "DOUBLE NOT NULL," +
                    COL_SER_ID_CATEGORIAS + "INTEGER NOT NULL"
        db!!.execSQL(createTableServicios)
        //CREAR TABLA COMPRENDEN
        val createTableComprenden =
            "CREATE TABLE" + TABLE_COMPRENDEN + "(" +
                    COL_COM_ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COL_COM_ID_CONTRATO + "INTEGER NOT NULL," +
                    COL_COM_ID_SERVICIO + "INTEGER NOT NULL,"
        db!!.execSQL(createTableComprenden)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("Drop table if Exists"+ DATABASE_NAME)
    }

    fun insertDataContratos(contrato: Contrato) {
        val db = this.writableDatabase
        var content = ContentValues()
        content.put(COL_CON_TIPO,contrato.tipo)
        content.put(COL_CON_FECHA_INICIO,contrato.fecha_ini)
        content.put(COL_CON_FECHA_FIN,contrato.fecha_fin)
        content.put(COL_CON_ID_EMPRESA,contrato.idEmpresa)
        var res = db.insert(TABLE_CONTRATOS,null,content)
        if(res == -1.toLong()){
            Toast.makeText(context,"No insertado",Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context,"Insertado",Toast.LENGTH_SHORT).show()
        }
    }
    fun insertDataEmpresas(empresa: Empresa) {
        val db = this.writableDatabase
        var content = ContentValues()
        content.put(COL_EMP_NOMBRE,empresa.nombre)
        content.put(COL_EMP_DIR,empresa.direccion)
        content.put(COL_EMP_TEL,empresa.tel)
        content.put(COL_EMP_CORREO,empresa.correo)
        var res = db.insert(TABLE_EMPRESAS,null,content)
        if(res == -1.toLong()){
            Toast.makeText(context,"No insertado",Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context,"Insertado",Toast.LENGTH_SHORT).show()
        }
    }

    fun Query(projection:Array<String>,selection:String,selectionArgs:Array<String>,sorOrder:String): Cursor? {
        val db = this.writableDatabase
        val qb=SQLiteQueryBuilder();
        qb.tables=TABLE_CONTRATOS
        val cursor = qb.query(db,projection,selection,selectionArgs,null,null,sorOrder)
        return cursor
    }
    fun delete(selection:String,selectionArgs:Array<String>):Int{
        val db = this.writableDatabase
        val count= db!!.delete(TABLE_CONTRATOS,selection,selectionArgs)
        return count
    }
    fun update(content:ContentValues,selection:String,selectionArgs:Array<String>):Int{
        val db = this.writableDatabase
        val count= db!!.update(TABLE_CONTRATOS,content,selection,selectionArgs)
        return count
    }

}