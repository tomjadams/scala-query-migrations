package sqm.migrate

import com.novocode.squery.combinator.Table

object Version extends Table[(Int, Long, String)]("db_version") {
  def id = column[Int]("id", O.AutoInc, O.NotNull)
  def version = column[Long]("version",  O.NotNull)
  def description = column[String]("description")
  def * = id ~ version ~ description
}

object CreateVersionTableMigration extends DatabaseMigration {
  import com.novocode.squery.combinator.Implicit._
  import com.novocode.squery.session.SessionFactory, SessionFactory._
  import com.novocode.squery.simple.StaticQueryBase._

  override def version = 0
  override def description = Some("Create db_version table.")

  override def up(sf: SessionFactory) {
    Version.createTable
  }

  override def down(sf: SessionFactory) {
    updateNA("drop table db_version")
  }
}
