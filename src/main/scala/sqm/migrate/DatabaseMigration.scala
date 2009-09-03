package sqm.migrate

trait DatabaseMigration {
  import com.novocode.squery.session.SessionFactory

  /** The version of this migration. */
  def version: Long

  /** An optional description of the migration. */
  def description: Option[String] = None

  /**
   * Applies the changes in this migration by moving the database version of to this version.
   * Requires an implicit SessionFactory to be defined in scope.
   */
  def up(sf: SessionFactory): Unit

  /**
   * Rolls back the changes in this migration unapplying the changes in up.
   * Requires an implicit SessionFactory to be defined in scope.
   */
  def down(sf: SessionFactory): Unit

  final def upWithTransaction(implicit sf: SessionFactory) {
    sf.withTransaction {
      up(sf)
    }
  }

  final def downWithTransaction(implicit sf: SessionFactory) {
    sf.withTransaction {
      down(sf)
    }
  }
}

import com.novocode.squery.combinator.Table

object Version extends Table[(Int, Long, String)]("db_version") {
  def id = column[Int]("id", O.AutoInc, O.NotNull)
  def version = column[Long]("version",  O.NotNull)
  def description = column[String]("description")
  def * = id ~ version ~ description
}

object DatabaseMigration0000 extends DatabaseMigration {
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
